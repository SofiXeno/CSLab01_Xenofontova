public class Application {

    public static void main(String[] args) {
        Sender client = new Sender();
        User u = new User();
        client.setUser(u);
        Receiver r = new Receiver();
        r.receiveMessage(client.sendMessage("Hello from Sofia Xenofontova"));
        User u2 = new User();
        client.setUser(u2);
        r.receiveMessage(client.sendMessage("Hello from this test"));

    }

}
//далі йде новий пакет у якому будуть змінені номер айді та номер юзера за поточним клієнтом
//також плюсується і номер пакету

