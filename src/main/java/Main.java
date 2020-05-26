import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
//        Sender client = new Sender();
//        User u = new User();
//        client.setUser(u);
//        Receiver r = new Receiver();
//        r.receiveMessage(client.sendMessage("Hello from Sofia Xenofontova"));
//        User u2 = new User();
//        client.setUser(u2);
//        r.receiveMessage(client.sendMessage("Hello from this test"));


        ExecutorService executorService = Executors.newFixedThreadPool(12);


        for (int i = 0; i < 24; i++)
            executorService.submit(() -> {
                TCPNetwork tcpNetwork = new TCPNetwork();
                tcpNetwork.receiveMessage();

            });


        try {
            executorService.shutdown();
            while (!executorService.awaitTermination(24L, TimeUnit.HOURS))
                System.out.println("Not yet. Still waiting for termination MAIN");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Processor.shutdown();
        System.out.println("Tasks gone!");


    }

}


