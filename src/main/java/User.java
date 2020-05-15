public class User {
    private static int freeUserID = 0;

    public int getUserID() {
        return userID;
    }

    private final int userID;

    public User(){
        userID = freeUserID++;
    }
}
