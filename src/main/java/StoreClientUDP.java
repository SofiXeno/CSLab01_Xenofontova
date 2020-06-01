import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class StoreClientUDP {

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {

                try (final DatagramSocket serverSocket = new DatagramSocket(0)) {
                    serverSocket.setSoTimeout(10_000);
                    System.out.println(serverSocket.getLocalPort());


                    final String message = "message from client";

                    final byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);//вит€гнути пов≥домленн€ в≥д кл≥Їнта

                    final DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, InetAddress.getByName(null), StoreServerUDP.SERVER_PORT);
                    serverSocket.send(packet);


                    final byte[] responseMessage = new byte[100];
                    final DatagramPacket response = new DatagramPacket(responseMessage, responseMessage.length);
                    serverSocket.receive(response);

                    final int responseMessageLength = response.getLength();
                    System.out.println("Response from server: " + new String(responseMessage, 0, responseMessageLength, StandardCharsets.UTF_8));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
