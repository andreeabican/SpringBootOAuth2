package Andreea.Bican.persistence;

import java.util.List;
import Andreea.Bican.model.StudentListMembership;

public interface StudentListMembershipRepository {
    public List<StudentListMembership> getStudentListMemberships(int studentListId);
}
