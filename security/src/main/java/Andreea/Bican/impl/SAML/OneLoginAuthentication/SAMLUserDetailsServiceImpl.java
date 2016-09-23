package Andreea.Bican.impl.SAML.OneLoginAuthentication;

/**
 * Created by andre on 17.08.2016.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);

    public Object loadUserBySAML(SAMLCredential credential)
            throws UsernameNotFoundException {

        // The method is supposed to identify local account of user referenced by
        // data in the SAML assertion and return UserDetails object describing the user.

        String userID = credential.getNameID().getValue();

        LOG.info(userID + " is logged in\n\n\n\n\n\n\n\n\n\n\n");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);

        // In a real scenario, this implementation has to locate user in a arbitrary
        // dataStore based on information present in the SAMLCredential and
        // returns such a date in a form of application specific UserDetails object.
        return new User(userID, "<abc123>", true, true, true, true, authorities);
    }
}
