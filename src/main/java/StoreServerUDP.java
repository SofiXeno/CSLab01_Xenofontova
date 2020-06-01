import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class StoreServerUDP {

    public static final int SERVER_PORT = 1234;

    public static void main(String[] args) {

        AtomicBoolean isRun = new AtomicBoolean(true);

        new Thread(() -> {
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isRun.set(false);
        }).start();


        try (final DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT)) {

serverSocket.setSoTimeout(2_000);


            while (isRun.get() ) {
                try {
                    final byte[] inputMessage = new byte[100];
                    final DatagramPacket packet = new DatagramPacket(inputMessage, inputMessage.length);
                    serverSocket.receive(packet);

                    new Thread(() -> {
                        try {
                            final int realMessageSize = packet.getLength();
                            System.out.println("Message from client: " + new String(inputMessage, 0, realMessageSize, StandardCharsets.UTF_8));


                            final String message = "SERVER_OK";

                            final byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

                            final DatagramPacket response = new DatagramPacket(messageBytes, messageBytes.length, packet.getAddress(), packet.getPort());
                            serverSocket.send(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }).start();

                }catch (SocketTimeoutException e){
                    System.out.println("Socket timeout");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
