//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Arrays;
//import com.google.common.primitives.Longs;


public class TCPNetwork implements Network {
//    Socket socket;
//    ServerSocket serverSocket;
//
//    OutputStream socketOutputStream;
//    InputStream serverInputstream;



    @Override
    public void receiveMessage(String msg) {



        Packet packet = new Packet(1, 1,1,1,msg);

        Processor.process(packet);



    }

    @Override
    public void sendAnswerMessage(String msg,  InetAddress inetAddress ) {
        System.out.println("Sends message: " + msg + " to: " + inetAddress.toString());
    }

//    @Override
//    public void connect() throws IOException {
//        socket = new Socket("localhost", 2305);
//
//        socketOutputStream = socket.getOutputStream();
//        serverInputstream = socket.getInputStream();
//
//    }
//
//
//
//    @Override
//    public void send(byte[] packetBytes) throws IOException {
//
//        socketOutputStream.write(packetBytes);
//        socketOutputStream.flush();
//
//        System.out.println(Arrays.toString(packetBytes));
//        System.out.println("Send\n");
//
//
//    }
//
//
//    @Override
//    public void listen() throws IOException {
//
//        serverSocket = new ServerSocket(2305);
//        socket = serverSocket.accept();
//
//
//        socketOutputStream = socket.getOutputStream();
//        serverInputstream = socket.getInputStream();
//
//
//    }
//
//    @Override
//    public void close() throws IOException {
//        socket.close();
//    }
}
