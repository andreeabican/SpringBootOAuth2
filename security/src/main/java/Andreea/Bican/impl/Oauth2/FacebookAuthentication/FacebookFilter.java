package Andreea.Bican.impl.Oauth2.FacebookAuthentication;

import Andreea.Bican.impl.IProviderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.common.AuthenticationScheme.form;
import static org.springframework.security.oauth2.common.AuthenticationScheme.query;

/**
 * Created by andre on 13.06.2016.
 */

public class FacebookFilter implements IProviderFilter {

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    public List<String> createScopesList()
    {
        List<String> scopes = new ArrayList<>();
        scopes.add("email");
        return scopes;
    }

    public OAuth2ProtectedResourceDetails getClient() {
       AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();

        authorizationCodeResourceDetails.setClientId("clientId");
        authorizationCodeResourceDetails.setClientSecret("clientSecret");
        authorizationCodeResourceDetails.setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
        authorizationCodeResourceDetails.setUserAuthorizationUri("https://www.facebook.com/dialog/oauth");
        authorizationCodeResourceDetails.setTokenName("oauth_token");
        authorizationCodeResourceDetails.setAuthenticationScheme(query);
        authorizationCodeResourceDetails.setClientAuthenticationScheme(form);
        authorizationCodeResourceDetails.setScope(createScopesList());

        return authorizationCodeResourceDetails;
    }

    public ResourceServerProperties getProviderResource() {
        ResourceServerProperties resourceServerProperties = new ResourceServerProperties();
        resourceServerProperties.setUserInfoUri("https://graph.facebook.com/me?fields=id,name,email");

        return resourceServerProperties;
    }

    @Bean(name="facebookFilter")
    public OAuth2ClientAuthenticationProcessingFilter createFilter() {

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(getClient(), oAuth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(new FacebookCustomUserInfoTokenService(getProviderResource().getUserInfoUri(), getClient().getClientId()));

        return facebookFilter;
    }
}
