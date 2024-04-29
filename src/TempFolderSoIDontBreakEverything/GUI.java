import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.*;

public class GUI implements Runnable {

    private JFrame frame = new JFrame("iMedia");
    private Container content = frame.getContentPane();

    private static ClientThatIUnderstand client;
    private JButton makePost;
    private JButton mainFeed;
    private JButton friendFeed;
    private JButton profileEdit;
    private JButton search;

    private JTextField usernameField;
    private JTextField passwordField;
    private JButton usernameButton;
    private JButton passwordButton;

    private JTextField username;
    private JTextField password;
    private JButton login;
    private JButton register;

    private JButton searchButton;
    private JTextField userSearch;

    private JButton[] addFriend;
    private JButton[] removeFriend;

    private JButton[] blockUser;
    private JButton[] unblockUser;

    private JButton[] likePost;
    private JButton[] unlikePost;

    private JButton[] dislikePost;
    private JButton[] undislikePost;

    private JButton[][] likeComment;
    private JButton[][] unlikeComment;

    private JButton[][] dislikeComment;
    private JButton[][] undislikeComment;

    private JButton[][] deleteComment;

    private JTextField[] comment;
    private JButton[] addComment;
    //private ActionListener[][] commentListeners;

    private JButton[] hidePost;
    private JButton[] deletePost;

    private JTextField addPost;

    private static GUI gui;

    private JPanel bottomMenu;

    private GridBagConstraints gbc = new GridBagConstraints();

    public static void main(String[] args) {
        client = new ClientThatIUnderstand();
        gui = new GUI();
        Thread thread = new Thread(gui);
        thread.start();
    }

    public GUI() {

    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(profileEdit)) {
                content.removeAll();
                content.add(gui.profileEdit(usernameField, passwordField, usernameButton, passwordButton));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();

            }

            if (e.getSource().equals(search)) {
                content.removeAll();
                content.add(gui.searchUsers(searchButton, userSearch));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();
            }

            if (e.getSource().equals(searchButton)) {
                
                System.out.println("Search button pressed");
                ArrayList<String[]> results = new ArrayList<>();

                try {
                    results = client.search(userSearch.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                content.removeAll();

                content.add(gui.searchUsersWithResults(searchButton, userSearch, results, addFriend, removeFriend, blockUser, unblockUser));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();

            }

            if (e.getSource().equals(login)) {
                if (client.login(username.getText(), password.getText())) {
                    
                    try {
                        ArrayList<String[]> posts = client.getPosts();
                        ArrayList<Integer> indexToRemove = new ArrayList<>();
                        for (int i = 0; i < posts.size(); i++) {
                            String id = client.getId(posts.get(i)[0]);
                            if (client.isBlocked(id)) {
                                indexToRemove.add(i);
                            }
                        }
    
                        int offset = 0;
                        for (Integer i : indexToRemove) {
                            int index = i - offset;
                            posts.remove(index);
                            offset++;
                        }
    
                        content.removeAll();
                        content.add(gui.feed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, content, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                        content.add(bottomMenu, BorderLayout.SOUTH);
                        content.revalidate();
                        content.repaint();
                        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


    
                }

            }

            if (e.getSource().equals(register)) {
                if (client.register(username.getText(), password.getText())) {
                    try {
                        ArrayList<String[]> posts = client.getPosts();
                        ArrayList<Integer> indexToRemove = new ArrayList<>();
                        for (int i = 0; i < posts.size(); i++) {
                            String id = client.getId(posts.get(i)[0]);
                            if (client.isBlocked(id)) {
                                indexToRemove.add(i);
                            }
                        }
    
                        int offset = 0;
                        for (Integer i : indexToRemove) {
                            int index = i - offset;
                            posts.remove(index);
                            offset++;
                        }
    
                        content.removeAll();
                        content.add(gui.feed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, content, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                        content.add(bottomMenu, BorderLayout.SOUTH);
                        content.revalidate();
                        content.repaint();
                        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

    
                }

            }

            if (e.getActionCommand().contains("Add")) {
                String username = e.getActionCommand().substring(4);
                try {
                    System.out.println(username);
                    String id = client.getId(username);
                    System.out.println(id);
                    client.addFriend(id);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println("Search button pressed");
                ArrayList<String[]> results = new ArrayList<>();

                try {
                    results = client.search(userSearch.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                content.removeAll();

                content.add(gui.searchUsersWithResults(searchButton, userSearch, results, addFriend, removeFriend, blockUser, unblockUser));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();
            }

            if (e.getActionCommand().contains("Remove")) {
                String username = e.getActionCommand().substring(7);
                try {
                    System.out.println(username);
                    String id = client.getId(username);
                    System.out.println(id);
                    client.removeFriend(id);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println("Search button pressed");
                ArrayList<String[]> results = new ArrayList<>();

                try {
                    results = client.search(userSearch.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                content.removeAll();

                content.add(gui.searchUsersWithResults(searchButton, userSearch, results, addFriend, removeFriend, blockUser, unblockUser));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();
            }

            if (e.getActionCommand().contains("Block")) {
                String username = e.getActionCommand().substring(6);
                try {
                    System.out.println(username);
                    String id = client.getId(username);
                    System.out.println(id);
                    client.blockUser(id);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println("Search button pressed");
                ArrayList<String[]> results = new ArrayList<>();

                try {
                    results = client.search(userSearch.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                content.removeAll();

                content.add(gui.searchUsersWithResults(searchButton, userSearch, results, addFriend, removeFriend, blockUser, unblockUser));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();
            }

            if (e.getActionCommand().contains("Unblock")) {
                String username = e.getActionCommand().substring(8);
                try {
                    System.out.println(username);
                    String id = client.getId(username);
                    System.out.println(id);
                    client.unblockUser(id);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.println("Search button pressed");
                ArrayList<String[]> results = new ArrayList<>();

                try {
                    results = client.search(userSearch.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                content.removeAll();

                content.add(gui.searchUsersWithResults(searchButton, userSearch, results, addFriend, removeFriend, blockUser, unblockUser));
                content.add(bottomMenu, BorderLayout.SOUTH);
                content.revalidate();
                content.repaint();
            }

            if (e.getSource().equals(mainFeed)) {
                try {
                    ArrayList<String[]> posts = client.getPosts();
                    ArrayList<Integer> indexToRemove = new ArrayList<>();
                    for (int i = 0; i < posts.size(); i++) {
                        String id = client.getId(posts.get(i)[0]);
                        if (client.isBlocked(id)) {
                            indexToRemove.add(i);
                        }
                    }

                    int offset = 0;
                    for (Integer i : indexToRemove) {
                        int index = i - offset;
                        posts.remove(index);
                        offset++;
                    }

                    content.removeAll();
                    content.add(gui.feed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, content, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                    content.add(bottomMenu, BorderLayout.SOUTH);
                    content.revalidate();
                    content.repaint();
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                
            }

            if (e.getSource().equals(makePost)) {
                
                try {
                    client.makePost(addPost.getText());

                    ArrayList<String[]> posts = client.getPosts();

                    content.removeAll();
                    content.add(gui.feed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, content, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                    content.add(bottomMenu, BorderLayout.SOUTH);
                    content.revalidate();
                    content.repaint();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (e.getSource().equals(friendFeed)) {
                try {
                    ArrayList<String[]> posts = client.getPosts();
                    ArrayList<Integer> indexToRemove = new ArrayList<>();
                    for (int i = 0; i < posts.size(); i++) {
                        String id = client.getId(posts.get(i)[0]);
                        if (!client.isFriend(id)) {
                            indexToRemove.add(i);
                        }
                    }

                    int offset = 0;
                    for (Integer i : indexToRemove) {
                        int index = i - offset;
                        posts.remove(index);
                        offset++;
                    }

                    content.removeAll();
                    content.add(gui.feed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, content, likeComment, unlikeComment, dislikeComment, undislikeComment,deleteComment));
                    content.add(bottomMenu, BorderLayout.SOUTH);
                    content.revalidate();
                    content.repaint();
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }


        };
    };

    ActionListener[] likebuttons = new ActionListener[20];
    ActionListener[][] commentListeners = new ActionListener[20][20];

    public void run() {

        usernameField = new JTextField("Enter New Username");
        passwordField = new JTextField("Enter New Password");
        usernameButton = new JButton("Change Username");
        usernameButton.addActionListener(actionListener);
        passwordButton = new JButton("Change Password");
        passwordButton.addActionListener(actionListener);

        addFriend = new JButton[10];
        for (int i = 0; i < 10; i++) {
            addFriend[i] = new JButton();
            addFriend[i].addActionListener(actionListener);
        }
        

        removeFriend = new JButton[10];
        for (int i = 0; i < 10; i++) {
            removeFriend[i] = new JButton();
            removeFriend[i].addActionListener(actionListener);
        }

        blockUser = new JButton[10];
        for (int i = 0; i < 10; i++) {
            blockUser[i] = new JButton();
            blockUser[i].addActionListener(actionListener);
        }

        unblockUser = new JButton[10];
        for (int i = 0; i < 10; i++) {
            unblockUser[i] = new JButton();
            unblockUser[i].addActionListener(actionListener);
        }

        hidePost = new JButton[20];
        for (int i = 0; i < 20; i++) {
            hidePost[i] = new JButton("Hide");
        }

        deletePost = new JButton[20];
        for (int i = 0; i < 20; i++) {
            deletePost[i] = new JButton("Delete");
        }

        likePost = new JButton[20];
        for (int i = 0; i < 20; i++) {
            likePost[i] = new JButton("Like");
        }

        unlikePost = new JButton[20];
        for (int i = 0; i < 20; i++) {
            unlikePost[i] = new JButton("Unlike");
        }

        dislikePost = new JButton[20];
        for (int i = 0; i < 20; i++) {
            dislikePost[i] = new JButton("Dislike");
        }

        undislikePost = new JButton[20];
        for (int i = 0; i < 20; i++) {
            undislikePost[i] = new JButton("Undislike");
        }

        
        likeComment = new JButton[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                likeComment[i][j] = new JButton("Like");
            }
        }

        unlikeComment = new JButton[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                unlikeComment[i][j] = new JButton("Unlike");
            }
        }

        dislikeComment = new JButton[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                dislikeComment[i][j] = new JButton("Dislike");
            }
        }

        undislikeComment = new JButton[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                undislikeComment[i][j] = new JButton("Undislike");
            }
        }

        deleteComment = new JButton[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                deleteComment[i][j] = new JButton("Delete");
            }
        }


        comment = new JTextField[20];
        for (int i = 0; i < 20; i++) {
            comment[i] = new JTextField("Thoughts?");
        }

        addComment = new JButton[20];
        for (int i = 0; i < 20; i++) {
            addComment[i] = new JButton("Post Comment");
        }

        for (int i = 0; i < 20; i++) {
            likebuttons[i] = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            };
        }

        
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                commentListeners[i][j] = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                    }
                };
            }
        }

        

        addPost = new JTextField();

        content.setLayout(new BorderLayout());
        
        gui = new GUI();
        
        content.setLayout(new BorderLayout());

        //Defining all buttons and text fields
        bottomMenu = new JPanel();
        bottomMenu.setLayout(new GridBagLayout());

        makePost = new JButton("Make Post");
        makePost.addActionListener(actionListener);

        mainFeed = new JButton("Main");
        mainFeed.addActionListener(actionListener);

        friendFeed = new JButton("Friends");
        friendFeed.addActionListener(actionListener);

        search = new JButton("Search");
        search.addActionListener(actionListener);

        profileEdit = new JButton("Profile");
        profileEdit.addActionListener(actionListener);

        bottomMenu.setBackground(new Color(100, 100, 100));
        
        gbc.insets = new Insets(0, 10, 0, 10);

        gbc.gridx = 0;
        bottomMenu.add(mainFeed, gbc);

        gbc.gridx = 1;
        bottomMenu.add(friendFeed, gbc);

        gbc.gridx = 2;
        bottomMenu.add(search, gbc);

        gbc.gridx = 3;
        bottomMenu.add(profileEdit, gbc);

        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        userSearch = new JTextField("Enter username");

        bottomMenu.setPreferredSize(new Dimension(100000, 40));

        login = new JButton("Login");
        login.addActionListener(actionListener);

        register = new JButton("Register");
        register.addActionListener(actionListener);

        username = new JTextField("Enter Username");
        password = new JTextField("Enter Password");

        content.add(gui.loginOrRegister(login, username, password, register));

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }


    public JPanel feed(JButton[] deletePost, JButton[] hidePost, JButton[] likePost, JButton[] unlikePost, 
        JButton[] dislikePost, JButton[] undislikePost, JTextField[] comment, JButton[] addComment, 
        ArrayList<String[]> posts, JTextField addPost, JButton makePost, JPanel bottomMenu, 
        Container localContent, JButton[][] likeComment, JButton[][] unlikeComment, JButton[][] dislikeComment, JButton[][] undislikeComment, JButton[][] deleteComment) {


        JPanel returnPanel = new JPanel();
        JPanel[] postPanels = new JPanel[posts.size()];
        JPanel preScrollPanel = new JPanel();
        preScrollPanel.setLayout(new GridLayout());
        Random rand = new Random();

        try {
            ArrayList<Integer> indexToRemove = new ArrayList<>();
            for (int i = 0; i < posts.size(); i++) {
                String id = posts.get(i)[4];
                boolean hidden = client.isHidden(id);
                System.out.println("Hidden?: " + hidden);
                if (hidden) {
                    indexToRemove.add(i);
                }
            }

            int offset = 0;
            for (Integer i : indexToRemove) {
                int index = i - offset;
                posts.remove(index);
                offset++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < posts.size(); i++) {
            
            String postCode = posts.get(i)[4];
            int thingToTrickMyComputer = i;
            likebuttons[i] = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("buttonPressed");
                    if (e.getActionCommand().equals("Like")) {
                        try {
                            client.likePost(postCode);

                            if (client.hasDisliked(postCode)) {
                                client.undislikePost(postCode);
                            }

                            ArrayList<String[]> posts = client.getPosts();
                            localContent.removeAll();
                        
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (e.getActionCommand().equals("Unlike")) {
                        try {
                            client.unlikePost(postCode);

                            ArrayList<String[]> posts = client.getPosts();

                            localContent.removeAll();
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (e.getActionCommand().equals("Dislike")) {
                        try {
                            client.dislikePost(postCode);

                            if (client.hasLiked(postCode)) {
                                client.unlikePost(postCode);
                            }

                            ArrayList<String[]> posts = client.getPosts();

                            localContent.removeAll();
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (e.getActionCommand().equals("Undislike")) {
                        try {
                            client.undislikePost(postCode);

                            ArrayList<String[]> posts = client.getPosts();

                            localContent.removeAll();
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();


                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (e.getActionCommand().equals("Delete")) {
                        try {
                            client.deletePost(postCode);

                            ArrayList<String[]> posts = client.getPosts();

                            localContent.removeAll();
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();
                        } catch (IOException e1) {

                            e1.printStackTrace();
                        }   
                    }

                    if (e.getActionCommand().equals("Hide")) {
                        try {
                            client.hidePost(postCode);

                            ArrayList<String[]> posts = client.getPosts();

                            localContent.removeAll();
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();
                        } catch (IOException e1) {

                            e1.printStackTrace();
                        }   
                    }

                    if (e.getActionCommand().equals("Post Comment")) {
                        try {
                            System.out.println("Trying to post it");
                            client.makeComment(postCode, comment[thingToTrickMyComputer].getText());

                            ArrayList<String[]> posts = client.getPosts();

                            localContent.removeAll();
                            localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                            localContent.add(bottomMenu, BorderLayout.SOUTH);
                            localContent.revalidate();
                            localContent.repaint();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            };

            System.out.println("Feed: " + postCode);
            postPanels[i] = new JPanel();
            postPanels[i].setBackground(new Color((255 - rand.nextInt(100)), (255 - rand.nextInt(100)), (255 - rand.nextInt(100))));
            postPanels[i].setLayout(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        
            postPanels[i].add(new JLabel(posts.get(i)[0]), gbc);

            gbc.gridy = 1;
            postPanels[i].add(new JLabel(posts.get(i)[1]), gbc);

            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = 2;
            postPanels[i].add(new JLabel(posts.get(i)[2]), gbc);
            gbc.gridx = 1;
            likePost[i].addActionListener(likebuttons[i]);
            unlikePost[i].addActionListener(likebuttons[i]);


            try {
                if (!client.hasLiked(postCode)) {
                    
                    postPanels[i].add(likePost[i], gbc);
                } else {
                    
                    postPanels[i].add(unlikePost[i], gbc);
                }
            } catch (IOException e1) {

                e1.printStackTrace();
            }

            gbc.gridx = 2;
            postPanels[i].add(new JLabel(posts.get(i)[3]), gbc);
            gbc.gridx = 3;

            dislikePost[i].addActionListener(likebuttons[i]);
            undislikePost[i].addActionListener(likebuttons[i]);
            try {
                if (!client.hasDisliked(postCode)) {
                    postPanels[i].add(dislikePost[i], gbc);
                } else {
                    postPanels[i].add(undislikePost[i], gbc);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            gbc.gridx = 4;

            hidePost[i].addActionListener(likebuttons[i]);
            deletePost[i].addActionListener(likebuttons[i]);
            try {
                if (!client.isOwnerPost(postCode)) {
                    postPanels[i].add(hidePost[i], gbc);
                } else {
                    postPanels[i].add(deletePost[i], gbc);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            gbc.gridy = 3;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;


            //----------------------------------------------------------------COMMENTS
            postPanels[i].add(comment[i], gbc);
            gbc.gridx = 3;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            addComment[i].addActionListener(likebuttons[i]);
            postPanels[i].add(addComment[i], gbc);


            
            try {
                ArrayList<String[]> comments = client.getComments(postCode);
                System.out.println("Number of comments: " + comments.size());
                GridBagConstraints gbc2 = new GridBagConstraints();
                
                for (int j = 0; j < comments.size(); j++) {

                    if(comments.get(0)[0].equals("")) {
                        System.out.println("Yay");
                        continue;
                    }

                    String commentCode = comments.get(j)[4];
                    commentListeners[i][j] = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Button pressed");
                            if (e.getActionCommand().equals("Like")) {
                                try {
                                    System.out.println("Trying to like comment");
                                    client.likeComment(postCode, commentCode);

                                    if (client.hasDislikedComment(postCode, commentCode)) {
                                        client.undislikeComment(postCode, commentCode);
                                    }

                                    ArrayList<String[]> posts = client.getPosts();

                                    localContent.removeAll();
                                    localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                                    localContent.add(bottomMenu, BorderLayout.SOUTH);
                                    localContent.revalidate();
                                    localContent.repaint();

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            if (e.getActionCommand().equals("Dislike")) {
                                try {
                                    client.dislikeComment(postCode, commentCode);

                                    if (client.hasLikedComment(postCode, commentCode)) {
                                        client.unlikeComment(postCode, commentCode);
                                    }

                                    ArrayList<String[]> posts = client.getPosts();

                                    localContent.removeAll();
                                    localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                                    localContent.add(bottomMenu, BorderLayout.SOUTH);
                                    localContent.revalidate();
                                    localContent.repaint();

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            if (e.getActionCommand().equals("Unlike")) {
                                try {
                                    client.unlikeComment(postCode, commentCode);

                                    ArrayList<String[]> posts = client.getPosts();

                                    localContent.removeAll();
                                    localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                                    localContent.add(bottomMenu, BorderLayout.SOUTH);
                                    localContent.revalidate();
                                    localContent.repaint();

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            if (e.getActionCommand().equals("Undislike")) {
                                try {
                                    client.undislikeComment(postCode, commentCode);

                                    ArrayList<String[]> posts = client.getPosts();

                                    localContent.removeAll();
                                    localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                                    localContent.add(bottomMenu, BorderLayout.SOUTH);
                                    localContent.revalidate();
                                    localContent.repaint();

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                
                            }

                            System.out.println("," + e.getActionCommand() + ",");
                                if (e.getActionCommand().equals("Delete")) {
                                    System.out.println("Trying to delete comment");
                                    try {
                                        client.deleteComment(postCode, commentCode);

                                        ArrayList<String[]> posts = client.getPosts();

                                        localContent.removeAll();
                                        localContent.add(gui.upddateFeed(deletePost, hidePost, likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost, bottomMenu, likeComment, unlikeComment, dislikeComment, undislikeComment, deleteComment));
                                        localContent.add(bottomMenu, BorderLayout.SOUTH);
                                        localContent.revalidate();
                                        localContent.repaint();


                                    } catch (IOException e1) {
                                     
                                        e1.printStackTrace();
                                    }
                                }
                            
                        }
                    };


                    JPanel commentPanel = new JPanel();
                    commentPanel.setLayout(new GridBagLayout());
                    

                    likeComment[i][j].addActionListener(commentListeners[i][j]);
                    dislikeComment[i][j].addActionListener(commentListeners[i][j]);
                    
                    unlikeComment[i][j].addActionListener(commentListeners[i][j]);
                    undislikeComment[i][j].addActionListener(commentListeners[i][j]);

                    deleteComment[i][j].addActionListener(commentListeners[i][j]);

                    gbc2.gridx = 0;
                    gbc2.gridy = 0 + 2 * j;
                    commentPanel.add(new JLabel(comments.get(j)[0] + ":    "), gbc2);
                    gbc2.gridx = 1;
                    commentPanel.add(new JLabel(comments.get(j)[1]), gbc2);
                    gbc2.gridx = 3;
                    if (client.isOwnerComment(postCode, commentCode) || client.isOwnerPost(postCode)) {
                        commentPanel.add(deleteComment[i][j], gbc2);
                    }
                    gbc2.gridx = 0;
                    gbc2.gridy = 1 + j * 2;
                    commentPanel.add(new JLabel(comments.get(j)[2]), gbc2);
                    gbc2.gridx = 1; 
                    if (!client.hasLikedComment(postCode, commentCode)) {
                        commentPanel.add(likeComment[i][j], gbc2);
                    } else {
                        commentPanel.add(unlikeComment[i][j], gbc2);
                    }
                    gbc2.gridx = 2;
                    commentPanel.add(new JLabel(comments.get(j)[3]), gbc2);
                    gbc2.gridx = 3; 
                    if (!client.hasDislikedComment(postCode, commentCode)) {
                        commentPanel.add(dislikeComment[i][j], gbc2);
                    } else {
                        commentPanel.add(undislikeComment[i][j], gbc2);
                    }

                    gbc.gridy = 4 + j; 
                    postPanels[i].add(commentPanel, gbc);
                }
                System.out.println(comments.size());
                //postPanels[i].setPreferredSize(new Dimension(200, 50 + 70 * comments.size()));
                System.out.println("Perfer size: " + postPanels[i].getPreferredSize());

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            preScrollPanel.add(postPanels[i]);
        }
        preScrollPanel.setLayout(new GridLayout(0, 1));

        JPanel newPostPanel = new JPanel();
        newPostPanel.setLayout(new BorderLayout());
        newPostPanel.add(new JLabel("Make new Post: "), BorderLayout.BEFORE_FIRST_LINE);
        newPostPanel.add(addPost);
        newPostPanel.add(makePost, BorderLayout.SOUTH);
        returnPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(preScrollPanel);
        newPostPanel.setPreferredSize(new Dimension(100000, 100));
        returnPanel.add(newPostPanel, BorderLayout.NORTH);
        returnPanel.add(scrollPane);

        return returnPanel;
    }

    public JPanel upddateFeed(JButton[] deletePost, JButton[] hidePost, JButton[] likePost, JButton[] unlikePost, 
        JButton[] dislikePost, JButton[] undislikePost, JTextField[] comment, JButton[] addComment, 
        ArrayList<String[]> posts, JTextField addPost, JButton makePost, JPanel bottomMenu, 
        JButton[][] likeComment, JButton[][] unlikeComment, JButton[][] dislikeComment, JButton[][] undislikeComment, 
        JButton[][] deleteCommentLocal) {
        JPanel returnPanel = new JPanel();
        JPanel[] postPanels = new JPanel[posts.size()];
        JPanel preScrollPanel = new JPanel();
        preScrollPanel.setLayout(new GridLayout());

        Random rand = new Random();

        try {
            ArrayList<Integer> indexToRemove = new ArrayList<>();
            for (int i = 0; i < posts.size(); i++) {
                String id = posts.get(i)[4];
                if (client.isHidden(id)) {
                    indexToRemove.add(i);
                }
            }

            int offset = 0;
            for (Integer i : indexToRemove) {
                int index = i - offset;
                posts.remove(index);
                offset++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < posts.size(); i++) {

            String postCode = posts.get(i)[4];

            System.out.println("Feed: " + postCode);
            postPanels[i] = new JPanel();
            postPanels[i].setLayout(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);



            gbc.gridx = 0;
            gbc.gridy = -1;
            gbc.gridwidth = 4;
            postPanels[i].setBackground(new Color((255 - rand.nextInt(100)), (255 - rand.nextInt(100)), (255 - rand.nextInt(100))));
            postPanels[i].add(new JSeparator(), gbc);

            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        
            postPanels[i].add(new JLabel(posts.get(i)[0]), gbc);

            gbc.gridy = 1;
            postPanels[i].add(new JLabel(posts.get(i)[1]), gbc);

            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = 2;
            postPanels[i].add(new JLabel(posts.get(i)[2]), gbc);
            gbc.gridx = 1;

            try {
                if (!client.hasLiked(postCode)) {
                    postPanels[i].add(likePost[i], gbc);
                } else {
                    postPanels[i].add(unlikePost[i], gbc);
                }
            } catch (IOException e1) {

                e1.printStackTrace();
            }

            gbc.gridx = 2;
            postPanels[i].add(new JLabel(posts.get(i)[3]), gbc);
            gbc.gridx = 3;
            try {
                if (!client.hasDisliked(postCode)) {
                    postPanels[i].add(dislikePost[i], gbc);
                } else {
                    postPanels[i].add(undislikePost[i], gbc);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            gbc.gridx = 4;

            try {
                if (!client.isOwnerPost(postCode)) {
                    postPanels[i].add(hidePost[i], gbc);
                } else {
                    postPanels[i].add(deletePost[i], gbc);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            

            
            gbc.gridy = 3;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;


            //----------------------------------------------------------------COMMENTS
            postPanels[i].add(comment[i], gbc);
            gbc.gridx = 3;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            postPanels[i].add(addComment[i], gbc);

            gbc.gridy = 4;

            try {
                ArrayList<String[]> comments = client.getComments(postCode);
                System.out.println("Number of comments: " + comments.size());
                GridBagConstraints gbc2 = new GridBagConstraints();

                if(comments.get(0)[0].equals("")) {
                    System.out.println("Yay");
                    continue;
                }
                
                for (int j = 0; j < comments.size(); j++) {
                    JPanel commentPanel = new JPanel();
                    commentPanel.setLayout(new GridBagLayout());
                    String commentCode = comments.get(j)[4];
                    
                    gbc2.gridx = 0;
                    gbc2.gridy = 0 + 2 * j;
                    commentPanel.add(new JLabel(comments.get(j)[0] + ":    "), gbc2);
                    gbc2.gridx = 1;
                    commentPanel.add(new JLabel(comments.get(j)[1]), gbc2);

                    gbc2.gridx = 3;
                    if (client.isOwnerComment(postCode, commentCode) || client.isOwnerPost(postCode)) {
                        commentPanel.add(deleteCommentLocal[i][j], gbc2);
                    }
                    gbc2.gridx = 0;
                    gbc2.gridy = 1 + j * 2;
                    commentPanel.add(new JLabel(comments.get(j)[2]), gbc2);
                    gbc2.gridx = 1; 

                    if (!client.hasLikedComment(postCode, commentCode)) {
                        commentPanel.add(likeComment[i][j], gbc2);
                    } else {
                        commentPanel.add(unlikeComment[i][j], gbc2);
                    }
                    gbc2.gridx = 2;
                    commentPanel.add(new JLabel(comments.get(j)[3]), gbc2);
                    gbc2.gridx = 3; 
                    if (!client.hasDislikedComment(postCode, commentCode)) {
                        commentPanel.add(dislikeComment[i][j], gbc2);
                    } else {
                        commentPanel.add(undislikeComment[i][j], gbc2);
                    }
                    

                    gbc.gridy = 4 + j; 
                    gbc.gridx = 0;
                    gbc.gridwidth = 5;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    postPanels[i].add(commentPanel, gbc);
                    gbc.fill = GridBagConstraints.NONE;
                }
                System.out.println(comments.size());
                //postPanels[i].setPreferredSize(new Dimension(200, 50 + 70 * comments.size()));
                System.out.println("Perfer size: " + postPanels[i].getPreferredSize());

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            

            preScrollPanel.add(postPanels[i]);
        }
        preScrollPanel.setLayout(new GridLayout(0, 1));

        JPanel newPostPanel = new JPanel();
        newPostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newPostPanel.setLayout(new BorderLayout());
        newPostPanel.add(new JLabel("Make new Post: "), BorderLayout.BEFORE_FIRST_LINE);
        newPostPanel.add(addPost);
        newPostPanel.add(makePost, BorderLayout.SOUTH);
        returnPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(preScrollPanel);
        //newPostPanel.setPreferredSize(new Dimension(300, 100));
        returnPanel.add(newPostPanel, BorderLayout.NORTH);
        returnPanel.add(scrollPane);

        return returnPanel;
    }

    public JPanel profileEdit(JTextField usernameField, JTextField passwordField, JButton usernameButton, JButton passwordButton) {
        // View firends
        // View blocks
        // Change username
        // Change Password
        Random rand = new Random();

        JPanel usernamePasswordPanel = new JPanel();
        usernamePasswordPanel.setBackground(new Color((255 - rand.nextInt(100)), (255 - rand.nextInt(100)), (255 - rand.nextInt(100))));

        //Making username and pawword editing
        usernamePasswordPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.gridy = 0;
        gbc2.gridx = 0;
        usernamePasswordPanel.add(usernameField, gbc2);

        gbc2.gridy = 1;
        gbc2.gridheight = 2;
        usernamePasswordPanel.add(usernameButton, gbc2);

        gbc2.gridheight = 1;
        gbc2.gridy = 3;
        usernamePasswordPanel.add(passwordField, gbc2);

        gbc2.gridheight = 1;
        gbc2.gridy = 4;
        usernamePasswordPanel.add(passwordButton, gbc2);

        return usernamePasswordPanel;
        

    }

    public JPanel searchUsers(JButton searchButton, JTextField userSearch) {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        Random rand = new Random();
        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color((255 - rand.nextInt(100)), (255 - rand.nextInt(100)), (255 - rand.nextInt(100))));;

        
        topPanel.add(userSearch);

        
        topPanel.add(searchButton);

        searchPanel.add(topPanel, BorderLayout.NORTH);

        JPanel results = new JPanel();

        results.add(new JLabel("Enter username to get results"));

        JScrollPane resultsScroll = new JScrollPane(results);

        searchPanel.add(resultsScroll);

        return searchPanel;

    }

    public JPanel searchUsersWithResults(JButton searchButton, JTextField userSearch, ArrayList<String[]> searchResults, JButton[] addFriend, JButton[] removeFriend, JButton[] blockUser, JButton[] unblockUser) {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        userSearch.setText("Enter username");
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(100, 100, 100));

        topPanel.add(userSearch);
        topPanel.add(searchButton);

        searchPanel.add(topPanel, BorderLayout.NORTH);

        JPanel results = new JPanel();
        results.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        int numResults = searchResults.size();
        if (searchResults.size() > 10) {
            numResults = 10;
        }
        for (int i = 0; i < numResults; i++) {
            try {
                gbc.gridy = i;
                gbc.gridx = 0;

                results.add(new JLabel(searchResults.get(i)[0]), gbc);

                gbc.gridx = 1;
                if (client.isFriend(searchResults.get(i)[1])) {
                    removeFriend[i].setText("Remove " + searchResults.get(i)[0]);
                    results.add(removeFriend[i], gbc);
                } else {
                    addFriend[i].setText("Add " + searchResults.get(i)[0]);
                    results.add(addFriend[i], gbc);
                }

                gbc.gridx = 2;
                if (client.isBlocked(searchResults.get(i)[1])) {
                    unblockUser[i].setText("Unblock " + searchResults.get(i)[0]);
                    results.add(unblockUser[i], gbc);
                } else {
                    blockUser[i].setText("Block " + searchResults.get(i)[0]);
                    results.add(blockUser[i], gbc);
                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }

        JScrollPane resultsScroll = new JScrollPane(results);

        searchPanel.add(resultsScroll);

        return searchPanel;
    }

    public JPanel loginOrRegister(JButton login, JTextField username, JTextField password, JButton register) {
        JPanel loginPanel = new JPanel();

        Random rand = new Random();

        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color((255 - rand.nextInt(100)), (255 - rand.nextInt(100)), (255 - rand.nextInt(100))));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel title = new JLabel("Login or Register");
        title.setFont(new Font("Arial", 1, 30));
        loginPanel.add(title);
        gbc.gridy = 2;
        loginPanel.add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        loginPanel.add(password, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 30, 5, 30);
        loginPanel.add(login, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        loginPanel.add(register, gbc);

        return loginPanel;
    }



    

}
