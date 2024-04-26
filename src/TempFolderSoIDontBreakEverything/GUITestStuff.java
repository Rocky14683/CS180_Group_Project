// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.*;


// public class GUITestStuff implements Runnable{

//     JButton test1;
//     JButton test2;
//     JButton test3;
//     JButton test4;

//     int postNum = 0;

//     ArrayList<PostGUI> allPosts = new ArrayList<>();

//     ActionListener actionListener = new ActionListener() {
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             System.out.println(e.getActionCommand());

//             if (e.getActionCommand().contains("Like Post")) {
//                 int number = Integer.valueOf(e.getActionCommand().split("    ")[1]);
//                 System.out.println(number);
//                 allPosts.get(number).updateLikes();
                
//             }
//         }
//     };
//     public static void main(String[] args) {
//         // SwingUtilities.invokeLater(new GUITestStuff());
//     }

//     public void run() {
//         Color optionColor = new Color(100, 100, 100);
//         JFrame frame = new JFrame("Paint Thing");

//         Container content = frame.getContentPane();
//         content.setLayout(new BorderLayout());

//         JPanel optionsPanel = new JPanel();
//         JPanel colorPanel = new JPanel();


//         test1 = new JButton("Test1");
//         test1.setPreferredSize(new Dimension(100, 20));

//         test2 = new JButton("Test2");
//         test2.setPreferredSize(new Dimension(100, 20));

//         test3 = new JButton("Test3");
//         test3.setPreferredSize(new Dimension(100, 20));

//         test4 = new JButton("Test4");
//         test4.setPreferredSize(new Dimension(100, 20));

//         optionsPanel.add(test1);
//         optionsPanel.add(test2);
//         optionsPanel.add(test3);
//         optionsPanel.add(test4);

//         //optionsPanel.setLayout(new GridLayout(1, 4, 20, 10));
//         //optionsPanel.setPreferredSize(new Dimension(10, 10));
//         optionsPanel.setBackground(optionColor);
//         optionsPanel.setPreferredSize(new Dimension(100, 30));

//         frame.add(optionsPanel, BorderLayout.SOUTH);
        
//         JPanel otherPanel = new JPanel();
//         Post testPost = new Post("Testing my Posts", null, new User("Lucas Abreu", "00000"));
//         PostGUI post1= new PostGUI(testPost, postNum);

//         Post testPost2 = new Post("Testing my Posts again", null, new User("Lucas Abreu 2", "00000"));
//         PostGUI post2= new PostGUI(testPost2, postNum);

//         Post testPost3 = new Post("Last test for the post thing", null, new User("Lucas Abreu 3", "00000"));
//         PostGUI post3= new PostGUI(testPost3, postNum);

//         Post testPost4 = new Post("Testing my Posts", null, new User("Lucas Abreu", "00000"));
//         PostGUI post4= new PostGUI(testPost4, postNum);

//         Post testPost5 = new Post("Testing my Posts again", null, new User("Lucas Abreu 2", "00000"));
//         PostGUI post5= new PostGUI(testPost5, postNum);

//         Post testPost6 = new Post("Last test for the post thing", null, new User("Lucas Abreu 3", "00000"));
//         PostGUI post6= new PostGUI(testPost6, postNum);

//         //otherPanel = redrawPosts();
//         JScrollPane scroll = new JScrollPane(otherPanel);
//         frame.add(scroll, BorderLayout.CENTER);

//         System.out.println("test");

//         frame.setSize(600, 400);
//         frame.setLocationRelativeTo(null);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setVisible(true);
//         content.revalidate();
//         content.repaint();



//     }

//     public class PostGUI {
//         JPanel postPanel = new JPanel();
//         Color postColor = new Color(200, 200, 200);
//         JPanel likeDislikPanel = new JPanel();
//         JPanel colorSpacerPanel = new JPanel();
//         JButton dislikeButton;
//         JButton likeButton;
//         Post post;
//         int likes;
//         int dislikes;
//         String owner;
//         String text;
    
//         public PostGUI(Post post, int num) {
//             this.post = post;
//             this.likes = post.getLikes();
//             this.dislikes = post.getDislikes();
//             this.owner = post.getOwner().getUsername();
//             this.text = post.getText();
//             dislikeButton = new JButton("Disike Post    " + postNum);
//             likeButton = new JButton("Like Post    " + postNum);
            
//             dislikeButton.addActionListener(actionListener);
//             likeButton.addActionListener(actionListener);
//             this.redrawPost();

//             postNum++;

//             allPosts.add(this);
//         }
    
//         public void updateLikes() {
//             System.out.println(owner);
//             System.out.println(likes);
//             likes++;
//             this.redrawPost();
//         }
    
//         public void updateDislikes() {
//             dislikes++;
//             this.redrawPost();
//         }
    
//         public void redrawPost() {
            
//             postPanel.add(new JLabel(owner));
//             postPanel.add(new JLabel(text));
    
    
//             colorSpacerPanel.setBackground(postColor);
//             likeDislikPanel.add(new JLabel("" + likes));
//             likeDislikPanel.add(likeButton);
//             likeDislikPanel.add(colorSpacerPanel);
//             likeDislikPanel.add(new JLabel("" + dislikes));
//             likeDislikPanel.add(dislikeButton);
//             likeDislikPanel.add(colorSpacerPanel);
//             likeDislikPanel.add(colorSpacerPanel);
//             likeDislikPanel.add(colorSpacerPanel);
//             likeDislikPanel.add(colorSpacerPanel);
//             likeDislikPanel.add(colorSpacerPanel);
//             likeDislikPanel.setBackground(postColor);
    
//             postPanel.add(likeDislikPanel);
    
//             postPanel.setLayout(new GridLayout(3, 1));
    
//             postPanel.setBackground(postColor);
//         }
//     }

// }
