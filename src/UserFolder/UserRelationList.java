package UserFolder;

//alex

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

//UserList class,
//each line in the text file should be a uniqueID
//arraylist users is a list of every id
//allow storage of friends list and ban list -> similar to cache

//deprecated: we dont need this to save friend or blacklist anymore, cuz we save all of them in the database.txt

/**
 * @author Rocky Chen, Alex
 * @version 3/27/2024
 */
public class UserRelationList {

    private ArrayList<String> users = new ArrayList<String>();

    //each user has either a friends list or ban list, 
    //name of the file containing data is the user id, so this way its unique
    //adds extra 0 if ban list or 1 if friend list, then creates the files in folder
    public UserRelationList() {
        this.users = new ArrayList<String>();
    }
    //do this if friend or ban list is already created, and you just want to make an extra object to read it


    //reads all the IDs in the database to get an up to date arraylist of the users, resets user list to what is read
    //i dont know if necessary?
//    public void read() {
//        try {
//            BufferedReader bfr = new BufferedReader(new FileReader(this.fileName));
//
//            ArrayList<String> newList = new ArrayList<>();
//
//            String line = bfr.readLine();
//
//            while(line != null) {
//                newList.add(line);
//                line = bfr.readLine();
//            }
//
//            this.users = newList;
//
//            bfr.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    //writes all of the IDs in users arraylist to the file to be saved
//    public void write() {
//        try {
//            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.fileName)));
//            for (String ID : users) {
//                pw.println(ID);
//            }
//            pw.flush();
//            pw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public boolean add(String id) {
//        read();     //reads to make sure list is up to date
//        String uniqueID = user.getUniqueID();

        for (String ID : this.users) {
            if (id.equals(ID)) {
                return false;           //user is already added to list
            }
        }

        this.users.add(id);   //user gets added to users list
//        write();                    //data is saved
        return true;                //returns true because user is added

    }

    public boolean remove(String id) {
//        read();     //reads to make sure list is up to date
//        String uniqueID = user.getUniqueID();

        for (String ID : this.users) {
            if (id.equals(ID)) {
                this.users.remove(ID);          //user gets removed from list
//                write();                        //data is saved
                return true;                    //returns true because user is removed
            }
        }

        return false;       //there was no user in the list to remove, so returns false

    }


    public boolean contains(String id) {
//        read();
//        String uniqueID = user.getUniqueID();

        for (String ID : this.users) {
            if (id.equals(ID)) {
                return true;
            }
        }

        return false;
    }


//    public String getFileName() {
//        return this.fileName;
//    }

//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }

    public ArrayList<String> getIDs() {
        return this.users;
    }


    public String toString() {
        String result = "";
        for (String ID : this.users) {
            result += ID + ", ";
        }
        return result.substring(0, result.length() - 2);
    }
}
