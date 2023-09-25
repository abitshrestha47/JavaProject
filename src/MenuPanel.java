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

public class MenuPanel extends JPanel {
    private String selectedImage;
    private JButton startButton;
    private JButton exitButton;
    private JButton signupButton;
    private JButton logoutButton;
    private JButton loginButton;
    private BufferedImage backgroundImage;
    private BufferedImage rocketImage;
    private BufferedImage ufoImage;
    private BufferedImage rocketShipImage;
    private BufferedImage upsideRockImage;
    private BufferedImage ufoCrashImage;
    private Timer timer;
    private Timer timerUFO;
    private Timer timerRocketShip;
    private  Image crashed;
    int rocketWidth = 250;
    int rocketHeight = 250;
    int rocketX;
    int rocketY;
    int ufoX;
    int ufoY;
    int rocketShipX;
    int rocketShipY;
    private boolean rocketshipBool=false;
    private boolean ufoShipBool=false;
    private boolean rocketBool=false;
    private JLabel chooseCharacter;
    private int id;
    MenuPanel() {
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                     if (rocketImage != null && isWithinRocketImageBounds(e.getX(), e.getY())) {
                        selectedImage = "rocket.png";
                        ufoShipBool=false;
                        rocketshipBool=false;
                        rocketBool=true;
                          if (timerUFO != null && timerUFO.isRunning()) {
                                timerUFO.stop();
                           }
                           if(timerRocketShip!=null && timerRocketShip.isRunning()) {
                            timerRocketShip.stop();
                           }
                        callThis();
                    }
                    else if (ufoImage != null && isWithinUFOImageBounds(e.getX(), e.getY())) {
                        selectedImage = "ufo.png";
                        rocketBool=false;
                        rocketshipBool=false;
                        ufoShipBool=true;
                        if(timer!=null && timer.isRunning()){
                            timer.stop();
                        }
                        if(timerRocketShip!=null && timerRocketShip.isRunning()){
                            timerRocketShip.stop();
                        }
                        callUfo();
                    }
                    else if (rocketShipImage != null && isWithinRocketShipImageBounds(e.getX(), e.getY())) {
                        selectedImage = "rocket-ship.png";
                        ufoShipBool=false;
                        rocketBool=false;
                        rocketshipBool=true;
                        if (timerUFO != null && timerUFO.isRunning()) {
                                timerUFO.stop();
                           }
                        if(timer!=null && timer.isRunning()){
                            timer.stop();
                           }
                        callRocketShip();
                    }
            }
        });
        setLayout(null);
        //START BUTTON
        startButton = new JButton("Start Game");
        startButton.setBackground(new Color(38, 51, 93)); 
        startButton.setForeground(Color.WHITE); 
        startButton.setFont(new Font("Arial", Font.BOLD, 18)); 
        // startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        startButton.setFocusable(false);

        //EXIT BUTTON
        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(38, 51, 93)); 
        exitButton.setForeground(Color.WHITE); 
        exitButton.setFont(new Font("Arial", Font.BOLD, 18)); 
        // exitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        exitButton.setFocusable(false);

        //SIGNUP BUTTON
        signupButton=new JButton("<html><u>Signup</u></html>");
        signupButton.setBackground(Color.BLACK);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //TO REMOVE BORDER OF BUTTON
        signupButton.setBorderPainted(false);
        //TO REMOVE BACKGROUND OF BUTTON
        signupButton.setContentAreaFilled(false);
        signupButton.setFont(new Font("Arial", Font.BOLD, 18)); 
        signupButton.setFocusable(false);
        signupButton.setForeground(Color.WHITE);

         //SIGNUP BUTTON
        logoutButton=new JButton("<html><u>Logout</u></html>");
        logoutButton.setBackground(Color.BLACK);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //TO REMOVE BORDER OF BUTTON
        logoutButton.setBorderPainted(false);
        //TO REMOVE BACKGROUND OF BUTTON
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18)); 
        logoutButton.setFocusable(false);
        logoutButton.setForeground(Color.WHITE);

          //SIGNUP BUTTON
        loginButton=new JButton("<html><u>Login</u></html>");
        loginButton.setBackground(Color.BLACK);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //TO REMOVE BORDER OF BUTTON
        loginButton.setBorderPainted(false);
        //TO REMOVE BACKGROUND OF BUTTON
        loginButton.setContentAreaFilled(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); 
        loginButton.setFocusable(false);
        loginButton.setForeground(Color.WHITE);
        if(id==0){
            logoutButton.setVisible(false);
        }
        //CHOOSE CHARCTER LABEL
        chooseCharacter=new JLabel("Choose Your Character");
        chooseCharacter.setForeground(Color.WHITE);
        Font largerFont = new Font("Arial", Font.BOLD, 24); 
        chooseCharacter.setFont(largerFont);

        //COMPONENTS LAYOUT MANAGE
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.setBounds(420,50,150,50);
        exitButton.setBounds(420, 110, 150, 50);
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chooseCharacter.setBounds(380, 450, 500, 50);
        signupButton.setBounds(370,480,150,50);
        logoutButton.setBounds(430,480,150,50);
        loginButton.setBounds(320,480,500,50);

        logoutButton.addActionListener(new ActionListener() {
            @override
            public void actionPerformed(ActionEvent e){
                id=0;
                logoutButton.setVisible(false);
                signupButton.setVisible(true);
                loginButton.setVisible(true);
            }
        });
        
        //MENUPANEL COMPONENTS ADD
        add(startButton);
        add(exitButton);
        add(chooseCharacter);
        add(signupButton);
        add(logoutButton);
        add(loginButton);

        //BACKGROUND IMAGE AND COMPONENNT IMAGES
        try {
            backgroundImage = ImageIO.read(new File("menu.jpg"));
            rocketImage = ImageIO.read(new File("rocket.png"));
            ufoImage = ImageIO.read(new File("ufo.png"));
            rocketShipImage = ImageIO.read(new File("rocket-ship.png"));
            upsideRockImage=ImageIO.read(new File("upsideRock.png"));
            crashed = new ImageIcon("crashed.gif").getImage();
            ufoCrashImage=ImageIO.read(new File("heroUfo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TO MAKE THE ROCKET CHARACTER FLOAT
    public void callThis(){
    if(rocketBool){
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            timer=new Timer(30,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                updateRocketPosition();
                repaint();
            }
            });
            timer.start();
        }
    }

    //TO MAKE THE UFO CHARACTER FLOAT 
    public void callUfo(){
        if(ufoShipBool){
            if (timerUFO != null && timerUFO.isRunning()) {
                timerUFO.stop();
            }
            timerUFO=new Timer(30,new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    updateUFOPosition();
                    repaint();
                }
            });
            timerUFO.start();
        }
    }
    
    //TO MAKE ROCKETSHIP CHARACTER FLOAT
    public void callRocketShip(){
        if(rocketshipBool){
                 if (timerRocketShip != null && timerRocketShip.isRunning()) {
                 timerRocketShip.stop();
            }
            timerRocketShip=new Timer(30,new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    updateRocketShipPosition();
                    repaint();
                }
            });
            timerRocketShip.start();
        }
    }

    //FOR FLOATING OF ROCKET FUNCTION
    public void updateRocketPosition() {
        if (rocketY >= getHeight() / 3 + 50) {
            rocketshipBool = true;
        } else if (rocketY <= getHeight() / 3) {
            rocketshipBool = false;
        }
    
        if (rocketshipBool) {
            rocketY--;
        } else {
            rocketY++;
        }
    }

    //FOR FLOATING OF UFO FUNCTION
    private void updateUFOPosition() {
        if (ufoY <= getHeight() / 2 -50) {
            ufoShipBool =false; 
        } 
        // else if (ufoY >= getHeight() / 2 - 150) {
        //     ufoShipBool =true; 
        // }

        if (ufoShipBool) {
            ufoY--;
        } else {
            ufoY--;
            // System.out.println(ufoY);
        }
    }

    //FOR FLOATING OF ROCKETSHIP FUNCTION
    private void updateRocketShipPosition() {
        if (rocketShipY <= (getHeight() / 3 + 120)) {
            rocketshipBool = true; 
        } else if (rocketShipY >= (getHeight()/3+70)) {
            rocketshipBool = false; 
        }

        if (rocketshipBool) {
           rocketShipY++;
        } 
        else {
            rocketShipY--;
      }
    }

    //PAINT TO PAINT IMAGES AND OTHER CONTENTS
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setDoubleBuffered(true);

        //FOR BACKGROUND 
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if(ufoCrashImage!=null){
            g.drawImage(ufoCrashImage, 40, getHeight()-50, 80, 60,this);
        }

        //crashed

        if(crashed!=null){
            g.drawImage(crashed, 55, getHeight()-50, 50, 40,this);
        }
        //UPSIDE ROCK   
        // if(upsideRockImage!=null){
        //     g.drawImage(upsideRockImage,730,0,150,290,this);
        // }

        //FOR ROCKET
        if (rocketImage != null) {
            if(!rocketBool){
                rocketX = (getWidth() / 3 + 40);
                rocketY = (getHeight() / 3);
            }
            g.drawImage(rocketImage, rocketX, rocketY, rocketWidth, rocketHeight, this);
        }


        //FOR UFO
        if (ufoImage != null) {
            if(!ufoShipBool){
                ufoX = (getWidth() / 3 + 320);
                ufoY = (getHeight() / 2 - 50);
            }
            g.drawImage(ufoImage, ufoX, ufoY, 90, 90, this);
        }

        //FOR ROCKET SHIP ANOTHER CHARACTER
        if (rocketShipImage != null) {
            if(!rocketshipBool){
                rocketShipX = (getWidth() / 5);
                rocketShipY = (getHeight() / 3 + 70);
            }
            g.drawImage(rocketShipImage, rocketShipX, rocketShipY, 120, 120, this);
        }
    }

    //TO PASS THE SELECTED CHARACTER TO GAMEPANEL
    public String getSelectedImageName() {
        return selectedImage;
    }

    public JButton getSignupButton(){
        return signupButton;
    }
    public JButton getLoginButton(){
        return loginButton;
    }
    //TO RETURN THE START BUTTON
    public JButton getStartButton() {
        return startButton;
    }

    public int getID(){
        return id;
    }

    //EXIT BUTTON
    public JButton getExitButton() {
        return exitButton;
    }

    //TO CHECK IF THE ROCKET BUTTON IS CLICKED I.E WITHING ITS FRAMES
    private boolean isWithinRocketImageBounds(int x, int y) {
        int rocketX = getWidth() / 3 + 40;
        int rocketY = getHeight() / 3;
        return x >= rocketX && x <= rocketX + rocketWidth && y >= rocketY && y <= rocketY + rocketHeight;
    }
    //TO CHECK IF THE UFO BUTTON IS CLICKED I.E WITHING ITS FRAMES 
    private boolean isWithinUFOImageBounds(int x, int y) {
        int UfoX = getWidth() / 3 + 320;
        int UfoY = getHeight() / 2-50;
        return x >= UfoX && x <= UfoX +90 && y >= UfoY && y <= UfoY + 90;
    }

    //TO CHECK IF THE ROCKETSHIP BUTTON IS CLICKED I.E WITHING ITS FRAMES 
    private boolean isWithinRocketShipImageBounds(int x, int y) {
        int UfoX = getWidth() / 5;
        int UfoY = getHeight() / 3+70;
        return x >= UfoX && x <= UfoX +120 && y >= UfoY && y <= UfoY +120;
    }

    public void alterID(int passedID){
        id=passedID;
        handleChange();
    }
    public void handleChange(){
        if(id!=0){
            signupButton.setVisible(false);
            logoutButton.setVisible(true);
            loginButton.setVisible(false);
        }
    }

}