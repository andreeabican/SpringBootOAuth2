package Andreea.Bican.impl.Oauth2;

import Andreea.Bican.impl.IProviderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 01.08.2016.
 */
public class AutoFilter implements IProviderFilter{

    @Autowired
    @Qualifier("listOfFilters")
    ArrayList<Filter> filters;


    @Override
    public List<String> createScopesList() {
        return null;
    }

    @Override
    public ResourceServerProperties getProviderResource() {
        return null;
    }

    @Override
    public OAuth2ProtectedResourceDetails getClient() {
        return null;
    }

    @Bean(name = "customFilter")
    public int addFilter() {
        filters.add(new CustomFilter());
        return 1;
    }

    @Override
    public Filter createFilter() {
        return null;
    }
}
