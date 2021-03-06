package GameMain.Frame;

import GameMain.Console.ConsoleListener;
import GameMain.Log.GameLog;
import GameMain.Log.LogLevel;
import GameMain.Log.ServerLog;
import GameMain.Log.SimpleLogInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;
import java.util.Scanner;

public enum GameServer implements ConsoleListener {

    GAME_SERVER;

    private GameLog log;

    {
        log = new ServerLog();
    }

    private ServerSocket socket;

    private static final int DEFAULT_PORT = 41457;

    private boolean createServer(int port){
        try {
            socket = new ServerSocket(port);
            log.log(SimpleLogInfo.LogInfoBuild(
                    String.format("ServerSocket is created, whose port is %d", port),
                    LogLevel.DEBUG
            ));
        } catch (IOException e) {
            log.log(SimpleLogInfo.LogInfoBuild(
                    String.format("ServerSocket [ port = %d ] fails to be created.", port),
                    LogLevel.DEBUG
            ));
            return false;
        }
        return true;
    }

    private static final Random random;
    static {
        random = new Random();
    }

    private boolean createServer(){
        if (createServer(DEFAULT_PORT)){
                return true;
        }

        boolean flag = true;
        int testPort = -1;

        while (flag){
            testPort = 256 + random.nextInt(65536-256);
            flag = !createServer(testPort);
        }

        System.out.println(String.format("Server is created [ port = %d ].", testPort));

        return true;
    }



    private Scanner consoleInput;
    {
        consoleInput = new Scanner(System.in);
    }

    /**
     * This method would find a String which was input in console, and we would use
     * another method to read it and perform it.
     * <p>
     * Please notice that, when we get a String from the console by this method, you
     * had better write this event in our log file.
     * <p>
     * By the way, this kind of information should be set an importance level as
     * LogLevel.TRACE.
     * <p>
     * The reason is that, these pieces of information would be meaningless for
     * us to research our program. Because we may input some impossible or invalid
     * command in it, we wish our program can skip it well and be robust well.
     *
     * @return The command from the console.
     */
    @Override
    public String getString() {
        return consoleInput.nextLine();
    }

    /**
     * Actually, these method I design only permitted to be used in this class method
     * <em>void run()</em>, any other situation I don't suggest it to be used.
     * Though I feel sorry because I can't eventually ban it on other stages.
     * <p>
     * It would be a proper fixed version when I promote it well.
     * By the way, I would write some possible log appearance possibility in it.
     * <p>
     * When we get a valid command, we would mark it as the information level INFO.
     * Because it means it invoke some important process, however, if the command was
     * abjected, I would make it more important like, WARN.
     * <p>
     * For example, you put some commands to make our program connect with other
     * servers, when we have already connected with one server.
     * Then this request would be rejected, and we would make it obvious in our log file.
     *
     * @param commandLine the command from the console.
     */
    @Override
    public void invoke(String commandLine) {
        commandLine = commandLine.trim();
    }
}