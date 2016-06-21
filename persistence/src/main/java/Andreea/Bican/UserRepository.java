package Andreea.Bican;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by andre on 27.05.2016.
 */
public class UserRepository{
    List<User> listOfUsers;

    public User getUser(String userId) {
        for(User user : listOfUsers){
            if(userId.equals(user.getId())){
                return user;
            }
        }
        return null;
    }

    public UserRepository(){
        listOfUsers = new LinkedList<>();
        User hardcodedUser = new User();
        hardcodedUser.setEmail("andreeasanzianabican@gmail.com");
        hardcodedUser.setId("110493580298130411508");
        hardcodedUser.setUserName("Andreea Sanziana Bican");
        hardcodedUser.setAuthority(true);

        listOfUsers.add(hardcodedUser);
    }
}
