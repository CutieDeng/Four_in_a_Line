package Code.Server;

import Code.GameArgs.BeginEvent;
import Code.GameArgs.GameList;
import Code.GameArgs.PieceLoadEvent;
import Code.GameArgs.WaitEvent;
import Code.Log.InfoLevel;
import Code.Log.ServerLogging;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class FourLineServer {

    public final static int PORT_DEFAULT = 9999;

    private final long id;
    private ServerLogging logging;
    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets;

    private ObjectOutputStream[] outputStream = new ObjectOutputStream[2];
    private ObjectInputStream[] inputStream = new ObjectInputStream[2];

    private String[] names = {"O", "X"};

    private GameList gameList;

    public FourLineServer(long id) {
        this.id = id;
        logging = new ServerLogging(id);
        logging.log("The Server is created.", InfoLevel.TOP);
        try {
            serverSocket = new ServerSocket(PORT_DEFAULT);
        } catch (IOException e) {
            logging.log(e.getMessage(), InfoLevel.WARN);
        }
        sockets = new ArrayList<>();
    }

    private void waitPlayers(){
        while (sockets.size() < 2){
            try {
                Socket nextSocket = serverSocket.accept();
                sockets.add(nextSocket);
                logging.log(String.format("Connect to the socket [%s]", nextSocket.getRemoteSocketAddress()),
                        InfoLevel.INFO);
            } catch (IOException e) {
            }
        }
    }

    public void work(){
        waitPlayers();
        gameList = new GameList(logging);
        int i = 0 ;
        for (Socket socket : sockets){
            try {
                outputStream[i] = new ObjectOutputStream(socket.getOutputStream());
                inputStream[i] = new ObjectInputStream(socket.getInputStream());
                outputStream[i++].writeObject(new BeginEvent());
            } catch (IOException e) {
                e.printStackTrace();
                logging.log(String.format("Cannot get the output stream from socket [%s]", socket.getRemoteSocketAddress()),
                        InfoLevel.WARN);
            }
        }
        gameList.flush();
        logging.log("Game content flushes.");

        Iterator<Socket> iterator = sockets.iterator();
        Socket nowWait;
        int number = 0;
        byte nextS ;
        while (gameList.whoWins() == null){
            nextS = -1;
            if (!iterator.hasNext()){
                iterator = sockets.iterator();
            }
            nowWait = iterator.next();
            try {
                logging.log(String.format("Attempt to create the stream to socket [%s]", nowWait.getRemoteSocketAddress()));
                outputStream[number%2].writeObject(new WaitEvent(names[number%2]));
                logging.log(String.format("Tell it to send a message to the server."));
                Object readObject;
                while (true){
                    readObject = inputStream[number%2].readObject();
                    if (readObject == null){
                        System.out.println("Server read nothing.");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        continue;
                    }
                    if (!(readObject instanceof PieceLoadEvent)){
                        continue;
                    }
                    nextS = ((PieceLoadEvent)readObject).getPiece();
                    try {
                        gameList.putByte(nextS);
                        System.out.println("[Server] GameList put a byte:" + nextS);
                    }catch (RuntimeException e){
                        continue;
                    }
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                logging.log(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logging.log(e.getMessage());
            }
            for (Socket everySocket : sockets){
                try {
                    new ObjectOutputStream(everySocket.getOutputStream()).writeObject(new PieceLoadEvent(names[number%2], nextS));
                } catch (IOException e) {
                    e.printStackTrace();
                    logging.log(e.getMessage());
                }
            }
            number ++;
        }
    }

    public static void main(String[] args) throws IOException {
    }
}
