import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GamePanel extends JPanel {
    private PreparedStatement updateStatement;
    // game width and height
    private int width = 1000;
    private int height = 600;
    // object starting position
    private int ufoX = 100;
    private int ufoY = 250;
    // object size and speed
    private int ufoSize = 50;
    // object falling speed from top to bottom
    private int ufoSpeed = 0;
    private int pipeX = width + 50;
    // gap between the upPipe and the downPipe
    private int pipeGap = 200;
    private int pipeWidth = 120;
    private int pipe1Height = 230;
    private int pipe2Height = height - pipe1Height - pipeGap;
    private int redObstacleY = (int) (Math.random() * (height - 100)) + 50;
    private int redObstacleWidth = 30;
    private int redObstacleHeight = 30;
    private int redObstacleSpeed = 5;
    private boolean redObstacleFired = false;
    private int redObstacleSize = 70;
    private int redObstacleX = width + 50;
    private int redObstacleY2 = (int) (Math.random() * (height - 100)) + 50;
    private int redObstacleWidth2 = 30;
    private int redObstacleHeight2 = 30;
    private int redObstacleSpeed2 = 5;
    private boolean redObstacleFired2 = false;
    private int redObstacleSize2 = 70;
    private int redObstacleX2 = width + 50;
    public boolean isRunning = true;
    private Clip soundClip;
    private Image planeImage;
    private Image treeImage;
    private Image ufoImage;
    private Image crashed;
    private Image villian;
    private Image villian2;
    private int score = 0;
    private JButton restartButton;
    private JButton MenuButton;
    private JLabel scoreValue;
    private int highScore = 0;
    // for creating random villian
    public int randomNumber;
    public int randomNumber2;
    String[] villianImg = { "villian1.png", "villian2.png", "villian3.png" };
    private String selectedImageName;
    private MenuPanel menu;
    private int id;
    public DBManager dbManager;
    private PreparedStatement selectStatement;
    private JLabel highScoreValue;

    public GamePanel(String imageString, MenuPanel menu, int userID) {
        try {
            dbManager = new DBManager();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.selectedImageName = imageString;
        this.menu = menu;
        id = userID;
        // System.out.println(id);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        // for background image
        if (id != 0) {
            try {
                updateStatement = dbManager.conn.prepareStatement("UPDATE users SET highscore=? WHERE id=?");
                selectStatement = dbManager.conn.prepareStatement("SELECT * FROM users WHERE id = ?");
                selectStatement.setInt(1, id);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    int dbHighscore = resultSet.getInt("highscore");
                    // System.out.println(dbHighscore);
                    highScore=dbHighscore;
                    highScoreValue = new JLabel(String.valueOf(highScore));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedImage originalImage = ImageIO.read(new File("background.jpg"));
            Image scaledImage = originalImage.getScaledInstance(1100, 600, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel backgroundLabel = new JLabel(scaledIcon);
            add(backgroundLabel);
            scoreValue = new JLabel("0");
            scoreValue.setBounds(410, 50, 100, 60);
            scoreValue.setFont(new Font("Arial", Font.BOLD, 70));
            scoreValue.setForeground(Color.WHITE);

            // MENUBUTTON
            MenuButton = new JButton("Menu");
            MenuButton.setBackground(Color.RED);
            MenuButton.setForeground(Color.WHITE);
            MenuButton.setFont(new Font("Arial", Font.BOLD, 12));
            MenuButton.setVisible(false); // hide the button initially
            MenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gotoMenu();
                }
            });

            // RESTART BUTTON
            restartButton = new JButton("Restart");
            restartButton.setBackground(Color.RED);
            restartButton.setForeground(Color.WHITE);
            restartButton.setFont(new Font("Arial", Font.BOLD, 12));
            restartButton.setVisible(false); // hide the button initially
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    restart();
                }
            });

            // SETLAYOUT && BOUNDS
            MenuButton.setBounds(560, 300, 80, 30);
            restartButton.setBounds(470, 300, 80, 30);

            backgroundLabel.add(scoreValue);
            backgroundLabel.add(restartButton);
            backgroundLabel.add(MenuButton);
            backgroundLabel.setLayout(null);
            // add(restartButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
        playSound(true);
        setSize(width, height);
        setVisible(true);
        // System.out.println(selectedImageName);
        if (selectedImageName != null) {
            planeImage = new ImageIcon(selectedImageName).getImage();
        } else {
            planeImage = new ImageIcon("heroUfo.png").getImage();
        }
        // System.out.println(planeImage);
        treeImage = new ImageIcon("downsideRock.png").getImage();
        ufoImage = new ImageIcon("upsideRock.png").getImage();
        crashed = new ImageIcon("crashed.gif").getImage();
        // villian = new ImageIcon("villian1.png").getImage();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    // System.out.println("hello");
                    ufoSpeed = -10;
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ufoSpeed = -10;
            }
        });
        // JLabel backgroundLabel = new JLabel();
        // Read the high score from a file (if it exists)

        // JLabel highScoreLabel = new JLabel("High Score: " + highScore);
        // highScoreLabel.setBounds(100, 100, 200, 60);
        // highScoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        // highScoreLabel.setForeground(Color.WHITE);
        // backgroundLabel.add(highScoreLabel);
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    // for sound
    private void playSound(Boolean value) {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File("ufoSound.wav").getAbsoluteFile());
            if (soundClip != null && soundClip.isRunning()) {
                soundClip.stop();
            }
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            if (value) {
                soundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception ex) {
            System.out.println("Error playing sound: " + ex.getMessage());
        }
    }

    // for checking and updating the game status
    private void update() {
        if (!isRunning) {
            restartButton.setVisible(true); // show the button when the game is over
            MenuButton.setVisible(true);
            // Update the high score
            if (score > highScore) {
                highScore = score;
                if (id != 0) {
                    try {
                        updateStatement.setInt(1, highScore);
                        updateStatement.setInt(2, id);
                        updateStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        File file = new File("highscore.txt");
                        FileWriter writer = new FileWriter(file);
                        writer.write(Integer.toString(highScore));
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return;
        }
        // make object fall when a space is not clicked
        ufoY += ufoSpeed;
        ufoSpeed += 1;

        // to create new obstacle one the old obstacle is out of screen
        pipeX -= 5;
        if (pipeX + pipeWidth < 0) {
            pipeX = width;
            pipe1Height = (int) (Math.random() * (height - pipeGap - 100)) + 50;
            pipe2Height = height - pipe1Height - pipeGap;
        }
        // generate the villians
        redObstacleX -= 7;
        if (redObstacleX + redObstacleSize < 0) {
            redObstacleX = width;
            redObstacleY = (int) (Math.random() * (height - redObstacleSize - 50)) + 25;
            double doubleRandomNumber = Math.random() * 3;
            randomNumber = (int) doubleRandomNumber;
        }
        if (score > 3) {
            redObstacleX2 -= 7;
            if (redObstacleX2 + redObstacleSize2 < 0) {
                redObstacleX2 = width;
                redObstacleY2 = (int) (Math.random() * (height - redObstacleSize2 - 30)) + 25;
                double doubleRandomNumber = Math.random() * 3;
                randomNumber2 = (int) doubleRandomNumber;
            }
        }
        // checks if the bird collided with the pipe or not
        if (ufoY < 0 || ufoY + ufoSize > height) {
            playSound(false);
            HelicopterSound.playCrashSound();
            isRunning = false;
        }
        if (ufoX + ufoSize > pipeX && ufoX < pipeX + pipeWidth) {
            if (ufoY < pipe1Height || ufoY + ufoSize > pipe1Height + pipeGap) {
                playSound(false);
                HelicopterSound.playCrashSound();
                isRunning = false;
            }
        }
        if (ufoX + ufoSize > redObstacleX && ufoX < redObstacleX + redObstacleSize) {
            if (ufoY < redObstacleY + redObstacleSize && ufoY + ufoSize > redObstacleY) {
                playSound(false);
                HelicopterSound.playCrashSound();
                isRunning = false;
            }
        }
        if (ufoX + ufoSize > redObstacleX2 && ufoX < redObstacleX2 + redObstacleSize2) {
            if (ufoY < redObstacleY2 + redObstacleSize2 && ufoY + ufoSize > redObstacleY2) {
                playSound(false);
                HelicopterSound.playCrashSound();
                isRunning = false;
            }
        }
        // increment the score every time the object passes through a pipe
        if (pipeX + pipeWidth == ufoX) {
            score++;
            scoreValue.setText(String.valueOf(score));
        }

        if (score > highScore) {
            highScore = score;
            // Write the new high score to the file
            if (id != 0) {
                try {
                    updateStatement.setInt(1, highScore);
                    updateStatement.setInt(2, id);
                    updateStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    File file = new File("highscore.txt");
                    FileWriter writer = new FileWriter(file);
                    writer.write(Integer.toString(highScore));
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        scoreValue.setText(String.valueOf(score));
        scoreValue.repaint();
        JLabel backgroundLabel = new JLabel();
        // Update the high score label
        if (id != 0) {
            try {
                selectStatement.setInt(1, id);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    int dbHighscore = resultSet.getInt("highscore");
                    highScore=dbHighscore;
                    highScoreValue = new JLabel(String.valueOf(highScore));
                    // System.out.println("if");
                } else {
                    highScoreValue.setText("NO SCORE FOUND");
                    // System.out.println("if");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // System.out.println("else");
            try {
                File file = new File("highscore.txt");
                Scanner scanner = new Scanner(file);
                highScore = scanner.nextInt();
                scanner.close();
            } catch (FileNotFoundException e) {
                // If the file doesn't exist, create a new one
                try {
                    File file = new File("highscore.txt");
                    FileWriter writer = new FileWriter(file);
                    writer.write("0");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            highScoreValue = new JLabel(String.valueOf(highScore));
        }
        highScoreValue.setBounds(700, 100, 100, 60);
        highScoreValue.setFont(new Font("Arial", Font.BOLD, 70));
        highScoreValue.setForeground(Color.WHITE);
        backgroundLabel.add(highScoreValue);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        villian = new ImageIcon(villianImg[randomNumber]).getImage();
        villian2 = new ImageIcon(villianImg[randomNumber2]).getImage();
        g.drawImage(planeImage, ufoX, ufoY, 60, 50, null);
        g.drawImage(ufoImage, pipeX, 0, pipeWidth, pipe1Height, null);
        g.drawImage(treeImage, pipeX, height - pipe2Height, pipeWidth, pipe2Height, null);
        // g.setColor(Color.RED);
        g.drawImage(villian, redObstacleX, redObstacleY, redObstacleSize, redObstacleSize, null);
        g.drawImage(villian2, redObstacleX2, redObstacleY2, redObstacleSize2, redObstacleSize2, null);
        if (!isRunning) {
            g.drawImage(crashed, ufoX, ufoY, 50, 40, null);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", width / 2 - 150, height / 2);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", width / 2 - 150, height / 2);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Your score: " + score, width / 2 - 80, height / 2 + 70);
            g.drawString("High score: " + highScore, width / 2 - 80, height / 2 + 100);
        }
    }

    // goto menu
    private void gotoMenu() {
        Window ancestorWindow=SwingUtilities.getWindowAncestor(this);
        ancestorWindow.remove(this);
        ancestorWindow.add(menu);
        ancestorWindow.revalidate();
        ancestorWindow.repaint();
    }

    // to restart a game
    private void restart() {
        ufoX = 100;
        ufoY = 250;
        ufoSpeed = 0;
        pipeX = width + 50;
        pipe1Height = 200;
        pipe2Height = height - pipe1Height - pipeGap;
        redObstacleY = (int) (Math.random() * (height - 100)) + 50;
        redObstacleWidth = 30;
        redObstacleHeight = 30;
        redObstacleSpeed = 5;
        redObstacleFired = false;
        redObstacleSize = 70;
        redObstacleX = width + 50;
        redObstacleY2 = (int) (Math.random() * (height - 100)) + 50;
        redObstacleWidth2 = 30;
        redObstacleHeight2 = 30;
        redObstacleSpeed2 = 5;
        redObstacleFired2 = false;
        redObstacleSize2 = 70;
        redObstacleX2 = width + 50;
        isRunning = true;
        score = 0;
        restartButton.setVisible(false);
        MenuButton.setVisible(false);
        playSound(true);
        scoreValue.setText(String.valueOf(score));
    }

    public void setSelectedImageName(String imageName) {
        selectedImageName = imageName;
        // System.out.println(selectedImageName);
    }
}
