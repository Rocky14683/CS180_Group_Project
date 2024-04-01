package DatabaseFolder;

//alex

import UserFolder.User;
import UserFolder.UserDataBase;
import UserFolder.UserProfile;
import UserFolder.UserRelationList;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

import org.junit.*;

//entire database, each line is the toString() of user class
//everything should be stored in here

/**
 * Database class file formation
 * USERNAME,UNIQUE_ID,PASSWORD
 * USERPROFILE -> Image name if none then "N/A"
 * USERPROFILE -> bio name if none then "N/A"
 * FRIENDS -> ID1,ID2,ID3,ID4,ID5....(no spaces)
 * BLACKLIST -> ID1,ID2,ID3,ID4,ID5....
 * END
 * ... repeat for next user
 */

/**
 * @author Rocky Chen, Alex
 * @version 3/29/2024
 */

public class Database {
    private static final String END = "END";
    private static final String ARROW = " -> ";
    private static final String NA = "N/A";
    private static final String fileName = "database.txt";
    private static ArrayList<User> users = new ArrayList<>();


    public Database() {     //creates database file
        try {
            File createDatabase = new File(Database.fileName);
            if (!createDatabase.exists()) {
                createDatabase.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database already exists"); // singleton
        }
    }


    //reads entire database and resets userlist to what is in txt file
    public static void read() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(fileName));

            ArrayList<User> newList = new ArrayList<User>();
            String line = null;
            while ((line = bfr.readLine()) != null) {
                User usr = User.toUser(line);
                newList.add(usr);
                String profilePic = bfr.readLine().split(ARROW)[1];
                String bio = bfr.readLine().split(ARROW)[1];
                if (profilePic.equals(NA)) {
                    profilePic = "";
                }
                if (bio.equals(NA)) {
                    bio = "";
                }
                usr.setProfile(new UserProfile(profilePic, bio));
                String[] friends = bfr.readLine().split(ARROW)[1].split(",");
                for (String friend : friends) {
                    usr.addFriend(friend);
                }
                String[] blackList = bfr.readLine().split(ARROW)[1].split(",");
                for (String black : blackList) {
                    usr.block(black);
                }
                if (!bfr.readLine().equals(END)) {
                    throw new LoginException("Database format fail");
                }
            }

            users = newList;

            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //writes all the users in arraylist to database file to save data
    public static void write() {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for (User user : users) {
                pw.println(user.toString());
                String profile = user.useProfile().getPFPFileName();
                pw.println("USERPROFILE" + ARROW + (profile.isEmpty() ? NA : profile));
                String bio = user.useProfile().getBio();
                pw.println("USERPROFILE" + ARROW + (bio.isEmpty() ? NA : bio));
                pw.print("FRIENDS" + ARROW);
                for (String friendId : user.getFriends().getIDs()) {
                    pw.print(friendId + ",");
                }
                pw.println();
                pw.print("BLACKLIST" + ARROW);
                for (String blackId : user.getBlackList().getIDs()) {
                    pw.print(blackId + ",");
                }
                pw.println();
                pw.println(END);
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //puts user in arraylist, then writes to database
    public static void saveUser(String name, String uniqueID, String password) {
        for (User user : users) {
            String ID = user.getUniqueID();

            if (uniqueID.equals(ID)) {
                users.remove(user);     //removes user from arraylist in order to write with new data at the end
            }
        }

        User toAdd = new User(name, uniqueID);      //creates new user obj to save
        toAdd.setPassword(password);
        users.add(toAdd);

        write();        //writes new arraylist to file
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public static User searchUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    protected static User getUser(String id) { // not suppose to be use by user
        for (User user : users) {
            if (user.getUniqueID().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Test
    public void testStore() {
        User newUser = UserDataBase.createUser("test");
        newUser.setPassword("abcd1234");
        newUser.setProfile(new UserProfile("test.jpg", "I am a test"));
        User friend = UserDataBase.createUser("friend");
        newUser.addFriend(friend.getUniqueID());
        User blocker = UserDataBase.createUser("friend");
        newUser.block(blocker.getUniqueID());
        users.add(newUser);
        write();
        read();
        String correctString = newUser.toString() + "\n" +
                "USERPROFILE" + ARROW + "test.jpg\n" +
                "USERPROFILE" + ARROW + "I am a test\n" +
                "FRIENDS" + ARROW + friend.getUniqueID() + ",\n" +
                "BLACKLIST" + ARROW + blocker.getUniqueID() + ",\n" +
                END + "\n";

        String correctUserInfo = newUser.toString() + "\n" +
                "USERPROFILE" + ARROW + newUser.useProfile().getProfilePic() + "\n" +
                "USERPROFILE" + ARROW + newUser.useProfile().getBio() + "\n" +
                "FRIENDS" + ARROW + friend.getUniqueID() + "\n" +
                "BLACKLIST" + ARROW + blocker.getUniqueID() + "\n" +
                END + "\n";


        String userInfo = newUser.toString() + "\n" +
                "USERPROFILE" + ARROW + newUser.useProfile().getProfilePic() + "\n" +
                "USERPROFILE" + ARROW + newUser.useProfile().getBio() + "\n" +
                "FRIENDS" + ARROW + newUser.getFriends().toString() + "\n" +
                "BLACKLIST" + ARROW + newUser.getBlackList().toString() + "\n" +
                END + "\n";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            Assert.assertEquals(correctString, sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(correctUserInfo, userInfo);
        System.out.println("Test passed!");
    }
}
