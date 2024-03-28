package Chat_Folder;

import User_Folder.User;

import javax.imageio.ImageIO;
import java.util.ArrayList;

public class GroupChat implements Chat{
    private User owner;
    private ArrayList<User> members = new ArrayList<>();

    public GroupChat(User owner) {
        this.owner = owner;
    }

    public void addMember(User user) {
        members.add(user);
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
