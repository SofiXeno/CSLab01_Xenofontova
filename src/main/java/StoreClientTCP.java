import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class StoreClientTCP {


    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
        try (final Socket socket = new Socket(InetAddress.getByName(null), StoreServerTCP.SERVER_PORT)) {

            final OutputStream outputStream = socket.getOutputStream();
            final InputStream inputStream = socket.getInputStream();


            outputStream.write("MESSAGE_FROM_CLIENT ".getBytes(StandardCharsets.UTF_8));



            final byte[] inputMessage = new byte[100];
            final int messageSize = inputStream.read(inputMessage);
            System.out.println("Response: " + new String(inputMessage, 0, messageSize, StandardCharsets.UTF_8));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
        }

}}
