package Andreea.Bican.model;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;

public class StudentList {
    private int studentListId;
    private int classId;
    private Set<String> authorities;

    public StudentList() {
        authorities = new HashSet<String>();
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

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities.clear();
        this.authorities.addAll(authorities);
    }
}
