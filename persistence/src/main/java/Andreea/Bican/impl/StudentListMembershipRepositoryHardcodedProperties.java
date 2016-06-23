package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "studentListMembershipRepositoryHardcoded")
public class StudentListMembershipRepositoryHardcodedProperties {

    @NestedConfigurationProperty
    private final HashMap<String,StudentListMembershipRepositoryHardcodedPropertyStudentListMembership> studentListMemberships = new HashMap<String,StudentListMembershipRepositoryHardcodedPropertyStudentListMembership>();

    public HashMap<String,StudentListMembershipRepositoryHardcodedPropertyStudentListMembership> getStudentListMemberships() {
        return studentListMemberships;
    }

    public void setStudentListMemberships(HashMap<String,StudentListMembershipRepositoryHardcodedPropertyStudentListMembership> studentListMemberships) {
        this.studentListMemberships.clear();
        this.studentListMemberships.putAll(studentListMemberships);
    }
}
