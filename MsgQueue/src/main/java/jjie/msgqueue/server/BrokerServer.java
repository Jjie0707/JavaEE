package jjie.msgqueue.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrokerServer {
    private ServerSocket serverSocket = null;

    // 当前考虑一个 BrokerServer 上只有一个 虚拟主机
    private VirtualHost virtualHost = new VirtualHost("default");
    // 使用这个 哈希表 表示当前的所有会话(也就是说有哪些客户端正在和咱们的服务器进行通信)
    // 此处的 key 是 channelId, value 为对应的 Socket 对象
    private ConcurrentHashMap<String, Socket> sessions = new ConcurrentHashMap<String, Socket>();
    // 引入一个线程池, 来处理多个客户端的请求.
    private ExecutorService executorService = null;
    // 引入一个 boolean 变量控制服务器是否继续运行
    private volatile boolean runnable = true;

    public BrokerServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("[BrokerServer] 启动!");
        executorService = Executors.newCachedThreadPool();
        try {
            while (runnable) {
                Socket clientSocket = serverSocket.accept();
                // 把处理连接的逻辑丢给这个线程池.
                executorService.submit(() -> {
                    processConnection(clientSocket);
                });
            }
        } catch (SocketException e) {
            System.out.println("[BrokerServer] 服务器停止运行或者运行失败!");
            e.printStackTrace();
        }
    }
    public void stop() throws IOException {
        runnable = false;
        // 把线程池中的任务都放弃了. 让线程都销毁.
        executorService.shutdownNow();
        serverSocket.close();
    }

    private void processConnection(Socket clientSocket) {

    }
}
