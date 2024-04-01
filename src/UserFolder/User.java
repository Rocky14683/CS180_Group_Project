package UserFolder;

import DatabaseFolder.Database;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author rocky chen
 * @Version 3/27/2024
 */
public class User {
    private String name;
    private final String uniqueID;
    private String password;
    private UserProfile profile = new UserProfile();
    private UserRelationList friends;
    private UserRelationList blackList;

    public User(String name, String uniqueID) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.friends = new UserRelationList();
        this.blackList = new UserRelationList();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public void setProfilePic(String pfpFileName, BufferedImage image) {
        this.profile.setProfilePicName(pfpFileName);
        this.profile.setProfilePic(image);
    }

    public void addFriend(String id) {
        friends.add(id);
    }

    public void removeFriend(String id) {
        friends.remove(id);
    }

    public void block(String id) {
        blackList.add(id);
    }

    public void unblock(String id) {
        blackList.remove(id);
    }

    public String getName() {
        return name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public UserRelationList getFriends() {
        return friends;
    }

    public UserRelationList getBlackList() {
        return blackList;
    }

    public boolean checkPassWord(String password) {
        return this.password.equals(password);
    }

    public boolean isFriend(String id) {
        return friends.contains(id);
    }

    public boolean isBlocked(String id) {
        return blackList.contains(id);
    }

    public UserProfile useProfile() {
        return profile;
    }

    public String toString() {
        return this.name + "," + this.uniqueID + "," + this.password;
    }

    public static User toUser(String str) {
        String[] strs = str.split(",");
        User ret = new User(strs[0], strs[1]);
        ret.setPassword(strs[2]);
        return ret;
    }

    //writes data to database
    public void saveData() {
        Database.saveUser(this.name, this.uniqueID, this.password);
    }

    public static void main(String[] args) {
        TestUser test = new TestUser();
        test.test();
    }

}


class TestUser {
    @Test
    public void test() {
        User user = UserDataBase.createUser("Test");
        user.setPassword("password");
        user.setProfilePic("pfp", new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB));
        user.useProfile().setBio("bio");
        Assert.assertEquals("Test", user.getName());
        Assert.assertTrue(user.checkPassWord("password"));
        Assert.assertEquals("pfp", user.useProfile().getPFPFileName());
        Assert.assertEquals("bio", user.useProfile().getBio());
        System.out.println("Test passed!");
    }
}