import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private JPasswordField password;
    private JButton submitButton;
    private Login login;
    private MenuPanel menuPanel;
    private JLabel gotoMenuLabel;
    private JLabel errorMessage;
    Signup(MenuPanel menuPanel) throws SQLException, ClassNotFoundException, RuntimeException{
        this.menuPanel=menuPanel;
        UIManager.getLookAndFeelDefaults().put( "TextField.caretForeground", Color.WHITE );
        setLayout(null);
        dbManager=new DBManager();
        insertStatement=dbManager.conn.prepareStatement("INSERT INTO users(username,email,password) VALUES (?,?,?)");
        usernameLabel=new JLabel("Username:");
        usernameLabel.setForeground(Color.decode("#01bfba"));
        emailLabel=new JLabel("Email:");
        emailLabel.setForeground(Color.decode("#01bfba"));
        passwordLabel=new JLabel("Password:");
        passwordLabel.setForeground(Color.decode("#01bfba"));

        gotoMenuLabel=new JLabel("Goto Menu");
        gotoMenuLabel.setForeground(Color.decode("#01bfba"));
        gotoMenuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        gotoMenuLabel.addMouseListener(new MouseAdapter() {
           @Override
            public void mouseClicked(MouseEvent e){
                gotoMenu();
            }
        });

        errorMessage=new JLabel("Email already taken");
        errorMessage.setForeground(Color.RED);
        errorMessage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        username=new JTextField("");
        username.setForeground(Color.WHITE);
        username.setBorder(new LineBorder(Color.decode("#25828b")));
        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                username.setBorder(new LineBorder(Color.decode("#0b3f83"))); 
                errorMessage.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                username.setBorder(new LineBorder(Color.decode("#25828b"))); // Reset border color when focus is lost
            }
        });
        
        email=new JTextField("");
        email.setBorder(new LineBorder(Color.decode("#25828b")));
        email.setForeground(Color.WHITE);
        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                errorMessage.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        password=new JPasswordField("");
        password.setBorder(new LineBorder(Color.decode("#25828b")));
        password.setForeground(Color.WHITE);
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                errorMessage.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        submitButton=new JButton("Sign UP");
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        gotoMenuLabel.setBounds(450,330,100,100);
        errorMessage.setBounds(450,350,200,100);
        errorMessage.setFont(new Font("Arial", Font.BOLD, 16));
        errorMessage.setVisible(false);

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
        add(gotoMenuLabel);
        add(errorMessage);
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
            String emailInput=email.getText();  
            if(isEmailAlreadyTaken(emailInput)){
                errorMessage.setText("Email is already taken");
                errorMessage.setVisible(true);
            }
            else{
                if(username.getText()==null||username.getText().isEmpty()){
                    errorMessage.setText("Username can't be empty");
                    errorMessage.setVisible(true);
                }
                else if(email.getText()==null||email.getText().isEmpty()){
                    errorMessage.setText("Email can't be empty");
                    errorMessage.setVisible(true);
                }
                else if(password.getPassword()==null || password.getPassword().length==0){
                    errorMessage.setText("Password can't be empty");
                    errorMessage.setVisible(true);
                }
                else{
                    insertStatement.setString(1,username.getText());
                    insertStatement.setString(2,email.getText());
                    char[] passwordChars=password.getPassword();
                    String password=new String(passwordChars); 
                    insertStatement.setString(3,password);
                    insertStatement.executeUpdate();
        
                    gotoLogin();            
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    private boolean isEmailAlreadyTaken(String email){
        String query="SELECT * FROM users WHERE email=?";
        PreparedStatement checkStatement;
        try {
            checkStatement = dbManager.conn.prepareStatement(query);
            checkStatement.setString(1, email);
            ResultSet isExists=checkStatement.executeQuery();
            if(isExists.next()){
                return true;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     
        return false;
    }
    private void gotoLogin(){
        Window ancestorWindow=SwingUtilities.getWindowAncestor(this);
        login=new Login(ancestorWindow,menuPanel);
        ancestorWindow.remove(this);
        ancestorWindow.add(login);
        ancestorWindow.revalidate();
        ancestorWindow.repaint();
    }
    private void gotoMenu(){
        Window ancestorMenu=SwingUtilities.getWindowAncestor(this);
        ancestorMenu.remove(this);
        ancestorMenu.add(menuPanel);
        ancestorMenu.revalidate();
        ancestorMenu.repaint();
    }
}
