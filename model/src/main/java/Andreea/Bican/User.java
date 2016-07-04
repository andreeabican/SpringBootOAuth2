package Andreea.Bican;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by andre on 26.05.2016.
 */
public class User {
    private String name;
    private String email;
    private String token;
    private String provider;
    private String id;
    private HashSet<String> authorities;

    public User() {
        authorities = new HashSet<String>();
    }

    public String getName() {

        if(name != null) {
            return name;
        }else {
            return "unknown";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        if(email != null){
            return email;
        }else{
            return "unknown";
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvider(String provider){ this.provider = provider; }

    public String getProvider() {
        if (provider != null) {
            return provider;
        } else {
            return "unknown";
        }
    }

    public void setToken(String token){ this.token = token; }

    public String getToken() {
        if (token != null) {
            return token;
        } else {
            return "unknown";
        }
    }

    public void setId(String id){ this.id = id; }

    public String getId(){
        if(id != null){
            return id;
        }else{
            return "unknown";
        }
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