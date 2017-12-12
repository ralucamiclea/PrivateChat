package auth;

/**
 * Created by raluca.miclea on 12/12/2017.
 */

public class User {

    private String username;
    private String password;

    public User(String user, String pass){
        this.username = user;
        this.password = pass;
    }

    public String getUsername(){
        return username;
    }
}
