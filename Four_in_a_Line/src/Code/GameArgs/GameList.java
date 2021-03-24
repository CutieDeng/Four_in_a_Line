package Code.GameArgs;

import Code.Client.FourLineClient;
import Code.Client.GamePanel;
import Code.Log.InfoLevel;
import Code.Log.Logging;

import java.util.ArrayList;
import java.util.Arrays;

public class GameList {
    private static final int COLUMN_DEFAULT = 10;
    private static final int ROW_DEFAULT = 10;

    private Logging logging;
    private int column = COLUMN_DEFAULT;
    private int row = ROW_DEFAULT;
    private String[] clients;
    private ArrayList<Byte> steps;
    private byte[][] table;
    private byte[] tail;
    private byte nowPlayer = 0;

    private HandlePiece handlePiece = null;

    public void setHandlePiece(HandlePiece handlePiece) {
        this.handlePiece = handlePiece;
    }

    private Byte winner = null;

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    private final static byte[][][] movement = {
            {
                    {0, 1}, {0, -1}
            },
            {
                    {1, 1}, {-1, -1}
            },
            {
                    {1, -1}, {-1, 1}
            },
            {
                    {1, 0}, {-1, 0}
            }
    };

    public GameList(Logging logging) {
        this.logging = logging;
    }

    public void flush(){
        if (winner != null){
            logging.log("You can't flush the game when the winner is determined.", InfoLevel.WARN);
            return ;
        }
        ArrayList<Byte> examineProperArrayList = new ArrayList<>();
        if (table == null){
            table = new byte[row][column];
        }
        else{
            for (byte[] eachColumn : table){
                Arrays.fill(table, (byte)-1);
            }
        }
        if (tail == null){
            tail = new byte[column];
        }
        Arrays.fill(tail, (byte)0);

        if (steps == null){
            steps = new ArrayList<>();
        }
        if (steps.size() > 0 && clients == null) {
            throw new RuntimeException("Flush but no player can be found.");
        }
        for (byte step : steps){
            putByte(step);
            examineProperArrayList.add(step);
            if (winner != null){
                logging.log(String.format(
                        "The Player [%s] wins.", clients[winner]
                ),
                        InfoLevel.TOP);
                break;
            }
        }
        if (examineProperArrayList.size() < steps.size()){
            steps = examineProperArrayList;
        }
    }

    private Byte checkWinner(int row, int column){
        if (table[row][column] == -1){
            logging.log(String.format("Examine a cell [%d][%d], which doesn't have a owner.", row, column));
            return null;
        }
        byte totalNum ;
        for (int moveKind = 0; moveKind < 4; moveKind++){
            totalNum = 0;
            for (int moveDirect = 0; moveDirect < 2; moveDirect++){
                totalNum += inALine(row, column, movement[moveKind][moveDirect][0], movement[moveKind][moveDirect][1]);
            }
            if (totalNum >= 3){
                return table[row][column];
            }
        }
        return null;
    }

    private byte inALine(int row, int column, int rowMove, int columnMove){

        byte now = table[row][column];
        byte num = 0;

        while ( (row + rowMove >= 0 ) && (row + rowMove < this.row) &&
                (column + columnMove >= 0) && (column + columnMove < this.column) &&
                (table[row + rowMove][column + columnMove] == now)){
            num++;
        }

        return num;
    }

    public void getArrayList(ArrayList<Byte> arrayList){
        this.steps = arrayList;
        flush();
    }

    public void putByte(byte nextStep){
        if (tail[nextStep] >= this.row){
            logging.log(String.format("You can't add a piece in a column [%d] whose pieces are full.", nextStep));
            throw new RuntimeException();
        }
        if (nextStep < 0 || nextStep >= this.column){
            logging.log(String.format("You can't add a piece in a column [%d] which isn't existed.", nextStep));
            throw new RuntimeException();
        }
        byte row = tail[nextStep];
        byte column = nextStep;
        table[row][column] = nowPlayer;
        nextPlayer();
        tail[nextStep] ++;
        winner = checkWinner(row, column);
        if (handlePiece != null){
            handlePiece.handlePiece(nextStep);
        }
    }

    private void inform(byte nextStep){
        if (winner != null){
            logging.log("You can't inform the array when the winner is determined.", InfoLevel.INFO);
            return ;
        }
        putByte(nextStep);
        steps.add(nextStep);
    }

    public String whoWins(){
        if (winner == null){
            return null;
        }
        return clients[winner];
    }

    private void nextPlayer(){
        nowPlayer ++;
        if (nowPlayer == clients.length){
            nowPlayer = 0;
        }
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < 4; i++){
            System.out.println("第" + i + "种运动模式");
            for (int j=0; j < 2; j++){
                System.out.println("方向" + j );
                System.out.println(Arrays.toString(movement[i][j]));
            }
        }
    }

}
