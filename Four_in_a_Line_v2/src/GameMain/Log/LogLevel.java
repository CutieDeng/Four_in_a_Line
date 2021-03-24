package GameMain.Log;

/**
 * LogLevel is an enum to show the importance degrees of the log information when running the program.
 * <p>
 *     There are four kinds of log information.
 * <p>
 *     TRACE : Every step when the program runs.
 *     If you don't have a special or necessary acquirement.
 *     Don't use this level standard.
 * <p>
 *     DEBUG : Every method when the program runs.
 * <p>
 *     NET_INFO : The internet information.
 * <p>
 *     GAME_INFO : The information about the game runs.
 * <p>
 *     INFO : Other information.
 * <p>
 *     WARN : The program throws an exception and we can try to fix it.
 * <p>
 *     FATAL : The program throws an exception but we can't solve it well.
 *     Or even we cannot deal with but still run our program.
 * <p>
 * <p>
 *     There are some other log standard levels you can use.
 *     But don't use it to mark the information's level.
 * <p>
 *     ALL : Every piece of information would be displayed.
 * <p>
 *     OFF : Every piece of information won't be disaplyed.
 * @version 2021-3-24
 * @author Cutie
 */

public enum LogLevel{

    ALL((byte)0),
    TRACE((byte)1),
    DEBUG((byte)2),
    INFO((byte)3),
    NET_INFO((byte)3),
    GAME_INFO((byte)3),
    WARN((byte)4),
    FATAL((byte)5),
    @Deprecated
    ERROR((byte)5),
    OFF((byte)6);

    private byte priority;

    LogLevel(byte priority){
        this.priority = priority;
    }

    /**
     * Compare the priority with the log level standard's priority.
     * @param logLevel The log level standard.
     * @return True if the log information's priority is higher than or equal to the standard.
     */
    public boolean examinePermission(LogLevel logLevel){
        return this.priority >= logLevel.priority;
    }
}
