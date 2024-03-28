package Chat_Folder;

import javax.imageio.ImageIO;

public interface Chat {
    public void sendMessage(String message);
    public void receiveMessage(String message);
    public void sendImage(ImageIO image);
    public void receiveImage(ImageIO image);
    public void sendFile(String file);
    public void receiveFile(String file);
}
