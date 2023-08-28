import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Signup extends JPanel{
    private PreparedStatement insertStatement;
    public DBManager dbManager;
    private BufferedImage signupBackground;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private Main main;
    private JLabel passwordLabel;
    private JTextField username;
    private JTextField email;
    private JTextField password;
    private JButton submitButton;
    private Login login;
    private MenuPanel menuPanel;
    Signup(MenuPanel menuPanel) throws SQLException, ClassNotFoundException, RuntimeException{
        this.menuPanel=menuPanel;
        setLayout(null);
        dbManager=new DBManager();
        insertStatement=dbManager.conn.prepareStatement("INSERT INTO users(username,email,password) VALUES (?,?,?)");
        usernameLabel=new JLabel("Username:");
        usernameLabel.setForeground(Color.decode("#01bfba"));
        emailLabel=new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#01bfba"));
        passwordLabel=new JLabel("Password:");
        passwordLabel.setForeground(Color.decode("#01bfba"));

        username=new JTextField("");
        username.setForeground(Color.WHITE);
        username.setBorder(new LineBorder(Color.decode("#25828b")));
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                username.setBorder(new LineBorder(Color.decode("#0b3f83"))); 
            }

            @Override
            public void focusLost(FocusEvent e) {
                username.setBorder(new LineBorder(Color.decode("#25828b"))); // Reset border color when focus is lost
            }
        });

        email=new JTextField("");
        email.setBorder(new LineBorder(Color.decode("#25828b")));
        email.setForeground(Color.WHITE);
        password=new JTextField("");
        password.setBorder(new LineBorder(Color.decode("#25828b")));
        password.setForeground(Color.WHITE);
        submitButton=new JButton("Sign UP");
        submitButton.setBorder(new LineBorder(Color.decode("#25828b")));

        submitButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                insertDB();
            }
        });
        submitButton.setFocusable(false);

        usernameLabel.setBounds(450,100,100,50);
        emailLabel.setBounds(450,170,100,50);
        passwordLabel.setBounds(450,240,100,50);

        username.setBounds(450,140,200,40);
        username.setOpaque(false);
        email.setBounds(450,210,200,40);
        email.setOpaque(false);
        password.setBounds(450,280,200,40);
        password.setOpaque(false);
        submitButton.setBounds(450,330,100,30);
         //TO REMOVE BACKGROUND OF BUTTON
        submitButton.setContentAreaFilled(false);
        submitButton.setForeground(Color.WHITE);

        add(emailLabel);
        add(passwordLabel);
        add(usernameLabel);
        add(username);
        add(email);
        add(password);
        add(submitButton);
        try{
            signupBackground=ImageIO.read(new File("signup.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setDoubleBuffered(true);

        //FOR BACKGROUND 
        if (signupBackground != null) {
            g.drawImage(signupBackground, 0, 0, getWidth(), getHeight(), this);
        }
    }
    private void insertDB(){
        try{
            insertStatement.setString(1,username.getText());
            insertStatement.setString(2,email.getText());
            insertStatement.setString(3,password.getText());
            insertStatement.executeUpdate();

            gotoLogin();            
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void gotoLogin(){
        Window ancestorWindow=SwingUtilities.getWindowAncestor(this);
        login=new Login(this,ancestorWindow,menuPanel);
        ancestorWindow.remove(this);
        ancestorWindow.add(login);
        ancestorWindow.revalidate();
        ancestorWindow.repaint();
    }
}
