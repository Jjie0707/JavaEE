package jjie.msgqueue.client;


import lombok.Data;

import java.io.IOException;

@Data
public class ConnectionFactory {
    // broker server 的 ip 地址
    private String host;
    // broker server 的端口号
    private int port;

    public Connection newConnection() throws IOException {
        Connection connection = new Connection(host, port);
        return connection;
    }

}
