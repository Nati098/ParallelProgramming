package massiv;

/**
 * контейнер для результата
 */
public class Result {

    double res = 0;
    boolean flag = false;


    public void incRes(double element) {
        while (flag) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        flag = true;
        res += element;
        flag = false;
    }

    public double getRes() {
        return res;
    }
}
