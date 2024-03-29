package UserFolder;

//alex

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

//DatabaseList class, 
//each line in the text file should be a uniqueID
//arraylist users is a list of every id
public class DatabaseList {

    private String fileName;
    private ArrayList<String> users = new ArrayList<String>();

    //each user has either a friends list or ban list, 
    //name of the file containing data is the user id, so this way its unique
    //adds extra 0 if ban list or 1 if friend list, then creates the files in folder
    public DatabaseList(String uniqueID, boolean friendsList) {
        this.fileName = (uniqueID + 0);
        if (friendsList) {
            this.fileName = (uniqueID + 1);
        }

        try {
            File newFile = new File(this.fileName + ".txt");
            newFile.createNewFile();
        } catch (Exception e) {
            //does nothing, file is already created
        }

    }

    //do this if friend or ban list is already created, and you just want to make an extra object to read it
    public DatabaseList(String fileName) {
        this.fileName = fileName;
    }


    //reads all the IDs in the database to get an up to date arraylist of the users, resets user list to what is read
    //i dont know if necessary?
    public void read() {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(this.fileName));

            ArrayList<String> newList = new ArrayList<>();

            String line = bfr.readLine();

            while(line != null) {
                newList.add(line);
                line = bfr.readLine();
            }

            this.users = newList;

            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //writes all of the IDs in users arraylist to the file to be saved
    public void write() {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.fileName)));
            for (String ID : users) {
                pw.println(ID);
            }
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean add(User user) {
        read();     //reads to make sure list is up to date
        String uniqueID = user.getUniqueID();

        for(String ID : this.users) {
            if (uniqueID.equals(ID)) {
                return false;           //user is already added to list
            }
        }

        this.users.add(uniqueID);   //user gets added to users list
        write();                    //data is saved
        return true;                //returns true because user is added

    }

    public boolean remove(User user) {
        read();     //reads to make sure list is up to date
        String uniqueID = user.getUniqueID();

        for(String ID : this.users) {
            if (uniqueID.equals(ID)) {
                this.users.remove(ID);          //user gets removed from list
                write();                        //data is saved
                return true;                    //returns true because user is removed
            }
        }

        return false;       //there was no user in the list to remove, so returns false

    }


    public boolean contains(User user) {
        read();
        String uniqueID = user.getUniqueID();

        for(String ID : this.users) {
            if (uniqueID.equals(ID)) {
                return true;
            }
        }

        return false;
    }



    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<String> UserIDs) {
        this.users = UserIDs;
    }
    
}
