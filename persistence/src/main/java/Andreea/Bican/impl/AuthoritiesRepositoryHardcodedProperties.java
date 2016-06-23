package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "authoritiesRepositoryHardcoded")
public class AuthoritiesRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,AuthoritiesRepositoryHardcodedPropertyAuthorities> authorities = new HashMap<String,AuthoritiesRepositoryHardcodedPropertyAuthorities>();

    public HashMap<String,AuthoritiesRepositoryHardcodedPropertyAuthorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(HashMap<String,AuthoritiesRepositoryHardcodedPropertyAuthorities> authorities) {
        this.authorities.clear();
        this.authorities.putAll(authorities);
    }
}
