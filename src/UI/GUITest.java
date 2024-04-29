package UI;

import java.net.Socket;

public class GUITest {


    public static void main(String[] args) {
        Socket socket = null;
        GUI test = new GUI();

        System.out.println("Connecting to server");
        //socket = new Socket("localhost", Network.Server.SOCKET_PORT);  //connects to server
        System.out.println("Connected to server");

        Thread thread = new Thread(test);

        thread.start();
    }
}