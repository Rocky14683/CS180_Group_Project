package Functional;

import UserFolder.*;

import java.io.*;

public interface CommentInterface {
    boolean likePost(User user);

    boolean dislikePost(User user);

    int getLikes();

    int getDislikes();

    String getText();

    String getCode();

    Post getParent();

    void setCommentPath(File commentPath);

    File getCommentPath();

    User getOwner();


}