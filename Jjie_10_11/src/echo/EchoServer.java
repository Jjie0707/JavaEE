package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//一个服务器类
public class EchoServer{
    private DatagramSocket socket;


    public EchoServer(int port) throws IOException {
        socket=new DatagramSocket(port);
    }
    public void start() throws IOException {
        System.out.println("server start!");
        //循环处理需求
        while(true){
            //构造一个数据报
            DatagramPacket requestPacket = new DatagramPacket(new byte[1024], 1024);

            //
            socket.receive(requestPacket);
//            socket是一个对象，对应一个socket文件
//            requestPacket是一个udp数据报
            String request=new String(requestPacket.getData(),0, requestPacket.getLength());
            //走到这里已经把需求转化成了字符串
            String response=response(request);
            DatagramPacket responsePacket=new DatagramPacket(response.getBytes(),
                    response.getBytes().length,requestPacket.getSocketAddress());
            socket.send(responsePacket);
        }
    }

    private String response(String request){
        //response的逻辑一般比较复杂，但是echo回显比较简单我们返回他的reverse就可以了
        StringBuilder response=new StringBuilder(request);
        return response.reverse().toString();
    }

    static void main() throws IOException {
        EchoServer server=new EchoServer(5600);
        server.start();
    }
}
