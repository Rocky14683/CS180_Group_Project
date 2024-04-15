package Network;

import UserFolder.User;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;


/**
 * Client.java
 * <p>
 * A class that connects to a server and sends inputs to do
 * <p>
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project
 *
 * @author Alexander Popa, lab section 012
 * @version April 7, 2024
 */

public class Client {
    private boolean loggedIn = false;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", Server.SOCKET_PORT);  //connects to server
            Client client = new Client(socket);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            while (true) {
                try {
                    if (!client.loggedIn) {
                        String option = "";// this is where the GUI for login/register will be
                        writer.println(option);
                        switch (option) {
                            case "login":
                                String username = JOptionPane.showInputDialog("Enter username:");
                                String password = JOptionPane.showInputDialog("Enter password:");
                                writer.println(username);
                                writer.println(password);
                                writer.flush();
                                break;
                            case "register":
                                String newUsername = JOptionPane.showInputDialog("Enter new username:");
                                String newPassword = JOptionPane.showInputDialog("Enter new password:");
                                writer.println(newUsername);
                                writer.println(newPassword);
                                writer.flush();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid option");
                                break;
                        }
                        if (!reader.readLine().equals("Invalid command")) {
                            client.loggedIn = true;
                        } else {
                            //UI warning
                            continue;
                        }
                    } else {
                        String cmd = ""; // this is where the GUI for the user actions will be
                        writer.println(cmd);
                        switch (cmd) {
                            // all command goes here

                            case "addFriend": {     //adds given username as friend to current logged in user
                                String userId = ""; //GUI for new userid
                                writer.println(userId);
                                writer.flush();
                                break;
                            }
                            case "removeFriend": {
                                String userId = ""; //GUI for new userid
                                writer.println(userId);
                                writer.flush();
                                break;
                            }
                            case "blockUser": {
                                String userId = ""; //GUI for new userid
                                writer.println(userId);
                                writer.flush();
                                break;
                            }
                            case "unblockUser": {
                                String userId = ""; //GUI for new userid
                                writer.println(userId);
                                writer.flush();
                                break;
                            }
                            case "updateUsername": {    //updates current user to given info
                                String newName = ""; //GUI for new username
                                //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                                writer.println(newName);
                                writer.flush();
                                break;
                            }
                            case "updatePassword": {    //updates current user to given info
                                String newPassword = ""; //GUI for new password
                                writer.println(newPassword);
                                writer.flush();
                                break;
                            }
                            case "updateProfilePic": {    //updates current user to given info
                                String dir = ""; //GUI for new profile picture path
                                writer.println(dir);
                                writer.flush();
                                break;
                            }
                            case "updateProfileBio": {    //updates current user to given info
                                String info = reader.readLine(); //new bio text
                                writer.println(info);
                                writer.flush();
                                break;
                            }
                            case "exit": {
                                writer.flush();
                                break;
                            }
                            default: {
                                writer.println("Invalid command");
                                writer.flush();
                                break;
                            }
                        }
                        //return ACK
                        if (reader.readLine().equals("ACK")) {
                            continue;
                        } else {
                            //UI warning
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); //shouldn't happen
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }


    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }
};
