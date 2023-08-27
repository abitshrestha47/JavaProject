import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
        usernameLabel.setForeground(Color.decode("#01bfba"));
        emailLabel=new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#01bfba"));
        passwordLabel=new JLabel("Password:");
        passwordLabel.setForeground(Color.decode("#01bfba"));

        username=new JTextField("");
        username.setForeground(Color.WHITE);
        username.setBorder(new LineBorder(Color.decode("#25828b")));
        email=new JTextField("");
        email.setBorder(new LineBorder(Color.decode("#25828b")));
        email.setForeground(Color.WHITE);
        password=new JTextField("");
        password.setBorder(new LineBorder(Color.decode("#25828b")));
        password.setForeground(Color.WHITE);
        submitButton=new JButton("Submit");
        submitButton.setBorder(new LineBorder(Color.decode("#25828b")));


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
}
