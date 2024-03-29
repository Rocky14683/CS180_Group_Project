package UserFolder;

import DatabaseFolder.Database;

public class User {
    private String name;
    private final String uniqueID;;
    private String password;
    private UserProfile profile;
    private UserList friends;
    private UserList blackList;

    public User (String name, String uniqueID) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.friends = new UserList(this.uniqueID, true);
        this.blackList = new UserList(this.uniqueID, false);
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

    public String toString() {
        return this.name + "," + this.uniqueID + "," + this.password;
    }

    //writes data to database
    public void saveData() {
        Database.saveUser(this.name, this.uniqueID, this.password);
    }

}
