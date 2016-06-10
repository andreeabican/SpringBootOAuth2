package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import Andreea.Bican.model.StudentListMembership;
import Andreea.Bican.persistence.StudentListMembershipRepository;

@Repository
public class StudentListMembershipRepositoryHardcoded implements StudentListMembershipRepository {

    @Autowired
    private StudentListMembershipRepositoryHardcodedProperties studentListMembershipRepostioryHarcodedProperties;

    public List<StudentListMembership> getStudentListMemberships(int studentListId) {
        HashMap<String,StudentListMembershipRepositoryHardcodedPropertyStudentListMembership> studentListMemberships = studentListMembershipRepostioryHarcodedProperties.getStudentListMemberships();
        if (studentListMemberships == null) {
            return null; // no student properties were set in .yml
        }
        List<StudentListMembership> foundStudentListMemberships = new ArrayList<StudentListMembership>();
        for (String studentListMembershipKey : studentListMemberships.keySet()) {
            if (studentListMemberships.get(studentListMembershipKey).getStudentListId() == studentListId) {
                StudentListMembership foundStudentListMembership = new StudentListMembership();
                foundStudentListMembership.setPk(studentListMemberships.get(studentListMembershipKey).getPk());
                foundStudentListMembership.setStudentListId(studentListMemberships.get(studentListMembershipKey).getStudentListId());
                foundStudentListMembership.setStudentId(studentListMemberships.get(studentListMembershipKey).getStudentId());
                foundStudentListMemberships.add(foundStudentListMembership);
            }
        }
        return foundStudentListMemberships;
    }
}
