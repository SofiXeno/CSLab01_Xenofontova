import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Processor implements Runnable {
    private Packet packet;

    private final static ExecutorService service = Executors.newFixedThreadPool(6);
    //private byte[] packetBytes;


    public Processor(Packet packet) {
        this.packet = packet;
    }

    public static void process(Packet packet) {

        service.submit(new Processor(packet));

    }


    public static void shutdown(Long l, TimeUnit t) {
        try {

            service.shutdown();
            while (!service.awaitTermination(l,t))
                System.out.println("Not yet. Still waiting for termination PROCESSOR");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void run() {

        try {
            Thread.sleep(1000);

            InetAddress inetAddress = InetAddress.getLocalHost();

            new TCPNetwork().sendAnswerMessage("#" + Thread.currentThread().getId() + " : OK! Hello from the other side --> it\'s " + LocalDateTime.now(), inetAddress);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
