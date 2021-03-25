package GameMain.Console;

/**
 * ConsoleListener is planned to use in class Client and Server.
 * It's used to get the command from the console and perform them.
 * <p>
 *     It extends the Interface <em>Runnable</em>, therefore it should be run as
 *     a new thread. There are two methods you need to override, not including
 *     the method <em>void run()</em> of Runnable, because it had been written
 *     before as default.
 * <p>
 *     The first method to override is <em>String getString()</em>. It's used to
 *     get the commandLine from console or other frames, which used to examine
 *     it can be perform or not.
 * <p>
 *     The second method to override is <em>void invoke(String commandLine)</em>.
 *     It's created to perform the codes which input in console or other input stream.
 * @see java.lang.Runnable
 * @version 2021-03-25
 * @author Cutie
 */
public interface ConsoleListener extends Runnable{
    /**
     * This method would find a String which was input in console, and we would use
     * another method to read it and perform it.
     * <p>
     *     Please notice that, when we get a String from the console by this method, you
     *     had better write this event in our log file.
     * <p>
     *     By the way, this kind of information should be set an importance level as
     *     LogLevel.TRACE.
     * <p>
     *     The reason is that, these pieces of information would be meaningless for
     *     us to research our program. Because we may input some impossible or invalid
     *     command in it, we wish our program can skip it well and be robust well.
     * @return The command from the console.
     */
    String getString();

    /**
     * Actually, these method I design only permitted to be used in this class method
     * <em>void run()</em>, any other situation I don't suggest it to be used.
     * Though I feel sorry because I can't eventually ban it on other stages.
     * <p>
     *     It would be a proper fixed version when I promote it well.
     *     By the way, I would write some possible log appearance possibility in it.
     * <p>
     *     When we get a valid command, we would mark it as the information level INFO.
     *     Because it means it invoke some important process, however, if the command was
     *     abjected, I would make it more important like, WARN.
     * <p>
     *     For example, you put some commands to make our program connect with other
     *     servers, when we have already connected with one server.
     *     Then this request would be rejected, and we would make it obvious in our log file.
     * @param commandLine the command from the console.
     */
    void invoke(String commandLine);

    /**
     * It's override from the interface <em>Runnable</em>.
     * By the way, this interface add a special annotation
     * <strong>default</strong> for it, which means you have no need
     * to write this method again.
     * Use this method is easy like,
     * <blockquote>
     *     new Thread(this).start();
     * </blockquote>
     * <p>
     *     Please note that, don't direct write these codes:
     * <blockquote>
     *     this.run();
     * </blockquote>
     * <p>
     *     Otherwise, you won't have another new thread to run the method, which is a never stop loop.
     *     It would completely stop our program to step for its complicated and complex design.
     */
    @Override
    default void run(){
        while (true){
            invoke(getString());
        }
    }
}
