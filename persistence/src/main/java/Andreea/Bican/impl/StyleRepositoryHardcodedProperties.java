package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "styleRepositoryHardcoded")
public class StyleRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,StyleRepositoryHardcodedPropertyStyle> styles = new HashMap<String,StyleRepositoryHardcodedPropertyStyle>();

    public HashMap<String,StyleRepositoryHardcodedPropertyStyle> getStyles() {
        return styles;
    }

    public void setStyle(HashMap<String,StyleRepositoryHardcodedPropertyStyle> styles) {
        this.styles.clear();
        this.styles.putAll(styles);
    }
}
