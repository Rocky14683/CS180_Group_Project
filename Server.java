package ServerClient;

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
 * 
 * A class that starts a server that only allows one client to connect at a time,
 * and allows the client to do actions through GUI, only closes manually
 * 
 * Note: more methods and if statements could be added to do other actions
 * 
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project
 * 
 * @author Alexander Popa, lab section 012
 * @version April 7, 2024
 */

public class Server {

    private User currentUser;
    
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException{

        while (true) {
            ServerSocket serverSocket = new ServerSocket (4242);
            if (serverSocket.isBound()) {

                Socket socket = serverSocket.accept();  //client connects here
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream()); 

                String message = reader.readLine(); //client entry

                if (message.equals("client entry")) {   //ensures that methods work for only one client

                    message = reader.readLine();        //message should contain user name and ID

                    String name = message.substring(0, message.indexOf(","));
                    String uniqueID = message.substring(message.indexOf(",") + 1, message.length());

                    this.currentUser = new User(name, uniqueID);        //sets up user object for further use

                    while (!message.equals("client exit")) {    //loops until client exits 

                        message = reader.readLine();    //message of what client wants to do

                        if (message.contains("public chat")) {

                            message = reader.readLine();    //next line sent should be message

                            publicChat(message);

                        }

                        if (message.contains("direct message")) {

                            message = reader.readLine();    //line should be to who you want to send to, name,ID

                            name = message.substring(0, message.indexOf(","));
                            uniqueID = message.substring(message.indexOf(",") + 1, message.length());

                            User user = new User(name, uniqueID);

                            message = reader.readLine();   //line should be message to send

                            directMessage(message, user);
                        }

                        if (message.contains("change name")) {

                            message = reader.readLine();    //name to change

                            changeName(message);

                        }

                        if (message.contains("change profile picture")) {

                            message = reader.readLine();    //picture url to change

                            changeProfilePicture(message);

                        }
                    }
                }
            }
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
