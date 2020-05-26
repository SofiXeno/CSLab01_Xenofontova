import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainTest {

    ExecutorService executorService = Executors.newFixedThreadPool(18);
    String msg = "hello";
    String encryptedMsg = Encryptor.encrypt(msg); //зашифрувати повідомлення

    //тест на виклик ексепшену
    @Test(expected = InterruptedException.class)
    public void ShouldThrowException_When_MainIsInterrupted() throws DecoderException {

        mainWork(1L, TimeUnit.MILLISECONDS);
    }

    //тест на правильність виконання
    @Test
    public void ShouldPass() throws DecoderException {
        mainWork(24L, TimeUnit.HOURS);

    }

    public void mainWork(Long l, TimeUnit t) {
        for (int i = 0; i < 10; i++)
            executorService.submit(() -> {
                TCPNetwork tcpNetwork = new TCPNetwork();
                tcpNetwork.receiveMessage(encryptedMsg);

            });

        try {
            executorService.shutdown();
            while (!executorService.awaitTermination(l, t))
                System.out.println("Not yet. Still waiting for termination MAIN");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Processor.shutdown(l,t);

    }


}