package GameMain.Log;

/**
 * SimpleLogInfo is a concrete class to realize the LogInfo.
 * There is only one static method to create its instance.
 * <blockquote>
 *     LogInfoBuild(String content, LogLevel logLevel);
 * </blockquote>
 * <p>
 *     And you can use other methods from LogInfo for free.
 * @see LogInfo
 * @version 2021-3-24
 * @author Cutie
 */
public class SimpleLogInfo extends LogInfo{

    private SimpleLogInfo(String content, LogLevel logLevel) {
        super(content, logLevel);
    }

    public static SimpleLogInfo LogInfoBuild(String content, LogLevel logLevel){
        return new SimpleLogInfo(content, logLevel);
    }

}
