package Network;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
 * @author Alexander Popa, lab section 012
 * @version April 7, 2024
 */


public class Client {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client() {
        try {
            socket = new Socket("localhost", Server.SOCKET_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            sendRequest("client entry", new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object[] sendRequest(String action, Object[] params) throws Exception {
        out.writeObject(action);
        out.writeObject(params);
        out.flush();

        boolean isSuccess = in.readBoolean();
        Object[] response = (Object[]) in.readObject();

        if (!isSuccess) {
            throw (Exception) response[0];
        }
        return response;
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            boolean menu = true;
            while (menu) {      //loops back to menu after each action

                //GUI FOR MENU
                //needs GUI for every individual action afterwards

                String action = JOptionPane.showInputDialog("Enter action (addFriend, removeFriend, blockUser, unblockUser, updateUser, exit):");
                switch (action.toLowerCase()) {
                    case "addfriend":
                        String userId = JOptionPane.showInputDialog("Your User ID:");
                        String friendId = JOptionPane.showInputDialog("Friend's User ID:");
                        client.addUser(userId, friendId);
                        JOptionPane.showMessageDialog(null, "Friend added successfully!");
                        break;
                    case "removefriend":
                        userId = JOptionPane.showInputDialog("Your User ID:");
                        friendId = JOptionPane.showInputDialog("Friend's User ID:");
                        client.removeUser(userId, friendId);
                        JOptionPane.showMessageDialog(null, "Friend removed successfully!");
                        break;
                    case "blockuser":
                        userId = JOptionPane.showInputDialog("Your User ID:");
                        String blockId = JOptionPane.showInputDialog("User ID to block:");
                        client.blockUser(userId, blockId);
                        JOptionPane.showMessageDialog(null, "User blocked successfully!");
                        break;
                    case "unblockuser":
                        userId = JOptionPane.showInputDialog("Your User ID:");
                        String unblockId = JOptionPane.showInputDialog("User ID to unblock:");
                        client.unblockUser(userId, unblockId);
                        JOptionPane.showMessageDialog(null, "User unblocked successfully!");
                        break;
                    case "updateuser":
                        userId = JOptionPane.showInputDialog("Your User ID:");
                        String newUsername = JOptionPane.showInputDialog("New Username:");
                        String newPassword = JOptionPane.showInputDialog("New Password:");
                        String profilePicturePath = JOptionPane.showInputDialog("Path to new profile picture:");
                        client.updateUser(userId, newUsername, newPassword, profilePicturePath);
                        JOptionPane.showMessageDialog(null, "User updated successfully!");
                        break;
                    case "exit":
                        menu = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid action");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void addUser(String userId, String friendId) throws Exception {
        sendRequest("AddFriend", new Object[]{userId, friendId});
    }

    public void removeUser(String userId, String friendId) throws Exception {
        sendRequest("RemoveFriend", new Object[]{userId, friendId});
    }

    public void blockUser(String userId, String blockId) throws Exception {
        sendRequest("BlockUser", new Object[]{userId, blockId});
    }

    public void unblockUser(String userId, String unblockId) throws Exception {
        sendRequest("UnBlockUser", new Object[]{userId, unblockId});
    }

    public void updateUser(String userId, String newUsername, String newPassword, String profilePicturePath) throws Exception {
        sendRequest("UpdateUser", new Object[]{userId, newUsername, newPassword, profilePicturePath});
    }
}
