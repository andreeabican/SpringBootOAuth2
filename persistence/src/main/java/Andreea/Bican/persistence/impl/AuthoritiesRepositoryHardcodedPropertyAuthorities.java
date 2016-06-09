package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class AuthoritiesRepositoryHardcodedPropertyAuthorities {
    private int pk;
    private String uuid;
    private String authority;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
            this.pk = pk;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
