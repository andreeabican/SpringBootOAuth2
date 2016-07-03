package Andreea.Bican;

import java.io.IOException;

/**
 * Created by andre on 03.07.2016.
 */
public interface TokenService {

    String getGoogleAuthorizationCode() throws Exception;

    String getAccessToken(String code) throws IOException;
}
