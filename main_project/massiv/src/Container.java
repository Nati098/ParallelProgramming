import java.util.ArrayList;
import java.util.Collection;

/**
 * содержит массив данных
 */

public class Container {

    private ArrayList<Double> mas;
    boolean flag = false;           //досупен ли контейнер для записи

    public Container() {
        mas = new ArrayList<Double>();
    }

    public void put(Collection<Double> elements) {
        while (flag)
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        flag = true;
        mas.addAll(elements);
        flag = false;

    }

    public ArrayList<Double> getArray() {
        return mas;
    }

}
