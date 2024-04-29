package Functional;

import UserFolder.*;

import java.util.*;
import java.io.*;

public interface PostInterface {

    boolean likePost(User user);

    boolean dislikePost(User user);

    ArrayList<Comment> getComments();

    boolean addComment(User user, Comment comment);

    int getDislikes();

    int getLikes();

    String getText();

    String getImage();

    String getPostCode();

    User getOwner();

    void setPostPath(File postPath);

    File getPostPath();

    ArrayList<User> getHiddenFrom();

}