package DatabaseFolder;

import UserFolder.User;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Public Chat.java
 * <p>
 * Program that writes information to a static public chat object that
 * users can send info and read info from
 *
 * @author Alexander Popa, L12
 * @version 4/14/24
 */

public class PublicChat extends Thread {

    // Gatekeepers
    public static Object gatekeeper = new Object();
    public static ArrayList<String[]> gatekeeperArray = new ArrayList<String[]>();

    // Dynamic
    private Object[] inputObject;
    private Object returnObject; //Threads can't return things so it will modify this field and then this
    // field can be accesed to "return" 
    private String requiredJob; //You can't pass parameters to the run function so this
    // is my work around, you define it before starting thread

    private String message; //message to send to chat from user input


    // Static
    private static String systemPath = System.getProperty("user.dir") + "/System/";   //Path to the system directory
    private static File chat; //File with all chat currently in use


    public PublicChat() {
        try {
            //Checking if required directories exist and making them if they don't
            chat = new File(systemPath + "chat");
            if (!chat.exists())
                if (chat.createNewFile()) {
                    System.out.println("Chat file created succesfully");

                } else {
                    System.out.println("Error making chat file or chat already exists");
                }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//----------------------------------------------------------------------------------------------------------

    public void run() {
        switch (this.requiredJob) //Switch case to decide what job the thread is completeing
        {
            case ("sendMessage"):

                if (!(inputObject[0] instanceof User)) {
                    System.out.println("inputObject is not a user");
                    break;
                }
                User currentUser = (User) inputObject[0];

                if (this.sendMessage(currentUser, message)) {
                    System.out.println("Message sent succesfully");
                } else {
                    System.out.printf("Message could not be sent");
                }
                break;

            case ("readMessages"):
                if (!(inputObject[0] instanceof User)) {
                    System.out.println("inputObject is not a user");
                    break;
                }
                User User2 = (User) inputObject[0];

                returnObject = readMessages(User2);

                break;
            
            default:
                System.out.println("Not a valid job for PublicChat: (" + requiredJob + ")");
        }
    }

//----------------------------------------------------------------------------------------------------------

    public boolean sendMessage(User user, String message) {
        synchronized (gatekeeper) {                 //idk what these do, but ill leave it in
            gatekeeperArray.add(new String[]{user.getUserId()});
        }
        int index = 0;

        synchronized (gatekeeper) {                 //idk what these do, but ill leave it in
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(user.getUserId())) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {
            try {

                //Writing data to the PublicChat file
                BufferedWriter bw = new BufferedWriter(new FileWriter(chat));
                bw.write(user.getUsername() + ": " + message);
                bw.newLine();
                bw.flush();

                bw.close();
                bw = null;

            } catch (IOException e) {
                e.printStackTrace(); //Figure out what to do in this situation
                return false;
            }

            return true;
        }
    }
    
    public ArrayList<String> readMessages(User user) {

        ArrayList<String> blockedUsers = user.getBlockedUsers();

        synchronized (gatekeeper) {                 //idk what these do, but ill leave it in
            gatekeeperArray.add(new String[]{user.getUserId()});
        }
        int index = 0;

        synchronized (gatekeeper) {                 //idk what these do, but ill leave it in
            for (int i = 0; i > gatekeeperArray.size(); i++) {
                if (gatekeeperArray.get(i)[0].equals(user.getUserId())) {
                    index = i;
                }
            }
        }

        synchronized (gatekeeperArray.get(index)) {
            try {

                //Writing data to the PublicChat file
                BufferedReader br = new BufferedReader(new FileReader(chat));
                
                ArrayList<String> messages = new ArrayList<String>();

                boolean add = true;

                String message = br.readLine();

                while (message != null) {       //loops through all messages and puts them in arraylist
                    
                    for (String blocked : blockedUsers) {       //if user is blocked, does not add to arraylist
                        if (message.substring(0, message.indexOf(":")).equals(blocked)) {
                            add = false;
                        }
                    }
                    
                    if (add) {
                        messages.add(message);
                    }

                    message = br.readLine();
                    add = true;
                }

                return messages;

            } catch (IOException e) {
                e.printStackTrace(); //Figure out what to do in this situation

            }
        }
    }

//----------------------------------------------------------------------------------------------------------

    public void sendMessage(String message) {
        this.requiredJob = "sendMessage";
        this.message = message;
    }
    public void readMessages() {
        this.requiredJob = "readMessages";
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setInputObject(Object[] object) {
        inputObject = object;
    }
}