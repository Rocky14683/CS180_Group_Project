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
                System.out.print("Operation: \n");
                String option = scan.nextLine();// this is where the GUI for login/register will be
                try {
                    if (!client.loggedIn) {
                        writer.write(option);
                        writer.newLine();
                        writer.flush();
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
                                client.loggedIn = true;
                                break;
                            case "register":
                                // String newUsername = JOptionPane.showInputDialog("Enter new username:");
                                // String newPassword = JOptionPane.showInputDialog("Enter new password:");
                                System.out.println("Username: ");
                                String newUsername = scan.nextLine();
                                System.out.println("Password: ");
                                String newPassword = scan.nextLine();
                                writer.write(newUsername + "\n");
                                writer.write(newPassword + "\n");
                                writer.flush();
                                client.loggedIn = true;
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Invalid option");
                                break;
                        }
                        if (reader.readLine().equals("ACK")) {
                            client.loggedIn = true;
                            System.out.println("success");
                            continue;
                        } else {
                            //UI warning
                            continue;
                        }
                    } else {
                        writer.write(option);
                        writer.newLine();
                        writer.flush();
                        switch (option) {
                            // all command goes here

                            case "addFriend": {     //adds given username as friend to current logged in user
                                String userId = scan.nextLine(); //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "removeFriend": {
                                String userId = scan.nextLine(); //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "blockUser": {
                                String userId = scan.nextLine(); //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "unblockUser": {
                                String userId = scan.nextLine(); //GUI for new userid
                                writer.write(userId);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateUsername": {    //updates current user to given info
                                String newName = scan.nextLine(); //GUI for new username
                                //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                                writer.write(newName);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updatePassword": {    //updates current user to given info
                                String newPassword = scan.nextLine(); //GUI for new password
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
                            case "MakePost": {
                                String text = "";
                                writer.write(text);
                                writer.newLine();
                                writer.flush();
                            }
                            case "LikePost": {
                                String postCode = "";
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                            }
                            case "UnlikePost": {
                                String postCode = "";
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                            }
                            case "DisikePost": {
                                String postCode = "";
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                            }
                            case "UndislikePost": {
                                String postCode = "";
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                            }
                            case "HidePost": {
                                String postCode = "";
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                            }
                            case "UnhidePost": {
                                String postCode = "";
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
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
                        String retCheck = reader.readLine();
                        //return ACK
                        if (retCheck.contains("Failed")) {
                            //UI warning
                            System.out.println(retCheck);
                            continue;
                        } else {
                            System.out.println(retCheck);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(); //shouldn't happen
                    return;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
        } catch (
                IOException e) {
            e.printStackTrace();
            return;
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
