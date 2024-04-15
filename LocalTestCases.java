package Network;

import java.io.Console;

/**
 * LocalTestCases.java
 * <p>
 * A class that to run some local test cases on client-server interaction and
 * ultimately the database
 * <p>
 * Note: more methods and if statements could be added to do other actions
 * <p>
 * Purdue University -- CS18000 -- Spring 2024 -- Team Project
 *
 * @author Alexander Popa, lab section 012
 * @version April 15, 2024
 */

public class LocalTestCases {

    private static Console console = System.console();
    

    public static void main(String[] args) {
        Server server = new Server(1234);   //initializes server
        System.out.println("Case 1: " + case1());
        System.out.println("Case 2: " + case2());
        System.out.println("Case 3: " + case3());
        System.out.println("Case 4: " + case4());
    }


    //tests for account creation, account modification, and login after client closed
    private static boolean case1() {

        boolean test = true;
        String answer;

        //setup
        Client client = new Client();

        System.out.println("register");
        System.out.println("AlexP");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (!answer.equals("Account created successfully")) {
            test = false;
        }

        System.out.println("updateUsername");
        System.out.println("AlexP22");
        answer = console.readLine();
        if (!answer.equals("Username updated successfully")) {
            test = false;
        }

        System.out.println("updatePassword");
        System.out.println("password1234");
        answer = console.readLine();
        if (!answer.equals("Password updated successfully")) {
            test = false;
        }

        System.out.println("updateProfilePic");
        System.out.println("euro.jpg");     //picture stored in network for now
        answer = console.readLine();
        if (!answer.equals("Profile picture updated successfully")) {
            test = false;
        }

        System.out.println("updateBio");
        System.out.println("Hi, my name is Alex!");
        answer = console.readLine();
        if (!answer.equals("Bio updated successfully")) {
            test = false;
        }

        System.out.println("exit");


        //test
        Client client2 = new Client();

        System.out.println("login");
        System.out.println("AlexP");
        System.out.println("password"); //should fail

        answer = console.readLine();
        if(!answer.equals("Invalid username or password")) {
            test = false;
        }

        System.out.println("login");
        System.out.println("AlexP22");
        System.out.println("password1234");
        answer = console.readLine();
        if (!answer.equals("Login successful")) {
            test = false;
        }

        System.out.println("exit");



        return test;


    }

    //tests for friend functionality
    private static boolean case2() {
        boolean test = true;
        String answer;

        //setup
        Client client = new Client();

        System.out.println("register");
        System.out.println("Friend123");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (!answer.equals("Account created successfully")) {
            test = false;
        }

        System.out.println("addFriend");
        System.out.println("AlexP");
        answer = console.readLine();
        if (!answer.equals("AlexP22 added as a friend")) {
            test = false;
        }


        return test;
    }

    //tests for blocking functionality
    private static boolean case3() {
        boolean test = true;
        String answer;

        //setup
        Client client = new Client();

        System.out.println("register");
        System.out.println("blocker");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (!answer.equals("Account created successfully")) {
            test = false;
        }

        System.out.println("blockUser");
        System.out.println("AlexP");
        answer = console.readLine();
        if (!answer.equals("AlexP has been blocked")) {
            test = false;
        }

        System.out.println("blockUser");
        System.out.println("Friend123");
        answer = console.readLine();
        if (!answer.equals("Friend123 has been blocked")) {
            test = false;
        }


        System.out.println("unblockUser");
        System.out.println("Friend123");
        answer = console.readLine();
        if (!answer.equals("Friend123 has been unblocked")) {
            test = false;
        }

        return test;
    }

    //tests for extreme cases
    private static boolean case4() {
        boolean test = true;
        String answer;

        //setup
        Client client = new Client();

        System.out.println("register");
        System.out.println("AlexP");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (answer.equals("Account created successfully")) {
            test = false;
        }

        System.out.println("register");
        System.out.println("hacker");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (!answer.equals("Account created successfully")) {
            test = false;
        }

        System.out.println("exit");

        System.out.println("login");
        System.out.println("hacker");    //username
        System.out.println("notpassword");     //password
        answer = console.readLine();
        if (answer.equals("Login successful")) {
            test = false;
        }
        
        System.out.println("login");
        System.out.println("hackman542");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (answer.equals("Login successful")) {
            test = false;
        }

        System.out.println("login");
        System.out.println("hacker");    //username
        System.out.println("password");     //password
        answer = console.readLine();
        if (!answer.equals("Login successful")) {
            test = false;
        }

        //tests not real user

        System.out.println("addFriend");
        System.out.println("notAUser");
        answer = console.readLine();
        if (!answer.equals("notAUser does not exist")) {
            test = false;
        }

        System.out.println("removeFriend");
        System.out.println("notAUser");
        answer = console.readLine();
        if (!answer.equals("notAUser does not exist")) {
            test = false;
        }

        System.out.println("blockuser");
        System.out.println("notAUser");
        answer = console.readLine();
        if (!answer.equals("notAUser does not exist")) {
            test = false;
        }

        System.out.println("unblockUser");
        System.out.println("notAUser");
        answer = console.readLine();
        if (!answer.equals("notAUser does not exist")) {
            test = false;
        }


        //tests for profile picture directory
        System.out.println("updateProfilePic");
        System.out.println("fakeimage!!!");
        answer = console.readLine();
        if (!answer.equals("Image does not exist")) {
            test = false;
        }


        return test;

    }


}
