package massiv;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Для счета параметров
 */

public class ParamsCounter implements Serializable{

    private ArrayList<Double> mas;
    private int nThr;           //число доступных потоков
    private double[] results = new double[2];   //массив результатов вычислений (для 2 и 3 лаб)
    private double mean;
    private double sigma = -1;
    boolean isCalc = false;

    /*считает сумму по выборке; на вход - arg - вычитаемое, pow - степень*/
    public double countMean(double arg, int pow) {
        Thread[] thr = new Thread[nThr];
        final int lim = mas.size()/(nThr-1);
        Result res = new Result();
        int[] sP = {0};             //начальная точка

        for (int i = 0; i < nThr; i++) {
            final int num = i;
            thr[i] = new Thread(
                    () -> {
                        Result sl = new Result();

                        for (int j = sP[0]; (j < lim*(num+1))&&(j < mas.size()); j++) {
                            sl.incRes(Math.pow(mas.get(j) - arg, pow));
                        }
                        sP[0] += lim;
                        //System.out.printf("Thr #%d: %.3f\n", thr[num].getId(), sl.getRes());

                        res.incRes(sl.getRes());
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

        return res.getRes();
    }


    public ParamsCounter(Container c, int nThrds) {
        mas = new ArrayList<>();
        mas.addAll(c.getArray());
        nThr = nThrds;
    }

    /*получение среднего на основе данных dist*/
    public double getMean() {

        System.out.println("start of mean's counting...");
        if (!isCalc) {
            mean = countMean( 0, 1)/mas.size();
            isCalc = true;
        }
        return mean;
    }

    /*получение ср.кв.отклонения на основе dist*/
    public double getSigma() {

        System.out.println("start of sigma's counting...");
        if (sigma == -1) {
            sigma = Math.sqrt(countMean(mean, 2)/mas.size());
        }
        return sigma;
    }

    /*заполнение массива results (2-3 лаба): 0 - сумма элементов, 1 - сумма квадратов разности*/
    public void fillRes(double firstArg, double secondArg) {
        results[0] = countMean(firstArg, 1);
        results[1] = countMean(secondArg, 2);
    }

    /*вывод results (2-3 лабв)*/
    public double[] getRes() {
        return results;
    }

}





/*
*         кусок из countMean
*         do {
            for (int i = count; (i < nThr+count)&&(i < mas.size()); i++) {
                final int num = i;
                thr[i-count] = new Thread(
                        () -> {
                            double sl = 0;

                            for (int j = 0; j < lim; j++) {
                                sl += Math.pow(mas.get(num) - arg, pow);
                            }

                            synchronized (new Object()) {
                                    res[0] += sl;
                            }
                        }
                );
                thr[i-count].start();
                res[1] = i;
            }

            for (int i=0; i < (int)res[1]-count; i++) {
                try {
                    thr[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count += nThr;
        }
        while (count < mas.size());
*/