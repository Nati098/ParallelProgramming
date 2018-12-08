import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 */
public class WorkedThread extends Thread {

    Socket socket;
    Server server;

    public WorkedThread(Server ser, Socket s) {
        this.socket = s;
        this.server = ser;
    }

    public void send(String str) {  //отправить сообщение этому клиенту

    }

    @Override
    public void run() {     //обрабатывать все новые сообщения от клиента

            try {
                Scanner scan = new Scanner(socket.getInputStream());

                while (socket.isConnected()) {
                    System.out.println(scan.nextLine());
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
