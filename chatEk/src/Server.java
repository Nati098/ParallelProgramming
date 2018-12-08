import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 */
public class Server {

    public static void main(String[] args) {
        new Server().start();
    }

    public void send(String str) {      //отправить соощение всем подключенным клиентам

    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(Client.PORT);

            while (true) {
                handleConnection(ss.accept());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleConnection(final Socket socket) {     //обработать новое подключение

    }
}
