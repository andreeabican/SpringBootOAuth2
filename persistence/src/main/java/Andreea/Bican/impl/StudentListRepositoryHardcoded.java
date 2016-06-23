package Andreea.Bican.impl;

import Andreea.Bican.StudentList;
import Andreea.Bican.StudentListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class StudentListRepositoryHardcoded implements StudentListRepository {

    @Autowired
    private StudentListRepositoryHardcodedProperties studentListRepostioryHarcodedProperties;

    public List<StudentList> getStudentLists(int classId) {
        HashMap<String,StudentListRepositoryHardcodedPropertyStudentList> studentLists = studentListRepostioryHarcodedProperties.getStudentLists();
        if (studentLists == null) {
            return null; // no student properties were set in .yml
        }
        List<StudentList> foundStudentLists = new ArrayList<StudentList>();
        for (String studentListKey : studentLists.keySet()) {
            if (studentLists.get(studentListKey).getClassId() == classId) {
                StudentList foundStudentList = new StudentList();
                foundStudentList.setStudentListId(studentLists.get(studentListKey).getStudentListId());
                foundStudentList.setClassId(studentLists.get(studentListKey).getClassId());
                String authoritiesString = studentLists.get(studentListKey).getAuthorities().trim();
               // foundStudentList.setAuthorities(Arrays.asList(authoritiesString.split("\\s*,\\s*")));
                foundStudentList.setAuthorities(authoritiesString);
                foundStudentLists.add(foundStudentList);
            }
        }
        return foundStudentLists;
    }
}
