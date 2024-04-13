package UserFolder;

import DatabaseFolder.DataWriter;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import DatabaseFolder.DataWriter;

import java.util.*;

public class User implements IUserOperations {
    private String userId;
    private String username;
    private String password; //hashing in future for security
    //private String posts;
    private ArrayList<String> friends;
    private ArrayList<String> blockedUsers;

    private UserProfile profile = new UserProfile();

    public static void main(String[] args) {
        TestUser test = new TestUser();
        test.test();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) { //New user
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<String>();
        this.blockedUsers = new ArrayList<String>();

        this.userId = String.format("%08d", DataWriter.getNumUsers() + 1);

    }

    public User(String userId, String username, String password) { //already existing user
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
        this.userId = userId;
    }

    public User() //Invalid user
    {
        this.username = "Invalid";
        this.password = "";
        this.friends = null;
        this.blockedUsers = null;
        this.userId = "";
    }

    public String toString() {
        return String.format("<Username: %s, UserId: %s, Password: %s>", username, userId, password);
    }

    public String getUserId() {

        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername, DataWriter dw) {
        dw.setInputObject(new Object[]{this, newUsername, this.password});
        dw.setJob("UpdateUser");
        dw.start();

        this.username = newUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword, DataWriter dw) {
        dw.setInputObject(new Object[]{this, this.username, newPassword});
        dw.setJob("UpdateUser");
        dw.start();
        this.password = newPassword;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void addFriend(User user, DataWriter dw) {
        dw.setInputObject(new Object[]{user, this});
        dw.setJob("AddFriend");
        dw.start();
    }

    public void addFriend(String newUserId) {
        friends.add(newUserId);
    }

    public void setProfile(UserProfile profile, DataWriter dw) {
        dw.setInputObject(new Object[]{this, profile});
        dw.setJob("SetProfile");
        dw.start();
        this.profile = profile;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(String dir, String bio) {
        this.profile.setProfilePicName(dir);
        this.profile.setBio(bio);
    }

    public void setProfilePic(String pfpFileName, BufferedImage image) {
        this.profile.setProfilePicName(pfpFileName);
        this.profile.setProfilePic(image);
    }

    public void removeFriend(User user, DataWriter dw) {
        dw.setInputObject(new Object[]{user, this});
        dw.setJob("RemoveFriend");
        dw.start();

    }

    public void removeFriend(String newUserId) {
        friends.remove(newUserId);
    }

    public void blockUser(User user, DataWriter dw) {
        dw.setInputObject(new Object[]{user, this});
        dw.setJob("BlockUser");
        dw.start();
    }

    public void blockUser(String newUserId) {
        blockedUsers.add(newUserId);
    }

    public void unblockUser(User user, DataWriter dw) {
        dw.setInputObject(new Object[]{user, this});
        dw.setJob("UnBlockUser");
        dw.start();

    }

    public void unblockUser(String newUserId) {
        blockedUsers.remove(newUserId);
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User check = (User) o;

        return userId.equals(check.getUserId());

    }

    public boolean writeUser(DataWriter dw) {
        try {
            dw.setInputObject(new Object[]{this});
            dw.setJob("CreateUser");
            dw.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User redifneUser(String newUserId, DataWriter dw) {
        try {
            dw.setInputObject(new Object[]{newUserId});
            dw.setJob("RedefineUser");
            dw.start();

            dw.join();
            return (User) dw.getReturnObject();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new User();
        }
    }

}


class TestUser {
    @Test
    public void test() {

    }
}