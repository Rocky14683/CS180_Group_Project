
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

                            User targetUser = dataWriter.redefineUser(userId);
                            if (targetUser.getUserId().equals("")) { //--------------------------------------------------------
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            try {
                                dataWriter.removeFriend(targetUser, user);
                            } catch (DoesNotExistException e) {
                            } finally {
                                dataWriter.blockUser(targetUser, user);
                            }
                            //do nothing
                            //removes user as friend (if they are friends

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
