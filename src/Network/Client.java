package Network;

import Network.Server;
import UI.GUI;

import java.io.*;
import java.net.Socket;
import java.util.*;

import javax.swing.*;


public class Client {
    BufferedReader reader;
    BufferedWriter writer;
    Socket socket;

    public static void main(String[] args) {
        GUI.main(args);
    }

    public Client() {

        try {
            System.out.println("Connecting to server");
            socket = new Socket("localhost", Server.SOCKET_PORT);  //connects to server
            System.out.println("Connected to server");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean login(String username, String password) {
        try {
            System.out.println("test2");
            writer.write("login");
            writer.newLine();
            writer.write(username);
            writer.newLine();
            writer.flush();
            writer.write(password);
            writer.newLine();
            writer.flush();
            Boolean isValid = reader.readLine().equals("Login successful");

            System.out.println(isValid);

            if (!isValid) {
                JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.WARNING_MESSAGE);
            }
            return isValid;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean register(String newUsername, String newPassword) {
        try {
            writer.write("register" + "\n");
            writer.write(newUsername + "\n");
            writer.write(newPassword + "\n");
            writer.flush();

            String result = reader.readLine();
            boolean isValid = result.equals("Account created successfully");

            System.out.println(isValid);
            if (!isValid) {
                JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.WARNING_MESSAGE);
            }
            return isValid;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addFriend(String userId) throws IOException {
        writer.write("addFriend");
        System.out.println(userId);
        writer.newLine();
        writer.write(userId);
        writer.newLine();
        writer.flush();

        String exceptions = reader.readLine();
        if (exceptions.equals("Blocked")) {
            JOptionPane.showMessageDialog(null, "Cannot add friend\n\nThis user has you blocked or you have them blocked", "Adding friend error", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void removeFriend(String userId) throws IOException {
        writer.write("removeFriend");
        writer.newLine();
        System.out.println(userId);
        writer.write(userId);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void blockUser(String userId) throws IOException {
        writer.write("blockUser");
        writer.newLine();
        writer.write(userId);
        writer.newLine();
        writer.flush();

        reader.readLine();

    }

    public void unblockUser(String userId) throws IOException {
        writer.write("unblockUser");
        writer.newLine();
        writer.write(userId);
        writer.newLine();
        writer.flush();

        reader.readLine();

    }

    public ArrayList<String[]> search(String searchTerm) throws IOException {
        writer.write("Search");
        writer.newLine();
        String search = searchTerm;
        System.out.println("Sending:" + search);
        writer.write(search);
        writer.newLine();
        writer.flush();
        String[] searchReturn = reader.readLine().split(";;");


        reader.readLine();
        ArrayList<String[]> searchResultsArraylist = new ArrayList<>();
        for (String s : searchReturn) {
            searchResultsArraylist.add(s.split(";"));
        }


        return searchResultsArraylist;
    }

    public boolean isFriend(String userId) throws IOException {
        writer.write("isFriend");
        writer.newLine();
        writer.write(userId);
        writer.newLine();
        writer.flush();

        boolean isFriend = reader.readLine().equals("friend");
        System.out.println(isFriend);
        return isFriend;
    }

    public boolean isBlocked(String userId) throws IOException {
        writer.write("isBlocked");
        writer.newLine();
        writer.write(userId);
        writer.newLine();
        writer.flush();

        boolean isFriend = reader.readLine().equals("blocked");
        System.out.println(isFriend);
        return isFriend;
    }


    public String getId(String username) throws IOException {
        writer.write("getId" + "\n");
        writer.write(username + "\n");
        writer.flush();

        return reader.readLine();

    }

    public ArrayList<String[]> getPosts() throws IOException {
        writer.write("getPosts\n");
        writer.flush();
        String postString = reader.readLine();

        System.out.println("String:::: " + postString);
        String[] postStringArray = postString.split(";;");

        ArrayList<String[]> returnList = new ArrayList<>();

        for (String s : postStringArray) {
            returnList.add(s.split(";"));
        }

        return returnList;
    }

    public ArrayList<String[]> getComments(String postCode) throws IOException {
        writer.write("getComments\n");
        writer.write(postCode + "\n");
        writer.flush();
        String commentString = reader.readLine();
        System.out.println("getComments commentString: " + commentString);

        System.out.println("Made it back to client, getComments");

        String[] commentStringArray = commentString.split(";;");
        System.out.println("Size: " + commentStringArray.length);

        ArrayList<String[]> returnList = new ArrayList<>();

        for (String s : commentStringArray) {
            returnList.add(s.split(";"));
        }

        return returnList;
    }

    public void makePost(String text) throws IOException {
        writer.write("makePost");
        writer.newLine();
        writer.write(text);
        writer.newLine();
        writer.flush();

        reader.readLine();

    }

    public void likePost(String postCode) throws IOException {
        System.out.println("Like post");
        writer.write("likePost\n");
        writer.write(postCode + "\n");
        writer.flush();
        reader.readLine();
    }

    public void unlikePost(String postCode) throws IOException {
        writer.write("unlikePost\n");
        writer.write(postCode + "\n");
        writer.flush();
        reader.readLine();
    }

    public void dislikePost(String postCode) throws IOException {
        writer.write("dislikePost\n");
        writer.write(postCode + "\n");
        writer.flush();
        reader.readLine();
    }

    public void undislikePost(String postCode) throws IOException {
        writer.write("undislikePost\n");
        writer.write(postCode + "\n");
        writer.flush();
        reader.readLine();
    }

    public boolean hasLiked(String postcode) throws IOException {
        writer.write("hasLiked\n");
        writer.write(postcode + "\n");
        writer.flush();

        boolean hasLiked = reader.readLine().equals("liked");
        System.out.println(hasLiked);
        return hasLiked;
    }

    public boolean hasDisliked(String postcode) throws IOException {
        writer.write("hasDisliked\n");
        writer.write(postcode + "\n");
        writer.flush();

        boolean hasDisliked = reader.readLine().equals("disliked");
        System.out.println(hasDisliked);
        return hasDisliked;
    }

    public boolean isOwnerPost(String postCode) throws IOException {
        writer.write("isOwnerPost\n");
        writer.write(postCode);
        writer.newLine();
        writer.flush();


        return reader.readLine().equals("owner");
    }

    public boolean isHidden(String postCode) throws IOException {
        writer.write("isHidden\n");
        writer.write(postCode);
        writer.newLine();
        writer.flush();


        return reader.readLine().equals("hidden");
    }

    public void deletePost(String postCode) throws IOException {
        writer.write("deletePost\n");
        writer.write(postCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void deleteComment(String postCode, String commentCode) throws IOException {
        writer.write("deleteComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void hidePost(String postCode) throws IOException {
        writer.write("hidePost\n");
        writer.write(postCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void updateUsername(String newUsername) throws IOException {
        writer.write("updateUsername\n");
        writer.write(newUsername);
        writer.newLine();
        writer.flush();

        String didItWork = reader.readLine();

        if (!didItWork.equals("yes")) {
            JOptionPane.showMessageDialog(null, "Username is taken", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updatePassword(String newPassword) throws IOException {
        writer.write("updatePassword\n");
        writer.write(newPassword);
        writer.newLine();
        writer.flush();
    }

    public void showInfo() {
        JOptionPane.showMessageDialog(null, "Username is taken", "Error", JOptionPane.WARNING_MESSAGE);
    }

    public void makeComment(String postCode, String text) throws IOException {
        writer.write("makeComment");
        writer.newLine();
        writer.write(postCode + "\n" + text + "\n");
        writer.flush();

        reader.readLine();
    }

    //-------------------------------------------------------------------

    public void likeComment(String postCode, String commentCode) throws IOException {
        writer.write("likeComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void unlikeComment(String postCode, String commentCode) throws IOException {
        writer.write("unlikeComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void dislikeComment(String postCode, String commentCode) throws IOException {
        writer.write("dislikeComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public void undislikeComment(String postCode, String commentCode) throws IOException {
        writer.write("undislikeComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        reader.readLine();
    }

    public boolean hasLikedComment(String postCode, String commentCode) throws IOException {
        writer.write("hasLikedComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        return reader.readLine().equals("liked");
    }

    public boolean hasDislikedComment(String postCode, String commentCode) throws IOException {
        writer.write("hasDislikedComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();

        return reader.readLine().equals("disliked");
    }

    public boolean isOwnerComment(String postCode, String commentCode) throws IOException {
        writer.write("isOwnerComment\n");
        writer.write(postCode);
        writer.newLine();
        writer.write(commentCode);
        writer.newLine();
        writer.flush();


        return reader.readLine().equals("owner");
    }

}