package Andreea.Bican.impl;

import Andreea.Bican.StudentListMembership;
import Andreea.Bican.StudentListMembershipRepository;
import Andreea.Bican.StudentListMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentListMembershipServiceImpl implements StudentListMembershipService {

    @Autowired
    private StudentListMembershipRepository studentListMembershipRepository;

    public List<StudentListMembership> getStudentListMemberships(int studentListId) {
        return studentListMembershipRepository.getStudentListMemberships(studentListId);
    }

}
