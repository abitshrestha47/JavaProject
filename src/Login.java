import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JPasswordField password;
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
    private JLabel createAccount;
    private JLabel gotoMenu;
    private Timer rocketStopTimer;

    Login(Window main,MenuPanel menuPanel){
        loginThis=this;
        windowAncestorMain=main;
        // System.out.println(main);
        // System.out.println(signup);
        this.menuPanel=menuPanel;
        UIManager.getLookAndFeelDefaults().put( "TextField.caretForeground", Color.WHITE );
        try {
            signup=new Signup(menuPanel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
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
                    if(flyerY>=165 && flyerY<=170){
                            timer.stop();
                            rocketStopTimer=new Timer(1500,new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent e){
                                    rocketStopTimer.stop();
                                    timer.start();
                                }
                            });
                            rocketStopTimer.start();
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

        //to show incorrect email or password
        errorMessage = new JLabel("Incorrect email or password");
        errorMessage.setForeground(Color.RED);
        
        errorMessage.setFont(new Font("Arial", Font.BOLD, 16));
        errorMessage.setBounds(450, 380, 300, 30);
        errorMessage.setVisible(false);
        headingLogin=new JLabel("Login");
        headingLogin.setForeground(Color.decode("#01bfba"));
        headingLogin.setFont(new Font("Arial", Font.BOLD, 20));
        emailLabel=new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#01bfba"));
        passwordLabel=new JLabel("Password:");
        passwordLabel.setForeground(Color.decode("#01bfba"));
        gotoMenu=new JLabel("Goto Menu");
        gotoMenu.setForeground(Color.decode("#01bfba"));
        gotoMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //create acc label
        createAccount=new JLabel("<html><u>Create Account</u></html>");
        createAccount.setForeground(Color.decode("#01bfba"));
        createAccount.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        createAccount.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                windowAncestorMain.remove(loginThis);
                windowAncestorMain.add(signup);
                windowAncestorMain.revalidate();
                windowAncestorMain.repaint();
            }
        });

        email=new JTextField("");
        email.setBorder(new LineBorder(Color.decode("#25828b")));
        email.setForeground(Color.WHITE);
        email.addMouseListener(new MouseAdapter() {
            @override
            public void mouseClicked(MouseEvent e) {
                errorMessage.setVisible(false);
            }
        });
        password=new JPasswordField("");
        password.setBorder(new LineBorder(Color.decode("#25828b")));
        password.setForeground(Color.WHITE);
        password.addMouseListener(new MouseAdapter() {
            @override
            public void mouseClicked(MouseEvent e) {
                errorMessage.setVisible(false);
            }
        });
        login=new JButton("Login");
        login.setBorder(new LineBorder(Color.decode("#25828b")));

        emailLabel.setBounds(450,170,100,50);
        passwordLabel.setBounds(450,240,100,50);
        headingLogin.setBounds(450,145,100,50);
        createAccount.setBounds(450,283,100,100);
        gotoMenu.setBounds(550,283,100,100);

        email.setBounds(450,210,200,40);
        email.setOpaque(false);
        password.setBounds(450,280,200,40);
        password.setOpaque(false);
        login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        login.setBounds(450,348,100,30);
        login.setFocusable(false);
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
        add(createAccount);
        add(gotoMenu);

        gotoMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                windowAncestorMain.remove(loginThis);
                windowAncestorMain.add(menuPanel);
                windowAncestorMain.revalidate();
                windowAncestorMain.repaint();
            }
        });

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
            if(email.getText()==null || email.getText().isEmpty()){
                errorMessage.setText("Email can't be empty");
                errorMessage.setVisible(true);
            }  
            else if(password.getPassword()==null || password.getPassword().length==0){
                errorMessage.setText("Password can't be empty");
                errorMessage.setVisible(true);
            }
            else{
            selectStatement.setString(1,email.getText());
            char[] passwordChars=password.getPassword();
            String password=new String(passwordChars);
            selectStatement.setString(2,password);
            ResultSet resultset=selectStatement.executeQuery();

            if((resultset).next()){                
                // System.out.println("sucessful");
                int id=resultset.getInt("ID");
                windowAncestorMain.remove(loginThis);
                menuPanel.alterID(id);
                windowAncestorMain.add(menuPanel);
                windowAncestorMain.revalidate();
                windowAncestorMain.repaint();
            }
            else{
                errorMessage.setText("Incorrect email or password");
                errorMessage.setVisible(true);
            }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
