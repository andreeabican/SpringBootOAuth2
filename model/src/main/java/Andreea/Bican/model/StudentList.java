package Andreea.Bican.model;

public class StudentList {
    private int studentListId;
    private int classId;
    private String authorities;

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

    public void setClassid(int classId) {
        this.classId = classId;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
