package models;

/**
 * Created by erick on 06/07/2017.
 */

public class TokenOutput {
    private String AccessToken;
    private String Username;
    private String TokenType;
    private int Expires;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public int getExpires() {
        return Expires;
    }

    public void setExpires(int expires) {
        Expires = expires;
    }
}
