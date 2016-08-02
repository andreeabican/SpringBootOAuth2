package Andreea.Bican;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by andre on 03.07.2016.
 */
public interface TokenService {

    String getGoogleAuthorizationCode() throws Exception;

    String getGoogleAccessToken(String code) throws IOException;

    String getGoogleRefreshToken(String code) throws IOException;

    String getGoogleAccessTokenByRefreshToken(String refreshToken) throws IOException;

    String sendGet(String url) throws Exception;

    String getAccessTokenByGoogleCredential();

    boolean checkToken(String URL) throws Exception;

    void storeEmailAndRefreshToken(String email, String refreshToken);

    String getEmailFromGoogleAccessToken(String token) throws IOException, ParseException;

}
