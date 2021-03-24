package GameMain.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GameLog is an abstract class of Logger.
 * It has only two methods, one is to create a complete string for the LogInfo, the other
 * is to print the log information.
 * <p>
 *     The first method:
 *     <blockquote>
 *         getContent(LogInfo);
 *     </blockquote>
 * <p>
 *     would create a special string for the LogInfo. It contains the importance degree, the concrete moment of the
 *     information when invoking the method, and the content of the information.
 *     I don't permit you to override this method.
 * <p>
 *     The second method is an abstract method. Please override it carefully.
 * <p>
 *     This method would base on the first method, which can extract the fundamental content of the LogInfo.
 *     Then use the <em>void log(LogInfo)</em> to truly print out the log information.
 * @version 2021-3-24
 * @see ClientLog
 * @see ServerLog
 * @author Cutie
 */
abstract public class GameLog {

    final protected String getContent(LogInfo logInfo){
        return String.format("[%s] [%s] %s", logInfo.getLogLevel(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                logInfo.getContent());
    }

    /**
     * This method is used to print the logInfo into the log.
     * @param logInfo The log information which would be printed.
     */
    abstract public void log(LogInfo logInfo);

}
