
package Network;

import DatabaseFolder.DataWriter;
import DatabaseFolder.Database;
import UserFolder.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static DataWriter database = new DataWriter();

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        while (true) {
            Socket socket = serverSocket.accept(); //wait for socket connection established
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            if (user == null) {
                String cmd = reader.readLine();
                String username = reader.readLine(); //username
                String password = reader.readLine(); //password
                switch (cmd) {
                    case "login": {
                        if (!database.logIn(username, password)) {
                            writer.println("Invalid username or password");
                            writer.flush();
                            return;
                        }
                        //                user = database.getUserId();
                        writer.println("Login successful");
                        break;
                    }
                    case "register": {
                        user = new User(username, password);
                        if (!database.createUser(user)) {
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
                        if (!database.redefineUser(friendId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User friend = (User) database.getReturnObject();
                        database.addFriends(friend, user);

                        writer.println(friend.getUsername() + " added as a friend");
                        writer.flush();
                        break;
                    }
                    case "removeFriend": {
                        String friendId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(friendId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetFriend = (User) database.getReturnObject();
                        database.removeFriend(user, targetFriend);

                        writer.println(targetFriend.getUsername() + " removed as a friend");
                        writer.flush();
                        break;
                    }
                    case "blockUser": {
                        String userId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(userId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetUser = (User) database.getReturnObject();
                        database.removeFriend(user, targetUser); //removes user as friend (if they are friends
                        database.blockUser(user, targetUser);

                        writer.println(targetUser.getUsername() + " has been blocked");
                        writer.flush();
                        break;
                    }
                    case "unblockUser": {
                        String userId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(userId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetUser = (User) database.getReturnObject();
                        database.unblockUser(user, targetUser);


                        writer.println(targetUser.getUsername() + " removed as a friend");
                        writer.flush();
                        break;
                    }
                    case "updateUsername": {    //updates current user to given info
                        String newName = reader.readLine(); //new username
                        //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                        user.setUsername(newName, this.database);
                        writer.println("Username updated successfully");
                        writer.flush();
                        break;
                    }
                    case "updatePassword": {    //updates current user to given info
                        String newPassword = reader.readLine(); //new password
                        user.setPassword(newPassword, this.database);
                        writer.println("Password updated successfully");
                        writer.flush();
                        break;
                    }
                    case "updateProfilePic": {    //updates current user to given info
                        String dir = reader.readLine(); //new profile picture filename
                        user.getProfile().setProfilePicName(dir);
                        if (!user.getProfile().loadProfilePic()) {
                            writer.println("Profile directory not found");
                            writer.flush();
                            break;
                        }

                        writer.println("Profile picture updated successfully");
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
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    private static void publicChat(String message) {    //TO BE IMPLEMENTED

    }

    private static void directMessage(String message, User user) {      //TO BE IMPLEMENTED

    }
}
