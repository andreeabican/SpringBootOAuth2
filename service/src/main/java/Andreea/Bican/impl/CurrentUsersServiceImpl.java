package Andreea.Bican.impl;

import Andreea.Bican.CurrentUsersService;
import Andreea.Bican.User;
import Andreea.Bican.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by andre on 26.06.2016.
 */
@Service
public class CurrentUsersServiceImpl implements CurrentUsersService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("listOfUsers")
    HashMap<String, User> loggedUsersList;

    @Override
    public void addLoggedUser(String token, String email) {
        User user = userRepository.getUser(email);
        loggedUsersList.put(token, user);
    }

    @Override
    public User getLoggedUserByToken(String token) {
        if(loggedUsersList.containsKey(token)){
            return loggedUsersList.get(token);
        }
        return null;
    }

    @Override
    public boolean checkLoggedUser(String token) {
        if(loggedUsersList.containsKey(token)){
            return true;
        }
        return false;
    }

    @Override
    public void deleteLoggedUser(String token) {
        loggedUsersList.remove(token);
    }


}
