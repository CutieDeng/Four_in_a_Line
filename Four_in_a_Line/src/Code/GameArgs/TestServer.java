package Code.GameArgs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket= new ServerSocket(9999);

        System.out.println("Server initialize.");

        Socket wait = serverSocket.accept();

        while (true){
            int r = wait.getInputStream().read();
            System.out.println(r);
        }
    }
}
