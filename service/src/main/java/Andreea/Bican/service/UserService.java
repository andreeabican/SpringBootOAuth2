package Andreea.Bican.service;

import org.springframework.stereotype.Service;
import Andreea.Bican.model.User;

public interface UserService {
    public User getUser(String userId);
}
