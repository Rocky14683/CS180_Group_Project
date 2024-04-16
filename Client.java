package Network;


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
    @SuppressWarnings("unused")
    private Socket socket;
    // private ObjectOutputStream out;
    // private ObjectInputStream in;

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
            @SuppressWarnings("resource")
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
                                System.out.println("Enter the username of the friend");
                                String username = scan.nextLine(); //GUI for new username
                                writer.write(username);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "removeFriend": {
                                System.out.println("Enter the username of the friend");
                                String username = scan.nextLine(); //GUI for new username
                                writer.write(username);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "blockUser": {
                                System.out.println("Enter the username of the user to block");
                                String username = scan.nextLine(); //GUI for new username
                                writer.write(username);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "unblockUser": {
                                System.out.println("Enter the username of the user to unblock");
                                String username = scan.nextLine(); //GUI for new username
                                writer.write(username);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateUsername": {    //updates current user to given info
                                System.out.println("Enter your new username (No duplicate usernames)");
                                String newName = scan.nextLine(); //GUI for new username
                                writer.write(newName);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updatePassword": {    //updates current user to given info
                                System.out.println("Enter your new password");
                                String newPassword = scan.nextLine(); //GUI for new password
                                writer.write(newPassword);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateProfilePic": {    //updates current user to given info
                                System.out.println("Enter the directory of your picture");
                                String dir = scan.nextLine(); //GUI for new profile picture path
                                writer.write(dir);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "updateProfileBio": {    //updates current user to given info
                                System.out.println("Enter the new text for your bio");
                                String info = scan.nextLine(); //new bio text
                                writer.write(info);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "searchUser": {    //searches for given username
                                System.out.println("Enter the username of the user to search");
                                String username = scan.nextLine();
                                writer.write(username);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "makePost": {
                                System.out.println("Enter the text of the post");
                                String text = reader.readLine();    //post text
                                System.out.println("Enter the directory of the image");
                                String image = reader.readLine();   //image file?
                                writer.write(text);
                                writer.newLine();
                                writer.flush();
                                writer.write(image);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "likePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "unlikePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "disikePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "undislikePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "hidePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "unhidePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "deletePost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "makeComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String comment = scan.nextLine();   //comment
                                writer.write(comment);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "likeComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String comment = scan.nextLine();   //comment
                                writer.write(comment);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "unlikeComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String comment = scan.nextLine();   //comment
                                writer.write(comment);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "dislikeComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String comment = scan.nextLine();   //comment
                                writer.write(comment);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "undislikeComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String comment = scan.nextLine();   //comment
                                writer.write(comment);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "deleteComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String comment = scan.nextLine();   //comment
                                writer.write(comment);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "searchPost": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                break;
                            }
                            case "searchComment": {
                                System.out.println("Enter the Post Code");
                                String postCode = scan.nextLine();  //postcode
                                writer.write(postCode);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Enter the Comment Code");
                                String commentCode = scan.nextLine();  //postcode
                                writer.write(commentCode);
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
                        String retCheck = reader.readLine();
                        //return ACK
                        if (retCheck.contains("Failed")) {
                            //UI warning
                            System.out.println(retCheck);
                            continue;
                        } else {
                            System.out.println(retCheck);   //server response
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