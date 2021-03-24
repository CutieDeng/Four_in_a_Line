package Code.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * ServerLogging is a class to create a Logging for a server.
 * <p>
 *     Server is the core of the game.
 *     We would run the game by exchanging the information between the server and the clients.
 *     Therefore, the server is the most important in our program.
 * <p>
 *     Then we must print the server events in our logging files.
 *     We may record some different kinds of information, and instant to examine the problems may happening in our process.
 * <p>
 *     Similarly, just use {@code log(String content)} to push it to print something you want down.
 *
 * @see Code.Log.Logging
 * @since 1.0
 * @author Cutie
 */
public class ServerLogging extends Logging{
    /**
     * The method would create a server logging.
     * <p>
     * When the server is created, the log content would be printed in the file whose name is the [server(number).txt].
     * The number is the same as {@code id}.
     * Below is the example.
     * <p>
     * If you just write down these codes:
     * <blockquote><prep>
     * ServerLogging serverLogging = new ServerLogging(1314520);
     * </prep></blockquote>
     * <p>
     * Then this object would print everything down in a special txt file named "server1314520.txt".
     * <p>
     * If you like, you can just double click and open the file to read the concrete process happening in the program.
     * <p>
     * Or in the future, we would design another application to help you read these information quickly.
     *
     * @param id It's the id of the server, and also the unique identifier of the server.
     */
    public ServerLogging(long id) {
        logFile = new File("ServerLog\\" +
                "server" + id +
                ".txt");
        boolean dirCreated = false;
        while (printWriter == null) {
            try {
                printWriter = new PrintWriter(new FileOutputStream(logFile, true));
            } catch (FileNotFoundException e) {
                File dir = logFile.getParentFile();
                if (!dir.exists()) {
                    dirCreated = dir.mkdirs();
                }
            }
        }
        if (dirCreated) {
            log("Can't find the appropriate directory but create one.", InfoLevel.WARN);
        }
    }
}
