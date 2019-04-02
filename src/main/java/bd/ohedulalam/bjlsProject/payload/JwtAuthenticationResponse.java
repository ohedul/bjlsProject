package bd.ohedulalam.bjlsProject.payload;

/*
* This is the payload that is used by Jwt*/

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    //This constructor will initialize the token.
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
