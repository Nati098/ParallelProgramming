package socketwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 */

public class Controller {

    public static final String NAME = "rmi://localhost/MASSIV";
    public static final int PORT = 1099;

    private static String STATUS;   //c = as Client; s = as ServerCounter
    private static int nDivs, dim;


    private static void generateArray(ArrayList<Double> arr) {

        Random rnd = new Random();

        for (int j=0; j < dim; j++)
            arr.add(rnd.nextDouble());      //mean[0] + sigma[0]*(sum(12, rnd) - 6)

    }

    private static void ifServer() {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter dimension of array:");
        dim = in.nextInt();
        System.out.println("Are Clients expected?\n(n = no clients; y = yes, they sre): ");
        String exp = in.next();

        ArrayList<Double> arr = new ArrayList<Double>();

        if (exp.equalsIgnoreCase("n")) {                           //если нет клиентов

            /*генерация массива*/
            System.out.println("start of array's generation...");
            generateArray(arr);
            System.out.println("array has been generated\n");

            ServerCounter sc = new ServerCounter(arr);
            sc.start();
        }
        if (exp.equalsIgnoreCase("y")) {                                                                  //если клиенты есть
            System.out.println("start of array's generation...");
            generateArray(arr);
            System.out.println("array has been generated");

            System.out.println("How many divisions of Data? (must be >=0)");
            nDivs = in.nextInt();

            ServerCounter sc = new ServerCounter(arr, nDivs);
            sc.start();

            System.out.println("Server has started");
            System.out.println("Ready. You can run clients!");

        }
    }


    public static void main(String[] args) {

        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //System.setProperty("java.rmi.server.hostname", "10.5.50.211");

        Scanner in = new Scanner(System.in);
        System.out.println("Enter status\n(c = as Client; s = as Server): ");
        STATUS = in.next();


        if (STATUS.equalsIgnoreCase("s"))                //если роль сервера
            ifServer();
        else                                                 //если роль клиента
            ClientCounter.start();
    }

}



/*            Thread startServ = new Thread(
                    () -> {
                        sc.start();
                    }
            );
           // startServ.setDaemon(true);
            startServ.start();
            try {
                startServ.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
*/

            /*запускаем клиентов*/
         /*   Thread[] thr = new Thread[nDivs];
            System.out.println("\n\nNow, the system is counting parameters\nhere a report:");
            for (int i = 0; i < nClients; i++) {
                final int num = i;
                thr[i] = new Thread(
                        () -> {
                            cc[num] = new ClientCounter(num);
                            cc[num].start();
                        }
                );
                thr[i].start();
            }
            for (int i=0; i < nClients; i++)
                try {
                    thr[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
