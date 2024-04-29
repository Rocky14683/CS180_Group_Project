
//package Network;

// import DatabaseFolder.*;
// import UserFolder.User;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server.java
 * <p>
 * A class that starts a server that only allows one client to connect at a time,
 * and allows the client to do actions through GUI, only closes manually
 * <p>
 * Note: more methods and if statements could be added to do other actions
 * <p>
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project
 *
 * @author Alexander Popa, lab section 012
 * @version April 7, 2024
 */

public class Server implements Runnable {
    protected final static int SOCKET_PORT = 1234;
    private User user;

    private Socket socket;
    private static DataWriter dataWriter = new DataWriter();
    BufferedReader reader;
    PrintWriter writer;

    public Server(Socket socket) {
        try {

            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        while (true) {
            System.out.println();
            System.out.println();
            System.out.println("Waiting for connection...");
            Socket socket = serverSocket.accept(); //wait for socket connection established
            System.out.println("User Connected");
            Server server = new Server(socket);
            new Thread(server).start();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                if (user == null) {
                    System.out.println("Waiting for cmd");
                    String cmd = reader.readLine();
                    System.out.println("Command: " + cmd);
                    String username = reader.readLine(); //username
                    System.out.println(username);
                    String password = reader.readLine(); //password
                    System.out.println(password);
                    
                    switch (cmd) {
                        case "login": {
                            System.out.println("login");
                            if (!dataWriter.logIn(username, password)) {
                                System.out.println("Invalid");
                                writer.println("Invalid");
                                writer.flush();
                                break;
                            }
                            String userId = dataWriter.getUserID(username);
                            if (userId.equals("")) { //--------------------------------------------------------
                                System.out.println("Failed to get userid");
                                break;
                            }

                            user = dataWriter.redefineUser(userId);
                            if (user.getUserId().equals("")) { //--------------------------------------------------------
                                System.out.println("Failed to redefine user");
                                break;
                            }
                            System.out.println("valid");
                            writer.println("Login successful");
                            writer.flush();

                            break;
                        }
                        case "register": {
                            try {
                                System.out.println("made it to register");
                                user = new User(username, password);
                                if (!dataWriter.createUser(user)) {
                                    System.out.println("Failed");
                                    writer.println("Invalid username or password");
                                    writer.flush();
                                    return;
                                }
                                System.out.println("Didn't fail");
                                writer.println("Account created successfully");
                                writer.flush();
                                break;
                            } catch (Exception e)
                            {
                                writer.write("Failed");
                                writer.flush();
                            }
                            break;
                        }
                        default: {
                            writer.println("Invalid command");
                            writer.flush();
                            break;
                        }
                    
                    }
                    writer.flush();
                } else {
                    System.out.println("Waiting for cmd");
                    String cmd = reader.readLine();
                    System.out.println("Command: " + cmd);
                    switch (cmd) {
                        // all command goes here

                        case "addFriend": {     //adds given username as friend to current logged in user
                            try {
                                String friendId = reader.readLine(); //userid of friend
                                System.out.println("b4");

                                User friend = dataWriter.redefineUser(friendId);
                                if (friend.getUserId().equals("")) { //--------------------------------------------------------
                                    writer.println("User not found");
                                    writer.flush();
                                    break;
                                }
                                System.out.println("after");
                                dataWriter.addFriends(friend, user);
                                System.out.println(friend.getUsername() + " added as a friend");
                                writer.println(friend.getUsername() + " added as a friend");
                                writer.flush();
                            continue;
                            } catch (Exception e) {
                                writer.println("Blocked");
                                writer.flush();
                            }
                            break;
                        }
                        case "removeFriend": {
                            String friendId = reader.readLine(); //userid of friend

                            User targetFriend = dataWriter.redefineUser(friendId);
                            if (targetFriend.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            dataWriter.removeFriend(targetFriend, user);

                            writer.println(targetFriend.getUsername() + " removed as a friend");
                            writer.flush();
                            break;
                        }
                        case "blockUser": {
                            String userId = reader.readLine(); //userid of friend
                            System.out.println("Block User");
                            User targetUser = dataWriter.redefineUser(userId);
                            if (targetUser.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            
                            //do nothing
                            //removes user as friend (if they are friends
                            System.out.println(targetUser.getUsername() + " has been blocked");
                            dataWriter.blockUser(targetUser, user);
                            writer.println(targetUser.getUsername() + " has been blocked");
                            writer.flush();
                            break;
                        }
                        case "unblockUser": {
                            String userId = reader.readLine(); //userid of friend

                            User targetUser = dataWriter.redefineUser(userId);

                            if (targetUser.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }

                            dataWriter.unblockUser(targetUser, user);
                            writer.println(targetUser.getUsername() + " has been unblocked");
                            writer.flush();
                            break;
                        }
                        case "updateUsername": {    //updates current user to given info
                            String newName = reader.readLine(); //new username
                            //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                            user.setUsername(newName, dataWriter);
                            writer.println("Username updated successfully");
                            writer.flush();
                            break;
                        }
                        case "updatePassword": {    //updates current user to given info
                            String newPassword = reader.readLine(); //new password
                            user.setPassword(newPassword, dataWriter);
                            writer.println("Password updated successfully");
                            writer.flush();
                            break;
                        }

                        case "updateProfileBio": {    //updates current user to given info
                            String info = reader.readLine(); //new bio text
                            user.getProfile().setBio(info);
                            writer.println("Bio updated successfully");
                            writer.flush();
                            break;
                        }

                        case "Search": {
                            System.out.println("Getting to search");
                            String search = reader.readLine();
                            ArrayList<String[]> users = dataWriter.getUsers();
                            String returnString = "";
                            for (String[] s : users) {
                                if (s[0].contains(search)) {

                                    returnString += s[0] + ";";
                                    returnString += s[1] + ";;";
                                }
                            }
                            System.out.println("Sending info to client");
                            
                            if (returnString.length() > 0) {
                                returnString = returnString.substring(0, returnString.length() - 2);
                            }
                            writer.println(returnString);

                            writer.println("Success");
                            writer.flush();
                            break;
                        }

                        case "isFriend": {
                            String maybeFriendId = reader.readLine();
                            User maybeFriend = dataWriter.redefineUser(maybeFriendId);
                            boolean isFriend = dataWriter.isFriend(maybeFriend, user);
                            if (isFriend) {

                                writer.println("friend");
                            } else {

                                writer.println("notFriend");
                            }
                            writer.flush();
                            break;
                        }

                        case "isBlocked": {
                            String maybeBlockedId = reader.readLine();
                            User maybeBlocked = dataWriter.redefineUser(maybeBlockedId);
                            boolean isBlocked = dataWriter.isBlocked(maybeBlocked, user);
                            if (isBlocked) {

                                writer.println("blocked");
                            } else {

                                writer.println("notBlocked");
                            }
                            writer.flush();
                            break;
                        }

                        case "getId": {
                            String username = reader.readLine();
                            String id = dataWriter.getUserID(username);
                            writer.println(id);
                            writer.flush();
                            break;
                        }

                        case "getPosts": {
                            ArrayList<String> postIds = dataWriter.getPosts();
                            System.out.println(postIds);
                            String returnString = "";
                            for (String s : postIds) {
                                Post post = dataWriter.redefinePost(s);
                                returnString += post.getOwner().getUsername() + ";";
                                returnString += post.getText() + ";";
                                returnString += post.getLikes() + ";";
                                returnString += post.getDislikes() + ";";
                                returnString += post.getPostCode() + ";;";
                            }
                            if (returnString.length() > 2) {
                                returnString = returnString.substring(0, returnString.length() - 2);
                            }
                            System.out.println("Before String: " + returnString);
                            writer.println(returnString);
                            writer.flush();
                            break;
                        }

                        case "getComments": {
                            System.out.println("getComments");
                            String postcode = reader.readLine();
                            System.out.println("getComments");
                            Post post = dataWriter.redefinePost(postcode);
                            System.out.println("getComments");
                            ArrayList<String> commentIds = dataWriter.getComments(post);
                            System.out.println("getComments");
                            String returnString = "";
                            for (String s : commentIds) {
                                
                                Comment comment = dataWriter.redefineComment(post, s);
                                returnString += comment.getOwner().getUsername() + ";";
                                returnString += comment.getText() + ";";
                                returnString += comment.getLikes() + ";";
                                returnString += comment.getDislikes() + ";";
                                returnString += comment.getCode() + ";;";
                            }

                             if (returnString.length() > 2) {
                                returnString = returnString.substring(0, returnString.length() - 2);
                            }
                            writer.println(returnString);
                            writer.flush();
                            break;
                        }

                        case "makePost": {
                            String text = reader.readLine();
                            Post post = new Post(text, null, user);
                            dataWriter.makePost(post);

                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "likePost": {
                            String postCode = reader.readLine();
                            Post post = dataWriter.redefinePost(postCode);
                            if (dataWriter.likePost(post, user)) {
                                writer.println("done");
                                System.out.println("Post Succesfully liked");
                            } else {
                                writer.println("-");
                                System.out.println("Liking post failed");
                            }
                            
                            writer.flush();
                            break;
                        }

                        case "unlikePost": {
                            String postCode = reader.readLine();
                            Post post = dataWriter.redefinePost(postCode);
                            dataWriter.unlikePost(post, user);
                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "dislikePost": {
                            String postCode = reader.readLine();
                            Post post = dataWriter.redefinePost(postCode);
                            dataWriter.dislikePost(post, user);
                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "undislikePost": {
                            String postCode = reader.readLine();
                            Post post = dataWriter.redefinePost(postCode);
                            dataWriter.undislikePost(post, user);
                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "hasLiked": {
                            String postcode = reader.readLine();
                            if (dataWriter.hasLiked(dataWriter.redefinePost(postcode), user)) {
                                writer.println("liked");
                            } else {
                                writer.println("noLiked");
                            }
                            writer.flush();

                            break;
                        }

                        case "hasDisliked": {
                            String postcode = reader.readLine();
                            if (dataWriter.hasDisliked(dataWriter.redefinePost(postcode), user)) {
                                writer.println("disliked");
                            } else {
                                writer.println("noDisliked");
                            }
                            writer.flush();

                            break;
                        }

                        case "isOwnerPost" : {
                            String postCode = reader.readLine();

                            boolean owner = dataWriter.redefinePost(postCode).getOwner().equals(user);

                            if (owner) {
                                writer.println("owner");
                            } else {
                                writer.println("notOwner");
                            }

                            writer.flush();
                            break;
                        }

                        case "isHidden": {
                            String postCode = reader.readLine();
                            
                            Post post = dataWriter.redefinePost(postCode);

                            boolean hidden = dataWriter.isHidden(post, user);
                            System.out.println(hidden);

                            if (hidden) {
                                writer.println("hidden");
                            } else {
                                writer.println("no");
                            }    
                            writer.flush();

                            break;
                        }

                        case "deletePost": {
                            String postCode = reader.readLine();

                            Post post = dataWriter.redefinePost(postCode);

                            dataWriter.deletePost(post, user);

                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "deleteComment": {
                            String commentCode = reader.readLine();

                            Comment comment = dataWriter.redefineComment(null, commentCode);

                            dataWriter.deleteComment(comment, user);

                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "hidePost": {
                            String postCode = reader.readLine();

                            Post post = dataWriter.redefinePost(postCode);

                            dataWriter.hidePost(post, user);

                            writer.println("done");
                            writer.flush();
                            break;
                        }

                        case "getInfo": {
                            writer.println(user.getUsername());
                            writer.println(user.getUserId());
                            writer.println(user.getFriends().size());
                            writer.println(user.getProfile().getBio());

                        }

                        case "makeComment": {
                            String postCode = reader.readLine();
                            String text = reader.readLine();

                            Post post = dataWriter.redefinePost(postCode);
                            System.out.println(post);
                            Comment comment = new Comment(post, text, user);
                            dataWriter.makeComment(post, comment);

                            writer.println("done");
                            writer.flush();

                            break;
                        }

                            // is Hidden
                            // Delete post
                            // Delete comment
                            // Hide post
                            // Update user
                            // GetInfo 


                            case "likeComment": {
                                String postCode = reader.readLine();
                                String commentCode = reader.readLine();
                                Post post = dataWriter.redefinePost(postCode);
                                Comment comment = dataWriter.redefineComment(post, commentCode);

                                if (dataWriter.likeComment(comment, user)) {
                                    writer.println("done");
                                    System.out.println("Comment Succesfully liked");
                                } else {
                                    writer.println("-");
                                    System.out.println("Liking comment failed");
                                }
                                
                                writer.flush();
                                break;
                            }
    
                            case "unlikeComment": {
                                String postCode = reader.readLine();
                                String commentCode = reader.readLine();
                                Post post = dataWriter.redefinePost(postCode);
                                Comment comment = dataWriter.redefineComment(post, commentCode);

                                if (dataWriter.unlikeComment(comment, user)) {
                                    writer.println("done");
                                    System.out.println("Comment Succesfully unliked");
                                } else {
                                    writer.println("-");
                                    System.out.println("Unliking comment failed");
                                }
                                
                                writer.flush();
                                break;
                            }

                            case "dislikeComment": {
                                String postCode = reader.readLine();
                                String commentCode = reader.readLine();
                                Post post = dataWriter.redefinePost(postCode);
                                Comment comment = dataWriter.redefineComment(post, commentCode);

                                if (dataWriter.dislikeComment(comment, user)) {
                                    writer.println("done");
                                    System.out.println("Comment Succesfully disliked");
                                } else {
                                    writer.println("-");
                                    System.out.println("disliking comment failed");
                                }
                                
                                writer.flush();
                                break;
                            }

                            case "undislikeComment": {
                                String postCode = reader.readLine();
                                String commentCode = reader.readLine();
                                Post post = dataWriter.redefinePost(postCode);
                                Comment comment = dataWriter.redefineComment(post, commentCode);

                                if (dataWriter.undislikeComment(comment, user)) {
                                    writer.println("done");
                                    System.out.println("Comment Succesfully liked");
                                } else {
                                    writer.println("-");
                                    System.out.println("Liking comment failed");
                                }
                                
                                writer.flush();
                                break;
                            }

                            case "hasLikedComment": {
                                String postCode = reader.readLine();
                                String commentCode = reader.readLine();
                                Post post = dataWriter.redefinePost(postCode);
                                Comment comment = dataWriter.redefineComment(post, commentCode);

                                if (dataWriter.hasLikedComment(comment, user)) {
                                    writer.println("liked");
                                } else {
                                    writer.println("no");
                                }

                                writer.flush();

                                break;
                            }

                            case "hasDislikedComment": {
                                String postCode = reader.readLine();
                                String commentCode = reader.readLine();
                                Post post = dataWriter.redefinePost(postCode);
                                Comment comment = dataWriter.redefineComment(post, commentCode);

                                if (dataWriter.hasDislikedComment(comment, user)) {
                                    writer.println("disliked");
                                } else {
                                    writer.println("no");
                                }

                                writer.flush();

                                break;

                            }

                            case "isOwnerComment": {

                            }

                        case "exit": {
                            socket.close();
                            break;
                        }
                        default: {
                            writer.println("Invalid command" + cmd);
                            writer.flush();
                            break;
                        }
                    }
                }
            } catch (AlreadyThereException | DoesNotExistException | ImNotSureWhyException e) {
                e.printStackTrace();
                writer.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            System.out.println("loop");
        }
        //<= to be implemented
    }

    // private static void publicChat(String message) {    //TO BE IMPLEMENTED

    // }

    // private static void directMessage(String message, User user) {      //TO BE IMPLEMENTED

    // }
}
