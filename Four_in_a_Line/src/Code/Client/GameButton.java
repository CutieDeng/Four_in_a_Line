package Code.Client;

import javax.swing.*;

public class GameButton extends JButton {
    private int row;
    private int column;

    public GameButton(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
