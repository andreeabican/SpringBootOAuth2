package Andreea.Bican.persistence;

import Andreea.Bican.model.User;

public interface UserRepository {
    public User getUser(String userId);
}
