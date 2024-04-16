package DatabaseFolder;

import UserFolder.*;
import java.util.*;
import java.io.*;

public class Post {

    private static int numPosts = 0;
    private String text;    
    private String image; 
    private String postCode;
    private User owner;
    private int likes;
    private ArrayList<User> likesUsers;
    private int dislikes;
    private ArrayList<User> dislikesUsers;
    private ArrayList<Comment> comments;
    private ArrayList<User> hiddenFrom;
    private File postPath;


    public Post(String text, String image, User owner) {
        this.text = text;
        this.image = image;
        this.owner = owner;
        numPosts += 1;
        this.postCode = String.format("%s-%04d", owner.getUserId(), numPosts);
        likes = 0;
        dislikes = 0;
        comments = new ArrayList<>();
        hiddenFrom = new ArrayList<>();
        likesUsers = new ArrayList<>();
        dislikesUsers = new ArrayList<>();

    }

    public Post(User owner, String text, ArrayList<User> likes, ArrayList<User> dislikes, ArrayList<User> hidden, ArrayList<Comment> comments) {
        this.owner = owner;
        this.text = text;
        this.likes = likes.size();
        this.likesUsers = likes;
        this.dislikes = dislikes.size();
        this.dislikesUsers = dislikes;
        this.hiddenFrom = hidden;
        this.comments = comments;
    }

    public Post() {
        likes = -1; //Indicates its not a valid post
    }

    public String toString() {
        String returnString = owner + ", Text: " + text + ", Likes: " + likesUsers + ", Dislikes: " + dislikesUsers;

        return returnString;
    }

    public boolean likePost(User user) {
        for (User u : likesUsers) {
            if (u.equals(user)){
                return false;
            }
        }
        if (user.equals(owner)) {
            return false;
        }

        likes += 1;
        likesUsers.add(user);
        return true;
    }

    public boolean dislikePost(User user) {
        for (User u : dislikesUsers) {
            if (u.equals(user)){
                return false;
            }
        }
        if (user.equals(owner)) {
            return false;
        }

        dislikes += 1;
        dislikesUsers.add(user);
        return true;
    }

    public boolean addComment(User user, Comment comment) {
        comments.add(comment);

        return true;
    }

    public int getDislikes() {
        return dislikes;
    }
    
    public int getLikes() {
        return likes;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String getPostCode() {
        return postCode;
    }

    public User getOwner() {
        return owner;
    }

    public void setPostPath(File postPath) {
        this.postPath = postPath;
    }

    public File getPostPath() {
        return postPath;
    }

    public ArrayList<User> getHiddenFrom() {
        return hiddenFrom;
    }

    
}
