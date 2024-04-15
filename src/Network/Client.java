package Network;

import UserFolder.User;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
            System.out.println("Connecting to server");
            socket = new Socket("localhost", Server.SOCKET_PORT);  //connects to server
            System.out.println("Connected to server");
            Client client = new Client(socket);
            System.out.println(1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(2);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            Scanner scan = new Scanner(System.in);
            while (true) {
                try {
                    if (!client.loggedIn) {
                        System.out.print("Operation: ");
                        String option = scan.nextLine();// this is where the GUI for login/register will be
                        writer.write(option);
                        writer.newLine();
                        switch (option) {
                            case "login":
                            System.out.println("test");
                                // String username = JOptionPane.showInputDialog("Enter username:");

                                // String password = JOptionPane.showInputDialog("Enter password:");
                                System.out.println("Username: ");
                                String username = scan.nextLine();
                                System.out.println("Password: ");
                                String password = scan.nextLine();
                                writer.write(username);
                                writer.newLine();
                                writer.write(password);
                                writer.newLine();
                                writer.flush();
                                break;
                            case "register":
                                // String newUsername = JOptionPane.showInputDialog("Enter new username:");
                                // String newPassword = JOptionPane.showInputDialog("Enter new password:");
                                System.out.println("Username: ");
                                String newUsername = scan.nextLine();
                                System.out.println("Password: ");
                                String newPassword = scan.nextLine();
                                writer.write(newUsername);
                                writer.newLine();
                                writer.write(newPassword);
                                writer.newLine();
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
                        writer.write(cmd);
                        writer.newLine();
                        writer.flush();
                        switch (cmd) {
                            // all command goes here

                            case "addFriend": {     //adds given username as friend to current logged in user
                                String userId = ""; //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "removeFriend": {
                                String userId = ""; //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "blockUser": {
                                String userId = ""; //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "unblockUser": {
                                String userId = ""; //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateUsername": {    //updates current user to given info
                                String newName = ""; //GUI for new username
                                //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                                writer.write(newName);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updatePassword": {    //updates current user to given info
                                String newPassword = ""; //GUI for new password
                                writer.write(newPassword);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateProfilePic": {    //updates current user to given info
                                String dir = ""; //GUI for new profile picture path
                                writer.write(dir);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateProfileBio": {    //updates current user to given info
                                String info = reader.readLine(); //new bio text
                                writer.write(info);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "exit": {
                                writer.flush();
                                break;
                            }
                            default: {
                                writer.write("Invalid command");
                                writer.newLine();
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
        System.out.println("11");
        this.socket = socket;
        System.out.println("12");
        // this.out = new ObjectOutputStream(socket.getOutputStream());
        // System.out.println("13");
        // // this.in = new ObjectInputStream(socket.getInputStream());
        // // System.out.println("14");
    }
};
