package GameMain.Frame;

/**
 * GameKeyListener is an interface for the game clients. Every clients I realize or
 * you realize must implement it, so that the gameFrame can truly use it to notify
 * the server what you input.
 * <p>
 * However, remember, if the client by mistake send a meaningless message, we must
 * ignore it and we can't just put it in our game queue.
 * @version 2021-03-26
 * @author Cutie
 */
public interface GameKeyListener {

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
     * @param keyInput The position you want to place then would send to the server.
     */
    void sendKeyInput(int keyInput);

}
