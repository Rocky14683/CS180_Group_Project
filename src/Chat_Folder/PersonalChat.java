package Chat_Folder;

import User_Folder.User;

import javax.imageio.ImageIO;

public class PersonalChat implements Chat{
    private User users[] = new User[2];

    public PersonalChat(User user1, User user2) {
        users[0] = user1;
        users[1] = user2;
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
