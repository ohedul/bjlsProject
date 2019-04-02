package bd.ohedulalam.bjlsProject.payload;
/*
* This is the login payload that will be used by login APIs
*/

import org.hibernate.validator.constraints.NotBlank;

public class LoginRequest {
    //let user login using username or email.
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
