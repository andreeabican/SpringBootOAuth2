package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "studentListRepositoryHardcoded")
public class StudentListRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,StudentListRepositoryHardcodedPropertyStudentList> studentLists = new HashMap<String,StudentListRepositoryHardcodedPropertyStudentList>();

    public HashMap<String,StudentListRepositoryHardcodedPropertyStudentList> getStudentLists() {
        return studentLists;
    }

    public void setStudentLists(HashMap<String,StudentListRepositoryHardcodedPropertyStudentList> studentLists) {
        this.studentLists.clear();
        this.studentLists.putAll(studentLists);
    }
}
