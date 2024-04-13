package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Client.java
 * <p>
 * A class that connects to a server and sends inputs to do
 * <p>
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project
 *
 * @author Alexander Popa, Aryan Wadhwa lab section 012
 * @version April 7, 2024
 */

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", Server.SOCKET_PORT);  //connects to server
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());

                writer.write("client entry");   //allows client to be connected to server
                writer.println();
                writer.flush();


                String name = JOptionPane.showInputDialog("Enter your username:");
                String uniqueID = JOptionPane.showInputDialog("Enter your unique ID:");
                writer.println(name + "," + uniqueID);

                // Main loop for user actions
                String input = "";
                while (!"exit".equals(input)) {
                    input = JOptionPane.showInputDialog("Enter command (public chat, direct message, change name, change profile picture, exit):");

                    switch (input) {
                        case "public chat":
                            writer.println("public chat");
                            String chatMessage = JOptionPane.showInputDialog("Enter message for public chat:");
                            writer.println(chatMessage);
                            break;
                        case "direct message":
                            writer.println("direct message");
                            String recipientName = JOptionPane.showInputDialog("Enter recipient's name:");
                            String recipientID = JOptionPane.showInputDialog("Enter recipient's unique ID:");
                            writer.println(recipientName + "," + recipientID);
                            String dmMessage = JOptionPane.showInputDialog("Enter your message:");
                            writer.println(dmMessage);
                            break;
                        case "change name":
                            writer.println("change name");
                            String newName = JOptionPane.showInputDialog("Enter new name:");
                            writer.println(newName);
                            break;
                        case "change profile picture":
                            writer.println("change profile picture");
                            String newPictureURL = JOptionPane.showInputDialog("Enter new profile picture URL:");
                            writer.println(newPictureURL);
                            break;
                        case "exit":
                            writer.println("client exit");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid command");
                            break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
