package jjie.msgqueue.client;

import jjie.msgqueue.MsgQueueApplication;
import jjie.msgqueue.common.consumer.Consumer;
import jjie.msgqueue.common.exception.MqException;
import jjie.msgqueue.server.BrokerServer;
import jjie.msgqueue.server.core.BasicProperties;
import jjie.msgqueue.server.core.ExchangeType;
import jjie.msgqueue.server.mapper.MetaMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static jjie.msgqueue.client.ClientTestSupport.await;
import static jjie.msgqueue.client.ClientTestSupport.start;
import static org.junit.jupiter.api.Assertions.*;

class ClientBrokerTest {
    private static BrokerServer broker;
    private static Thread brokerThread;
    private static int port;
    private static ConfigurableApplicationContext context;
    private static ConfigurableApplicationContext previousContext;
    private final List<Connection> connections = new ArrayList<>();

    @BeforeAll
    static void startBroker(@TempDir Path tempDir) throws Exception {
        previousContext = MsgQueueApplication.context;
        context = new SpringApplicationBuilder(MsgQueueApplication.class)
                .web(WebApplicationType.NONE)
                .run("--spring.datasource.url=jdbc:sqlite:" + tempDir.resolve("broker.db"),
                     "--mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.nologging.NoLoggingImpl");
        MsgQueueApplication.context = context;
        // MetaMapper.check() uses the project's fixed path instead of the configured data source.
        context.getBean(MetaMapper.class).createTable();
        broker = new BrokerServer(0);
        ServerSocket listener = (ServerSocket) ReflectionTestUtils.getField(broker, "serverSocket");
        port = listener.getLocalPort();
        brokerThread = Thread.ofVirtual().name("test-broker").start(() -> {
            try {
                broker.start();
            } catch (Exception e) {
                throw new AssertionError("Broker 启动失败", e);
            }
        });
    }

    @AfterAll
    static void stopBroker() throws Exception {
        try {
            if (broker != null) {
                if (ReflectionTestUtils.getField(broker, "executorService") == null) {
                    ((ServerSocket) ReflectionTestUtils.getField(broker, "serverSocket")).close();
                } else {
                    broker.stop();
                }
                brokerThread.join(3000);
                Object host = ReflectionTestUtils.getField(broker, "virtualHost");
                Object consumers = ReflectionTestUtils.getField(host, "consumerManager");
                ((ExecutorService) ReflectionTestUtils.getField(consumers, "workerPool")).shutdownNow();
            }
        } finally {
            MsgQueueApplication.context = previousContext;
            if (context != null) {
                context.close();
            }
        }
    }

    @AfterEach
    void closeConnections() {
        connections.forEach(Connection::close);
    }

    @Test
    void topologyOperationsReturnActualBrokerResults() throws Exception {
        Channel channel = openChannel();
        String exchange = uniqueName();
        String queue = uniqueName();
        assertTrue(await("声明交换机", start(() ->
                channel.exchangeDeclare(exchange, ExchangeType.DIRECT, false, false, Map.of()))));
        assertTrue(await("重复声明交换机", start(() ->
                channel.exchangeDeclare(exchange, ExchangeType.DIRECT, false, false, Map.of()))));
        assertTrue(await("声明队列", start(() -> channel.queueDeclare(queue, false, false, false, Map.of()))));
        assertTrue(await("重复声明队列", start(() -> channel.queueDeclare(queue, false, false, false, Map.of()))));
        assertFalse(await("绑定不存在的队列", start(() -> channel.queueBind(queue + "missing", exchange, queue))));
        assertTrue(await("创建绑定", start(() -> channel.queueBind(queue, exchange, queue))));
        assertFalse(await("重复绑定", start(() -> channel.queueBind(queue, exchange, queue))));
        assertTrue(await("解除绑定", start(() -> channel.queueUnbind(queue, exchange))));
        assertFalse(await("再次解除绑定", start(() -> channel.queueUnbind(queue, exchange))));
        assertFalse(await("发布到已解绑的直连队列", start(() ->
                channel.basicPublish(exchange, queue, null, new byte[]{1}))));
        assertTrue(await("删除队列", start(() -> channel.queueDelete(queue))));
        assertFalse(await("再次删除队列", start(() -> channel.queueDelete(queue))));
        assertTrue(await("删除交换机", start(() -> channel.exchangeDelete(exchange))));
        assertFalse(await("再次删除交换机", start(() -> channel.exchangeDelete(exchange))));
        assertFalse(await("发布到不存在的交换机", start(() ->
                channel.basicPublish(exchange, queue, null, new byte[]{1}))));
        assertTrue(await("关闭 channel", start(channel::close)));
    }

    @Test
    void deliveryContainsOriginalBytesAndManualAckReturnsTrueOnlyOnce() throws Exception {
        Channel subscriber = openChannel();
        Channel publisher = openChannel();
        String exchange = uniqueName();
        String queue = uniqueName();
        byte[] body = {0, 1, -1, 0, 42};
        assertTrue(await("声明交换机", start(() ->
                publisher.exchangeDeclare(exchange, ExchangeType.DIRECT, false, false, Map.of()))));
        assertTrue(await("声明队列", start(() -> publisher.queueDeclare(queue, false, false, false, Map.of()))));
        assertTrue(await("创建直连绑定", start(() -> publisher.queueBind(queue, exchange, queue))));

        CompletableFuture<Delivery> delivery = new CompletableFuture<>();
        Consumer consumer = (tag, properties, bytes) -> delivery.complete(new Delivery(tag, properties, bytes));
        assertTrue(await("订阅现有队列", start(() -> subscriber.basicConsume(queue, false, consumer))));
        assertThrows(MqException.class, () -> subscriber.basicConsume(queue, false, consumer));
        assertFalse(await("确认未知消息", start(() -> subscriber.basicAck(queue, "missing"))));
        assertTrue(await("发布消息", start(() -> publisher.basicPublish(exchange, queue, new BasicProperties(), body))));

        Delivery received = await("收到消息回调", delivery);
        assertArrayEquals(body, received.body());
        assertEquals(subscriber.getChannelId(), received.consumerTag());
        assertEquals(queue, received.properties().getRoutingKey());
        assertEquals(0, received.properties().getDeliverMode());
        String id = received.properties().getId();
        assertNotNull(id);
        assertFalse(id.isBlank());
        assertTrue(await("确认已投递消息", start(() -> subscriber.basicAck(queue, id))));
        assertFalse(await("重复确认", start(() -> subscriber.basicAck(queue, id))));
    }

    @Test
    void failedSubscriptionCanBeRetriedOnSameChannel() throws Exception {
        Channel channel = openChannel();
        String queue = uniqueName();
        Consumer consumer = (tag, properties, body) -> {};
        assertFalse(await("订阅不存在的队列", start(() -> channel.basicConsume(queue, false, consumer))));
        assertTrue(await("创建此前不存在的队列", start(() ->
                channel.queueDeclare(queue, false, false, false, Map.of()))));
        assertTrue(await("在同一个 channel 上重新订阅", start(() -> channel.basicConsume(queue, false, consumer))));
    }

    @Test
    void successfulCloseRemovesChannelFromConnection() throws Exception {
        Channel channel = openChannel();
        Connection connection = channel.getConnection();
        assertSame(channel, connection.getChannelMap().get(channel.getChannelId()));
        assertTrue(await("关闭 channel", start(channel::close)));
        assertFalse(connection.getChannelMap().containsKey(channel.getChannelId()),
                "close 返回 true 后，连接仍持有已关闭的 channel");
    }

    private Channel openChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(port);
        Connection connection = factory.newConnection();
        connections.add(connection);
        Channel channel = await("创建 channel", start(connection::createChannel));
        assertNotNull(channel);
        assertSame(connection, channel.getConnection());
        assertSame(channel, connection.getChannelMap().get(channel.getChannelId()));
        return channel;
    }

    private static String uniqueName() {
        return "test" + UUID.randomUUID().toString().replace("-", "");
    }

    private record Delivery(String consumerTag, BasicProperties properties, byte[] body) {}
}
