package GameMain.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ServerLog is the concrete class to achieve the GameLog.
 * <p>
 *     It would print the information about server to the serverLog.txt
 * <p>
 *     At the initial duration, it would initialize the log printer as a PrintStream.
 *     Note that, it's not a static variable. Though I originally make it static.
 *     But then I change my mind.
 *     It would print the stack trace when it can't create a printStream.
 *     Then the program would stop to run.
 * <p>
 *     Note: This exception won't be recorded in the log file.
 * @see GameLog
 * @version 2021-03-24
 * @author Cutie
 */
final public class ServerLog extends GameLog{
    private PrintStream logPrint;

    {
        try {
            logPrint = new PrintStream(new FileOutputStream("serverLog.txt", true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * It's an empty constructor.
     */
    public ServerLog() {
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
