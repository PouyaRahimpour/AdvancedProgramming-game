import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    public Server(ServerSocket serversocket) {
        this.serverSocket = serversocket;
    }
    public void startServer() {
//        try {
//            while (!serverSocket.isClosed()) {
//                Socket socket = serverSocket.accept();
//                System.out.println("new client is connected");
//                ClientHandler clientHandler = new ClientHandler(socket);
//
//                Thread thread = new Thread(clientHandler);
//                thread.start();
//            }
//        } catch (IOException e) {
//
//        }
//        public void closeServerSocket() {
//            try {
//
//            } catch ()
//        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);
        Socket s = ss.accept();
        Scanner input = new Scanner(System.in);

        System.out.println("CONNECTED");

        new Thread(() -> {
            while (true) {
                InputStreamReader in = null;
                try {
                    in = new InputStreamReader(s.getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader bf = new BufferedReader(in);
                String str;
                try {
                    str = bf.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Client:" + str);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                PrintWriter pr;
                try {
                    pr = new PrintWriter(s.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pr.println(input.nextLine());
                pr.flush();
            }
        }).start();

    }
}
