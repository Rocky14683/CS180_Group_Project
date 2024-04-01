package ChatFolder;

import javax.imageio.ImageIO;

/**
 * @author rocky chen
 * @version 3/27/2024
 */
public interface Chat {
    public void sendMessage(String message);

    public void receiveMessage(String message);

    public void sendImage(ImageIO image);

    public void receiveImage(ImageIO image);

    public void sendFile(String file);

    public void receiveFile(String file);
}
