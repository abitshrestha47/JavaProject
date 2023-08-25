import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private String selectedImage;
    private JButton startButton;
    private JButton exitButton;
    private BufferedImage backgroundImage;
    private BufferedImage rocketImage;
    private BufferedImage ufoImage;
    private BufferedImage rocketShipImage;
    private int ufoY;
    private int ufoDirection = 1;
    int rocketWidth = 250;
    int rocketHeight = 250;

    MenuPanel() {
        startButton = new JButton("Start Game");
        exitButton = new JButton("Exit");

        startButton.setBackground(Color.BLUE);
        startButton.setForeground(Color.WHITE);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(startButton);
        add(exitButton);
        try {
            backgroundImage = ImageIO.read(new File("menu.jpg"));
            rocketImage = ImageIO.read(new File("rocket.png"));
            ufoImage = ImageIO.read(new File("ufo.png"));
            rocketShipImage = ImageIO.read(new File("rocket-ship.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Timer timer = new Timer(30, new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // updateUFOPosition();
        // repaint();
        // }
        // });
        // timer.start();
    }

    private void updateUFOPosition() {
        ufoY += ufoDirection;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (rocketImage != null) {
            int rocketX = (getWidth() / 3 + 40);
            int rocketY = (getHeight() / 3);
            g.drawImage(rocketImage, rocketX, rocketY, rocketWidth, rocketHeight, this);
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (rocketImage != null && isWithinRocketImageBounds(e.getX(), e.getY())) {
                        selectedImage = "rocket.png";
                        repaint();
                    }
                }
            });

        }
        if (ufoImage != null) {
            int ufoX = (getWidth() / 3 + 320);
            ufoY = (getHeight() / 2 - 50);
            g.drawImage(ufoImage, ufoX, ufoY, 90, 90, this);
            addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    if (ufoImage != null && isWithinUFOImageBounds(e.getX(), e.getY())) {
                        selectedImage = "ufo.png";
                        repaint();
                    }
                }
            });
        }
        if (rocketShipImage != null) {
            int rocketShipX = (getWidth() / 5);
            int rocketShipY = (getHeight() / 3 + 70);
            g.drawImage(rocketShipImage, rocketShipX, rocketShipY, 120, 120, this);
            addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    if (rocketShipImage != null && isWithinRocketShipImageBounds(e.getX(), e.getY())) {
                        selectedImage = "rocket-ship.png";
                        repaint();
                    }
                }
            });
        }
    }

    public String getSelectedImageName() {
        return selectedImage;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    private boolean isWithinRocketImageBounds(int x, int y) {
        int rocketX = getWidth() / 3 + 40;
        int rocketY = getHeight() / 3;
        return x >= rocketX && x <= rocketX + rocketWidth && y >= rocketY && y <= rocketY + rocketHeight;
    }
        private boolean isWithinUFOImageBounds(int x, int y) {
        int UfoX = getWidth() / 3 + 320;
        int UfoY = getHeight() / 2-50;
        return x >= UfoX && x <= UfoX +90 && y >= UfoY && y <= UfoY + 90;
    }
           private boolean isWithinRocketShipImageBounds(int x, int y) {
        int UfoX = getWidth() / 5;
        int UfoY = getHeight() / 3+70;
        return x >= UfoX && x <= UfoX +120 && y >= UfoY && y <= UfoY +120;
    }

}