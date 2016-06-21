package Andreea.Bican.impl;

/**
 * Created by andre on 21.06.2016.
 */
public interface AccessToken {
    String sendGet(String requestMessage) throws Exception;

    boolean checkToken(String token) throws Exception;
}
