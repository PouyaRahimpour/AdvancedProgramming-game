import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        Socket socket = serverSocket.accept();
        Scanner input = new Scanner(System.in);

        System.out.println("CONNECTED");

        // listener
        new Thread(() -> {
            while (true) {
                InputStreamReader in;
                try {
                    in = new InputStreamReader(socket.getInputStream());
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

        //printer
        new Thread(() -> {
            while (true) {
                PrintWriter pr;
                try {
                    pr = new PrintWriter(socket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pr.println(input.nextLine());
                pr.flush();
            }
        }).start();


    }
}
