import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

/**
 * Для счета параметров
 */

public class ParamsCounter {

    private ArrayList<Double> mas;
    private int nThr;           //число доступных потоков
    private double mean;
    private double sigma = -1;
    boolean isCalc = false;

    /*считает среднее по выборке; на вход - arg - вычитаемое, pow - степень*/
    private double countMean(double arg, int pow) {
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

                        System.out.println("Thr #" + thr[num].getId()+": "+sl.getRes());

                        res.incRes(sl.getRes());
                    }
            );
            sP[0] += lim;
            thr[i].start();
        }

        for (int i=0; i < nThr; i++) {
            try {
                thr[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return res.getRes()/mas.size();
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
            mean = countMean((double) 0, 1);
            isCalc = true;
        }
        return mean;
    }

    /*получение ср.кв.отклонения на основе dist*/
    public double getSigma() {

        System.out.println("start of sigma's counting...");
        if (sigma == -1) {
            sigma = countMean(mean, 2);
        }

        return sigma;
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