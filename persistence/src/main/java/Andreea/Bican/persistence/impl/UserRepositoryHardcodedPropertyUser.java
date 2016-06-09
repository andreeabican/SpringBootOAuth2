package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class UserRepositoryHardcodedPropertyUser {
    private String email;
    private String name;
    private String uuid;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
