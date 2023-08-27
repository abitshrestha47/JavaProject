import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Signup extends JPanel{
    private BufferedImage signupBackground;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JTextField username;
    private JTextField email;
    private JTextField password;
    private JButton submitButton;
    Signup(){
        setLayout(null);
        usernameLabel=new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        emailLabel=new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        passwordLabel=new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);

        username=new JTextField("Enter your username...");
        email=new JTextField("Enter your email...");
        password=new JTextField("Enter your password...");
        submitButton=new JButton("Submit");

        usernameLabel.setBounds(500,200,100,50);
        emailLabel.setBounds(500,270,100,50);
        passwordLabel.setBounds(500,340,100,50);

        username.setBounds(500,240,200,40);
        email.setBounds(500,310,200,40);
        password.setBounds(500,380,200,40);
        submitButton.setBounds(500,430,100,30);
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
}
