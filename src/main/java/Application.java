public class Application {

    public static void main(String[] args) {
        Sender client = new Sender();
        User u = new User();
        client.setUser(u);
        Receiver r = new Receiver();
        r.receiveMessage(client.sendMessage("Hello from senderkhvkutfhgfckhvkhf"));
        User u2 = new User();
        client.setUser(u2);
        r.receiveMessage(client.sendMessage("Hello from senderkhvklrjbgkshjrgrkhgutfhgfckhvkhf"));

    }

}
