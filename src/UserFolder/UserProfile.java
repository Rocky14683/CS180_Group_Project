package UserFolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserProfile {
    private BufferedImage profilePic; //cache
    private String pfpFileName;
    private String bio;

    public UserProfile(String pfpFileName, String bio) {
        this.pfpFileName = pfpFileName;
        this.bio = bio;
    }

    public void setProfilePic(BufferedImage profilePic) {
        this.profilePic = profilePic;
        try {
            ImageIO.write(profilePic, "bmp", new File(this.getPfpStorageDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPFPFileName() {
        return pfpFileName;
    }

    public String getPfpStorageDir() {
        return "../Resources/" + pfpFileName;
    }

    public BufferedImage getProfilePic() {
        return profilePic;
    }

    public String getBio() {
        return bio;
    }

}
