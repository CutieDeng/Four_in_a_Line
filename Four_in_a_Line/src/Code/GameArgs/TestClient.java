package Code.GameArgs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("Succeed to connect with the server.");
        int cnt = 0;
        Scanner input = new Scanner(System.in);
        while (true){
            if (input.hasNextInt()){
                socket.getOutputStream().write(input.nextInt());
            }
            else{
                input.next();
            }
        }
    }
}
