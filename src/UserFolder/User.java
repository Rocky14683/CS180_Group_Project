package UserFolder;

import DatabaseFolder.AlreadyThereException;
import DatabaseFolder.BlockedException;
import DatabaseFolder.DataWriter;
import DatabaseFolder.DoesNotExistException;
import DatabaseFolder.ImNotSureWhyException;
import DatabaseFolder.InvalidOperationException;

import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import DatabaseFolder.DataWriter;

import java.util.*;

public class User {
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
        dw.updateUser(this, newUsername, this.password);
        this.password = newUsername;

        this.username = newUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword, DataWriter dw) {
        dw.updateUser(this, this.username, newPassword);
        this.password = newPassword;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void addFriend(User user, DataWriter dw) throws AlreadyThereException, BlockedException, InvalidOperationException, ImNotSureWhyException, DoesNotExistException {
        dw.addFriends(user, this);
        friends.add(user.getUserId());
    }

    public void addFriend(String newFriend) {
        friends.add(newFriend);
    }

    public void setProfile(UserProfile profile, DataWriter dw) {
        dw.setProfile(this, profile.toString());
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

    public void removeFriend(User user, DataWriter dw) throws DoesNotExistException, ImNotSureWhyException {
        dw.removeFriend(user, this);
        friends.remove(user.getUserId());
    }

    public void removeFriend(String oldFriend) {
        friends.remove(oldFriend);
    }

    public void blockUser(User user, DataWriter dw) throws AlreadyThereException, DoesNotExistException, ImNotSureWhyException {
        dw.blockUser(user, this);
        blockedUsers.add(user.getUserId());
    }

    public void blockUser(String newBlock) {
        blockedUsers.add(newBlock);
    }

    public void unblockUser(User user, DataWriter dw) throws DoesNotExistException, ImNotSureWhyException {
        dw.unblockUser(user, this);
        blockedUsers.remove(user.getUserId());

    }

    public void unblockUser(String oldBlock) {
        blockedUsers.remove(oldBlock);
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
            dw.createUser(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User redifneUser(String newUserId, DataWriter dw) throws DoesNotExistException, ImNotSureWhyException {
    
        dw.redefineUser(newUserId);
        return (User) dw.getReturnObject()[0];

    }

}


class TestUser {
    @Test
    public void test() {

    }
}