package Code.Client;

import Code.GameArgs.ClickPosition;
import Code.GameArgs.GameList;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private int width = 800;
    private int height = 600;
    private GamePanel gamePanel;

    public GameFrame(GameList gameList){
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("image\\logo.jpg").getImage());

        setLayout(new BorderLayout());

        gamePanel = new GamePanel(gameList);
        add(gamePanel);

        setVisible(true);
    }

    public void setClickPosition(ClickPosition clickPosition) {
        gamePanel.setClick(clickPosition);
    }

}
