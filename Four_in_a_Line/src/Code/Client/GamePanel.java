package Code.Client;

import Code.GameArgs.GameList;
import Code.GameArgs.ClickPosition;
import Code.GameArgs.HandlePiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GamePanel extends JPanel implements HandlePiece {

    private static Font font = new Font("Free Ink", Font.BOLD, 25);

    private ClickPosition click;
    private GameList gameList;

    private byte[] height;

    private GameButton[][] buttons;

    public void setClick(ClickPosition click) {
        this.click = click;
    }

    public GamePanel(GameList gameList) {
        this.gameList = gameList;
        gameList.setHandlePiece(this);
        setLayout(new GridLayout(gameList.getRow(), gameList.getColumn()));
        buttons = new GameButton[gameList.getRow()][gameList.getColumn()];
        height = new byte[gameList.getColumn()];
        Arrays.fill(height, (byte) 0);
        for (int row = 0; row < gameList.getRow(); row++){
            for (int column = 0; column < gameList.getColumn(); column ++){

                GameButton gameButton = new GameButton(gameList.getRow() - row - 1, column);
                buttons[gameList.getRow() - row - 1][column] = gameButton;
                if (row + 1== gameList.getRow()){
                    gameButton.setEnabled(true);
                }
                else{
                    gameButton.setEnabled(false);
                }
                gameButton.setFont(font);

                gameButton.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int r = ((GameButton)e.getSource()).getRow();
                                int c = ((GameButton)e.getSource()).getColumn();
                                click.informClick(r, c);
                            }
                        }
                );

                add(gameButton);
            }
        }
    }

    @Override
    public void handlePiece(byte c) {
        GameButton gameButton = buttons[height[c]][c];
        gameButton.setEnabled(false);
        height[c]++;
        gameButton = buttons[height[c]][c];
        gameButton.setEnabled(true);

    }
}
