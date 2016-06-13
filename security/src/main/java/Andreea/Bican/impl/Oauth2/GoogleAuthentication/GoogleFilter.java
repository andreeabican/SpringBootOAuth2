package Andreea.Bican.impl.Oauth2.GoogleAuthentication;

import Andreea.Bican.impl.Configuration.CustomUserInfoTokenServices;
import Andreea.Bican.impl.Configuration.SecurityConfiguration;
import Andreea.Bican.impl.Oauth2.ProviderFilter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * Created by andre on 13.06.2016.
 */

public class GoogleFilter extends ProviderFilter{

    OAuth2ClientContext oAuth2ClientContext;

    public GoogleFilter()
    {
        this.oAuth2ClientContext = SecurityConfiguration.oauth2ClientContext;
    }

    public String createAuthenticationPath(String provider)
    {
        return "/login/" + provider;
    }

    @Bean
    @ConfigurationProperties(prefix="google.client")
    OAuth2ProtectedResourceDetails getClient() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties(prefix="google.resource")
    ResourceServerProperties getProviderResource() {
        return new ResourceServerProperties();
    }

    public OAuth2ClientAuthenticationProcessingFilter createFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(createAuthenticationPath("google"));
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(getClient(), oAuth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(new CustomUserInfoTokenServices(getProviderResource().getUserInfoUri(), getClient().getClientId(), "google"));
        return googleFilter;
    }
}
