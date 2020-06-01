import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class StoreServerTCP {
    public static final int SERVER_PORT = 2222;

    public static void main(String[] args) {


        try (final ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
while(true)
            runClient(serverSocket.accept());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void runClient(final Socket socket) {
        new Thread(()-> {
            try {
                final InputStream inputStream = socket.getInputStream();
                final OutputStream outputStream = socket.getOutputStream();


                final byte[] inputMessage = new byte[100];
                final int messageSize = inputStream.read(inputMessage);
                System.out.println("Message from client: " + new String(inputMessage,0,messageSize, StandardCharsets.UTF_8));


                outputStream.write("SERVER_OK".getBytes(StandardCharsets.UTF_8));


            } catch (IOException e) {
                e.printStackTrace();
            } finally {


                try {


                    socket.close();
                } catch (Exception e) {

                }

            }
        }).start();

    }


}
