package Andreea.Bican.impl.Oauth2.GoogleAuthentication;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.common.AuthenticationScheme.query;

/**
 * Created by andre on 13.06.2016.
 */

public class GoogleFilter{

    public List<String> createScopesList(){
        List<String> scopes = new ArrayList<>();
        scopes.add("profile");
        scopes.add("email");
        return scopes;
    }

    OAuth2ProtectedResourceDetails getClient() {

        AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setClientId("565779346670-4hqpp1qbqa45go8pue5ncgirsk4rnc1o.apps.googleusercontent.com");
        authorizationCodeResourceDetails.setClientSecret("ulAV2AnP1vkezZ1ORN7D9pA6");
        authorizationCodeResourceDetails.setAccessTokenUri("https://www.googleapis.com/oauth2/v3/token");
        authorizationCodeResourceDetails.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/auth");
        authorizationCodeResourceDetails.setTokenName("oauth_token");
        authorizationCodeResourceDetails.setClientAuthenticationScheme(query);
        authorizationCodeResourceDetails.setScope(createScopesList());

        return authorizationCodeResourceDetails;
    }

    ResourceServerProperties getProviderResource() {
        ResourceServerProperties resourceServerProperties = new ResourceServerProperties();
        resourceServerProperties.setUserInfoUri("https://www.googleapis.com/plus/v1/people/me");
        resourceServerProperties.setPreferTokenInfo(false);

        return resourceServerProperties;
    }

    @Bean(name="googleFilter")
    public OAuth2ClientAuthenticationProcessingFilter createFilter(OAuth2ClientContext oAuth2ClientContext) {

        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(getClient(), oAuth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(new GoogleCustomUserInfoTokenService(getProviderResource().getUserInfoUri(), getClient().getClientId()));
        return googleFilter;
    }
}
