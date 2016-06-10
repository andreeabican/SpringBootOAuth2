package Andreea.Bican.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.StudentListMembership;
import Andreea.Bican.service.StudentListMembershipService;
import Andreea.Bican.persistence.StudentListMembershipRepository;
import Andreea.Bican.persistence.impl.StudentListMembershipRepositoryHardcoded;

@Service
public class StudentListMembershipServiceImpl implements StudentListMembershipService {

    @Autowired
    private StudentListMembershipRepository studentListMembershipRepository;

    public List<StudentListMembership> getStudentListMemberships(int studentListId) {
        return studentListMembershipRepository.getStudentListMemberships(studentListId);
    }

}
