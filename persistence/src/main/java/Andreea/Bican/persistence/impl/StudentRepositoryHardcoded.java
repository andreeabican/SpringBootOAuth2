package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import Andreea.Bican.model.Student;
import Andreea.Bican.persistence.StudentRepository;

@Repository
public class StudentRepositoryHardcoded implements StudentRepository {

    @Autowired
    private StudentRepositoryHardcodedProperties studentRepostioryHarcodedProperties;

    public Student getStudent(int studentId) {
        HashMap<String,StudentRepositoryHardcodedPropertyStudent> students = studentRepostioryHarcodedProperties.getStudents();
        if (students == null) {
            return null; // no student properties were set in .yml
        }
        for (String studentKey : students.keySet()) {
            if (students.get(studentKey).getStudentId() == studentId) {
                Student foundStudent = new Student();
                foundStudent.setId(students.get(studentKey).getStudentId());
                foundStudent.setName(students.get(studentKey).getName());
                return foundStudent;
            }
        }
        return null;
    }
}
