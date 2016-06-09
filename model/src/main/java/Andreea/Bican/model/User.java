package Andreea.Bican.model;

import java.util.Set;
import java.util.HashSet;

public class User {
    private String name;
    private String email;
    private String uuid;
    private HashSet<String> authorities;

    public User() {
        authorities = new HashSet<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<String> getAuthorities() {
        return authorities;
        /* the following is a more protected version of get for authorities --- if it were desired to prevent direct access to the internal data object
        HashSet<String> copyAuthorities = new HashSet<String>(authorities.size());
        copyAuthorities.addAll(authorities);
        return copyAuthorities;
        */
    };

    public void setAuthorities(Set<String> authorities) {
        this.authorities.clear();
        this.authorities.addAll(authorities);
    }
}
