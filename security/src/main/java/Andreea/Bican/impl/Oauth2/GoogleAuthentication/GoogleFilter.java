package Andreea.Bican.impl.Oauth2.GoogleAuthentication;

import Andreea.Bican.ClientAppDetails;
import Andreea.Bican.impl.IProviderFilter;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.common.AuthenticationScheme.query;

/**
 * Created by andre on 13.06.2016.
 */
public class GoogleFilter implements IProviderFilter {

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    @Qualifier("listOfFilters")
    ArrayList<Filter> filters;

    @Bean(name="googleFilter")
    public int addFilter() {
        filters.add(createFilter());
        return 1;
    }

    public List<String> createScopesList(){
        List<String> scopes = new ArrayList<>();
        scopes.add("profile");
        scopes.add("email");
        return scopes;
    }

    public OAuth2ProtectedResourceDetails getClient() {

        AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setClientId(ClientAppDetails.getGoogleClientId());
        authorizationCodeResourceDetails.setClientSecret(ClientAppDetails.getGoogleClientSecret());
        authorizationCodeResourceDetails.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        authorizationCodeResourceDetails.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
        authorizationCodeResourceDetails.setTokenName("oauth_token");
        authorizationCodeResourceDetails.setClientAuthenticationScheme(query);
        authorizationCodeResourceDetails.setScope(createScopesList());
        return authorizationCodeResourceDetails;
    }

    public ResourceServerProperties getProviderResource() {
        ResourceServerProperties resourceServerProperties = new ResourceServerProperties();
        resourceServerProperties.setUserInfoUri("https://www.googleapis.com/plus/v1/people/me");
        resourceServerProperties.setPreferTokenInfo(false);
        GoogleCredential googleCredential;
        return resourceServerProperties;
    }

    public OAuth2ClientAuthenticationProcessingFilter createFilter() {

        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(getClient(), oAuth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(new GoogleCustomUserInfoTokenService(getProviderResource().getUserInfoUri(), getClient().getClientId()));
        googleFilter.setAllowSessionCreation(true);
        return googleFilter;
    }
}
