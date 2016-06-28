package Andreea.Bican.impl;

import Andreea.Bican.CurrentContextService;
import Andreea.Bican.CurrentUsersService;
import Andreea.Bican.User;
import Andreea.Bican.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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

    @Autowired
    CurrentContextService securityCurrentContextService;

    @Override
    public void addLoggedUser(String token, String email) {
        User user = userRepository.getUser(email);
        User currentContextUser = securityCurrentContextService.getUserFromCurrentContext();

        if(user!=null) {
            user.setToken(currentContextUser.getToken());
            user.setProvider(currentContextUser.getProvider());
            user.setId(currentContextUser.getId());
            loggedUsersList.put(token, user);
        }else{
            //Unknown user by system
            currentContextUser.setAuthorities(new HashSet<String>(Arrays.asList("GUEST")));
            loggedUsersList.put(token, currentContextUser);
        }
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
