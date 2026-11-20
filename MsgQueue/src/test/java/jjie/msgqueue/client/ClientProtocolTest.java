package jjie.msgqueue.client;

import jjie.msgqueue.common.Request;
import jjie.msgqueue.common.Response;
import jjie.msgqueue.common.arguments.BasicArguments;
import jjie.msgqueue.common.arguments.ExchangeDeclareArguments;
import jjie.msgqueue.common.arguments.QueueDeclareArguments;
import jjie.msgqueue.common.returns.BasicReturns;
import jjie.msgqueue.common.returns.SubscribeReturns;
import jjie.msgqueue.common.tools.BinaryTool;
import jjie.msgqueue.server.core.BasicProperties;
import jjie.msgqueue.server.core.ExchangeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static jjie.msgqueue.client.ClientTestSupport.await;
import static jjie.msgqueue.client.ClientTestSupport.start;
import static org.junit.jupiter.api.Assertions.*;

class ClientProtocolTest {
    @Test
    void controlReplyPreservesChannelIdRequestIdAndResultThroughSerialization() throws Exception {
        BasicReturns response = new BasicReturns();
        response.setChannelId("C-test");
        response.setRid("R-test");
        response.setOk(true);

        BasicReturns decoded = assertInstanceOf(BasicReturns.class, BinaryTool.from(BinaryTool.toBinary(response)));
        assertEquals("C-test", decoded.getChannelId());
        assertEquals("R-test", decoded.getRid());
        assertTrue(decoded.isOk());
    }

    @Test
    void deliveryReplyPreservesInheritedFieldsAndMessageThroughSerialization() throws Exception {
        byte[] body = {0, 42, -1};
        BasicProperties properties = new BasicProperties();
        properties.setRoutingKey("jobs");
        SubscribeReturns response = new SubscribeReturns();
        response.setChannelId("C-test");
        response.setRid("");
        response.setOk(true);
        response.setConsumerTag("C-test");
        response.setBasicProperties(properties);
        response.setBody(body);

        SubscribeReturns decoded = assertInstanceOf(SubscribeReturns.class, BinaryTool.from(BinaryTool.toBinary(response)));
        assertAll(
                () -> assertEquals("C-test", decoded.getChannelId()),
                () -> assertEquals("", decoded.getRid()),
                () -> assertTrue(decoded.isOk()),
                () -> assertEquals("C-test", decoded.getConsumerTag()),
                () -> assertEquals(properties, decoded.getBasicProperties()),
                () -> assertArrayEquals(body, decoded.getBody()));
    }

    @Test
    void rejectedCreationReturnsNullAndRemovesPendingChannel() throws Exception {
        try (Peer peer = new Peer()) {
            Future<Channel> creating = start(peer.client::createChannel);
            BasicArguments request = peer.request(0x1);
            peer.reply(0x1, request, false);
            assertNull(await("服务端拒绝创建 channel", creating));
            assertTrue(peer.client.getChannelMap().isEmpty());
        }
    }

    @Test
    void failedCreateWriteDoesNotLeavePendingChannel() throws Exception {
        try (Peer peer = new Peer()) {
            peer.client.setDataOutputStream(new DataOutputStream(new OutputStream() {
                @Override
                public void write(int value) throws IOException {
                    throw new IOException("模拟发送失败");
                }
            }));

            IOException failure = assertThrows(IOException.class, peer.client::createChannel);
            assertEquals("模拟发送失败", failure.getMessage());
            assertTrue(peer.client.getChannelMap().isEmpty(), "创建请求发送失败后，连接仍持有未创建的 channel");
        }
    }

    @Test
    void rejectedDeclarationsReturnFalseAndSendRequestedOptions() throws Exception {
        try (Peer peer = new Peer()) {
            Channel channel = peer.openChannel();
            Map<String, Object> options = Map.of("test-option", 17);
            Future<Boolean> declaringExchange = start(() ->
                    channel.exchangeDeclare("events", ExchangeType.TOPIC, true, true, options));
            ExchangeDeclareArguments exchange = assertInstanceOf(ExchangeDeclareArguments.class, peer.request(0x3));
            assertEquals(channel.getChannelId(), exchange.getChannelId());
            assertEquals("events", exchange.getExchangeName());
            assertEquals(ExchangeType.TOPIC, exchange.getExchangeType());
            assertTrue(exchange.isDurable());
            assertTrue(exchange.isAutoDelete());
            assertEquals(options, exchange.getArguments());
            peer.reply(0x3, exchange, false);
            assertFalse(await("交换机声明被拒绝", declaringExchange));

            Future<Boolean> declaringQueue = start(() -> channel.queueDeclare("jobs", true, true, true, options));
            QueueDeclareArguments queue = assertInstanceOf(QueueDeclareArguments.class, peer.request(0x5));
            assertEquals(channel.getChannelId(), queue.getChannelId());
            assertEquals("jobs", queue.getQueueName());
            assertTrue(queue.isDurable());
            assertTrue(queue.isExclusive());
            assertTrue(queue.isAutoDelete());
            assertEquals(options, queue.getArguments());
            peer.reply(0x5, queue, false);
            assertFalse(await("队列声明被拒绝", declaringQueue));
        }
    }

    @Test
    void rejectedCloseReturnsFalseAndRetainsChannel() throws Exception {
        try (Peer peer = new Peer()) {
            Channel channel = peer.openChannel();
            Future<Boolean> closing = start(channel::close);
            BasicArguments request = peer.request(0x2);
            assertEquals(channel.getChannelId(), request.getChannelId());
            peer.reply(0x2, request, false);
            assertFalse(await("服务端拒绝关闭 channel", closing));
            assertSame(channel, peer.client.getChannelMap().get(channel.getChannelId()));
        }
    }

    @Test
    void outOfOrderRepliesAreMatchedToChannelAndRequestId() throws Exception {
        try (Peer peer = new Peer()) {
            Channel first = peer.openChannel();
            Channel second = peer.openChannel();
            Future<Boolean> deleteA = start(() -> first.queueDelete("a"));
            BasicArguments requestA = peer.request(0x6);
            Future<Boolean> deleteB = start(() -> first.queueDelete("b"));
            BasicArguments requestB = peer.request(0x6);
            Future<Boolean> deleteC = start(() -> second.queueDelete("c"));
            BasicArguments requestC = peer.request(0x6);
            assertEquals(first.getChannelId(), requestA.getChannelId());
            assertEquals(first.getChannelId(), requestB.getChannelId());
            assertEquals(second.getChannelId(), requestC.getChannelId());
            assertNotEquals(requestA.getRid(), requestB.getRid());

            peer.reply(0x6, requestC, true);
            assertTrue(await("第二个 channel 的删除结果", deleteC));
            peer.reply(0x6, requestB, false);
            assertFalse(await("第一个 channel 的第二个删除结果", deleteB));
            assertFalse(deleteA.isDone(), "收到其他请求的响应时，第一个请求应当继续等待");
            peer.reply(0x6, requestA, true);
            assertTrue(await("第一个 channel 的第一个删除结果", deleteA));
        }
    }

    @Test
    void fragmentedTcpResponseStillReturnsTrue() throws Exception {
        try (Peer peer = new Peer()) {
            Future<Channel> creating = start(peer.client::createChannel);
            BasicArguments request = peer.request(0x1);
            byte[] frame = frame(0x1, returns(request, true));
            // 先写完整帧头和一个字节。收到剩余字节前，客户端应保持等待。
            peer.output.write(frame, 0, 9);
            peer.output.flush();
            assertThrows(TimeoutException.class, () -> peer.client.readFailure.get(500, MILLISECONDS),
                    "完整帧到达前，客户端不应当把短读当作坏帧");
            peer.output.write(frame, 9, frame.length - 9);
            peer.output.flush();
            Channel channel = await("接收分段响应后创建 channel", creating);
            assertNotNull(channel);
            assertEquals(request.getChannelId(), channel.getChannelId());
        }
    }

    @Test
    void disconnectCompletesPendingRequestWithIOException() throws Exception {
        try (Peer peer = new Peer()) {
            Channel channel = peer.openChannel();
            Future<Boolean> deleting = start(() -> channel.queueDelete("jobs"));
            peer.request(0x6);
            peer.socket.shutdownOutput();
            assertInstanceOf(IOException.class, await("客户端收到 EOF", peer.client.readFailure));

            ExecutionException failure = assertThrows(ExecutionException.class, () -> deleting.get(3, SECONDS),
                    "收到 EOF 后，在途请求应当以异常结束");
            assertInstanceOf(IOException.class, failure.getCause());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0x6, 0xc})
    void frameForUnknownChannelDoesNotPreventNextValidReply(int type) throws Exception {
        try (Peer peer = new Peer()) {
            Channel channel = peer.openChannel();
            Future<Boolean> deleting = start(() -> channel.queueDelete("jobs"));
            BasicArguments request = peer.request(0x6);
            BasicReturns unknown = type == 0xc ? new SubscribeReturns() : new BasicReturns();
            unknown.setChannelId("closed-or-unknown-channel");
            unknown.setRid(request.getRid());
            unknown.setOk(false);
            peer.send(frame(type, unknown));
            peer.reply(0x6, request, true);
            assertTrue(await("未知 channel 帧之后的合法响应", deleting));
        }
    }

    @Test
    void replyBetweenMapLookupAndWaitStillCompletesRequest() throws Exception {
        try (Peer peer = new Peer()) {
            Channel initial = peer.openChannel();
            CountDownLatch lookupMissed = new CountDownLatch(1);
            CountDownLatch resumeLookup = new CountDownLatch(1);
            CountDownLatch replyDispatched = new CountDownLatch(1);
            Channel channel = new Channel(initial.getChannelId(), peer.client) {
                @Override
                public void putReturns(BasicReturns response) {
                    super.putReturns(response);
                    replyDispatched.countDown();
                }
            };
            channel.setBasicReturnsMap(new ConcurrentHashMap<>() {
                private final AtomicBoolean firstMiss = new AtomicBoolean(true);

                @Override
                public BasicReturns get(Object rid) {
                    BasicReturns response = super.get(rid);
                    if (response == null && firstMiss.compareAndSet(true, false)) {
                        lookupMissed.countDown();
                        awaitLatch(resumeLookup);
                    }
                    return response;
                }
            });
            peer.client.getChannelMap().put(channel.getChannelId(), channel);
            Future<Boolean> deleting = start(() -> channel.queueDelete("jobs"));
            try {
                BasicArguments request = peer.request(0x6);
                awaitLatch(lookupMissed);
                peer.reply(0x6, request, true);
                // 如果查询和 wait 共用锁，分发线程要等查询继续后才能通知。
                replyDispatched.await(500, MILLISECONDS);
            } finally {
                resumeLookup.countDown();
            }
            try {
                assertTrue(await("查到空值后到达的删除响应", deleting));
            } finally {
                // 让旧实现中漏掉通知的测试线程退出。
                synchronized (channel) {
                    channel.notifyAll();
                }
            }
        }
    }

    @Test
    void concurrentWritesKeepTypeLengthAndPayloadInTheSameFrame() throws Exception {
        try (Peer peer = new Peer()) {
            CountDownLatch firstHeaderWritten = new CountDownLatch(1);
            CountDownLatch finishFirstFrame = new CountDownLatch(1);
            peer.client.setDataOutputStream(new DataOutputStream(new FilterOutputStream(peer.client.getOutputStream()) {
                @Override
                public void write(byte[] bytes, int offset, int length) throws IOException {
                    out.write(bytes, offset, length);
                    if (length == Integer.BYTES && ByteBuffer.wrap(bytes, offset, length).getInt() == 0x3) {
                        firstHeaderWritten.countDown();
                        awaitLatch(finishFirstFrame);
                    }
                }
            }));
            byte[] firstBody = {11, 12};
            byte[] secondBody = {21, 22};
            Future<Void> first = start(() -> {
                peer.client.writeRequest(requestFrame(0x3, firstBody));
                return null;
            });
            Future<Void> second;
            try {
                awaitLatch(firstHeaderWritten);
                second = start(() -> {
                    peer.client.writeRequest(requestFrame(0x5, secondBody));
                    return null;
                });
                try {
                    second.get(500, MILLISECONDS);
                } catch (TimeoutException expectedWithFrameLock) {
                    // 持有整帧锁时，第二个写入会在第一个帧完成后继续。
                }
            } finally {
                finishFirstFrame.countDown();
            }
            await("第一个并发写入", first);
            await("第二个并发写入", second);

            byte[] expected = ByteBuffer.allocate(20)
                    .putInt(0x3).putInt(firstBody.length).put(firstBody)
                    .putInt(0x5).putInt(secondBody.length).put(secondBody).array();
            assertArrayEquals(expected, peer.input.readNBytes(expected.length),
                    "同一连接上的两次写入应当各自产生一个连续帧");
        }
    }

    private static void awaitLatch(CountDownLatch latch) {
        try {
            assertTrue(latch.await(3, SECONDS), "等待测试线程到达协调点超时");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("测试线程被中断", e);
        }
    }

    private static Request requestFrame(int type, byte[] body) {
        Request request = new Request();
        request.setType(type);
        request.setLength(body.length);
        request.setPayload(body);
        return request;
    }

    private static BasicReturns returns(BasicArguments request, boolean ok) {
        BasicReturns reply = new BasicReturns();
        reply.setChannelId(request.getChannelId());
        reply.setRid(request.getRid());
        reply.setOk(ok);
        return reply;
    }

    private static byte[] frame(int type, BasicReturns reply) throws IOException {
        byte[] payload = BinaryTool.toBinary(reply);
        return ByteBuffer.allocate(2 * Integer.BYTES + payload.length)
                .putInt(type).putInt(payload.length).put(payload).array();
    }

    private static class ObservedConnection extends Connection {
        private final CompletableFuture<IOException> readFailure = new CompletableFuture<>();

        ObservedConnection(String host, int port) throws IOException {
            super(host, port);
        }

        @Override
        public Response readResponse() throws IOException {
            try {
                return super.readResponse();
            } catch (IOException e) {
                // Connection starts its reader in the superclass constructor.
                if (readFailure != null) {
                    readFailure.complete(e);
                }
                throw e;
            }
        }
    }

    private static final class Peer implements AutoCloseable {
        private final ObservedConnection client;
        private final Socket socket;
        private final DataInputStream input;
        private final DataOutputStream output;

        Peer() throws IOException {
            try (ServerSocket listener = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
                listener.setSoTimeout(3000);
                client = new ObservedConnection("127.0.0.1", listener.getLocalPort());
                try {
                    socket = listener.accept();
                } catch (IOException e) {
                    client.close();
                    throw e;
                }
            }
            socket.setSoTimeout(3000);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }

        Channel openChannel() throws Exception {
            Future<Channel> creating = start(client::createChannel);
            BasicArguments request = request(0x1);
            reply(0x1, request, true);
            Channel channel = await("创建 channel", creating);
            assertNotNull(channel);
            assertEquals(request.getChannelId(), channel.getChannelId());
            assertSame(channel, client.getChannelMap().get(channel.getChannelId()));
            return channel;
        }

        BasicArguments request(int type) throws Exception {
            assertEquals(type, input.readInt(), "请求类型");
            int length = input.readInt();
            assertTrue(length > 0 && length <= 65536, "请求 payload 长度");
            byte[] payload = new byte[length];
            input.readFully(payload);
            BasicArguments request = assertInstanceOf(BasicArguments.class, BinaryTool.from(payload));
            assertNotNull(request.getRid());
            assertNotNull(request.getChannelId());
            return request;
        }

        void reply(int type, BasicArguments request, boolean ok) throws IOException {
            send(frame(type, returns(request, ok)));
        }

        void send(byte[] frame) throws IOException {
            output.write(frame);
            output.flush();
        }

        @Override
        public void close() throws IOException {
            try {
                client.close();
            } finally {
                socket.close();
            }
        }
    }
}
