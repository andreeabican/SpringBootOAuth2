package Andreea.Bican.impl;

import Andreea.Bican.CurrentContextService;
import Andreea.Bican.CurrentUsersService;
import Andreea.Bican.User;
import Andreea.Bican.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    public void logUser(String token, String email) {
        User loggedUser = getLoggedUserByEmail(email);
        if(loggedUser != null)      //There is no logged user with the current email
        {
            updateTokenForLoggedUser(token, email);
        }else{ /* If there is no user logged with current email look in the database
             and check if the email is there*/
            User userFromRepository = getUserFromRepository(email);
            if(userFromRepository != null){
                addUserFromRepository(token, userFromRepository);
            }else{
                addUserFromCurrentContext(token);
            }
        }
    }

    @Override
    public void addUserFromCurrentContext(String token){
        User user = securityCurrentContextService.getUserFromCurrentContext();
        loggedUsersList.put(token, user);
    }

    @Override
    public void addUserFromRepository(String token, User user){
        User currentContextUser = securityCurrentContextService.getUserFromCurrentContext();
        /* There are information that need to be updated, like provider, id
         Those are not provided by the repository*/
        user.setId(currentContextUser.getId());
        user.setProvider(currentContextUser.getProvider());
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
    public User getLoggedUserByEmail(String email){
        for(Map.Entry<String, User> entry : loggedUsersList.entrySet()){
            if(entry.getValue().getEmail().equals(email)){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public User getUserFromRepository(String email){
        User user = userRepository.getUser(email);
        return user;
    }

    @Override
    public void updateTokenForLoggedUser(String newToken, String email){
        String lastToken = null;
        for(Map.Entry<String, User> entry : loggedUsersList.entrySet()){
            if(entry.getValue().getEmail().equals(email)){
                lastToken = entry.getKey();
            }
        }
        if(lastToken != null){
            User user = loggedUsersList.remove(lastToken);
            loggedUsersList.put(newToken, user);
        }
    }

    @Override
    public boolean checkLoggedUser(String token) {
        if(loggedUsersList.containsKey(token)){
            return true;
        }
        return false;
    }

    @Override
    public void deleteLoggedUserByEmail(String email){
        String token = null;
        for(Map.Entry<String, User> entry : loggedUsersList.entrySet()){
            if(entry.getValue().getEmail().equals(email)){
                token = entry.getKey();
            }
        }
        if(token != null){
            deleteLoggedUserByToken(token);
        }
    }
    @Override
    public void deleteLoggedUserByToken(String token) {
        loggedUsersList.remove(token);
    }
}