package socketwork;

import massiv.Container;
import massiv.ParamsCounter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;


import static socketwork.Controller.NAME;

/**
 * Одним словом, сервер
 */

public class ServerCounter {

    private static ArrayList<Double> mas = new ArrayList<Double>(10);      //полный массив данных
    private static int nDivs = 0;
    private boolean exClients;                  //маркер, есть ли клиенты (true = есть, false = нет)


    /*если есть подключенные клиенты, то выполняем этот код*/
    private void existsClient() {

        try {
            ImpCounter cntr = new ImpCounter();

            /*заполнение ImpCounter*/
            for (int i = 0; i< nDivs; i++) {
                Container c = new Container();
                c.put(new ArrayList<Double>(mas.subList(i*mas.size()/nDivs, (i+1)*mas.size()/nDivs)));

                cntr.addPC(c);
            }

            /**/
            Naming.rebind(NAME, cntr);

        }
        catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /*если нет клиентов, то этот код*/
    private void noClients() {
        Container cont = new Container();
        cont.put(mas);

        System.out.println("array has been got\n\nnow, the system is counting parametrs\nhere a report:");

        int[] num = new int[1];                         //задаем число потоков для выполнения подсчетов
        num[0] = cont.getArray().size()%7;
        if (num[0] == 0)
            num[0] = cont.getArray().size()%11;

        ParamsCounter pc = new ParamsCounter(cont, num[0]+1);

        System.out.printf("\nRESULT: %d __ %.3f __ %.3f\n", cont.getArray().size(), pc.getMean(), pc.getSigma());

    }


    /*число лиентов не задано - ориентир - TIME; если клиентов нет - самостоятельный подсчет*/
    public ServerCounter(ArrayList<Double> m) {
        mas.addAll(m);
        exClients = false;
    }

    /*число клиентов задано - n*/
    public ServerCounter(ArrayList<Double> m, int n) {
        mas.addAll(m);
        nDivs = n;
        exClients = true;
    }

    /*запуск сервера*/
    public void start() {
        if (exClients)
            existsClient();
        else
            noClients();
    }

}


//start rmiregistry -J-Djava.class.path="C:\Bill\PP\project\massivy\out\production\massivy"
//java -classpath C:/Bill/PP/project/massivy/out/production/massivy socketwork.ServerCounter

//private boolean flag=false;               //маркер - здоступен ли массив в реестре
//private static final double TIME = 0;       //время ожидания подключения клиентов

    /*получить значение флага
    public boolean getFlag() {
        return flag;
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        System.out.println("SERVERRRRRRRRRRRRRRR");
    }*/