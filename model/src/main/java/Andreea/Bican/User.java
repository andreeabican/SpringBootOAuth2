package Andreea.Bican;

import java.security.Principal;
import java.util.UUID;

/**
 * Created by andre on 26.05.2016.
 */
public class User implements Principal{
    static UUID uuid;
    private String userName;
    private String id;
    private String email;
    private boolean authority;

    public User() {}

    public User(String userName) { userName = userName; }

    public UUID getUUID() {
        return uuid;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getAuthority() {  return authority; }

    public void setAuthority(boolean authority) { this.authority = authority; }

    @Override
    public String getName() {
        return userName;
    }
}