package massiv;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class Main {

    // static int dim, nThr;
    static int[] sigma = {4, 0, 5, 10};
    static int[] mean = {1, 1, 5, 2};


    /*вычисление суммы; n - число слагаемых*/
    private static double sum (int n, Random rnd) {

        double res = 0;
        for (int i=0; i < n; i++)
            res += rnd.nextDouble();

        return res;
    }


    public static void main(String[] args) {

        long[][] times = new long[4][4];
        int[] nThrds = {2, 3, 5, 7};
        int[] dims = {1000, 5000, 500000, 1000000};

        for (int i=0; i < 4; i++) {
            for (int j=0; j < 4; j++) {

                System.out.printf("*************************************\nREPORT#%d%d:\n", i, j);

                long startTime = System.currentTimeMillis();
                act(nThrds[i%4], dims[j%4]);
                long resTime = System.currentTimeMillis() - startTime;

                System.out.println("RESULT TIME: " + resTime + " millis\n*************************************\n");
                times[i][j] = resTime;
            }
        }

        System.out.println("And wait!");
        for (int i=0; i < 4; i++)
            System.out.printf("\t"+ dims[i]);
        for (int i=0; i < 4; i++) {
            System.out.printf("\n%d\t", nThrds[i]);
            for (int j = 0; j < 4; j++)
                System.out.printf("%s\t\t", times[i][j]);
        }
    }


    public static void act(int nThr, int dim) {

        /*ввод мерности пространства, числа точек и числа доступных потоков
        Scanner in = new Scanner(System.in);
        System.out.println("Enter dimension: ");
        dim = in.nextInt();
        System.out.println("Enter num of threads: ");
        nThr = in.nextInt();*/

        int limit = dim/(nThr-1);

        Random rnd = new Random();
        Container mas = new Container();
        Thread[] thr = new Thread[nThr];

        System.out.println("start of array's generation...");
        for (int i=0; i < nThr; i++) {
            final int num = i;
            thr[i] = new Thread(
                    () -> {
                        ArrayList<Double> arr = new ArrayList<>();
                        for (int j=0; (j < limit)&&(j+num*limit < dim); j++)
                            arr.add(rnd.nextDouble());      //mean[0] + sigma[0]*(sum(12, rnd) - 6)

                        mas.put(arr);
                        System.out.printf("Thr#%d: done!\n", thr[num].getId());
                    }
            );

            thr[i].start();
        }

        for (int i=0; i < nThr; i++) {
            try {
                thr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("array has been generated\n\nnow, the system is counting parametrs\nhere a report:");

        ParamsCounter pc = new ParamsCounter(mas, nThr);

        //System.out.println(mas.getArray().toString());
        System.out.printf("\nRESULT: %d __ %d __ %.3f __ %.3f\n", nThr, mas.getArray().size(), pc.getMean(), pc.getSigma());

    }
}
