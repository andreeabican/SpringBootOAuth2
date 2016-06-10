package Andreea.Bican.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.User;
import Andreea.Bican.service.UserService;
import Andreea.Bican.persistence.UserRepository;
import Andreea.Bican.persistence.impl.UserRepositoryHardcoded;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.getUser(userId);
    }
}
