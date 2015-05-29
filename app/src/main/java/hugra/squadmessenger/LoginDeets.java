package hugra.squadmessenger;

/**
 * Created by hugo on 29/05/15.
 */
public class LoginDeets{
    String userName;
    String password;
    public LoginDeets(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String toString(){
        return "userName: " + userName + " password: " + password;
    }
}
