import java.nio.charset.StandardCharsets;

public class Sender {
    private static byte freeClientID = 0;
    private final byte clientID;
    private User loggedUser;

    public Sender() {
        clientID = freeClientID++;
    }

    public byte[] sendMessage(String messageText) {
        if (loggedUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        return messageText.getBytes(StandardCharsets.UTF_8);

    }

    public void setUser(User user) {
        this.loggedUser = user;
    }
}
