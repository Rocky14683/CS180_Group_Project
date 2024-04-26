package TempFolderSoIDontBreakEverything;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GUI implements Runnable {


    private JFrame frame = new JFrame("Main Feed");
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

    private JButton[] likeComment;
    private JButton[] unlikeComment;

    private JButton[] dislikeComment;
    private JButton[] undislikeComment;

    private JTextField[] comment;
    private JButton[] addComment;

    private JTextField addPost;

    private static GUI gui;

    private JPanel bottomMenu;

    private GridBagConstraints gbc = new GridBagConstraints();

    private JPanel variablePanel = new JPanel();

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
                    content.removeAll();
                    content.add(bottomMenu, BorderLayout.SOUTH);
                    content.revalidate();
                    content.repaint();
                }
            }

            if (e.getSource().equals(register)) {
                if (client.register(username.getText(), password.getText())) {
                    content.removeAll();
                    content.add(bottomMenu, BorderLayout.SOUTH);
                    content.revalidate();
                    content.repaint();
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
                    content.add(gui.feed(likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost));
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
                    content.add(gui.feed(likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost));
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
                    content.add(gui.feed(likePost, unlikePost, dislikePost, undislikePost, comment, addComment, posts, addPost, makePost));
                    content.add(bottomMenu, BorderLayout.SOUTH);
                    content.revalidate();
                    content.repaint();
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
    };

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

        
        likeComment = new JButton[20];
        for (int i = 0; i < 20; i++) {
            likeComment[i] = new JButton("like");
        }

        unlikeComment = new JButton[20];
        for (int i = 0; i < 20; i++) {
            unlikeComment[i] = new JButton("Unlike");
        }

        dislikeComment = new JButton[20];
        for (int i = 0; i < 20; i++) {
            dislikeComment[i] = new JButton("Dislike");
        }

        undislikeComment = new JButton[20];
        for (int i = 0; i < 20; i++) {
            undislikeComment[i] = new JButton("Undislike");
        }


        comment = new JTextField[20];
        for (int i = 0; i < 20; i++) {
            comment[i] = new JTextField();
        }

        addComment = new JButton[20];
        for (int i = 0; i < 20; i++) {
            addComment[i] = new JButton();
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


    public JPanel feed(JButton[] likePost, JButton[] unlikePost, JButton[] dislikePost, JButton[] undislikePost, JTextField[] comment, JButton[] addComment, ArrayList<String[]> posts, JTextField addPost, JButton makePost) {
        JPanel returnPanel = new JPanel();
        JPanel[] postPanels = new JPanel[posts.size()];
        JPanel preScrollPanel = new JPanel();
        preScrollPanel.setLayout(new GridLayout());

        for (int i = 0; i < posts.size(); i++) {
            postPanels[i] = new JPanel();
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
            postPanels[i].add(likePost[i], gbc);
            gbc.gridx = 2;
            postPanels[i].add(new JLabel(posts.get(i)[3]), gbc);
            gbc.gridx = 3;
            postPanels[i].add(dislikePost[i], gbc);

            postPanels[i].setPreferredSize(new Dimension(200, 100));

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

    public JPanel profileEdit(JTextField usernameField, JTextField passwordField, JButton usernameButton, JButton passwordButton) {
        // View firends
        // View blocks
        // Change username
        // Change Password
        
        JPanel usernamePasswordPanel = new JPanel();

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

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(100, 100, 100));

        
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

        loginPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(password, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        loginPanel.add(login, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(register, gbc);

        return loginPanel;
    }



    

}
