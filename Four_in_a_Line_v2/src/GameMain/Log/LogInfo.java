package GameMain.Log;

/**
 * LogInfo is an abstract class to express a piece of information.
 * <p>
 *     Please note that, LogInfo won't print its special information or exception
 *     in the log. Therefore, If you guess something wrong in the class, please use
 *     System.out.print() to try to finish the problem.
 * <p>
 *     By the way, I have some questions why we often meet some confusing problems,
 *     like writing some codes in the compiler but fails to work.
 *     However, when we just add some simple codes, like
 * <blockquote>
 *     System.out.println("I work!");
 * </blockquote>
 * <p>
 *     Then it works really.
 *     It seems ridiculous, doesn't it?
 * <p>
 * <p>
 *     LogInfo though has a constructor, whose access permission is <em>protected</em>.
 *     I don't advise you to directly use this constructor to create its subclasses.
 *     And advise you to try to write some static method to create it and it may be helpful.
 *     For example, <code><blockquote>
 *         public static T LogInfoBuild( some args ) {
 *             // Write the concrete ways to create this object.
 *         }
 *     </blockquote></code>
 * <p>
 *     LogInfo would contains two pieces of information, in which one is the string of the information,
 *     the other is the level to express the importance degree of the information.
 * <p>
 *     When you want to print the information to the file, you should use the method <em>log(GameLog)</em>.
 *     This method acquires you to plug a GameLog (just a log printer) in it, and it would use the GameLog to
 *     print the information in the file.
 * <p>
 *     You can DIY the GameLog by the anonymous class to print the information to any file you like.
 * <p>
 *     I must notice everyone that, the default permitted level of the information which likely to be printed
 *     is the Level DEBUG.
 * <p>
 *     I'm sorry because I can't use a special data file to store the information I want. I can just stupidly
 *     record it in my program. Sorry for my incompetent.
 * @see SimpleLogInfo
 * @author Cutie
 * @version 2021-03-24
 */

@SuppressWarnings("unused")
abstract public class LogInfo {

    private static final LogLevel DEFAULT_PERMIT_LEVEL = LogLevel.DEBUG;
    private static LogLevel permitLevel = DEFAULT_PERMIT_LEVEL;

    private final String content;
    private final LogLevel logLevel;

    protected LogInfo(String content, LogLevel logLevel) {
        this.content = content;
        this.logLevel = logLevel;
    }

    /**
     * Print this information by the Logger. But if your level standard of the information is
     * too high, then maybe this information would be skipped and you won't see it in the file.
     * @param gameLog The Logger which can print the information.
     */
    public void log(GameLog gameLog){
        if (this.logLevel.examinePermission(permitLevel)){
            gameLog.log(this);
        }
    }

    /**
     * This method is used to get the LogLevel of the information.
     * @return The importance level of the information.
     */
    LogLevel getLogLevel(){
        return this.logLevel;
    }

    /**
     * This method is used to get the concrete content of the information.
     * @return The concrete content of the information.
     */
    String getContent(){
        return this.content;
    }

    /**
     * This static method is used to change the permitted information standard.
     * You can write that to display all the log information:
     * <blockquote>
     *     setPermitLevel(LogLevel.ALL);
     * </blockquote>
     * <p>
     *     However, you can also write that to display anything:
     * <blockquote>
     *     setPermitLevel(LogLevel.OFF);
     * </blockquote>
     * <p>
     *     I don't suggest you to change it often.
     * @param permitLevel The permitted information standard, or the least importance level of the information
     *                    which can be displayed.
     */
    public static void setPermitLevel(LogLevel permitLevel){
        LogInfo.permitLevel = permitLevel;
    }

}
