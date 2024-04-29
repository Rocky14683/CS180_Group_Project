package Functional;
import java.util.ArrayList;
import java.io.*;

import UserFolder.*;


public class Comment {
    private int likes;
    private ArrayList<User> likeUsers;
    private int dislikes;
    private ArrayList<User> dislikeUsers;
    private String text;
    private User owner;
    private String code;
    private Post parent;
    private File commentPath;
    private static int numComments = 0;

    public Comment(Post parent, String text, User owner) { //Making new comment
        likes = 0;
        dislikes = 0;
        likeUsers = new ArrayList<>();
        dislikeUsers = new ArrayList<>();
        this.text = text;
        this.owner = owner;
        this.parent = parent;
        numComments += 1;
        this.code = String.format("%s-%08d", owner.getUserId(), numComments);
    }

    public Comment(ArrayList<User> likes, ArrayList<User> dislikes, String text, User owner, Post parent, String code) {
        this.likes = likes.size();
        this.dislikes = dislikes.size();
        this.dislikeUsers = dislikes;
        this.likeUsers = likes;
        this.text = text;
        this.owner = owner;
        this.parent = parent;
        this.code = code;
    }

    public Comment() {
        likes = -1; //Indicates its null
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

    public Post getParent() {
        return parent;
    }

    public void setCommentPath(File commentPath) {
        this.commentPath = commentPath;
    }

    public File getCommentPath() {
        return commentPath;
    }

    public User getOwner() {
        return owner;
    }

    //same format as post (owner, text, likes, dislikes)
    public String toString() {
        String commentString = this.getOwner() + ", Text: " + this.getText() +
                ", Likes: " + this.getLikes() + ", Dislikes: " + this.getDislikes();
        return commentString;
    }

}