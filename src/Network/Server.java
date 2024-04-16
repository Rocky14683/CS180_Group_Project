
package Network;

import DatabaseFolder.*;
import UserFolder.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

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
                        //                user = dataWriter.getUserId();
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
                String cmd = reader.readLine();
                switch (cmd) {
                    // all command goes here

                    case "addFriend": {     //adds given username as friend to current logged in user
                        String friendId = reader.readLine(); //userid of friend
                        if (!dataWriter.redefineUser(friendId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User friend = (User) dataWriter.getReturnObject()[0];
                        dataWriter.addFriends(friend, user);

                        writer.println(friend.getUsername() + " added as a friend");
                        writer.flush();
                        break;
                    }
                    case "removeFriend": {
                        String friendId = reader.readLine(); //userid of friend
                        if (!dataWriter.redefineUser(friendId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetFriend = (User) dataWriter.getReturnObject()[0];
                        dataWriter.removeFriend(user, targetFriend);

                        writer.println(targetFriend.getUsername() + " removed as a friend");
                        writer.flush();
                        break;
                    }
                    case "blockUser": {
                        String userId = reader.readLine(); //userid of friend
                        if (!dataWriter.redefineUser(userId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetUser = (User) dataWriter.getReturnObject()[0];
                        dataWriter.removeFriend(user, targetUser); //removes user as friend (if they are friends
                        dataWriter.blockUser(user, targetUser);

                        writer.println(targetUser.getUsername() + " has been blocked");
                        writer.flush();
                        break;
                    }
                    case "unblockUser": {
                        String userId = reader.readLine(); //userid of friend
                        if (!dataWriter.redefineUser(userId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetUser = (User) dataWriter.getReturnObject()[0];
                        dataWriter.unblockUser(user, targetUser);


                        writer.println(targetUser.getUsername() + " removed as a friend");
                        writer.flush();
                        break;
                    }
                    case "updateUsername": {    //updates current user to given info
                        String newName = reader.readLine(); //new username
                        //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                        user.setUsername(newName, this.dataWriter);
                        writer.println("Username updated successfully");
                        writer.flush();
                        break;
                    }
                    case "updatePassword": {    //updates current user to given info
                        String newPassword = reader.readLine(); //new password
                        user.setPassword(newPassword, this.dataWriter);
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

                    case "MakePost": {
                        String text = reader.readLine();
                        Post post = new Post(text, null, user);
                        dataWriter.makePost(post);
                        writer.println("Post made succefully");
                        writer.flush();
                        break;
                    }

                    case "LikePost":
                        String postCode = reader.readLine();
                        if (dataWriter.redefinePost(postCode)) {
                            dataWriter.likePost((Post)dataWriter.getReturnObject()[0], user);
                            writer.println("Post liked");
                            writer.flush();
                        } else {
                            writer.println("Failed to like post");
                            writer.flush();
                        }
                        break;

                    case "UnlikePost":
                        postCode = reader.readLine();
                        if (dataWriter.redefinePost(postCode)) {
                            dataWriter.unlikePost((Post)dataWriter.getReturnObject()[0], user);
                            writer.println("Post unliked");
                            writer.flush();
                        } else {
                            writer.println("Failed to unlike post");
                            writer.flush();
                        }
                        break;

                    case "DislikePost":
                        postCode = reader.readLine();
                        if (dataWriter.redefinePost(postCode)) {
                            dataWriter.dislikePost((Post)dataWriter.getReturnObject()[0], user);
                            writer.println("Post disliked");
                            writer.flush();
                        } else {
                            writer.println("Failed to dislike post");
                            writer.flush();
                        }
                        break;

                    case "UndislikePost":
                        postCode = reader.readLine();
                        if (dataWriter.redefinePost(postCode)) {
                            dataWriter.undislikePost((Post)dataWriter.getReturnObject()[0], user);
                            writer.println("Post undisliked");
                            writer.flush();
                        } else {
                            writer.println("Failed to undislike post");
                            writer.flush();
                        }
                        break;

                    case "HidePost":
                        postCode = reader.readLine();
                        if (dataWriter.redefinePost(postCode)) {
                            dataWriter.hidePost((Post)dataWriter.getReturnObject()[0], user);
                            writer.println("Post hidden");
                            writer.flush();
                        } else {
                            writer.println("Failed to hide post");
                            writer.flush();
                        }
                        break;

                    case "UnhidePost":
                        postCode = reader.readLine();
                        if (dataWriter.redefinePost(postCode)) {
                            dataWriter.unhidePost((Post)dataWriter.getReturnObject()[0], user);
                            writer.println("Post unhidden");
                            writer.flush();
                        } else {
                            writer.println("Failed to unhide post");
                            writer.flush();
                        }
                        break;
                    
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
                writer.println("ACK");
            }
        } catch (AlreadyThereException | ExistingUsernameException | InvalidOperationException |
                 BlockedException | DoesNotExistException | ImNotSureWhyException e) {
            writer.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //<= to be implemented
    }

    private static void publicChat(String message) {    //TO BE IMPLEMENTED

    }

    private static void directMessage(String message, User user) {      //TO BE IMPLEMENTED

    }
}
