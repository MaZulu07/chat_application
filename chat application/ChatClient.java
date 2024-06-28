import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String serverAddress) throws IOException {
        socket = new Socket(serverAddress, 12345);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void start() {
        new Thread(new IncomingReader()).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            out.println(scanner.nextLine());
        }
    }

    private class IncomingReader implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient("localhost");
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
