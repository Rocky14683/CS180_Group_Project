
package Network;

import DatabaseFolder.*;
import UserFolder.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.*;

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
    private static DataWriter database = new DataWriter();
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
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        while (true) {
            Socket socket = serverSocket.accept(); //wait for socket connection established
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }

    @Override
    public void run() {

        try {
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
                String cmd = reader.readLine();
                switch (cmd) {
                    // all command goes here

                    case "addFriend": {     //adds given username as friend to current logged in user
                        String friendId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(friendId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User friend = (User) database.getReturnObject();
                        database.addFriends(friend, user);

                        writer.println(friend.getUsername() + " added as a friend");
                        writer.flush();
                        break;
                    }
                    case "removeFriend": {
                        String friendId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(friendId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetFriend = (User) database.getReturnObject();
                        database.removeFriend(user, targetFriend);

                        writer.println(targetFriend.getUsername() + " removed as a friend");
                        writer.flush();
                        break;
                    }
                    case "blockUser": {
                        String userId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(userId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetUser = (User) database.getReturnObject();
                        database.removeFriend(user, targetUser); //removes user as friend (if they are friends
                        database.blockUser(user, targetUser);

                        writer.println(targetUser.getUsername() + " has been blocked");
                        writer.flush();
                        break;
                    }
                    case "unblockUser": {
                        String userId = reader.readLine(); //userid of friend
                        if (!database.redefineUser(userId)) {
                            writer.println("User not found");
                            writer.flush();
                            break;
                        }
                        User targetUser = (User) database.getReturnObject();
                        database.unblockUser(user, targetUser);


                        writer.println(targetUser.getUsername() + " removed as a friend");
                        writer.flush();
                        break;
                    }
                    case "updateUsername": {    //updates current user to given info
                        String newName = reader.readLine(); //new username
                        //NEEDS TO CHECK WHETHER USERNAME IS ALREADY TAKEN OR NOT TO AVOID DUPLICATION
                        user.setUsername(newName, this.database);
                        writer.println("Username updated successfully");
                        writer.flush();
                        break;
                    }
                    case "updatePassword": {    //updates current user to given info
                        String newPassword = reader.readLine(); //new password
                        user.setPassword(newPassword, this.database);
                        writer.println("Password updated successfully");
                        writer.flush();
                        break;
                    }
                    case "updateProfilePic": {    //updates current user to given info
                        String dir = reader.readLine(); //new profile picture filename
                        user.getProfile().setProfilePicName(dir);
                        if (!user.getProfile().loadProfilePic()) {
                            writer.println("Profile directory not found");
                            writer.flush();
                            break;
                        }

                        writer.println("Profile picture updated successfully");
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
                writer.println("ACK");
            }
        } catch (AlreadyThereException | ExistingUsernameException | InvalidOperationException |
                 BlockedException | DoesNotExistException | ImNotSureWhyException e) {
            writer.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //<= to be implemented
    }

    private static void publicChat(String message) {    //TO BE IMPLEMENTED

    }

    private static void directMessage(String message, User user) {      //TO BE IMPLEMENTED

    }
}


public class ServerTest {

    private Server server;
    private Socket socketMock;
    private BufferedReader reader;
    private PrintWriter writer;
    private DataWriter databaseMock;

    @BeforeEach
    public void setUp() throws IOException {
        socketMock = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writer = new PrintWriter(outputStream, true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());

        when(socketMock.getOutputStream()).thenReturn(outputStream);
        when(socketMock.getInputStream()).thenReturn(inputStream);

        reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("".getBytes())));
        databaseMock = mock(DataWriter.class);
        Server.database = databaseMock;

        server = new Server(socketMock);
        server.writer = this.writer; // Override the writer to use our local mock
        server.reader = this.reader; // Override the reader to use our local mock
    }

    @Test
    public void testLoginSuccess() throws IOException {
        String input = "login\nusername\npassword\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.logIn("username", "password")).thenReturn(true);

        server.run();

        String expectedOutput = "Login successful\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testLoginFailure() throws IOException {
        String input = "login\nusername\npassword\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.logIn("username", "password")).thenReturn(false);

        server.run();

        String expectedOutput = "Invalid username or password\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testRegisterSuccess() throws IOException {
        String input = "register\nnewuser\nnewpass\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        User newUser = new User("newuser", "newpass");
        when(databaseMock.createUser(any(User.class))).thenReturn(true);

        server.run();

        String expectedOutput = "Account created successfully\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testRegisterFailure() throws IOException {
        String input = "register\nnewuser\nnewpass\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.createUser(any(User.class))).thenReturn(false);

        server.run();

        String expectedOutput = "Invalid username or password\n";
        assertEquals(expectedOutput, writer.toString());
    }

    // Add more tests for other commands like addFriend, removeFriend, etc.

    @AfterEach
    public void tearDown() throws IOException {
        socketMock.close();
    }

    @Test
    public void testAddFriendSuccess() throws IOException {
        String input = "addFriend\nfriendId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("friendId")).thenReturn(true);
        when(databaseMock.getReturnObject()).thenReturn(new User("friendId", "friendPassword"));

        server.run();

        String expectedOutput = "friendId added as a friend\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testAddFriendFailure() throws IOException {
        String input = "addFriend\nfriendId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("friendId")).thenReturn(false);

        server.run();

        String expectedOutput = "User not found\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testRemoveFriendSuccess() throws IOException {
        String input = "removeFriend\nfriendId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("friendId")).thenReturn(true);
        when(databaseMock.getReturnObject()).thenReturn(new User("friendId", "friendPassword"));

        server.run();

        String expectedOutput = "friendId removed as a friend\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testRemoveFriendFailure() throws IOException {
        String input = "removeFriend\nfriendId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("friendId")).thenReturn(false);

        server.run();

        String expectedOutput = "User not found\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testBlockUserSuccess() throws IOException {
        String input = "blockUser\nuserId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("userId")).thenReturn(true);
        when(databaseMock.getReturnObject()).thenReturn(new User("userId", "userPassword"));

        server.run();

        String expectedOutput = "userId has been blocked\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testBlockUserFailure() throws IOException {
        String input = "blockUser\nuserId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("userId")).thenReturn(false);

        server.run();

        String expectedOutput = "User not found\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUnblockUserSuccess() throws IOException {
        String input = "unblockUser\nuserId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("userId")).thenReturn(true);
        when(databaseMock.getReturnObject()).thenReturn(new User("userId", "userPassword"));

        server.run();

        String expectedOutput = "userId removed as a friend\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUnblockUserFailure() throws IOException {
        String input = "unblockUser\nuserId\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        when(databaseMock.redefineUser("userId")).thenReturn(false);

        server.run();

        String expectedOutput = "User not found\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUpdateUsername() throws IOException {
        String input = "updateUsername\nnewUsername\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        server.user = new User("oldUsername", "password"); // Set a user

        server.run();

        String expectedOutput = "Username updated successfully\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUpdatePassword() throws IOException {
        String input = "updatePassword\nnewPassword\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        server.user = new User("username", "oldPassword"); // Set a user

        server.run();

        String expectedOutput = "Password updated successfully\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUpdateProfilePicSuccess() throws IOException {
        String input = "updateProfilePic\npathToPic\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        server.user = new User("username", "password"); // Set a user
        when(server.user.getProfile().loadProfilePic()).thenReturn(true);

        server.run();

        String expectedOutput = "Profile picture updated successfully\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUpdateProfilePicFailure() throws IOException {
        String input = "updateProfilePic\npathToPic\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        server.user = new User("username", "password"); // Set a user
        when(server.user.getProfile().loadProfilePic()).thenReturn(false);

        server.run();

        String expectedOutput = "Profile directory not found\n";
        assertEquals(expectedOutput, writer.toString());
    }

    @Test
    public void testUpdateProfileBio() throws IOException {
        String input = "updateProfileBio\nnewBio\n";
        server.reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        server.user = new User("username", "password"); // Set a user

        server.run();

        String expectedOutput = "Bio updated successfully\n";
        assertEquals(expectedOutput, writer.toString());
    }

}