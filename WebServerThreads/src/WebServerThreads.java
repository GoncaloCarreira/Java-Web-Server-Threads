import java.io.*;
import java.net.ServerSocket;

import java.net.Socket;

public class WebServerThreads {
    public static void main(String[] args) throws IOException {

        int portNumber = 9001;

        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(Thread.currentThread().getName());
            Thread thread = new Thread(new HandleClients(clientSocket));
            thread.start();
        }
    }
}

