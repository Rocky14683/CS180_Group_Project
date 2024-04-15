package Network;

import DatabaseFolder.DataWriter;
import DatabaseFolder.Database;
import UserFolder.User;
import DatabaseFolder.Post;
import DatabaseFolder.Comment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
    private DataWriter database = new DataWriter();
    private PublicChat chat = new PublicChat();

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        while (true) {
            Socket socket = serverSocket.accept();
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
                String message = reader.readLine();
                if (message.equals("client entry")) {   //ensures that methods work for only one client & allow access after login
                    String cmd = reader.readLine();
                    switch (cmd) {
                        // all command goes here

                        case "addFriend": {     //adds given username as friend to current logged in user
                            String username = reader.readLine(); //username of friend
    
                            try {
                                database.setInputObject(new Object[]{username});
                                database.setJob("GetId");
                                String userID = (String) database.getReturnObject();

                                database.setInputObject(new Object[]{userID});
                                database.setJob("RedefineUser");
                            
                                User friend = (User) database.getReturnObject();
                                user.addFriend(friend);
                                writer.println(username + " added as a friend");
                                writer.flush();
                            } catch (Exception e) {
                                writer.println(username + " does not exist");
                                writer.flush();
                            }
                            break;
                        }
                        case "removeFriend": {
                            String username = reader.readLine(); //username of friend
    
                            try {
                                database.setInputObject(new Object[]{username});
                                database.setJob("GetId");
                                String userID = (String) database.getReturnObject();

                                database.setInputObject(new Object[]{userID});
                                database.setJob("RedefineUser");
                            
                                User friend = (User) database.getReturnObject();
                                user.removeFriend(friend);
                                writer.println(username + " removed as a friend");
                                writer.flush();
                            } catch (Exception e) {
                                writer.println(username + " does not exist");
                                writer.flush();
                            }
                            break;
                        }
                        case "blockUser": {
                            String username = reader.readLine(); //username of user to block
    
                            try {
                                database.setInputObject(new Object[]{username});
                                database.setJob("GetId");
                                String userID = (String) database.getReturnObject();

                                database.setInputObject(new Object[]{userID});
                                database.setJob("RedefineUser");
                            
                                User blocked = (User) database.getReturnObject();
                                user.block(blocked);
                                writer.println(username + " has been blocked");
                                writer.flush();
                            } catch (Exception e) {
                                writer.println(username + " does not exist");
                                writer.flush();
                            }
                            break;
                        }
                        case "unblockUser": {
                            String username = reader.readLine(); //username of user to unblock
    
                            try {
                                database.setInputObject(new Object[]{username});
                                database.setJob("GetId");
                                String userID = (String) database.getReturnObject();

                                database.setInputObject(new Object[]{userID});
                                database.setJob("RedefineUser");
                            
                                User blocked = (User) database.getReturnObject();
                                user.unblock(blocked);
                                writer.println(username + " has been unblocked");
                                writer.flush();
                            } catch (Exception e) {
                                writer.println(username + " does not exist");
                                writer.flush();
                            }                            
                            break;
                        }
                        case "updateUsername": {    //updates current user to given info
                            String info = reader.readLine(); //new username
                            
                            database.setInputObject(new Object[]{info});
                            database.setJob("UsernameExists");

                            if ((boolean) database.getReturnObject()) {
                                writer.println("Username already taken, try again");
                                writer.flush();
                            } else {
                                user.setUsername(info, database);
                                writer.println("Username updated successfully");
                                writer.flush();
                            }
                            break;
                        }
                        case "updatePassword": {    //updates current user to given info
                            String info = reader.readLine(); //new password
                            user.setPassword(info, database);
                            writer.println("Password updated successfully");
                            writer.flush();
                            break;
                        }
                        case "updateProfilePic": {    //updates current user to given info
                            String info = reader.readLine(); //new profile picture filename
                            user.setProfilePic(info, database);
                            writer.println("Profile picture updated successfully");
                            writer.flush();
                            break;
                        }
                        case "updateProfileBio": {    //updates current user to given info
                            String info = reader.readLine(); //new bio text
                            user.getProfile.setBio(info);
                            writer.println("Bio updated successfully");
                            writer.flush();
                            break;
                        }

                        case "allUsers": {  //returns an arraylist to string of all usernames
                            try {
                                database.setJob("AllUsers");
                                writer.println( ((ArrayList<String>) database.getReturnObject()).toString());
                                writer.flush();
                            } catch (Exception e) {
                                writer.println("Error");
                                writer.flush();
                            }
                            break;
                        }

                        case "makePost": {      //creates a post
                            String text = reader.readLine(); //new post text
                            String image = reader.readLine(); //new post image text

                            Post post = new Post(text, image, user);

                            database.setInputObject(Object[]{post});
                            database.setJob("MakePost");
                        }

                        case "sendPublicMessage": {
                            String info = reader.readLine(); //message to send
                            sendMessage(message);
                            writer.println("Message sent successfully");
                            writer.flush();
                            break;
                        }
                        case "readPublicMessages": {
                            writer.println(readMessages().toString());  //sends back messages as arraylist to string
                            writer.flush();
                            break;
                        }




                        default: {
                            writer.println("Invalid command");
                            writer.flush();
                            break;
                        }
                    
                    }
                } else if (message.equals("client exit")) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        chat.setInputObject(new Object[]{user, this});
        chat.sendMessage(message);
        chat.start();
    }

    private ArrayList<String> readMessages() {
        chat.setInputObject(new Object[]{user, this});
        chat.readMessages();
        chat.start();

        try {
            return (ArrayList<String>) chat.getReturnObject();  //idk how to fix
        } catch (Exception e) {
            e.printStackTrace();    //threading issue?
        }
    }
}