package ChatFolder;

import UserFolder.User;

import javax.imageio.ImageIO;
import java.util.ArrayList;

/**
 * @author rocky chen
 * @version 3/29/2024
 */
public class PersonalChat implements Chat {
    private User[] users = new User[2];
    private String chatName;

    //two people chat
    public PersonalChat(User user1, User user2) {
        this.users[0] = user1;
        this.users[1] = user2;
        this.chatName = user1.getName().split(" ")[0] + " and " + user2.getName().split(" ")[0];
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
