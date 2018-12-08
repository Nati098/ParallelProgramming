import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 */
public class Client {

    static final int PORT = 7777;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String ip = in.nextLine();

        try {
            Socket soc = new Socket(ip, PORT);
            Scanner read = new Scanner(soc.getInputStream());
            PrintWriter write = new PrintWriter(soc.getOutputStream());

            while (soc.isConnected()) {
                while (read.hasNext()) {
                    System.out.println(read.nextLine());
                }

                write.print(in.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
