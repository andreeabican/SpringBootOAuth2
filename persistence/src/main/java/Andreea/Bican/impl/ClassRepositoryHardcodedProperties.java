package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "classRepositoryHardcoded")
public class ClassRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,ClassRepositoryHardcodedPropertyClass> classes = new HashMap<String,ClassRepositoryHardcodedPropertyClass>();

    public HashMap<String,ClassRepositoryHardcodedPropertyClass> getClasses() {
        return classes;
    }

    public void setClasses(HashMap<String,ClassRepositoryHardcodedPropertyClass> classes) {
        this.classes.clear();
        this.classes.putAll(classes);
    }
}
