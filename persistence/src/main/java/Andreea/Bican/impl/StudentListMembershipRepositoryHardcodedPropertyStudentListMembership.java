package Andreea.Bican.impl;

import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class StudentListMembershipRepositoryHardcodedPropertyStudentListMembership {
    private int pk;
    private int studentListId;
    private int studentId;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getStudentListId() {
        return studentListId;
    }

    public void setStudentListId(int studentListId) {
        this.studentListId = studentListId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
