import java.io.IOException;
import java.net.InetAddress;

public interface Network {



void receiveMessage();
void sendAnswerMessage(String msg, InetAddress inetAddress);



//void connect() throws IOException;
//void listen() throws IOException;
//void send (byte[] packet) throws IOException;
//void close() throws IOException;


}
