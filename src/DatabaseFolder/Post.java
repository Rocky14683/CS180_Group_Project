package DatabaseFolder;

import UserFolder.*;
import java.util.*;

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

    
}
