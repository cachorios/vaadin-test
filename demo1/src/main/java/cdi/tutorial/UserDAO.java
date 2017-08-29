package cdi.tutorial;

import java.util.List;

/**
 * Created by cachorios on 27/08/2017.
 */
public interface UserDAO {
    public User getUserBy(String username, String password);
    public User getUserBy(String username);

    public boolean saveUser(User user);

    public List<User> getUsers();

}
