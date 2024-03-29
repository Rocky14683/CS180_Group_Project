package UserFolder;

import java.util.ArrayList;
import java.util.UUID;

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


}
