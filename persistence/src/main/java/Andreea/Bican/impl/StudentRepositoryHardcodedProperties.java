package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "studentRepositoryHardcoded")
public class StudentRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,StudentRepositoryHardcodedPropertyStudent> students = new HashMap<String,StudentRepositoryHardcodedPropertyStudent>();

    public HashMap<String,StudentRepositoryHardcodedPropertyStudent> getStudents() {
        return students;
    }

    public void setStudent(HashMap<String,StudentRepositoryHardcodedPropertyStudent> students) {
        this.students.clear();
        this.students.putAll(students);
    }
}
