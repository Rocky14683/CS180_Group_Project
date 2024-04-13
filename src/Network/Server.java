package Network;

import DatabaseFolder.DataWriter;
import DatabaseFolder.Database;
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
    private DataWriter database = new DataWriter();

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

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
                String message = reader.readLine();
                if (message.equals("client entry")) {   //ensures that methods work for only one client & allow access after login
                    String cmd = reader.readLine();
                    switch (cmd) {
                        // all command goes here

                        default: {
                            writer.println("Invalid command");
                            writer.flush();
                        }
                    }
                } else if (message.equals("client exit")) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void publicChat(String message) {    //TO BE IMPLEMENTED

    }

    private static void directMessage(String message, User user) {      //TO BE IMPLEMENTED

    }

    private static void changeName(String message) {    //TO BE IMPLEMENTED

    }

    private static void changeProfilePicture(String photo) {    //TO BE IMPLEMENTED

    }
}
