package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "userRepositoryHardcoded")
public class UserRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,UserRepositoryHardcodedPropertyUser> users = new HashMap<String,UserRepositoryHardcodedPropertyUser>();

    public HashMap<String,UserRepositoryHardcodedPropertyUser> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String,UserRepositoryHardcodedPropertyUser> users) {
        this.users.clear();
        this.users.putAll(users);
    }
}
