package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginUI {
    private JFrame frame;
    private Container container;
    private JButton logInButton;
    private JButton registerButton;
    private JTextField userNameField;
    private JLabel title;
    private JPasswordField passwordField;

    private String[] returnObjects = new String[3];
    public boolean dataUsable = false;

    private final Color backgroundColor = new Color(245, 245, 220);


    /**
     * initialize LogInGUI frame (constructor)
     *
     * @param frame -> JFrame
     */
    public LoginUI(JFrame frame) {
        frame.setTitle("Login/Registration");
        frame.setSize(600, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(backgroundColor);
        frame.setVisible(true);

        this.frame = frame;
    }

    public String[] getReturnObjects() {
        return returnObjects;
    }

    public void reset() {
        dataUsable = false;
        returnObjects = new String[3];
    }


    public LoginUI() {
        frame = new JFrame();
        frame.setTitle("Login/Registration");
        frame.setSize(600, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(backgroundColor);
        frame.setVisible(true);

        this.show();
    }

    /**
     * Show LogInGUI (Visible)
     */
    public void show() {
        frame.setContentPane(new Container());
        run();
    }

    public void kill() {
        frame.dispose();
    }

    /**
     * Creates the actual frame layout (buttons, users, etc.) with locations and fonts
     */
    public void setFrame() {
        int center = frame.getHeight() / 2;
        title.setBounds(frame.getWidth() / 2 - 200, center - 160 - userNameField.getHeight() / 2, 400, 60);


        userNameField.setSize(150, 30);
        userNameField.setBounds(frame.getWidth() / 2 - userNameField.getWidth() / 2, center - 50 - userNameField.getHeight() / 2,
                userNameField.getWidth(), userNameField.getHeight());
        userNameField.setText("User Name");
        userNameField.setForeground(Color.GRAY);

        passwordField.setSize(150, 30);
        passwordField.setBounds(frame.getWidth() / 2 - passwordField.getWidth() / 2, center - passwordField.getHeight() / 2,
                passwordField.getWidth(), passwordField.getHeight());
        passwordField.setText("Password");
        passwordField.setForeground(Color.GRAY);

        logInButton.setSize(150, 30);
        logInButton.setBounds(frame.getWidth() / 2 - logInButton.getWidth() / 2, center + 50 - logInButton.getHeight() / 2,
                logInButton.getWidth(), logInButton.getHeight());

        registerButton.setSize(150, 30);
        registerButton.setBounds(frame.getWidth() / 2 - registerButton.getWidth() / 2, center + 100 - registerButton.getHeight() / 2,
                registerButton.getWidth(), registerButton.getHeight());
    }

    /**
     * Creates the buttons and scrollbar (BorderLayout) used in setFrame()
     */
    public void createAndAdd(JPanel panel) {
        logInButton = new JButton("Log In");
        userNameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        registerButton = new JButton("Create Account");
        title = new JLabel("Welcome!!", SwingConstants.CENTER);
        Font f = new Font("Default", Font.BOLD, 50);
        title.setFont(f);
        title.setForeground(new Color(206, 184, 136));
        panel.setBackground(backgroundColor);
        panel.setLayout(null);
        panel.add(userNameField);
        panel.add(passwordField);
        panel.add(logInButton);
        panel.add(registerButton);
        panel.add(title);
    }

    /**
     * initialize the userList and button actionListeners, run GUI
     */
    public void run() {
        JPanel panel = new JPanel();
        frame.setSize(600, 540);
        container = frame.getContentPane();
        container.setLayout(new BorderLayout());
        createAndAdd(panel);
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        setFrame();

        if (passwordField.getText().equals("Password")) { // this is deprecated but im still going to use it lmao
            passwordField.setEchoChar((char) 0);
        }
        userNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = (JTextField) e.getComponent();
                String userName = userNameField.getText();
                if (!userName.isEmpty() && !userName.equals("User Name")) {
                    userNameField.setText(userName);
                } else {
                    userNameField.setText("");
                }
                field.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userNameField.getText().isEmpty()) {
                    userNameField.setForeground(Color.GRAY);
                    userNameField.setText("User Name");
                }
            }

        });
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField field = (JTextField) e.getComponent();
                String password = String.valueOf(passwordField.getPassword());
                if (!password.isEmpty() && !password.equals("Password")) {
                    passwordField.setText(password);
                } else {
                    passwordField.setText("");
                }
                field.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setText("Password");
                }
            }
        });
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if (userName.isEmpty() || password.isEmpty() || userName.equals("User Name") || password.equals("Password")) {
                    JOptionPane.showMessageDialog(null, "Please fill in your username and password!", "Error", JOptionPane.WARNING_MESSAGE, null);
                } else {
                    returnObjects[0] = "login";
                    returnObjects[1] = userName;
                    returnObjects[2] = password;
                    dataUsable = true;
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if (userName.isEmpty() || password.isEmpty() || userName.equals("User Name") || password.equals("Password")) {
                    JOptionPane.showMessageDialog(null, "Please fill in your username and password!", "Error", JOptionPane.WARNING_MESSAGE, null);
                } else {
                    returnObjects[0] = "register";
                    returnObjects[1] = userName;
                    returnObjects[2] = password;
                    dataUsable = true;
                }
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setLocation(frame.getWidth() / 2 - panel.getWidth() / 2, frame.getHeight() / 2 - panel.getHeight() / 2);
                setFrame();
            }
        });

        container.add(panel, BorderLayout.CENTER);
        frame.setContentPane(container);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        LoginUI login = new LoginUI(frame);
        login.show();
    }
}