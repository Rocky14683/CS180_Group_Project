// package Network;
package TempFolderSoIDontBreakEverything;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

public class Client_temp_unuse {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", Server.SOCKET_PORT);  //connects to server
            try {
                //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());

                writer.write("client entry");   //allows client to be connected to server
                writer.println();
                writer.flush();


                //GUI FOR LOGIN
                //need database logic here to allow the correct username and password to login
                //and then grabs uniqueID as well to create a new user object for the server

                String name = null;
                String uniqueID = null;

                writer.write(name + "," + uniqueID);

                boolean menu = true;

                while (menu) {   //loops back to menu after each action

                    //GUI FOR MENU
                    //needs GUI for every individual action afterwards

                    String message = null; //user action (goes to if statements in server)

                    writer.write(message);
                    writer.println();
                    writer.flush();

                    //individual user input for said action, i.e. if user wants to change name,
                    //top write should be "change name", bottom write should be "Alex" the name to change
                    message = null;
                    writer.write(message);
                    writer.println();
                    writer.flush();


                    //GUI if user wants to return back to menu or quit

                }

                writer.write("client exit");
                writer.println();
                writer.flush();

            } catch (Exception e) {
                e.printStackTrace(); //shouldn't happen
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
