package GameMain.GameComponent;

import javax.swing.*;

/**
 * GButton is a kind of special JButton. You should add
 * two special arguments including <em>row</em> and <em>colummn</em>.
 * <p>
 * It would be used in the special game situation, like you need to know
 * the concrete position of the game button.
 * @author Cutie
 * @version 2021-03-26
 */
public class GButton extends JButton {

    private final int column;
    private final int row;

    /**
     * Creates a button with no set text or icon.
     * @param column The column of the button position.
     * @param row The row of the button position.
     */
    public GButton(int row, int column) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
