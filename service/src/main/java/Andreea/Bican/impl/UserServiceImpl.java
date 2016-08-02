package Andreea.Bican.impl;

import Andreea.Bican.User;
import Andreea.Bican.UserRepository;
import Andreea.Bican.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String email) {
        return userRepository.getUser(email);
    }
}
