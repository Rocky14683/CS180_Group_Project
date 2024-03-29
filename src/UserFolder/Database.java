package UserFolder;

//alex

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

//entire database, each line is the toString() of user class
public class Database {
    
    private static final String fileName = "database.txt";
    private static ArrayList<User> users;

    public Database() {     //creates database file
        try {
            File createDatabase = new File(Database.fileName);
            createDatabase.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database already exists");
        }
    }


    //reads entire database and resets userlist to what is in txt file
    public static void read() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(fileName));

            ArrayList<User> newList = new ArrayList<User>();

            String line = bfr.readLine();

            while(line != null) {
                
                String name = line.substring(0, line.indexOf(","));
                String uniqueID = line.substring(line.indexOf(",") + 1, line.lastIndexOf(","));
                String password = line.substring(line.lastIndexOf(",") + 1, line.length());

                User toAdd = new User(name, uniqueID);
                toAdd.setPassword(password);
                newList.add(toAdd);


                line = bfr.readLine();
            }

            users = newList;

            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //writes all the users in arraylist to database file to save data
    public static void write() {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for (User user : users) {
                pw.println(user.toString());
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //puts user in arraylist, then writes to database
    public static void saveUser(String name, String uniqueID, String password) {
        for (User user : users) {
            String ID = user.getUniqueID();

            if (uniqueID.equals(ID)) {
                users.remove(user);     //removes user from arraylist in order to write with new data at the end
            }
        }

        User toAdd = new User(name, uniqueID);      //creates new user obj to save
        toAdd.setPassword(password);
        users.add(toAdd);

        write();        //writes new arraylist to file
    }


    public ArrayList<User> getUsers() {
        return users;
    }




}
