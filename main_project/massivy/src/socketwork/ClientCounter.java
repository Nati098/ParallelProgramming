package socketwork;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;


import static socketwork.Controller.NAME;


/**
 * Клиент-обработчик данных
 */

public class ClientCounter {

    private static final int SLEEP_TIME = 3000;     //время сна после вычисления 1 подвыборки
    private static String id;

    public ClientCounter() {}

    public static void start() {
        try {
            Counter cntr = (Counter) Naming.lookup(NAME);

            Scanner in = new Scanner(System.in);
            System.out.println("Enter id: ");
            id = in.next();

            cntr.sayHello(id);

            int i=0;
            while (cntr.isExistData()) {
                System.out.println("\nFor subseq " + i + ": " + Arrays.toString(cntr.fillResults()));
                i++;
                Thread.sleep(SLEEP_TIME);
            }

            System.out.println("\nDATA EXPIRED!");

            if (cntr.isFlag())
                System.out.println("RESULT: " + Arrays.toString(cntr.getResults()));

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }

}
