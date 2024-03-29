package UserFolder;

import javax.imageio.ImageIO;

public class UserProfile {
    private String profilePicture;
    private String bio;

    public UserProfile(String profilePicture, String bio) {
        this.profilePicture = profilePicture;
        this.bio = bio;
    }

    public String getProfilePicFile() {
        return profilePicture;
    }

    public String getBio() {
        return bio;
    }
}
