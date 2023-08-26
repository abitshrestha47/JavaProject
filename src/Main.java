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

        //TO SHOW MENU PANEL AT FIRST
        menu=new MenuPanel();
        menu.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showGamePanel();
            }
        });
        menu.getExitButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        frame.add(menu);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
    //TO SHOW GAMEPANEL AFTER START
    private void showGamePanel(){
        frame.remove(menu);
        String selectedImageName = menu.getSelectedImageName(); // Get the selected image's name
        gamePanel=new GamePanel(selectedImageName,menu);
        gamePanel.setSelectedImageName(selectedImageName);
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
