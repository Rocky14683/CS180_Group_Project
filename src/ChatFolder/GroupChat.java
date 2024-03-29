package ChatFolder;

import UserFolder.User;

import javax.imageio.ImageIO;
import java.util.ArrayList;

public class GroupChat implements Chat {
    private ArrayList<User> members = new ArrayList<>();
    private String groupName;

    //multiple people chat
    public GroupChat(ArrayList<User> members, String groupName) {
        this.members = members;
        this.groupName = groupName;
    }

    public GroupChat(User owner, String groupName) {
        this.members.add(owner);
        this.groupName = groupName;
    }

    public void addMember(User user) {
        members.add(user);
        this.groupName = user.getName().split(" ")[0] + "'s group chat";
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void receiveMessage(String message) {

    }

    @Override
    public void sendImage(ImageIO image) {

    }

    @Override
    public void receiveImage(ImageIO image) {

    }

    @Override
    public void sendFile(String file) {

    }

    @Override
    public void receiveFile(String file) {

    }
}
