import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 22225);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
            ){
                String input;
                do{
                    System.out.print("Client> ");
                    input = in.readLine();
                    out.println(input);
                    System.out.println("Server> " + br.readLine());
                }while(!input.equals("exit"));
            }
    }
}


