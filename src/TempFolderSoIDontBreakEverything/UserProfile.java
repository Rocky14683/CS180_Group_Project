//package UserFolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// import org.junit.*;
// import org.junit.runner.RunWith;


/**
 * @Author rocky chen
 * @Version 3/27/2024
 */
public class UserProfile {
    private BufferedImage profilePic; //cache
    private String pfpFileName;
    private String bio;

    public UserProfile() {
    }


    public UserProfile(String pfpFileName, String bio) {
        this.pfpFileName = pfpFileName;
        this.bio = bio;
    }

    public void setProfilePic(BufferedImage profilePic) {
        this.profilePic = profilePic;
    }

    public void setProfilePicName(String pfpFileName) {
        this.pfpFileName = pfpFileName;
    }

    public boolean loadProfilePic() {
        if (this.pfpFileName != null) {
            try {
                profilePic = ImageIO.read(new File(getPfpStorageName()));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public String getPFPFileName() {
        return pfpFileName;
    }

    public String getPfpStorageName() {
        return pfpFileName + ".bmp";
    }

    public BufferedImage getProfilePic() {
        return profilePic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // public static void main(String[] args) {
    //     UserProfileTest test = new UserProfileTest();
    //     test.testUserProfile();
    // }

}

// class UserProfileTest {
//     @Test
//     public void testUserProfile() {
//         UserProfile profile = new UserProfile("pfp", "bio");
//         profile.setProfilePic(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB));
//         profile.loadProfilePic();
//         Assert.assertEquals("pfp", profile.getPFPFileName());
//         Assert.assertEquals("bio", profile.getBio());
//         try {
//             BufferedImage img = ImageIO.read(new File(profile.getPfpStorageName()));
//             Assert.assertNotNull(img);
//         } catch (Exception e) {
//             Assert.fail("Profile pic is null");
//         }
//         System.out.println("Test passed");
//     }
// }
