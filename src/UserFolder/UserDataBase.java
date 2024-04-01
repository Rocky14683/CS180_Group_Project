package UserFolder;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Rocky Chen
 * @version 3/27/2024
 */
public class UserDataBase {
    private static ArrayList<User> users = new ArrayList<>();


    public static User createUser(String name) {
        String uniqueID = UUID.randomUUID().toString();
        User newUser = new User(name, uniqueID);
        users.add(newUser);
        return newUser;
    }

    public static ArrayList<User> searchUser(String name) {
        ArrayList<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getName().contains(name)) {
                result.add(user);
            }
        }

        return result;
    }

    protected static User getUser(String id) { // not suppose to be use by user
        for (User user : users) {
            if (user.getUniqueID().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public static void banUser(User user) {
        users.remove(user);
    }

    public static void unbanUser(User user) {
        users.add(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        TestUserDataBase test = new TestUserDataBase();
        test.test();
    }
}

class TestUserDataBase {
    public void test() {
        User user = UserDataBase.createUser("Test");
        Assert.assertEquals("Test", user.getName());
        Assert.assertTrue(UserDataBase.searchUser("Test").contains(user));
        UserDataBase.banUser(user);
        Assert.assertFalse(UserDataBase.getUsers().contains(user));
        UserDataBase.unbanUser(user);
        Assert.assertTrue(UserDataBase.getUsers().contains(user));
        System.out.println("Test passed!");
    }
}
