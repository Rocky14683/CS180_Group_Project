package TempFolderSoIDontBreakEverything;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PostGUI {
    JPanel postPanel = new JPanel();
    Color postColor = new Color(200, 200, 200);
    JPanel likeDislikPanel = new JPanel();
    JPanel colorSpacerPanel = new JPanel();
    JButton dislikeButton = new JButton("Disike Post");
    JButton likeButton = new JButton("Like Post");
    Post post;
    int likes;
    int dislikes;
    String owner;
    String text;

    public PostGUI(Post post) {
        this.post = post;
        this.likes = post.getLikes();
        this.dislikes = post.getDislikes();
        this.owner = post.getOwner().getUsername();
        this.text = post.getText();
        this.redrawPost();

    }

    public void updateLikes() {
        likes++;
        this.redrawPost();
    }

    public void updateDislikes() {
        dislikes++;
        this.redrawPost();
    }

    public void redrawPost() {
        
        postPanel.add(new JLabel(owner));
        postPanel.add(new JLabel(text));


        colorSpacerPanel.setBackground(postColor);
        likeDislikPanel.add(new JLabel("" + likes));
        likeDislikPanel.add(likeButton);
        likeDislikPanel.add(colorSpacerPanel);
        likeDislikPanel.add(new JLabel("" + likes));
        likeDislikPanel.add(dislikeButton);
        likeDislikPanel.add(colorSpacerPanel);
        likeDislikPanel.add(colorSpacerPanel);
        likeDislikPanel.add(colorSpacerPanel);
        likeDislikPanel.add(colorSpacerPanel);
        likeDislikPanel.add(colorSpacerPanel);
        likeDislikPanel.setBackground(postColor);

        postPanel.add(likeDislikPanel);

        postPanel.setLayout(new GridLayout(3, 1));

        postPanel.setBackground(postColor);
    }
}

