package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class AuthoritiesRepositoryHardcodedPropertyAuthorities {
    private int pk;
    private String userUuid;
    private String authority;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
            this.pk = pk;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
