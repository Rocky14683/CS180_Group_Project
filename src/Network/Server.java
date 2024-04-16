
package Network;

import DatabaseFolder.*;
import UserFolder.User;

import java.io.*;
import java.net.*;


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
    private User user = null;

    private Socket socket;
    private static DataWriter dataWriter = new DataWriter();
    BufferedReader reader;
    PrintWriter writer;

    public Server(Socket socket) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
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
                    System.out.println(cmd);
                    String username = reader.readLine(); //username
                    System.out.println(username);
                    String password = reader.readLine(); //password
                    System.out.println(password);
                    switch (cmd) {
                        case "login": {
                            System.out.println();
                            if (!dataWriter.logIn(username, password)) {
                                System.out.println("Invalid");
                                writer.println("Invalid username or password");
                                writer.flush();
                                return;
                            }
                            String userId = dataWriter.getUserID(username);
                            if (userId.equals("")) { //--------------------------------------------------------
                                System.out.println("Failed to get userid");
                                return;
                            }

                            user = dataWriter.redefineUser(userId);
                            if (user.getUserId().equals("")) { //--------------------------------------------------------
                                System.out.println("Failed to redefine user");
                                return;
                            }
                            System.out.println("valid");
                            writer.println("Login successful");
                            break;
                        }
                        case "register": {
                            user = new User(username, password);
                            if (!dataWriter.createUser(user)) {
                                writer.println("Invalid username or password");
                                writer.flush();
                                return;
                            }
                            writer.println("Account created successfully");
                            break;
                        }
                        default: {
                            writer.println("Invalid command");
                            break;
                        }
                    }
                    writer.flush();
                } else {
                    System.out.println("Waiting for cmd");
                    String cmd = reader.readLine();
                    switch (cmd) {
                        // all command goes here

                        case "addFriend": {     //adds given username as friend to current logged in user
                            String username = reader.readLine(); //username of friend
                            String userId = dataWriter.getUserID(username);
                            User friend = dataWriter.redefineUser(userId);

                            if (friend.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            dataWriter.addFriends(friend, user);
                            writer.println(friend.getUsername() + " added as a friend");
                            writer.flush();
                            continue;
                        }
                        case "removeFriend": {
                            String username = reader.readLine(); //username of friend
                            String userId = dataWriter.getUserID(username);
                            User targetUser = dataWriter.redefineUser(userId);
                            if (targetUser.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            dataWriter.removeFriend(targetUser, user);

                            writer.println(targetUser.getUsername() + " removed as a friend");
                            writer.flush();
                            break;
                        }
                        case "blockUser": {
                            String username = reader.readLine(); //username of friend
                            String userId = dataWriter.getUserID(username);
                            User targetUser = dataWriter.redefineUser(userId);
                            if (targetUser.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (!dataWriter.blockUser(targetUser, user)) {
                                throw new BlockedException("User is already blocked");
                            }

                            //do nothing
                            //removes user as friend (if they are friends

                            writer.println(targetUser.getUsername() + " has been blocked");
                            writer.flush();
                            break;
                        }
                        case "unblockUser": {
                            String username = reader.readLine(); //username of friend
                            String userId = dataWriter.getUserID(username);
                            User targetUser = dataWriter.redefineUser(userId);

                            if (targetUser.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }

                            if (!dataWriter.unblockUser(targetUser, user)) {
                                throw new BlockedException("User is not blocked");
                            }
                            writer.println(targetUser.getUsername() + " has been unblocked");
                            writer.flush();
                            break;
                        }
                        case "updateUsername": {    //updates current user to given info
                            String newName = reader.readLine(); //new username

                            if (dataWriter.usernameExist(newName)) {
                                writer.println("Username already taken");
                                writer.flush();
                            } else {
                                user.setUsername(newName, dataWriter);
                                writer.println("Username updated successfully");
                                writer.flush();
                            }
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

                        case "searchUser": {    //searches for a username
                            String username = reader.readLine(); //username
                            if (dataWriter.usernameExist(username)) {
                                String userId = dataWriter.getUserID(username);
                                User userSearched = dataWriter.redefineUser(userId);

                                //username, bio, friendslist, blockedlist, profile picture file name

                                writer.println(username + ", Bio: " + userSearched.getProfile().getBio() +
                                        ", Friends: " + userSearched.getFriends().toString() +
                                        ", Blocked: " + userSearched.getBlockedUsers().toString() +
                                        ", Profile Picture: " + userSearched.getProfile().getPFPFileName());
                            } else {
                                writer.println("Username: " + username + " does not exist");
                                writer.flush();
                            }

                            break;

                        }

                        case "makePost": {
                            String text = reader.readLine(); //text for post
                            String image = reader.readLine(); //image for post

                            Post post = new Post(text, image, user);
                            dataWriter.makePost(post);

                            writer.println("Post made successfully");
                            writer.flush();
                            break;
                        }

                        case "likePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.likePost(dataWriter.redefinePost(postCode), user)) { //likes post
                                writer.println("Post liked successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be liked");
                                writer.flush();
                            }
                            break;
                        }
                        case "unlikePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.unlikePost(dataWriter.redefinePost(postCode), user)) { //unlikes post
                                writer.println("Post unliked successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be unliked");
                                writer.flush();
                            }
                            break;
                        }
                        case "dislikePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.dislikePost(dataWriter.redefinePost(postCode), user)) { //dislikes post
                                writer.println("Post disliked successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be disliked");
                                writer.flush();
                            }
                            break;
                        }
                        case "undislikePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.undislikePost(dataWriter.redefinePost(postCode), user)) { //undislikes post
                                writer.println("Post undisliked successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be undisliked");
                                writer.flush();
                            }
                            break;
                        }
                        case "hidePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.hidePost(dataWriter.redefinePost(postCode), user)) { //hides post
                                writer.println("Post hidden successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be hidden");
                                writer.flush();
                            }
                            break;
                        }
                        case "unhidePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.unhidePost(dataWriter.redefinePost(postCode), user)) { //unhides post
                                writer.println("Post unhidden successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be unhidden");
                                writer.flush();
                            }
                            break;
                        }
                        case "deletePost": {
                            String postCode = reader.readLine(); //postcode from client
                            if (dataWriter.deletePost(dataWriter.redefinePost(postCode), user)) { //deletes post
                                writer.println("Post deleted successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be deleted");
                                writer.flush();
                            }
                            break;
                        }


                        case "makeComment": {
                            String postCode = reader.readLine(); //postcode from client
                            String comment = reader.readLine(); //comment from client
                            Post post = dataWriter.redefinePost(postCode);
                            Comment cmt = new Comment(post, comment, user);
                            if (dataWriter.makeComment(dataWriter.redefinePost(postCode), cmt)) { //makes comment
                                writer.println("Post commented to successfully");
                                writer.flush();
                            } else {
                                writer.println("Post cannot be commented to");
                                writer.flush();
                            }
                            break;
                        }

                        case "likeComment": {
                            String postCode = reader.readLine();    //postcode from client
                            String commentCode = reader.readLine(); //commentcode from client
                            Post post = dataWriter.redefinePost(postCode);
                            Comment comment = dataWriter.redefineComment(post, commentCode);
                            if (dataWriter.likeComment(comment, user)) { //likes comment
                                writer.println("Comment liked successfully");
                                writer.flush();
                            } else {
                                writer.println("Comment cannot be liked");
                                writer.flush();
                            }
                            break;
                        }
                        case "unlikeComment": {
                            String postCode = reader.readLine();    //postcode from client
                            String commentCode = reader.readLine(); //commentcode from client

                            Post post = dataWriter.redefinePost(postCode); //redefines post
                            Comment comment = dataWriter.redefineComment(post, commentCode);
                            if (dataWriter.unlikeComment(comment, user)) { //unlikes comment
                                writer.println("Comment unliked successfully");
                                writer.flush();
                            } else {
                                writer.println("Comment cannot be unliked");
                                writer.flush();
                            }
                            break;
                        }
                        case "dislikeComment": {
                            String postCode = reader.readLine();    //postcode from client
                            String commentCode = reader.readLine(); //commentcode from client
                            Post post = dataWriter.redefinePost(postCode); //redefines post

                            Comment comment = dataWriter.redefineComment(post, commentCode);
                            if (dataWriter.dislikeComment(comment, user)) { //dislikes comment
                                writer.println("Comment disliked successfully");
                                writer.flush();
                            } else {
                                writer.println("Comment cannot be disliked");
                                writer.flush();
                            }
                            break;
                        }
                        case "undislikeComment": {
                            String postCode = reader.readLine();    //postcode from client
                            String commentCode = reader.readLine(); //commentcode from client
                            Post post = dataWriter.redefinePost(postCode); //redefines post

                            Comment comment = dataWriter.redefineComment(post, commentCode);
                            if (dataWriter.undislikeComment(comment, user)) { //undislikes comment
                                writer.println("Comment undisliked successfully");
                                writer.flush();
                            } else {
                                writer.println("Comment cannot be undisliked");
                                writer.flush();
                            }
                            break;
                        }
                        case "deleteComment": {
                            String postCode = reader.readLine();    //postcode from client
                            String commentCode = reader.readLine(); //commentcode from client
                            Post post = dataWriter.redefinePost(postCode); //redefines post

                            Comment comment = dataWriter.redefineComment(post, commentCode);
                            if (dataWriter.deleteComment(comment, user)) { //deletes comment
                                writer.println("Comment deleted successfully");
                                writer.flush();
                            } else {
                                writer.println("Comment cannot be deleted");
                                writer.flush();
                            }
                            break;
                        }

                        case "searchPost": {
                            String postCode = reader.readLine();
                            Post post = dataWriter.redefinePost(postCode);
                            if (post.getLikes() == -1) {    //indicates null post
                                writer.println("Post does not exist");
                                writer.flush();
                            } else {
                                String returnString = post.toString() + "| Comments: ";
                                for (Comment comment : post.getComments()) {
                                    returnString = returnString + comment.toString() + "|";
                                }
                                writer.println(returnString);
                                writer.flush();
                            }
                            break;
                        }

                        case "searchComment": {
                            String postCode = reader.readLine();
                            String commentCode = reader.readLine();
                            Post post = dataWriter.redefinePost(postCode);
                            Comment comment = dataWriter.redefineComment(post, commentCode);
                            if (comment.getLikes() == -1) {    //indicates null post
                                writer.println("Comment does not exist");
                                writer.flush();
                            } else {
                                writer.println(comment.toString());
                                writer.flush();
                            }
                            break;
                        }


                        case "exit": {
                            socket.close();
                            break;
                        }
                        default: {
                            writer.println("Invalid command");
                            writer.flush();
                            break;
                        }
                    }
                }
            } catch (AlreadyThereException | ExistingUsernameException | InvalidOperationException |
                     BlockedException | DoesNotExistException | ImNotSureWhyException e) {
//                e.printStackTrace();
                writer.println("Failed " + e.getMessage());
                writer.flush();
                System.out.println("Failed " + e.getMessage());
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