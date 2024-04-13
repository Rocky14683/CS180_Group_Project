package UserFolder;

import DatabaseFolder.DataWriter;

import java.util.ArrayList;

/**
 * UserFolder.IUserOperations
 * <p>
 * Interface for the User class
 *
 * @author Lucas Abreu, L12
 * @version 4/1/24
 */

public interface IUserOperations {

    //Getters and setters
    public String getUserId();

    public String getUsername();

    public void setUsername(String username, DataWriter dw);

    public String getPassword();

    public void setPassword(String password, DataWriter dw);

    public ArrayList<String> getFriends();

    public ArrayList<String> getBlockedUsers();

    //Everything else
    void addFriend(User user, DataWriter dw);

    void addFriend(String userId);

    void removeFriend(User user, DataWriter dw);

    void removeFriend(String userId);

    void blockUser(User user, DataWriter dw);

    void blockUser(String userId);

    void unblockUser(User user, DataWriter dw);

    void unblockUser(String userId);

    String toString();

    boolean equals(Object o);

    boolean writeUser(DataWriter dw);


    //maybe more operations later
}