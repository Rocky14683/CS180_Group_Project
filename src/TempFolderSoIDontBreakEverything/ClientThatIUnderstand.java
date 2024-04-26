package TempFolderSoIDontBreakEverything;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientThatIUnderstand {

    
    private boolean loggedIn = false;
    BufferedReader reader;
    BufferedWriter writer;

    public ClientThatIUnderstand() {

        Socket socket = null;
        
        try {
            System.out.println("Connecting to server");
            socket = new Socket("localhost", Server.SOCKET_PORT);  //connects to server
            System.out.println("Connected to server");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            @SuppressWarnings("resource")
            Scanner scan = new Scanner(System.in);

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
            writer.write(password);
            writer.newLine();
            writer.flush();
            Boolean isValid = reader.readLine().equals("Login successful");

            System.out.println(isValid);
            return isValid;

        } catch(IOException e) {
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

            boolean isValid = reader.readLine().equals("Account created successfully");
            
            System.out.println(isValid);
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

        reader.readLine();

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


    public String getId(String username) throws IOException{
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
        writer.flush();
        String commentString = reader.readLine();

        String[] commentStringArray = commentString.split(";;");

        ArrayList<String[]> returnList = new ArrayList<>();

        for (String s : commentStringArray) {
            returnList.add(s.split(";"));
        }

        return new ArrayList<>();
    }

    public void makePost(String text) throws IOException {
        writer.write("makePost");
        writer.newLine();
        writer.write(text);
        writer.newLine();
        writer.flush(); 

        reader.readLine();

    }

}
