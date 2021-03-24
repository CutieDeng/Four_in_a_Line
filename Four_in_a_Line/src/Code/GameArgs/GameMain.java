package Code.GameArgs;

import Code.Client.FourLineClient;
import Code.Server.FourLineServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class GameMain {

    private static Random random = new Random();
    private static FourLineClient client;
    private static FourLineServer server;
    private static Runnable serverThread = null;

    public static void main(String[] args) {

        long id = 0;
//        random.nextLong();
        client = new FourLineClient(id);
        System.out.println("Your client id :" + id);

        Scanner input = new Scanner(System.in);
        String r;
        while (!client.isConnect()){
            try {
                System.out.println("Your computer : "+
                        InetAddress.getLocalHost());
            } catch (UnknownHostException e) {
            }
            System.out.println("Please input the ip you want to connect with: ");
            r = input.nextLine();
            System.out.println("input");
            if (r.equals("")){
                System.out.println("You make yourself the server.");
                server = new FourLineServer(id);
                serverThread = new Runnable() {
                    @Override
                    public void run() {
                        server.work();
                    }
                };
                new Thread(serverThread).start();
                client.attemptConnect();
            }
            else{
                client.attemptConnect(r);
                if (!client.isConnect()){
                    System.out.println("Fail to connect.");
                }
                else{
                    System.out.println("Succeed to connect.");
                }
                System.out.println();
            }
        }
        client.waitToPlayer();
        System.out.println("Wait to Player done.");
        client.waitBegin();
        System.out.println("Wait begin done.");
        new Thread(client).start();
        System.out.println("Thread starts.");
    }

}
