package Andreea.Bican.impl.Oauth2.FacebookAuthentication;

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


public class FacebookFilter extends ProviderFilter {

    OAuth2ClientContext oAuth2ClientContext;

    public FacebookFilter()
    {
        oAuth2ClientContext = SecurityConfiguration.oauth2ClientContext;
    }

    public String createAuthenticationPath(String provider)
    {
        return "/login/" + provider;
    }

    @Bean
    @ConfigurationProperties(prefix="facebook.client")
    OAuth2ProtectedResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties(prefix="facebook.resource")
    ResourceServerProperties facebookResource() { return new ResourceServerProperties(); }

    public OAuth2ClientAuthenticationProcessingFilter createFilter() {

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(createAuthenticationPath("facebook"));
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oAuth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(new CustomUserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId(), "facebook"));
        return facebookFilter;
    }
}
