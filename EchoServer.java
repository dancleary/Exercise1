import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {
    public static void main(String[] args) throws Exception {
        int count = 0;
        try (ServerSocket serverSocket = new ServerSocket(22225)) {
            while (true) {
                
                count++;
                Socket socket = serverSocket.accept();
                String address = socket.getInetAddress().getHostAddress();
                Runnable r = new echoThread(socket, address, count);
                Thread t = new Thread(r);
                t.start();
                
                System.out.println("Clients connected: " + count);
                }
            }
        }
}
class echoThread implements Runnable {
    private final Socket socket;
    private final String address;
    private final int count;
    PrintStream out;
    BufferedReader in;
    echoThread(Socket socket, String address, int count){
        this.socket = socket;
        this.address = address;
        this.count = count;
    }
    
    @Override
    public void run(){
        System.out.printf("Client %d connected: %s%n", count, address);
        try{
        OutputStream os = socket.getOutputStream();
        out = new PrintStream(os, true, "UTF-8");
        in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        }catch (IOException e) {System.err.println(e);}
        String input;
        try{        
        do {
            input = in.readLine();
            out.println(input);
        } while (!input.equals("exit"));
        }catch (IOException e) {System.err.println(e);
        }finally{
            System.out.printf("Client %d disconnected: %s%n", count, address);
        }
    }
}
