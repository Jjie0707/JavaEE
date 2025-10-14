package echo;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    private DatagramSocket socket;
    private String serverIp;
    private int serverPort;

    public EchoClient(String serverIp,int serverPort) throws SocketException {
        socket=new DatagramSocket();
        this.serverIp=serverIp;
        this.serverPort=serverPort;
    }

    public void start() throws IOException {
        System.out.println("client start!");
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("<-");
            String request=sc.nextLine();
            if(request.equals("quit")){
                System.out.println("exit");
                break;
            }
            DatagramPacket requestPacket=new DatagramPacket(request.getBytes(),
                    request.getBytes().length, InetAddress.getByName(this.serverIp),this.serverPort);
            //走到这里已经够建好了请求数据报，由socket发向指定的ip和端口
            socket.send(requestPacket);
            DatagramPacket responsePacket=new DatagramPacket(new byte[1024],1024);
            socket.receive(responsePacket);
            String response=new String(responsePacket.getData(),0,responsePacket.getLength());
            System.out.println(response);
            System.out.println("->");
        }

    }

    static void main() throws IOException {
        EchoClient client=new EchoClient("127.0.0.1",5600);
        client.start();
    }
}
