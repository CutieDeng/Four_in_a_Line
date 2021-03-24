package Code.Log;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

abstract public class Logging {

    private final static InfoLevel PERMIT_INFO_LEVEL = InfoLevel.INFO;

    protected File logFile ;
    protected PrintWriter printWriter ;
    private InfoLevel permitInfoLevel = PERMIT_INFO_LEVEL;

    /**
     * This method can change the limitation about the logging.
     * <p>
     *     For example, if you set the limitation at InfoLevel.WARN, then only the WARN information you would see in the logging txt file.
     * <p>
     *     But if you set the limitation at InfoLevel.INFO, you would see all information in the logging txt file.
     * <p>
     *     Sure, it's an obvious principle in it, like you can see the information more important or equal to the importance-limitation set by this method.
     * <p>
     *     Some advice:
     * <p>
     *     If you are trying to promote the program, just set the INFO level is proper.
     * <p>
     *     However, if you just want to play the game, why not set a higher level like InfoLevel.WARN or InfoLevel.TOP?
     * <p>
     *     That would release your capacity I believe.
     * @param permitInfoLevel The level of the information you permit to print out.
     */
    public void setPermitInfoLevel(InfoLevel permitInfoLevel){
        this.permitInfoLevel = permitInfoLevel;
    }

    /**
     * This method is used to print the concrete content in our logging file.
     * <p>
     *     You should notice some bad information. For example, when you have already set the permitted information level
     *     InfoLevel.WARN, then you again use this method to want to print a piece of information whose priority level is
     *     InfoLevel.INFO, then we wouldn't play it, because this action disobeys our principles.
     * <p>
     *     So don't be lazy.
     * <p>
     *     By the way, if you put a string which is null, I won't perform this method and just ignore this exception.
     * @param text The content we want to print out.
     * @param infoLevel The importance level of the information.
     */
    public void log(String text, InfoLevel infoLevel){
        if (!infoLevel.isPermitted(this.permitInfoLevel)){
            return ;
        }
        if (text == null){
            return ;
        }
        printWriter.print(String.format("[%s]", infoLevel));
        printWriter.print(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")));
        printWriter.print(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss : ")));
        printWriter.println(text);
        printWriter.flush();
    }

    /**
     * This method is used to print the information that tell us what is happening in programming.
     * <p>
     *     The information's priority would be set InfoLevel.INFO by default.
     * <p>
     *     If you have already set the permitted level higher than INFO, use this method wouldn't
     *     do its job. Please care about that.
     * @param text The content we want to print out.
     */
    public void log(String text){
        log(text, InfoLevel.INFO);
    }

}
