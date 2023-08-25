import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Main {
    private static MenuPanel menu;
    private static GamePanel gamePanel;
    private static JFrame frame;
    public Main() {
        frame=new JFrame();
        frame.setTitle("UFO Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);

        menu=new MenuPanel();
        menu.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showGamePanel();
            }
        });
        frame.add(menu);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    private void showGamePanel(){
        frame.remove(menu);
        gamePanel=new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
