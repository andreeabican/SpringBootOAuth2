package Andreea.Bican;

/**
 * Created by andre on 23.06.2016.
 */
public class StudentListMembership {
    private int pk;
    private int studentListId;
    private int studentId;

    public StudentListMembership() {

    }

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