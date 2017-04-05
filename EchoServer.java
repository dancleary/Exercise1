import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(22225)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    String address = socket.getInetAddress().getHostAddress();
                    System.out.printf("Client connected: %s%n", address);
                    OutputStream os = socket.getOutputStream();
                    PrintStream out = new PrintStream(os, true, "UTF-8");
                    BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                    String input;
                    do{
                        input = in.readLine();
                        out.println(input);
                    }while(!input.equals("exit"));
                    System.out.printf("Client disconnected: %s%n", address);    
                }
            }
        }
    }
}