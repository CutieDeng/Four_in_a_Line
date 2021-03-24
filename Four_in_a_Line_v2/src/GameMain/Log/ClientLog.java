package GameMain.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * ClientLog is the class to realize the abstract class GameLog.
 * Its usage is to create the log file for the client.
 * And it would create a file named clientLog.txt
 * <p>
 *     If some exception happens in the process creating the printStream,
 *     it would invoke these codes:
 * <blockquote>
 *     System.exit(-1)
 * </blockquote>
 * <p>
 *     This rule is similar to the class ServerLog.
 * @see ServerLog
 * @see GameLog
 * @author Cutie
 * @version 2021-03-24
 */
final public class ClientLog extends GameLog{
    private PrintStream logPrint;

    {
        try {
            logPrint = new PrintStream(new FileOutputStream("clientLog.txt", true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * It's an empty constructor.
     */
    public ClientLog() {
    }

    /**
     * This method is used to print the logInfo into the log.
     *
     * @param logInfo The log information which would be printed.
     */
    @Override
    public void log(LogInfo logInfo) {
        logPrint.println(super.getContent(logInfo));
    }
}
