package GameMain.Frame;

import GameMain.Console.ConsoleListener;
import GameMain.Log.ClientLog;
import GameMain.Log.GameLog;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public enum GameClient implements ConsoleListener {

    GAME_CLIENT;

    private GameLog log;

    {
        log = new ClientLog();
    }

    private final Scanner consoleInput;
    {
        consoleInput = new Scanner(System.in);
    }

    private Socket clientSocket = null;

    private boolean connect(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);
            return true;
        } catch (IOException e) {
            return false;
        }
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

        if (commandLine.startsWith("connect(") && commandLine.endsWith(")")){

            commandLine = commandLine.substring(8, commandLine.length()-1).trim();

            String[] split = commandLine.split(":");
            if (split.length == 1){

            }
            else if (split.length == 2){

            }
            return ;
        }
    }
}
