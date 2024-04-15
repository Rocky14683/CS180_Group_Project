package DatabaseFolder;
import UserFolder.*;
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
public class DataWriter extends Thread  {

    // Gatekeepers
    public static Object gatekeeper = new Object();
    public static ArrayList<String[]> gatekeeperArray = new ArrayList<String[]>();

    // Dynamic
    private Object[] inputObject;
    private Object[] returnObject; //Threads can't return things so it will modify this field and then this
    // field can be accesed to "return" 
    private String requiredJob; //You can't pass parameters to the run function so this
    // is my work around, you define it before starting thread


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

//----------------------------------------------------------------------------------------------------------

    public void run() {
        switch (this.requiredJob) //Switch case to decide what job the thread is completeing
        {
            case ("CreateUser"):

                if (!(inputObject[0] instanceof User)) {
                    System.out.println("inputObject is not a user");
                    returnObject = new Object[] {new InvalidInputObject("inputObject is not a user")};
                    break;
                }
                User newUser = (User) inputObject[0];

                try {
                    if (this.createUser(newUser)) {
                        System.out.println("User created succesfully");
                    } else {
                        System.out.printf("User could not be created");
                    }
                } catch(ImNotSureWhyException e) {
                    returnObject = new Object[] {e};
                } catch(AlreadyThereException e) {
                    returnObject = new Object[] {e};
                } catch(ExistingUsernameException e) {
                    returnObject = new Object[] {e};
                    System.out.println("UsernameAlreadyExists");
                }
                break;

        //-----------------------

            case ("RedefineUser"):
                if ((inputObject.length == 0)) {
                    System.out.println("Input object inisilized incorrectly");
                    returnObject = new Object[] {new InvalidInputObject("inputObject is not a string")};

                }
                if (!(inputObject[0] instanceof String)) {
                    System.out.println("inputObject is not a string");
                    returnObject = new Object[] {new InvalidInputObject("Input object inisilized incorrectly")};
                    break;
                }
                String newUserId = (String) inputObject[0];

                try {
                    redefineUser(newUserId);
                } catch (DoesNotExistException e) {
                    returnObject = new Object[] {e, new User()};

                } catch (ImNotSureWhyException e) {
                    returnObject = new Object[] {e, new User()};
                }

                break;

        //-----------------------

            case ("AddFriend"):
                for (int i = 0; i < inputObject.length; i++) {
                    if (!(inputObject[i] instanceof User)) {
                        System.out.println("inputObject is not a user");
                        returnObject = new Object[] {new InvalidInputObject("inputObject is not a user")};
                        break;
                    }
                }

                User newFriend = (User) inputObject[0];
                User user = (User) inputObject[1];

                try {
                    addFriends(newFriend, user);
                    
                } catch(ImNotSureWhyException e) {
                    returnObject = new Object[] {e};
                } catch(AlreadyThereException e) {
                    returnObject = new Object[] {e};
                } catch(BlockedException e) {
                    returnObject = new Object[] {e};
                } catch(InvalidOperationException e) {
                    returnObject = new Object[] {e};
                } catch(DoesNotExistException e) {
                    returnObject = new Object[] {e};
                }

                System.out.println("Friend succesfully added");
                returnObject = new Object[] {true};
                break;

        //-----------------------

            case ("RemoveFriend"):
                for (int i = 0; i < inputObject.length; i++) {
                    if (!(inputObject[i] instanceof User)) {
                        System.out.println("inputObject is not a user");
                        break;
                    }
                }

                User oldFriend = (User) inputObject[0];
                user = (User) inputObject[1];

                try{
                    removeFriend(oldFriend, user);
                    
                } catch (DoesNotExistException e) {
                    returnObject = new Object[] {e};
                } catch (ImNotSureWhyException e) {
                    returnObject = new Object[] {e};
                }

                System.out.println("Friend succesfully removed");
                break;

        //-----------------------

            case ("BlockUser"):
                for (int i = 0; i < inputObject.length; i++) {
                    if (!(inputObject[i] instanceof User)) {
                        System.out.println("inputObject is not a user");
                        break;
                    }
                }

                User newBlock = (User) inputObject[0];
                user = (User) inputObject[1];

                try {
                    if (!blockUser(newBlock, user)) {
                        break;
                    }
                } catch(AlreadyThereException e) {
                    returnObject = new Object[] {e};
                } catch(DoesNotExistException e) {
                    returnObject = new Object[] {e};
                } catch(ImNotSureWhyException e) {
                    returnObject = new Object[] {e};
                }

                System.out.println("User succesfully blocked");
                break;

        //-----------------------

            case ("UnBlockUser"):
                for (int i = 0; i < inputObject.length; i++) {
                    if (!(inputObject[i] instanceof User)) {
                        System.out.println("inputObject is not a user");
                        break;
                    }
                }

                User oldBlock = (User) inputObject[0];
                user = (User) inputObject[1];

                try {
                    if (!unblockUser(oldBlock, user)) {
                        break;
                    }
                } catch(ImNotSureWhyException e) {
                    returnObject = new Object[] {e};
                } catch(DoesNotExistException e) {
                    returnObject = new Object[] {e};
                }

                System.out.println("User succesfully unblocked");
                break;

        //-----------------------

            case ("UpdateUser"):
                if (!(inputObject[0] instanceof User)) {
                    System.out.println("inputObject[0] is not a user");
                    break;
                }
                for (int i = 1; i < inputObject.length; i++) {
                    {
                        if (!(inputObject[i] instanceof String)) {
                            System.out.println("inputObject is not a user");
                            break;
                        }
                    }
                }
                user = (User) inputObject[0];
                String newUsername = (String) inputObject[1];
                String newPassword = (String) inputObject[2];

                if (!updateUser(user, newUsername, newPassword)) {
                    System.out.println("Unable to update user");
                    break;
                }
                System.out.println("User updated succesfully");
                break;
            
        //-----------------------

            case ("SetProfile"):

                if (!(inputObject[0] instanceof User)) {
                    System.out.println("inputObject[0] is not a user");
                    break;
                }

                if (!(inputObject[1] instanceof String)) {
                    System.out.println("inputObject[1] is not a user");
                    break;
                }

                user = (User) inputObject[0];
                String profile = (String) inputObject[1];
                if (!setProfile(user, profile)) {
                    break;
                }

                System.out.println("Profile successfully set");
                break;
                
            case ("CheckLogin"):
                for (Object o : inputObject) {
                    if (!(o instanceof String)) {
                        System.out.println("Username or password not strings");
                        break;
                    }
                }
                String userName = (String)inputObject[0];
                String password = (String)inputObject[1];

                returnObject = new Object[] {(logIn(userName, password))};
                break;

            case ("GetId"):
                
                if (!(inputObject[0] instanceof String)) {
                    System.out.println("input object isn't a string");
                }

                userName = (String)inputObject[0];

                if (!(getUserID(userName))) {
                    break;
                }

                System.out.println("Success!");
                break;

            case ("UsernameExists"):
                if(!(inputObject[0] instanceof String)) {
                    System.out.println("Input object not string");
                }

                userName = (String)inputObject[0];

                try {
                returnObject = new Object[] {usernameExist(userName)};

                } catch (ImNotSureWhyException e) {
                    e.printStackTrace();
                }

            case ("MakePost"):        
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object not a Post");
                }

                Post post = (Post)inputObject[0];

                if (!(makePost(post))) {
                    System.out.println("Post Failed");
                    break;
                }

                System.out.println("Success");
                break;

            case ("RedefinePost"):
                if(!(inputObject[0] instanceof String)) {
                    System.out.println("Input object not a string");
                }

                String postCode = (String)inputObject[0];

                try {
                    if(!(redefinePost(postCode))) {
                        System.out.println("Error redefining post");
                        break;
                    }

                    System.out.println("Post Redefined");
                    break;
                } catch (DoesNotExistException | ImNotSureWhyException e) {
                    e.printStackTrace();
                }

            case("LikePost"):
                System.out.println();
                System.out.println("Trying to like post:");
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object[0] not a Post");
                    break;
                }

                if(!(inputObject[1] instanceof User)) {
                    System.out.println("Input object[1] not a User");
                    break;
                }

                post = (Post)inputObject[0];
                user = (User)inputObject[1];

                try {
                    if(!(likePost(post, user))) {
                        System.out.println("Failed to like post");
                        break;
                    }
                    System.out.println("Post liked");
                    break;

                } catch (AlreadyThereException e) {
                    e.printStackTrace();
                }


            case("UnlikePost"):
                System.out.println();
                System.out.println("Trying to unlike post:");
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object[0] not a Post");
                    break;
                }

                if(!(inputObject[1] instanceof User)) {
                    System.out.println("Input object[1] not a User");
                    break;
                }

                post = (Post)inputObject[0];
                user = (User)inputObject[1];

                try {
                    if(!(unlikePost(post, user))) {
                        System.out.println("Failed to like post");
                        break;
                    }
                    System.out.println("Post liked");
                    break;

                } catch (DoesNotExistException e) {
                    e.printStackTrace();
                }


            case("DisikePost"):
                System.out.println();
                System.out.println("Trying to dislike post:");
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object[0] not a Post");
                    break;
                }

                if(!(inputObject[1] instanceof User)) {
                    System.out.println("Input object[1] not a User");
                    break;
                }

                post = (Post)inputObject[0];
                user = (User)inputObject[1];

                try {
                    if(!(dislikePost(post, user))) {
                        System.out.println("Failed to dislike post");
                        break;
                    }
                    System.out.println("Post disliked");
                    break;
                } catch (AlreadyThereException e) {
                    e.printStackTrace();
                }

            case("UndislikePost"):
                System.out.println();
                System.out.println("Trying to undislike post:");
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object[0] not a Post");
                    break;
                }

                if(!(inputObject[1] instanceof User)) {
                    System.out.println("Input object[1] not a User");
                    break;
                }

                post = (Post)inputObject[0];
                user = (User)inputObject[1];

                try {
                    if(!(undislikePost(post, user))) {
                        System.out.println("Failed to undislike post");
                        break;
                    }
                    System.out.println("Post undisliked");
                    break;
                } catch (DoesNotExistException e) {
                    e.printStackTrace();
                }  
                
                case("HidePost"):
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object[0] not a Post");
                }

                if(!(inputObject[1] instanceof User)) {
                    System.out.println("Input object[1] not a User");
                }

                post = (Post)inputObject[0];
                user = (User)inputObject[1];

                try {
                    if(!(hidePost(post, user))) {
                        System.out.println("Failed to hide post");
                        break;
                    }
                    System.out.println("Post hidden");
                    break;
                } catch (AlreadyThereException e) {
                    e.printStackTrace();
                }

            case("UnhidePost"):
                if(!(inputObject[0] instanceof Post)) {
                    System.out.println("Input object[0] not a Post");
                }

                if(!(inputObject[0] instanceof User)) {
                    System.out.println("Input object[1] not a User");
                }

                post = (Post)inputObject[0];
                user = (User)inputObject[1];

                try {
                    if(!(unhidePost(post, user))) {
                        System.out.println("Failed to unhide post");
                        break;
                    }
                    System.out.println("Post u hidden");
                    break;
                } catch (DoesNotExistException e) {
                    e.printStackTrace();
                }

            default:
                System.out.println("Not a valid job for DataWrtier: (" + requiredJob + ")");
        
            
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

//Exceptions - AlreadyThereException
    public boolean createUser(User user) throws AlreadyThereException, ExistingUsernameException, ImNotSureWhyException{
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
        int index = 0;

        synchronized (gatekeeper) {
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(user.getUserId())) {
                    index = i;
                }
            }
        }

        if (!usernameExist(user.getUsername())) {
            throw (new ExistingUsernameException("Read the exception name"));
        }

        synchronized (gatekeeperArray.get(index)) {
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

//Exceptions - DoesNotExistException
    public boolean redefineUser(String userId) throws DoesNotExistException, ImNotSureWhyException {
        int index = 0;
        synchronized (gatekeeper) {
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(userId)) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {
            //Making sure the user actually exists
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

                returnObject = new Object[] {user};

                return true;


            } catch (IOException e) {
                e.printStackTrace();
                throw (new ImNotSureWhyException("IOError"));
            }
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

//Exceptions - AlreadyThereException, BlockedException, InvalidOperationException
    public boolean addFriends(User newFreiend, User user) throws AlreadyThereException, BlockedException, InvalidOperationException, ImNotSureWhyException, DoesNotExistException {
        int index = 0;
        synchronized (gatekeeper) {
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(user.getUserId())) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {
            try {
                ArrayList<User> friends = new ArrayList<>();
                ArrayList<String> friendsId = user.getFriends();

                if (friendsId != null) {
                    for (String f : friendsId) {
                        if (f != null && redefineUser(f)) {
                            User newUser = (User)returnObject[0];
                            friends.add(newUser);
                        }
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
        int index = 0;
        synchronized (gatekeeper) {
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(userId.getUserId())) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {
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

                userId.addFriend(oldFreiendId.getUserId());
                return true;

            } catch (IOException e) {
                e.printStackTrace();
                throw (new ImNotSureWhyException("IOError"));
            }
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean blockUser(User newBlockId, User userId) throws AlreadyThereException, DoesNotExistException, ImNotSureWhyException {
        int index = 0;
        synchronized (gatekeeper) {
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(userId.getUserId())) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {
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

                
                removeFriend(newBlockId, userId);
                removeFriend(userId, newBlockId);

            } catch (IOException e) {
                System.out.println("Unable to add friend");
                return false;
            }

            userId.blockUser(newBlockId.getUserId());
            return true;
        }
    }

//UserStuff--------------------------------------------------------------------------------------------------

    public boolean unblockUser(User oldBlock, User user) throws DoesNotExistException, ImNotSureWhyException{
        int index = 0;
        synchronized (gatekeeper) {
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(user.getUserId())) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {

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
        System.out.println("Hits login"); 
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

    public Boolean getUserID(String userName) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(directoryPaths[0] + "userNames"));

            String line = br.readLine();

            while (line != null) {
                String[] lineArray = line.split(", ");
                if (lineArray[0].equals(userName)) {
                    br.close();
                    br = null;
                    returnObject = new Object[] {lineArray[1]};
                }
                line = br.readLine();
            }

            br.close();
            br = null;

        } catch (IOException e) {
            System.out.println("Something is broken");
        }

        return false;
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

            if (!postDirectory.exists() && !postDirectory.mkdir()) {
                System.out.println("Error occured when making directory");

                //The userDirectory does not exist and making it failed
                return false;
            }

            System.out.println("Post directory has been made");
            postDirectoryPath = postPath + post.getPostCode() + "/";

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

        } catch(Exception e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean redefinePost(String code) throws DoesNotExistException, ImNotSureWhyException {
        String thisPostPath = postPath + code;
        File postDirectory = new File(thisPostPath);

        if (!postDirectory.exists()) {
            return false;
        }

        String ownerId = code.split("-")[0];

        redefineUser(ownerId);
        User owner = (User)returnObject[0];

        ArrayList<User> likes = new ArrayList<>();
        ArrayList<User> dislikes = new ArrayList<>();
        String text = "";
        ArrayList<User> hiddenFrom = new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(thisPostPath + "/likes"));
            String line = br.readLine();

            
            while (line != null) {
                redefineUser(line);
                likes.add((User)returnObject[0]);
                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader(thisPostPath + "/dislikes"));
            line = br.readLine();

            while (line != null) {
                redefineUser(line);
                dislikes.add((User)returnObject[0]);
                line = br.readLine();
            }

            br.close();
            br = new BufferedReader(new FileReader(thisPostPath + "/text"));
            text = br.readLine();

            br.close();
            br = new BufferedReader(new FileReader(thisPostPath + "/hiddenFrom"));
            line = br.readLine();

            while (line != null) {
                redefineUser(line);
                hiddenFrom.add((User)returnObject[0]);
                line = br.readLine();
            }

            br.close();

        } catch(IOException e) {
            return false;
        }

        returnObject = new Object[] {new Post(owner, text, likes, dislikes, hiddenFrom, comments)};
        
        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean likePost(Post post, User liker) throws AlreadyThereException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/likes"));
            String line = br.readLine();

            while (line != null) {
                if (line.equals(liker.getUserId())) {
                    br.close();
                    br = null;
                    throw (new AlreadyThereException("User already liked post"));
                }
                line = br.readLine();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() +"/likes", true));
            bw.write(liker.getUserId());
            bw.newLine();
            bw.flush();
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch(IOException e) {
            return false;
        }

        return true;

    }

//PostStuff--------------------------------------------------------------------------------------------------

    public boolean unlikePost(Post post, User unliker) throws DoesNotExistException {

        try {
            BufferedReader br = new BufferedReader(new FileReader(postPath + post.getPostCode() + "/likes"));
            String line = br.readLine();

            boolean liked = false;
            ArrayList<String> likeIds= new ArrayList<>();
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
                throw(new DoesNotExistException("User has not liked post"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() +"/likes"));

            for(String s : likeIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch(IOException e) {
            return false;
        }

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

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() +"/dislikes", true));
            bw.write(disliker.getUserId());
            bw.newLine();
            bw.flush();
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch(IOException e) {
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
            ArrayList<String> dislikeIds= new ArrayList<>();
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
                throw(new DoesNotExistException("User has not disliked post"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() +"/dislikes"));

            for(String s : dislikeIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch(IOException e) {
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
            ArrayList<String> hiddenIds= new ArrayList<>();
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
                throw(new DoesNotExistException("User has not hidden this post"));
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(postPath + post.getPostCode() +"/hiddenFrom"));

            for(String s : hiddenIds) {
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            bw = null;
            br.close();
            br = null;
        } catch(IOException e) {
            return false;
        }

        return true;
    }

//PostStuff--------------------------------------------------------------------------------------------------



//OtherThings------------------------------------------------------------------------------------------------
    public void setJob(String job) {
        this.requiredJob = job;
    }

    public Object[] getReturnObject() {
        return returnObject;
    }

    public void setInputObject(Object[] object) {
        inputObject = object;
    }


//
//StaticMethods----------------------------------------------------------------------------------------------

    public static int getNumUsers() {
        synchronized (gatekeeper) {
            return numUsers; // Do I need to synchronize to just return value?
        }
    }

}
