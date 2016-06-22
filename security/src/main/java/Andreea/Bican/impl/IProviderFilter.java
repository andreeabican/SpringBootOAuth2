package Andreea.Bican.impl;

import Andreea.Bican.impl.IFilter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import java.util.List;

/**
 * Created by andre on 22.06.2016.
 */
public interface IProviderFilter extends IFilter {

    List<String> createScopesList();

    ResourceServerProperties getProviderResource();

    OAuth2ProtectedResourceDetails getClient();
}
