package Andreea.Bican;

import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Created by andre on 03.07.2016.
 */
public interface TokenService {

    String getGoogleAuthorizationCode() throws Exception;

    String getGoogleAccessToken(String code) throws IOException;

    String sendGet(String url) throws Exception;

    boolean checkToken(String URL) throws Exception;

    String getEmailFromGoogleAccessToken(String token) throws IOException, ParseException;
}
