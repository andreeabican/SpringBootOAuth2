package Andreea.Bican.impl.Oauth2.Filters;

import Andreea.Bican.impl.IFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 13.06.2016.
 */
public class ProviderFilter implements IFilter{

    @Autowired
    @Qualifier("facebookFilter")
    OAuth2ClientAuthenticationProcessingFilter facebookFilter;

    @Autowired
    @Qualifier("googleFilter")
    OAuth2ClientAuthenticationProcessingFilter googleFilter;

    @Bean(name="providerFilter")
    public CompositeFilter createFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        filters.add(facebookFilter);
        filters.add(googleFilter);

        filter.setFilters(filters);
        return filter;
    }
}
