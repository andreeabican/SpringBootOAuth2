package Andreea.Bican.service;

import java.util.List;
import Andreea.Bican.model.StudentList;

public interface StudentListService {
    public List<StudentList> getStudentLists(int classId);
}
