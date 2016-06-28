package Andreea.Bican;

import java.util.List;

/**
 * Created by andre on 23.06.2016.
 */
public class StudentList {
    private int studentListId;
    private int classId;
    private List<String> authorities;

    public StudentList() {
    }

    public int getStudentListId() {
        return studentListId;
    }

    public void setStudentListId(int studentListId) {
        this.studentListId = studentListId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

}