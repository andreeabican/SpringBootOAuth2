package Andreea.Bican.persistence;

import java.util.List;
import Andreea.Bican.model.StudentList;

public interface StudentListRepository {
    public List<StudentList> getStudentLists(int classId);
}
