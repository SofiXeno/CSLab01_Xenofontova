import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    private Integer id;
    private String login;
    private String password;
    private String role;




//    private static int freeUserID = 0;
//
//    public int getUserID() {
//        return userID;
//    }
//
//    private final int userID;
//
//    public User(){
//        userID = freeUserID++;
//    }
}
