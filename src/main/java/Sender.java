public class Sender {
    private final byte clientID;
    private static byte freeClientID = 0;
    private User loggedUser;

    public Sender(){
        clientID = freeClientID++;
    }

    public byte[] sendMessage(String messageText){
        if(loggedUser==null) {
            throw new IllegalStateException("No user is logged in.");
        }


    }

    public void setUser(User user){
        this.loggedUser = user;
    }
}
