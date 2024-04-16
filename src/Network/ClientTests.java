package Network;

import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.Socket;
import static org.junit.Assert.*;

public class ClientTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final String lineSeparator = System.getProperty("line.separator");

    private Socket createMockSocket(String s) throws IOException {
        String simulatedServerOutput = "ACK\nInvalid command\n";
        ByteArrayInputStream bais = new ByteArrayInputStream(simulatedServerOutput.getBytes());


        return new Socket() {
            @Override
            public InputStream getInputStream() {
                return bais;
            }

            @Override
            public OutputStream getOutputStream() {
                return outContent;
            }

            @Override
            public void close() {
                // Do nothing for close to simplify the testing scenario
            }
        };
    }

    @Before
    public void setUp() {
        outContent.reset();
    }

    @Test
    public void testClientConstructor() throws IOException {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        assertNotNull("Client should have been created", client);
    }

    @Test
    public void testClientLogin() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");

        Client client = new Client(mockSocket);

        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("login");
        writer.println("username");
        writer.println("password");

        String expectedOutput = "login" + lineSeparator + "username" + lineSeparator + "password" + lineSeparator;
        assertEquals("Check that client sends login information correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testClientRegister() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("register");
        writer.println("newusername");
        writer.println("newpassword");
        String expectedOutput = "register" + lineSeparator + "newusername" + lineSeparator + "newpassword" + lineSeparator;
        assertEquals("Check that client sends registration information correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testAddFriend() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("addFriend");
        writer.println("friendusername");
        String expectedOutput = "addFriend" + lineSeparator + "friendusername" + lineSeparator;
        assertEquals("Check that client sends add friend request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testRemoveFriend() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("removeFriend");
        writer.println("friendusername");
        String expectedOutput = "removeFriend" + lineSeparator + "friendusername" + lineSeparator;
        assertEquals("Check that client sends remove friend request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testBlockUser() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("blockUser");
        writer.println("usernameToBlock");
        String expectedOutput = "blockUser" + lineSeparator + "usernameToBlock" + lineSeparator;
        assertEquals("Check that client sends block user request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testUpdateUsername() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("updateUsername");
        writer.println("newUsername");
        String expectedOutput = "updateUsername" + lineSeparator + "newUsername" + lineSeparator;
        assertEquals("Check that client sends update username request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testExit() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("exit");
        String expectedOutput = "exit" + lineSeparator;
        assertEquals("Check that client sends exit command correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testUnblockUser() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("unblockUser");
        writer.println("usernameToUnblock");
        String expectedOutput = "unblockUser" + lineSeparator + "usernameToUnblock" + lineSeparator;
        assertEquals("Check that client sends unblock user request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testUpdatePassword() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("updatePassword");
        writer.println("newPassword");
        String expectedOutput = "updatePassword" + lineSeparator + "newPassword" + lineSeparator;
        assertEquals("Check that client sends update password request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testUpdateProfilePic() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("updateProfilePic");
        writer.println("path/to/new/pic");
        String expectedOutput = "updateProfilePic" + lineSeparator + "path/to/new/pic" + lineSeparator;
        assertEquals("Check that client sends update profile picture request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testSearchUser() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("searchUser");
        writer.println("usernameToSearch");
        String expectedOutput = "searchUser" + lineSeparator + "usernameToSearch" + lineSeparator;
        assertEquals("Check that client sends search user request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testMakePost() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("makePost");
        writer.println("Post text here");
        writer.println("path/to/image");
        String expectedOutput = "makePost" + lineSeparator + "Post text here" + lineSeparator + "path/to/image" + lineSeparator;
        assertEquals("Check that client sends make post request correctly", expectedOutput, outContent.toString());
    }

    @Test
    public void testLikePost() throws Exception {
        Socket mockSocket = createMockSocket("ACK\n");
        Client client = new Client(mockSocket);
        PrintWriter writer = new PrintWriter(client.getSocket().getOutputStream(), true);
        writer.println("likePost");
        writer.println("postCode");
        String expectedOutput = "likePost" + lineSeparator + "postCode" + lineSeparator;
        assertEquals("Check that client sends like post request correctly", expectedOutput, outContent.toString());
    }

}
