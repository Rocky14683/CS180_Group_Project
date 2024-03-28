package User_Folder;

import java.util.ArrayList;

public class User {
    private String name;
    private final String uniqueID;
    private String password;
    private UserProfile profile;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<User> blackList = new ArrayList<>();

    public User (String name, String uniqueID) {
        this.name = name;
        this.uniqueID = uniqueID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    public void block(User user) {
        blackList.add(user);
    }

    public void unblock(User user) {
        blackList.remove(user);
    }

    public String getName() {
        return name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public boolean checkPassWord(String password) {
        return this.password.equals(password);
    }

    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    public boolean isBlocked(User user) {
        return blackList.contains(user);
    }

    public UserProfile useProfile() {
        return profile;
    }

}
