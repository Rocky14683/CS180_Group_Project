import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MoreGUITests implements Runnable {


    JFrame frame = new JFrame("Main Feed");
    Container content = frame.getContentPane();

    JButton testButton;

    JPanel bottomMenu;

    ArrayList<JPanel> posts;

    GridBagConstraints gbc = new GridBagConstraints();

    JPanel variablePanel = new JPanel();

    static boolean change;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(testButton)) {
                System.out.println("Button pressed");
                content.removeAll();
                content.add(variablePanel);
                content.revalidate();
                content.repaint();
            }
        };
    };

    public void run() {
        
        testButton = new JButton("test");
        testButton.addActionListener(actionListener);
        
        content.add(testButton);

    
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }



    
}
