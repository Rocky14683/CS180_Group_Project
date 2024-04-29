// package DatabaseFolder;

// import UserFolder.*;

import java.io.*;
import java.util.*;

/**
 * DatabaseFolder.DataWriter
 * <p>
 * Program that writes information about a user to a database
 *
 * @author Lucas Abreu, L12
 * @version 4/1/24
 */

//REMEMBER TO READD THE INTERFACE
public class DataWriter {

    // Gatekeepers
    public static Object gatekeeper = new Object();
    public static ArrayList<String[]> gatekeeperArray = new ArrayList<String[]>();


    // Static
    private static String systemPath = System.getProperty("user.dir") + "/System/";   //Path to the system directory
    private static File systemInfo; //File in system directory containing system info
    private static File userNames; //File with all usernames currently in use
    private static int numUsers = -1;  //Number of total users
    private static File userDirectories; //Folder of all the users
    private static String userPath = System.getProperty("user.dir") + "/UserDirectories/";  //Path the the userDirectory folder
    private static File allPosts;  //Directory containing all posts made on the platform
    private static String postPath = System.getProperty("user.dir") + "/Posts/"; //Path to the posts folder
    private static File[] directories = {systemInfo, userDirectories, allPosts}; //Array of all directories
    private static String[] directoryPaths = {systemPath, userPath, postPath};   //Array of paths to all directories


    public DataWriter() {
        try {
            //Checking if required directories exist and making them if they don't
            for (int i = 0; i < directories.length; i++) {
                if (!(new File(directoryPaths[i]).exists())) {
                    System.out.printf("Missing required directory %s, Creating it now...\n", directoryPaths[i]);
                    directories[i] = new File(directoryPaths[i]);
                    if (directories[i].mkdir()) {
                        System.out.printf("%s file made succesfully\n", directoryPaths[i]);
                    } else {
                        System.out.printf("Error making %s file\n", directoryPaths[i]);
                    }
                }
            }


            systemInfo = new File(systemPath + "SystemInfo");
            if (!systemInfo.exists()) {
                if (systemInfo.createNewFile()) {
                    System.out.println("SystemInfo file created succesfully");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(systemInfo));
                    bw.write("0");
                    bw.flush();
                    bw.close();
                    bw = null;
                }

            }

            userNames = new File(systemPath + "userNames");
            if (!userNames.exists())
                if (userNames.createNewFile()) {
                    System.out.println("Usernames file created succesfully");

                } else {
                    System.out.println("Error making usernames file");
                }


            //Inisializing numUsers
            BufferedReader br = new BufferedReader(new FileReader(systemInfo));
            try {
                synchronized (gatekeeper) {
                    if (numUsers == -1) //Checking if its been initalized yet
                    {
                        numUsers = Integer.valueOf(br.readLine());
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("SystemInfo file formated incorrectly");
            }
            br.close();
            br = null;

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//UserStuff--------------------------------------------------------------------------------------------------

    //Exceptions - AlreadyThereException
    public boolean createUser(User user) throws AlreadyThereException, ExistingUsernameException, ImNotSureWhyException {
        System.out.println();
        System.out.println("Creating user with Id: " + user.getUserId());
        File userData = null;  //File for where user data, Private so multiple datawriters can 
        //write to diffrent userdatas simultaniously
        File friends = null;   //File for where friend's userIds are stored
        File blocked = null;   //File for where blocked userIds are stroed
        File userPosts = null; //File containing "Post codes", post codes used to find posts from other file
        File profile = null;
        File[] files = {userData, friends, blocked, userPosts, profile};  //Array containing a users files
        String[] fileNames = {"userData", "friends", "blocked", "posts", "profile"}; //Names of the users files
        File userDirectory = null; //Directroy with name of the user Id 
        String userDirectoryPath = null; //Path to that directory

        synchronized (gatekeeper) {
            gatekeeperArray.add(new String[]{user.getUserId()});
        }

        if (!usernameExist(user.getUsername())) {
            throw (new ExistingUsernameException("Read the exception name"));
        }

        synchronized (gatekeeperArray) {
            try {

                if (new File(user.getUserId()).exists()) {
                    System.out.println("File already exists");

                    //There is already a folder for the user
                    throw (new AlreadyThereException("UserAlreadyExists"));
                }

                //Creating file in UserDirectories for new user
                userDirectory = new File(userPath + user.getUserId());

                if (!userDirectory.exists() && !userDirectory.mkdir()) {
                    System.out.println("Error occured when making directory");

                    //The userDirectory does not exist and making it failed
                    throw (new ImNotSureWhyException("userDirectroyError"));
                }

                System.out.println("User directory has been made");
                userDirectoryPath = userPath + user.getUserId() + "/";

                for (int i = 0; i < files.length; i++) {
                    files[i] = new File(userDirectoryPath + fileNames[i]);
                    if (!files[i].createNewFile()) {
                        System.out.printf("Error occured when making %s file\n", fileNames[i]);

                        throw (new ImNotSureWhyException("File Writing Error"));
                    }
                    System.out.printf("%s file created succesfully\n", fileNames[i]);
                }

                //Writing data to the userData file
                BufferedWriter bw = new BufferedWriter(new FileWriter(files[0]));
                bw.write(user.getUserId());
                bw.newLine();
                bw.write(user.getUsername());
                bw.newLine();
                bw.write(user.getPassword());
                bw.flush();

                bw.close();
                bw = null;

                //Updating how many users there are
                BufferedReader br = new BufferedReader(new FileReader(systemInfo));
                bw = new BufferedWriter(new FileWriter(systemInfo));
                try {
                    synchronized (gatekeeper) {
                        numUsers++;
                        bw.write(String.valueOf(numUsers));
                        bw.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Error occured when writing the SystemInfo file");
                    e.printStackTrace();
                    bw.close();
                    br.close();
                    bw = null;
                    br = null;
                    throw (new ImNotSureWhyException("SystemInfoError"));
                }

                bw.close();
                br.close();
                bw = null;
                br = null;
                synchronized (gatekeeper) {
                    userNames = new File(directoryPaths[0] + "userNames");

                    bw = new BufferedWriter(new FileWriter(userNames, true));
                    if (!(numUsers == 0)) {
                        bw.newLine();
                    }
                    bw.write(user.getUsername() + ", " + user.getUserId());
                    bw.flush();
                    bw.close();
                    bw = null;
                }
            } catch (IOException e) {
                e.printStackTrace(); //Figure out what to do in this situation
                throw (new ImNotSureWhyException("IOerror"));
            }

            return true;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    //Needs to return User
//Exceptions - DoesNotExistException
    public User redefineUser(String userId) throws DoesNotExistException, ImNotSureWhyException {

        synchronized (gatekeeperArray) {
            //Making sure the user actually exists
            System.out.println(userId);
            if (!(new File(directoryPaths[1] + userId)).exists()) {
                System.out.println("UserId provided does not exist");

                //User thats trying to login does not exist
                throw (new DoesNotExistException("UserDoesNotExist"));
            }

            try {
                File input = new File(directoryPaths[1] + userId + "/userData");
                //Reading User files and making a user object out of them
                BufferedReader br = new BufferedReader(new FileReader(input));

                String line = br.readLine();
                ArrayList<String> lineArray = new ArrayList<String>();

                while (line != null) {
                    lineArray.add(line);
                    line = br.readLine();
                }

                User user = new User(userId, lineArray.get(1), lineArray.get(2));

                br.close();
                br = new BufferedReader(new FileReader(new File(directoryPaths[1] + userId + "/friends")));

                line = br.readLine();
                while (line != null) {
                    user.addFriend(line);
                    line = br.readLine();
                }

                br.close();
                br = new BufferedReader((new FileReader(new File(directoryPaths[1] + userId + "/blocked"))));

                line = br.readLine();
                while (line != null) {
                    user.blockUser(line);
                    line = br.readLine();
                }
                br.close();

                br = new BufferedReader((new FileReader(new File(directoryPaths[1] + userId + "/profile"))));

                line = br.readLine();
                String buffer[] = new String[2];
                for (int i = 0; i < 2 && line != null; i++) {
                    buffer[i] = line;
                    line = br.readLine();
                }
                br.close();
                user.setProfile(buffer[0], buffer[1]);
                user.getProfile().loadProfilePic();

                return user;


            } catch (IOException e) {
                e.printStackTrace();
                throw (new ImNotSureWhyException("IOError"));
            }
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    //Exceptions - AlreadyThereException, BlockedException, InvalidOperationException
    public boolean addFriends(User newFreiend, User user) throws AlreadyThereException, BlockedException, InvalidOperationException, ImNotSureWhyException, DoesNotExistException {

        synchronized (gatekeeperArray) {
            try {
                ArrayList<User> friends = new ArrayList<>();
                ArrayList<String> friendsId = user.getFriends();

                if (friendsId != null) {
                    for (String f : friendsId) {
                        User newUser = redefineUser(f);
                        friends.add(newUser);
                    }
                }

                if (friends != null && friends.contains(newFreiend)) {
                    System.out.println("Friend is already added");
                    throw (new AlreadyThereException("Freind already added"));

                } else if (newFreiend.getBlockedUsers().contains(user.getUserId())) {
                    System.out.println("User you are trying to add has blocked you");
                    throw (new BlockedException("You are blocked"));

                } else if (user.getBlockedUsers().contains(newFreiend.getUserId())) {
                    System.out.println("You have this user blocked, you cannot add them");
                    throw (new BlockedException("Yout have this user blocked"));

                } else if (newFreiend.getUserId().equals(user.getUserId())) {
                    System.out.println("You cannot add yourself as a friend");
                    throw (new InvalidOperationException("You cannot add yourself"));
                }
                user.addFriend(newFreiend.getUserId());

                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(directoryPaths[1]
                        + user.getUserId() + "/friends"), true));
                BufferedReader br = new BufferedReader(new FileReader(new File(directoryPaths[1]
                        + user.getUserId() + "/friends")));

                if (!(br.readLine() == null)) {
                    bw.newLine();
                }

                bw.write(newFreiend.getUserId());
                bw.flush();
                bw.close();
                br.close();

            } catch (IOException e) {
                System.out.println("Unable to add friend");
                throw (new ImNotSureWhyException("IOError"));
            }

            return true;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    //Exceptions - DoesNotExistException
    public boolean removeFriend(User oldFreiendId, User userId) throws DoesNotExistException, ImNotSureWhyException {

        synchronized (gatekeeperArray) {
            ArrayList<String> friends = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(directoryPaths[1]
                        + userId.getUserId() + "/friends")));

                String line = br.readLine();

                while (line != null) {
                    friends.add(line);
                    line = br.readLine();

                }

                if (!friends.remove(oldFreiendId.getUserId())) {
                    System.out.println("User is not firend");
                    br.close();
                    throw (new DoesNotExistException("User is not friend"));
                }
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(directoryPaths[1]
                        + userId.getUserId() + "/friends"), false));

                for (int i = 0; i < friends.size(); i++) {
                    bw.write(friends.get(i));
                    bw.newLine();
                    bw.flush();
                }
                bw.close();

                userId.removeFriend(oldFreiendId.getUserId());
                return true;

            } catch (IOException e) {
                e.printStackTrace();
                throw (new ImNotSureWhyException("IOError"));
            }
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean blockUser(User newBlockId, User userId) throws AlreadyThereException, DoesNotExistException, ImNotSureWhyException {

        synchronized (gatekeeperArray) {
            try {
                if (userId.getBlockedUsers().contains(newBlockId.getUserId())) {
                    System.out.println("This user is already blocked");
                    return false;
                }
                //Check if desired freind has this person blocked
                //Check if user is already friend
                //Check if user is in the file 

                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(directoryPaths[1] +
                        userId.getUserId() + "/blocked"), true));
                BufferedReader br = new BufferedReader(new FileReader(new File(directoryPaths[1] +
                        userId.getUserId() + "/blocked")));
                if (!(br.readLine() == null)) {
                    bw.newLine();
                }
                bw.write(newBlockId.getUserId());
                bw.flush();


                br.close();
                bw.close();
                br = null;
                bw = null;

                try {
                    removeFriend(newBlockId, userId);
                } catch (DoesNotExistException e) {
                    //ignore
                }

                try {
                    removeFriend(userId, newBlockId);
                } catch (DoesNotExistException e) {
                    //ignore
                }


            } catch (IOException e) {
                System.out.println("Unable to add friend");
                return false;
            }

            userId.blockUser(newBlockId.getUserId());
            return true;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean unblockUser(User oldBlock, User user) throws DoesNotExistException, ImNotSureWhyException {

        synchronized (gatekeeperArray) {

            ArrayList<String> blocked = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(
                        new File(directoryPaths[1] + user.getUserId() + "/blocked")));
                String line = br.readLine();
                while (line != null) {
                    blocked.add(line);
                    line = br.readLine();
                }
                br.close();
                br = null;


                if (!blocked.contains(oldBlock.getUserId())) {
                    System.out.println("This user is not blocked");
                    return false;
                }
                blocked.remove(oldBlock.getUserId());

                BufferedWriter bw = new BufferedWriter(new FileWriter(
                        new File(directoryPaths[1] + user.getUserId() + "/blocked"), false));
                for (String s : blocked) {
                    bw.write(s);
                    bw.newLine();
                    bw.flush();
                }
                bw.close();
                bw = null;

                user.unblockUser(oldBlock.getUserId());

                return true;

            } catch (IOException e) {
                System.out.println("Unable to add friend");
                return false;
            }
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean setProfile(User user, String profile) { //I'm not 100% sure what this method does, I need to comment more
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(directoryPaths[1] + user.getUserId() + "/profile"), false));

            bw.write(profile);
            bw.flush();
            bw.close();
            bw = null;

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean updateUser(User user, String username, String password) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    new File(directoryPaths[1] + user.getUserId() + "/userData"), false));

            bw.write(user.getUserId());
            bw.newLine();
            bw.write(username);
            bw.newLine();
            bw.write(password);
            bw.flush();
            bw.close();

            BufferedReader br = new BufferedReader(new FileReader(userNames));
            ArrayList<String[]> usernames = new ArrayList<>();

            String line = br.readLine();
            Boolean foundUser = false;
            while (line != null) {
                usernames.add(line.split(", "));
                if (usernames.get(usernames.size())[0].equals(user.getUsername())) {
                    usernames.set(usernames.size(), new String[]{username, user.getUserId()});
                    foundUser = true;
                    break;
                }
                line = br.readLine();
            }
            br.close();

            if (!foundUser) {
                return false;
            }

            bw = new BufferedWriter(new FileWriter(userNames, true));
            if (!(numUsers == 0)) {
                bw.newLine();
            }
            bw.write(user.getUsername() + ", " + user.getUserId());
            bw.flush();
            bw.close();
            bw = null;

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean logIn(String username, String password) {
        try {

            boolean usernameExists = false;
            String userId = null;
            BufferedReader br = null;


            br = new BufferedReader(new FileReader(userNames));
            String line = br.readLine();

            while (line != null) {
                String[] lineArray = line.split(", ");
                if (lineArray[0].equals(username)) {
                    userId = lineArray[1];
                    usernameExists = true;
                    break;
                }
                line = br.readLine();
            }

            br.close();
            if (!usernameExists) {
                return false;
            }

            String realPassword = null;

            br = new BufferedReader(new FileReader(new File(directoryPaths[1] + userId + "/userData")));

            for (int i = 0; i < 3; i++) {
                realPassword = br.readLine();
            }
            br.close();

            return password.equals(realPassword);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    //returns String
    public String getUserID(String userName) throws DoesNotExistException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(directoryPaths[0] + "userNames"));

            String line = br.readLine();

            while (line != null) {
                String[] lineArray = line.split(", ");
                System.out.println(lineArray[0] + " + " + userName);
                if (lineArray[0].equals(userName)) {
                    br.close();
                    br = null;
                    return lineArray[1];
                }
                line = br.readLine();
            }

            br.close();
            br = null;

        } catch (IOException e) {
            System.out.println("Something is broken");
        }

        throw (new DoesNotExistException("User does not exist"));

    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean usernameExist(String username) throws ImNotSureWhyException {

        synchronized (gatekeeper) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(directoryPaths[0] + "userNames"));

                String line = br.readLine();
                while (line != null) {
                    String[] lineArray = line.split(", ");
                    if (lineArray[0].equals(username)) {
                        br.close();
                        return false;
                    }
                    line = br.readLine();
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
                throw (new ImNotSureWhyException("IOError"));
            }
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    //Alot of this is the same as createUser but just diffrent files
    public boolean makePost(Post post) {
        File postDirectory = null;
        File likes = null;
        File dislikes = null;
        File text = null;
        File image = null;
        File postInfo = null;
        File hiddenFrom = null;
        File[] files = {likes, dislikes, text, image, postInfo, hiddenFrom};
        String[] fileNames = {"likes", "dislikes", "text", "image", "postInfo", "hiddenFrom"};
        File comments = null;
        String postDirectoryPath = null;

        try {

            if (new File(post.getPostCode()).exists()) {
                System.out.println("The Post already exists");

                return false;
            }

            postDirectory = new File(postPath + post.getPostCode());

            post.setPostPath(postDirectory);

            if (!postDirectory.exists() && !postDirectory.mkdir()) {
                System.out.println("Error occured when making directory");

                //The userDirectory does not exist and making it failed
                return false;
            }

            System.out.println("Post directory has been made");
            postDirectoryPath = postPath + post.getPostCode() + "/";
            System.out.println();
            System.out.println();
            System.out.println("PATHHH: " + postDirectoryPath);

            for (int i = 0; i < files.length; i++) {
                files[i] = new File(postDirectoryPath + fileNames[i]);
                if (!files[i].createNewFile()) {
                    System.out.printf("Error occured when making %s file\n", fileNames[i]);

                    throw (new ImNotSureWhyException("File Writing Error"));
                }
                System.out.printf("%s file created succesfully\n", fileNames[i]);
            }

            comments = new File(postDirectoryPath + "comments");
            if (!comments.mkdir()) {
                System.out.printf("Directroy couln't be made");

                throw (new ImNotSureWhyException("File Writing Error"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postDirectoryPath + fileNames[2]));
            bw.write(post.getText());
            bw.flush();
            bw.close();
            bw = null;

            bw = new BufferedWriter(new FileWriter(directoryPaths[1] + post.getOwner().getUserId() + "/posts"));

            bw.write(post.getPostCode());
            bw.close();

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    //Return a post
    public Post redefinePost(String code) throws DoesNotExistException, ImNotSureWhyException {
        String thisPostPath = postPath + code;
        File postDirectory = new File(thisPostPath);

        if (!postDirectory.exists()) {
            return new Post();
        }

        String ownerId = code.split("-")[0];

        User owner = redefineUser(ownerId);

        ArrayList<User> likes = new ArrayList<>();
        ArrayList<User> dislikes = new ArrayList<>();
        String text = "";
        ArrayList<User> hiddenFrom = new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(thisPostPath + "/likes"));
            String line = br.readLine();


            while (line != null) {
                likes.add(redefineUser(line));
                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader(thisPostPath + "/dislikes"));
            line = br.readLine();

            while (line != null) {
                dislikes.add(redefineUser(line));
                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader(thisPostPath + "/text"));
            text = br.readLine();

            br.close();
            br = new BufferedReader(new FileReader(thisPostPath + "/hiddenFrom"));
            line = br.readLine();

            while (line != null) {
                hiddenFrom.add(redefineUser(line));
                line = br.readLine();
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
            return new Post();
        }

        return new Post(owner, text, likes, dislikes, hiddenFrom, comments, code);
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean likePost(Post post, User liker) throws AlreadyThereException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/likes"));

            System.out.println("Test Like post");
            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() + "/likes", true));
            bw.write(liker.getUserId());
            bw.newLine();
            bw.flush();
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean unlikePost(Post post, User unliker) throws DoesNotExistException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/likes"));
            String line = br.readLine();

            ArrayList<String> likeIds = new ArrayList<>();
            while (line != null) {
                System.out.println(unliker.getUserId() + ", " + line);
                if (line.equals(unliker.getUserId())) {

                } else {
                    likeIds.add(line);
                }
                line = br.readLine();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() + "/likes"));

            for (String s : likeIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("Post Unliked");
        return true;

    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean dislikePost(Post post, User disliker) throws AlreadyThereException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/dislikes"));
            String line = br.readLine();

            while (line != null) {
                if (line.equals(disliker.getUserId())) {
                    br.close();
                    br = null;
                    throw (new AlreadyThereException("User already disliked post"));
                }
                line = br.readLine();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() + "/dislikes", true));
            bw.write(disliker.getUserId());
            bw.newLine();
            bw.flush();
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            return false;
        }

        return true;

    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean undislikePost(Post post, User undisliker) throws DoesNotExistException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/dislikes"));
            String line = br.readLine();

            boolean disliked = false;
            ArrayList<String> dislikeIds = new ArrayList<>();
            while (line != null) {
                if (line.equals(undisliker.getUserId())) {
                    disliked = true;
                } else {
                    dislikeIds.add(line);
                }
                line = br.readLine();
            }

            if (!disliked) {
                br.close();
                br = null;
                throw (new DoesNotExistException("User has not disliked post"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() + "/dislikes"));

            for (String s : dislikeIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean hidePost(Post post, User user) throws AlreadyThereException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/hiddenFrom"));

            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    br.close();
                    br = null;
                    throw (new AlreadyThereException("Post is already hidden"));
                }
                line = br.readLine();
            }
            br.close();
            br = null;

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() + "/hiddenFrom"));

            bw.write(user.getUserId());
            bw.newLine();
            bw.flush();
            bw.close();
            bw = null;

        } catch (IOException e) {
            return false;
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean unhidePost(Post post, User user) throws DoesNotExistException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/hiddenFrom"));
            String line = br.readLine();

            boolean hidden = false;
            ArrayList<String> hiddenIds = new ArrayList<>();
            while (line != null) {
                if (line.equals(user.getUserId())) {
                    hidden = true;
                } else {
                    hiddenIds.add(line);
                }
                line = br.readLine();
            }

            if (!hidden) {
                br.close();
                br = null;
                throw (new DoesNotExistException("User has not hidden this post"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() + "/hiddenFrom"));

            for (String s : hiddenIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean deletePost(Post post, User user) throws DoesNotExistException, ImNotSureWhyException {

        if (!user.equals(post.getOwner())) {
            System.out.println("Cannot delete post you don't own");
            return false;
        }


        File postDirectory = new File(postPath + post.getPostCode());
        File commentDirectroy = new File(postDirectory + "/" + "comments");
        String[] comments = commentDirectroy.list();

        System.out.println(comments.length);
        for (String s : comments) {
            System.out.println("1");
            Comment comment = redefineComment(post, s);
            System.out.println("2");
            deleteComment(comment, user);
            System.out.println("3");
        }
        commentDirectroy.delete();
        String[] files = postDirectory.list();

        for (String s : files) {
            File file = new File(postDirectory + "/" + s);
            file.delete();
        }
        postDirectory.delete();

        try {
            BufferedReader br = new BufferedReader(new FileReader("userDirectories" + "/" + user.getUserId() + "/posts"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("userDirectories" + "/" + user.getUserId() + "/posts"));

            String line = br.readLine();
            ArrayList<String> postCodes = new ArrayList<>();
            while (line != null) {
                if (!line.equals(post.getPostCode())) {
                    postCodes.add(line);
                }
            }

            for (String s : postCodes) {
                bw.write(s);
                bw.newLine();
            }

            bw.flush();

            br.close();
            bw.close();
            br = null;
            bw = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean makeComment(Post post, Comment comment) {
        File likes = null;
        File dislikes = null;
        File text = null;
        File[] files = {likes, dislikes, text};
        String[] fileNames = {"likes", "dislikes", "text"};

        try {
            File commentDirectory = new File(post.getPostPath() + "/comments/" + comment.getCode());
            comment.setCommentPath(commentDirectory);
            System.out.println("Comment Directory: " + commentDirectory);
            if (!commentDirectory.mkdir()) {
                System.out.println("making comment directory failed");
            }

            for (int i = 0; i < files.length; i++) {
                files[i] = new File(commentDirectory + "/" + fileNames[i]);

                if (!files[i].createNewFile()) {
                    System.out.println("Error making " + fileNames[i] + " file");
                } else {
                    System.out.println(fileNames[i] + " file made succesfully");
                }
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(commentDirectory + "/" + fileNames[2]));

            bw.write(comment.getText());
            bw.flush();

            bw.close();
            bw = null;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean likeComment(Comment comment, User user) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(comment.getCommentPath() + "/likes"));

            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    System.out.println("Already liked this comment");
                    br.close();
                    br = null;
                    return false;
                }

                line = br.readLine();
            }
            br.close();
            br = null;

            BufferedWriter bw = new BufferedWriter(new FileWriter(comment.getCommentPath() + "/likes", true));

            bw.write(user.getUserId());
            bw.newLine();
            bw.flush();

            bw.close();
            bw = null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean dislikeComment(Comment comment, User user) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(comment.getCommentPath() + "/dislikes"));

            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    System.out.println("Already liked this comment");
                    br.close();
                    br = null;
                    return false;
                }

                line = br.readLine();
            }
            br.close();
            br = null;

            BufferedWriter bw = new BufferedWriter(new FileWriter(comment.getCommentPath() + "/dislikes", true));

            bw.write(user.getUserId());
            bw.newLine();
            bw.flush();

            bw.close();
            bw = null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean unlikeComment(Comment comment, User unliker) throws DoesNotExistException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(comment.getCommentPath() + "/likes"));
            String line = br.readLine();

            boolean liked = false;
            ArrayList<String> likeIds = new ArrayList<>();
            while (line != null) {
                if (line.equals(unliker.getUserId())) {
                    liked = true;
                } else {
                    likeIds.add(line);
                }
                line = br.readLine();
            }

            if (!liked) {
                br.close();
                br = null;
                throw (new DoesNotExistException("User has not liked comment"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(comment.getCommentPath() + "/likes"));

            for (String s : likeIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            return false;
        }

        return true;

    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean undislikeComment(Comment comment, User unliker) throws DoesNotExistException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(comment.getCommentPath() + "/dislikes"));
            String line = br.readLine();

            boolean liked = false;
            ArrayList<String> likeIds = new ArrayList<>();
            while (line != null) {
                if (line.equals(unliker.getUserId())) {
                    liked = true;
                } else {
                    likeIds.add(line);
                }
                line = br.readLine();
            }

            if (!liked) {
                br.close();
                br = null;
                throw (new DoesNotExistException("User has not disliked comment"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(comment.getCommentPath() + "/dislikes"));

            for (String s : likeIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch (IOException e) {
            return false;
        }

        return true;

    }

//PostStuff--------------------------------------------------------------------------------------------------

    public Comment redefineComment(Post post, String code) throws DoesNotExistException, ImNotSureWhyException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(post.getPostPath() + "/comments/" + code + "/likes"));

            String line = br.readLine();
            ArrayList<User> likes = new ArrayList<>();
            while (line != null) {
                User userLike = redefineUser(line);
                likes.add(userLike);

                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader(post.getPostPath() + "/comments/" + code + "/dislikes"));

            line = br.readLine();
            ArrayList<User> dislikes = new ArrayList<>();
            while (line != null) {
                User userDislike = redefineUser(line);
                dislikes.add(userDislike);

                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader(post.getPostPath() + "/comments/" + code + "/text"));

            String text = br.readLine();

            br.close();
            br = null;

            String[] codeArray = code.split("-");
            User owner = redefineUser(codeArray[0]);

            return new Comment(likes, dislikes, text, owner, post, code);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Comment();
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean deleteComment(Comment comment, User user) {

        if (!(user.equals(comment.getParent().getOwner()) || user.equals(comment.getOwner()))) {
            System.out.println("Only post owner and comment owner can delete this comment");
            return false;
        }
        File commentPath = comment.getCommentPath();

        String[] files = commentPath.list();

        for (String s : files) {
            File newFile = new File(commentPath + "/" + s);
            newFile.delete();
        }
        commentPath.delete();

        return true;
    }

    //Return all users
    public ArrayList<String[]> getUsers() throws ImNotSureWhyException{

        try {
            ArrayList<String[]> allUsers = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(userNames));
            String line = br.readLine();

            line = br.readLine();

            while (line != null) {
                String[] lineArray = line.split(", ");
                allUsers.add(lineArray);

                line = br.readLine();
            }

            br.close();
            br = null;
            return allUsers;

        } catch (IOException e) {
            throw (new ImNotSureWhyException("IOexception in getUsers"));
        }

    }

    public ArrayList<String> getPosts() {


        String[] allPostIds = new File(directoryPaths[2]).list();
        ArrayList<String> allPostIdsButArrayList = new ArrayList<>();

        for (String s : allPostIds) {
            allPostIdsButArrayList.add(s);
        }

        return allPostIdsButArrayList;
    }

    public ArrayList<String> getComments(Post post) {
        File commentFolder = new File(post.getPostPath() + "/comments");
        System.out.println("Post Path: " + commentFolder);

        String[] allPostIds = commentFolder.list();
        if (allPostIds == null) {
            allPostIds = new String[0];
        }
        System.out.println(allPostIds.length);
        ArrayList<String> allCommentsButArrayList = new ArrayList<>();
        
        for (String s : allPostIds) {
            allCommentsButArrayList.add(s);
        }

        return allCommentsButArrayList;
    }

    public boolean isFriend(User maybeFriend, User user) throws DoesNotExistException, ImNotSureWhyException {

        ArrayList<User> friends = new ArrayList<>();
        ArrayList<String> friendsId = user.getFriends();

        if (friendsId != null) {
            for (String f : friendsId) {
                User newUser = redefineUser(f);
                friends.add(newUser);
            }
        }

        return friends.contains(maybeFriend);


        
    }

    public boolean isBlocked (User maybeBlocked, User user) {
        ArrayList<String> blocked = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                new File(directoryPaths[1] + user.getUserId() + "/blocked")));
            String line = br.readLine();
            while (line != null) {
                blocked.add(line);
                line = br.readLine();
            }
            br.close();
            br = null;


            return blocked.contains(maybeBlocked.getUserId());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
                
    }

    public int getNumPosts() {
        File posts = new File (directoryPaths[2]);

        return posts.list().length;
    }

    public boolean hasLiked(Post post, User user) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/likes"));
            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    br.close();
                    br = null;
                    return true;
                }
                line = br.readLine();
            }
            br.close();
            br = null;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return false;

    }

    public boolean hasDisliked(Post post, User user) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/dislikes"));
            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    br.close();
                    br = null;
                    return true;
                }
                line = br.readLine();
            }
            br.close();
            br = null;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return false;

    }

    public boolean isHidden(Post post, User user) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/hiddenFrom"));

            String line = br.readLine();

            while (line != null) {
                System.out.println("Line, isHidden: " + line);
                if (line.equals(user.getUserId())) {
                    br.close();
                    br = null;
                    return true;
                }
                line = br.readLine();
            }
            br.close();
            br = null;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    public boolean hasLikedComment(Comment comment, User user) {
        try {
        BufferedReader br = new BufferedReader(new FileReader(comment.getCommentPath() + "/likes"));

            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    System.out.println("Already liked this comment");
                    br.close();
                    br = null;
                    return true;
                }

                line = br.readLine();
            }
            br.close();
            br = null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean hasDislikedComment(Comment comment, User user) {
        try {
        BufferedReader br = new BufferedReader(new FileReader(comment.getCommentPath() + "/dislikes"));

            String line = br.readLine();

            while (line != null) {
                if (line.equals(user.getUserId())) {
                    System.out.println("Already liked this comment");
                    br.close();
                    br = null;
                    return true;
                }

                line = br.readLine();
            }
            br.close();
            br = null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

//StaticMethods----------------------------------------------------------------------------------------------

    public static int getNumUsers() {
        synchronized (gatekeeper) {
            return numUsers; // Do I need to synchronize to just return value?
        }
    }

}
