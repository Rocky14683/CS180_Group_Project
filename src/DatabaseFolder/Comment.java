package DatabaseFolder;
import java.util.ArrayList;


import UserFolder.*;


public class Comment {
    private int likes;
    private ArrayList<User> likeUsers;
    private int dislikes;
    private ArrayList<User> dislikeUsers;
    private String text;
    private User owner;
    private String code;
    private static int numComments = 0;

    public Comment(String text, User owner) {
        likes = 0;
        dislikes = 0;
        likeUsers = new ArrayList<>();
        dislikeUsers = new ArrayList<>();

        this.text = text;
        this.owner = owner;

        numComments += 1;
        this.code = String.format("%s-%08d", owner.getUserId(), numComments);
    }

    public boolean likePost(User user) {
        for (User u : likeUsers) {
            if (u.equals(user)){
                return false;
            }
        }
        if (user.equals(owner)) {
            return false;
        }

        likes += 1;
        likeUsers.add(user);
        return true;
    }

    public boolean dislikePost(User user) {
        for (User u : dislikeUsers) {
            if (u.equals(user)){
                return false;
            }
        }
        if (user.equals(owner)) {
            return false;
        }
        dislikes += 1;
        dislikeUsers.add(user);
        return true;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }

    
}
