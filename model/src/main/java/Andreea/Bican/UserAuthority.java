package Andreea.Bican;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by andre on 23.06.2016.
 */
public class UserAuthority implements GrantedAuthority {

    private String authority;

    public UserAuthority() {}

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}