package Andreea.Bican.persistence;

import org.springframework.stereotype.Repository;
import Andreea.Bican.model.User;

public interface UserRepository {
    public User getUser(String userId);
}
