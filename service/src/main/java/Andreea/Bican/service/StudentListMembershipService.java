package Andreea.Bican.service;

import java.util.List;
import Andreea.Bican.model.StudentListMembership;

public interface StudentListMembershipService {
    public List<StudentListMembership> getStudentListMemberships(int studentListId);
}
