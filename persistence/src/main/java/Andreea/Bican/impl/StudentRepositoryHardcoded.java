package Andreea.Bican.impl;

import Andreea.Bican.Student;
import Andreea.Bican.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

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
