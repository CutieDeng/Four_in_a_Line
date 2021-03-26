package GameMain.GameComponent;

import java.util.List;

/**
 * GameLogic is an interface for the game visual frame. Please note that,
 * it should be directly implemented by the class game displaying window or
 * game main frame.
 * <p>
 * There are totally some methods you need to realize.
 * <p>
 * The first method <em>void reset(List list)</em> is use to replace the original
 * list of game.
 * Of course, it has quite a lot of usages like, directly loading a new save file, or
 * just begin a new game war situation.
 * <p>
 * The second method <em>void reset()</em> is the same method as before, in addition,
 * it's set just for start a new game.
 * <p>
 * The third method <em>boolean attemptPiece(byte t)</em> is use to examine whether this
 * type of step is allowed or not.
 * It would return <Strong>True</Strong> when this step is valid, but it won't truly add it
 * in the game pieces list.
 * If you want to add it in our game, just use the fourth method <em>void setPiece(byte t)</em>
 * and it won't check its validity but just add it directly.
 * <p>
 * We didn't have a valid overall examine method for our game step list.
 * Therefore, I'm worried its safety but just brainlessly believe our server's safety.
 * I wish other authors won't make it a mess.
 * @version 2021-03-26
 * @author Cutie
 */

public interface GameLogic {

    /**
     * This method would delete the original list and set this argument list
     * as the game list.
     * <p>
     * Please note that, it won't examine whether the list is valid or invalid.
     * If use this method without thinking, maybe it would cause many exceptions.
     * @param list The list you want to set as your game list.
     */
    void reset(List<Byte> list);

    /**
     * This method would delete the original list and set a empty game list.
     * <p>
     * By the way, do not forget when you change your game list recording the steps
     * of your pieces, together change your game window.
     */
    void reset();

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
     * @param t The place you attempt to place your piece.
     * @return True if this action is permitted.
     * False otherwise.
     */
    boolean attemptPiece(byte t);

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
     * @param t The place of piece we would add in our game list.
     */
    void setPiece(byte t);

    /**
     * This method would set the winner and stopped the game.
     * After this method, we can't do any other methods unless we invoke
     * <em>void reset()</em>.
     * <p>
     * By the way, use <em>void reset(List list)</em> is also okay, because
     * I would overrides this method, including make the winner null.
     * @param beginner False means the latter player wins, and True means the
     *                 beginner wins.
     */
    void setWinner(boolean beginner);
}
