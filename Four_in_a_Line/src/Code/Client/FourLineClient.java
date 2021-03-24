package Code.Client;

import Code.GameArgs.*;
import Code.Log.ClientLogging;
import Code.Log.InfoLevel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

import static Code.Server.FourLineServer.PORT_DEFAULT;

public class FourLineClient implements Runnable, ClickPosition {

    private final long id;
    private ClientLogging logging;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    volatile private Socket socket;

    private GameList gameList;
    private String name = null;
    private boolean canSend = false;

    public FourLineClient(long id) {
        this.id = id;
        logging = new ClientLogging(id);
        logging.log("The Client is created.", InfoLevel.TOP);
    }

    public boolean attemptConnect(String ip, int port){
        int attemptTimes = 0;
        while (socket == null && attemptTimes < 3){
            logging.log(String.format("%d time%s: Attempt to connect to the server [ip=%s, port=%d]", (attemptTimes+1),
                    (attemptTimes==0) ? " " : "s" ,
                    ip, port)
            );
            try {
                socket = new Socket(ip, port);
            } catch (IOException e) {
            }
            attemptTimes++;
        }
        if (socket == null){
            logging.log(String.format("Can't connect with the server [ip=%s, port=%d].", ip, port), InfoLevel.INFO);
            return false;
        }
        logging.log(String.format("Succeed to connect the server [ip=%s, port=%d]", ip, port), InfoLevel.TOP);
        return true;
    }

    public boolean attemptConnect(String ip){
        return attemptConnect(ip, PORT_DEFAULT);
    }

    public boolean attemptConnect(){
        return attemptConnect("127.0.0.1", PORT_DEFAULT);
    }

    public void waitToPlayer(){
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            logging.log("Succeed to create the IO stream.");
            System.out.println("Create IO stream.");
        } catch (IOException e) {
            e.printStackTrace();
            logging.log(String.format("Socket cannot get the IO stream from server[%s]", socket.getInetAddress().getHostAddress()),
                    InfoLevel.WARN);
        }
    }

    public void waitBegin(){
        Object read = null;
        while (read == null || !(read instanceof BeginEvent)){
            try {
                read = inputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println(read.getClass().getSimpleName());
        logging.log("Game begin!");
        gameList = new GameList(logging);
    }

    public boolean isConnect(){
        return socket != null && socket.isConnected();
    }

    private GameFrame gameFrame;

    @Override
    public void run() {
        gameFrame = new GameFrame(gameList);
        gameFrame.setClickPosition(this);
        Object read = null;
        while (gameList.whoWins() == null){
            try {
                System.out.println("Begin read.");
                read = inputStream.readObject();
                if (read==null){
                    System.out.println("null");
                }
                System.out.println("Finish read.");
                logging.log("read something from the server. Class:" +
                        read.getClass().getSimpleName());
            } catch (IOException e) {
                System.out.println("No object to read.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                }
                continue;
            } catch (ClassNotFoundException e) {
                System.out.println("class no found.");
                continue;
            }
            if (read instanceof WaitEvent){
                canSend = true;
                if (name == null){
                    name = ((WaitEvent) read).getName();
                }
                System.out.println("Now it's your turn.");
                logging.log("Now it's your turn.");
                continue;
            }
            if (read instanceof WinEvent){
                System.out.println(((WinEvent)read).getName() + " wins.");
                logging.log(((WinEvent)read).getName() + " wins.");
                break;
            }
            if (read instanceof PieceLoadEvent){
                gameList.putByte(((PieceLoadEvent)read).getPiece());
                continue;
            }
        }
    }

    @Override
    public void informClick(int row, int column) {
        System.out.println(String.format("You click [%d][%d]", row, column));
        if (canSend){
            canSend = false;
            try {
                outputStream.writeObject(
                        new PieceLoadEvent(name, (byte)column)
                );
            } catch (IOException e) {
            }
        }
        else{
            logging.log(String.format("You do an invalid click [%d][%d]", row, column), InfoLevel.INFO);
        }
    }
}
