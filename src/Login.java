import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login extends JPanel{
    public DBManager dbManager;
    private JButton login;
    private PreparedStatement selectStatement;
    private JTextField email;
    private JTextField password;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel headingLogin;
    private BufferedImage loginBufferedImage;
    private BufferedImage flyer;
    private Timer timer;
    private int flyerY;
    private boolean movingUp=true;
    private Login loginThis;
    private MenuPanel menuPanel;
    private Signup signup;
    private Window windowAncestorMain;
    private JLabel errorMessage;
    Login(Window main,MenuPanel menuPanel){
        loginThis=this;
        windowAncestorMain=main;
        // System.out.println(main);
        // System.out.println(signup);
        this.menuPanel=menuPanel;
        try {
            dbManager=new DBManager();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        flyerY=170;
        setLayout(null);
        timer=new Timer(30,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(movingUp){
                    flyerY-=5;
                    if(flyerY<-250){
                        flyerY=getHeight();
                    }
                }
                repaint();
            }
        });
        timer.start();
            try {
            // Initialize the selectStatement for checking login
            selectStatement = dbManager.conn.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            loginBufferedImage=ImageIO.read(new File("login.jpg"));
            flyer=ImageIO.read(new File("flyer.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        errorMessage = new JLabel("Incorrect email or password");
        errorMessage.setForeground(Color.RED);
        errorMessage.setFont(new Font("Arial", Font.BOLD, 16));
        errorMessage.setBounds(450, 360, 300, 30);
        errorMessage.setVisible(false);
        headingLogin=new JLabel("Login");
        headingLogin.setForeground(Color.decode("#01bfba"));
        headingLogin.setFont(new Font("Arial", Font.BOLD, 20));
        emailLabel=new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#01bfba"));
        passwordLabel=new JLabel("Password:");
        passwordLabel.setForeground(Color.decode("#01bfba"));

        email=new JTextField("");
        email.setBorder(new LineBorder(Color.decode("#25828b")));
        email.setForeground(Color.WHITE);
        password=new JTextField("");
        password.setBorder(new LineBorder(Color.decode("#25828b")));
        password.setForeground(Color.WHITE);
        login=new JButton("Login");
        login.setBorder(new LineBorder(Color.decode("#25828b")));

        emailLabel.setBounds(450,170,100,50);
        passwordLabel.setBounds(450,240,100,50);
        headingLogin.setBounds(450,145,100,50);

        email.setBounds(450,210,200,40);
        email.setOpaque(false);
        password.setBounds(450,280,200,40);
        password.setOpaque(false);
        login.setBounds(450,330,100,30);
         //TO REMOVE BACKGROUND OF BUTTON
        login.setContentAreaFilled(false);
        login.setForeground(Color.WHITE);

        add(headingLogin);
        add(emailLabel);
        add(passwordLabel);
        add(email);
        add(password);
        add(login);
        add(errorMessage);

        login.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setDoubleBuffered(true);
        if(loginBufferedImage!=null){
            g.drawImage(loginBufferedImage, 0, 0, getWidth(),getHeight(),this);
        }
        if(flyer!=null){
            g.drawImage(flyer, 280, flyerY, 100, 200, this);
        }
    }
    private void checkLogin(){
        try{
            selectStatement.setString(1,email.getText());
            selectStatement.setString(2,password.getText());
            ResultSet resultset=selectStatement.executeQuery();

            if((resultset).next()){                
                System.out.println("sucessful");
                int id=resultset.getInt("ID");
                windowAncestorMain.remove(loginThis);
                menuPanel.alterID(id);
                windowAncestorMain.add(menuPanel);
                windowAncestorMain.revalidate();
                windowAncestorMain.repaint();
            }
            else{
                System.out.println("failed");
                errorMessage.setVisible(true);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
