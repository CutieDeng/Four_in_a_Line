package GameMain.Frame;

import GameMain.GameComponent.GButton;
import GameMain.GameComponent.GameKeyListener;
import GameMain.GameComponent.GameLogic;
import GameMain.Log.ClientLog;
import GameMain.Log.GameLog;
import GameMain.Log.LogLevel;
import GameMain.Log.SimpleLogInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * SingleGame is a class to create the game for a local personal computer.
 * <p>
 * It contains the game window, including the frame, the panel and some buttons.
 * <p>
 * You just use the method <em>void initialize()</em> to start a game.
 * Other methods are what you shouldn't use in this class.
 * @author Cutie
 * @version 2021-03-26
 */

public class SingleGame implements GameKeyListener, GameLogic {

    private static final GameLog gameLog = new ClientLog();

    private JFrame gameFrame = null;
    private JPanel bodyPanel = null;
    private GButton[][] buttons;

    private byte[][] position;
    private byte[] nowPlace;

    private List<Byte> gameList;

    private byte winner;

    private byte nowPlayer;

    private static final int DEFAULT_COLUMN = 10;
    private static final int DEFAULT_ROW = 10;

    private static final Font font = new Font("Ink Free", Font.BOLD, 32);

    private int column;
    private int row;

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    {
        row = DEFAULT_ROW;
        column = DEFAULT_COLUMN;
        gameFrame = new JFrame();
        bodyPanel = new JPanel();
        gameFrame.setLayout(new BorderLayout());
        bodyPanel.setLayout(new GridLayout(row, column));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setIconImage(
                new ImageIcon("image\\logo.jpg", "ICON").getImage()
        );
        gameFrame.setTitle("Four In a Line");
        gameFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        gameFrame.add(bodyPanel);
        String i1 = "The frame and panel is initialized well.";
        gameLog.log(SimpleLogInfo.LogInfoBuild(i1, LogLevel.TRACE));
    }

    /**
     * This method would initialize the game arguments, and start the game soon.
     */
    public void initialize(){
        buttons = new GButton[DEFAULT_ROW][DEFAULT_COLUMN];
        nowPlace = new byte[DEFAULT_COLUMN];
        position = new byte[DEFAULT_ROW][DEFAULT_COLUMN];
        nowPlayer = 1;
        for (int r = row - 1; r >= 0; r--){

            for (int c = 0; c < column; c++){

                buttons[r][c] = new GButton(r, c);
                buttons[r][c].setFont(font);
                buttons[r][c].setFocusPainted(false);
                buttons[r][c].addActionListener(
                        e -> {
                            if (winner != 0){
                                return ;
                            }
                            int buttonColumn = ((GButton)e.getSource()).getColumn();
                            int buttonRow = ((GButton)e.getSource()).getRow();
                            gameLog.log(SimpleLogInfo.LogInfoBuild(String.format("You click [%d][%d]", buttonRow, buttonColumn), LogLevel.TRACE));
                            if (nowPlace[buttonColumn] == buttonRow &&
                            attemptPiece((byte) buttonColumn)){
                                gameLog.log(SimpleLogInfo.LogInfoBuild(String.format("You click valid in position [%d]", buttonColumn), LogLevel.DEBUG));
                                sendKeyInput((byte) buttonColumn);
                            }
                        }
                );

                bodyPanel.add(buttons[r][c]);
            }

        }

        reset();
        gameFrame.setVisible(true);
    }

    /**
     * This method is use to send the value of piece position you want to set your piece, to
     * the game server from your client.
     * <p>
     * To protect the local mistakes, we won't directly change our frame unless we listen to
     * the server's response.
     * <p>
     * Therefore, we would have a boolean expression or variable for our clients to think or
     * know whether he is the beginner or the latter.
     * <p>
     * Then he can use this method in the proper opportunity to send the server where he truly
     * want to place his piece.
     *
     * @param keyInput The position you want to place then would send to the server.
     */
    @Override
    public void sendKeyInput(byte keyInput) {
        setPiece(keyInput);
    }

    /**
     * This method would delete the original list and set this argument list
     * as the game list.
     * <p>
     * Please note that, it won't examine whether the list is valid or invalid.
     * If use this method without thinking, maybe it would cause many exceptions.
     *
     * @param list The list you want to set as your game list.
     */
    @Override
    public void reset(List<Byte> list) {
        winner = 0;
        gameList = list;
    }

    /**
     * This method would delete the original list and set a empty game list.
     * <p>
     * By the way, do not forget when you change your game list recording the steps
     * of your pieces, together change your game window.
     */
    @Override
    public void reset() {
        winner = 0;
        for (GButton[] button : buttons) {
            IntStream.range(0, buttons[0].length).forEach(j -> button[j].setText(""));
        }
        if (gameList == null){
            gameList = new ArrayList<>();
        }
        else{
            gameList.clear();
        }
        gameLog.log(SimpleLogInfo.LogInfoBuild("You reset the game.", LogLevel.DEBUG));
    }

    /**
     * This method would test whether this place can be set your piece or not.
     * But it won't be invoked when you originally can't be displayed and just
     * wait for the opponent.
     * <p>
     * There are two ways for ensuring this invalid situation.
     * <p>
     * One is just to ban and ignore your clicking when you just click the buttons.
     * <p>
     * The second way is to abject and ignore the clicking information in the server.
     *
     * @param t The place you attempt to place your piece.
     * @return True if this action is permitted.
     * False otherwise.
     */
    @Override
    public boolean attemptPiece(byte t) {
        return true;
    }

    /**
     * This method would directly place your piece in the game list.
     * <p>
     * I must announce that, don't for the convenience and just use this method in
     * the clicking mouse action listener. It may create quite a lot of problems.
     * <p>
     * You can just only use this method in the client when it's listening to the server.
     * I would try my best to make our server safe therefore we can completely believe our server.
     * But we have no need to believe the client. Because it has some chance for the bad persons to
     * destroy our logic system.
     * <p>
     * Therefore, just make sure the safety of our server but no need to care about the running
     * environment of our clients.
     *
     * @param t The place of piece we would add in our game list.
     */
    @Override
    public void setPiece(byte t) {
        gameList.add(t);
        buttons[nowPlace[t]][t].setText(
                (nowPlayer == 1)? "X" : "O"
        );
        position[nowPlace[t]][t] = nowPlayer;
        nowPlayer = (byte) - nowPlayer;
        winner = checkWinner(nowPlace[t], t);
        nowPlace[t] ++;
    }

    /**
     * This method would set the winner and stopped the game.
     * After this method, we can't do any other methods unless we invoke
     * <em>void reset()</em>.
     * <p>
     * By the way, use <em>void reset(List list)</em> is also okay, because
     * I would overrides this method, including make the winner null.
     *
     * @param beginner False means the latter player wins, and True means the
     *                 beginner wins.
     */
    @Override
    public void setWinner(boolean beginner) {
        if (beginner){
            winner = 1;
        }
        else{
            winner = -1;
        }
    }

    private byte checkWinner(int row, int column){
        int nowLength;
        byte ans = 0;
        nowLength = getLength(row, column, 1, 0) + getLength(row, column, -1, 0);
        if (nowLength >= 3){
            ans = position[row][column];
            changeRed(row, column, 1, 0);
            changeRed(row, column ,-1, 0);
        }
        nowLength = getLength(row, column, 1, 1) + getLength(row, column, -1, -1);
        if (nowLength >= 3){
            ans = position[row][column];
            changeRed(row, column, 1, 1);
            changeRed(row, column, -1, -1);
        }
        nowLength = getLength(row, column, 0, 1) + getLength(row, column, 0,-1);
        if (nowLength >= 3){
            ans = position[row][column];
            changeRed(row, column, 0, 1);
            changeRed(row, column, 0, -1);
        }
        nowLength = getLength(row, column, 1, -1) + getLength(row, column, -1, 1);
        if (nowLength >= 3){
            ans = position[row][column];
            changeRed(row, column, 1, -1);
            changeRed(row, column, -1, 1);
        }
        return ans;
    }

    private byte getLength(int row, int column, int rowMove, int columnMove){

        int nowRow = row + rowMove;
        int nowColumn = column + columnMove;

        byte p = position[row][column];
        byte ans = 0;

        while ((nowRow >= 0 && nowRow < this.row) &&
                (nowColumn >= 0 && nowColumn < this.column) &&
                (position[nowRow][nowColumn] == p)){
            ans ++;
            nowRow += rowMove;
            nowColumn += columnMove;
        }
        return ans;
    }

    private void changeRed(int row, int column, int rowMove, int columnMove){
        int nowRow = row + rowMove;
        int nowColumn = column + columnMove;

        buttons[row][column].setBackground(Color.RED);

        byte p = position[row][column];

        while ((nowRow >= 0 && nowRow < this.row) &&
                (nowColumn >= 0 && nowColumn < this.column) &&
                (position[nowRow][nowColumn] == p)){
            buttons[nowRow][nowColumn].setBackground(Color.RED);
            nowRow += rowMove;
            nowColumn += columnMove;
        }
    }
}