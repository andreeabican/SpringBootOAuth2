package Andreea.Bican.impl;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import Andreea.Bican.User;
import Andreea.Bican.Authorities;
import Andreea.Bican.UserRepository;
import Andreea.Bican.AuthoritiesRepository;

@Repository
public class UserRepositoryHardcoded implements UserRepository {

    @Autowired
    private UserRepositoryHardcodedProperties userRepostioryHarcodedProperties;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public User getUser(String userEmail) {
        HashMap<String,UserRepositoryHardcodedPropertyUser> users = userRepostioryHarcodedProperties.getUsers();
        if (users == null) {
            return null; // no user properties were set in .yml
        }
        for (String userKey : users.keySet()) {
            if (users.get(userKey).getEmail().equals(userEmail)) {
                User foundUser = new User();
                foundUser.setEmail(users.get(userKey).getEmail());
                foundUser.setName(users.get(userKey).getName());
                String userUuid = users.get(userKey).getUserUuid();
                Set<String> userAuthorities = new HashSet<String>();
                List<Authorities> uuidAuthorities = authoritiesRepository.getAuthorities(userUuid);
                for (Authorities authority : uuidAuthorities) {
                    userAuthorities.add(authority.getAuthority());
                }
                foundUser.setAuthorities(userAuthorities);
                return foundUser;
            }
        }
        return null; // userID not found in .yml properties
    }
}
