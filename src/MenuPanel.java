import java.awt.GridLayout;

import javax.swing.*;

public class MenuPanel extends JPanel{
    private JButton startButton;
    private JButton exitButton;
    MenuPanel(){
        setLayout(new GridLayout(2, 1));
        startButton=new JButton("Start Game");
        exitButton=new JButton("Exit");

        add(startButton);
        add(exitButton);
    }
}