package socketwork;

import massiv.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * реализует Counter
 */

public class ImpCounter extends UnicastRemoteObject implements Counter{

    private ArrayList<ParamsCounter> pc = new ArrayList<ParamsCounter>();  //массив для хранения данных
    private boolean existData=true, flag=true;       //еще есть данные, которые надо посчитать: true = есть
    private int pos=0, masLen=0;              //индекс последней взятой посл-сти
    private double sum=0;


    protected ImpCounter() throws RemoteException {
        super();
    }

    public void addPC(Container c) throws RemoteException {
        pc.add(new ParamsCounter(c, c.getArray().size()%7+1));       //т.к. необходимо задать какое-то nThrds, то положим его равным этому
        masLen+=c.getArray().size();
        sum+=pc.get(pc.size()-1).countMean(0, 1);
    }

    public void sayHello(String id) throws RemoteException {
        System.out.printf("\n\nClient %s is here! Begins to calculate...\n", id);
    }

    public double[] fillResults() throws RemoteException {

        if ((pos+1) >= pc.size())       //если это последняя подвыборка данных
            existData = false;

        int bufPos = pos;
        pos++;
        System.out.printf("\nSubseq #%d:\nCalculating...", bufPos);
        pc.get(bufPos).fillRes(0, sum/masLen);
        return pc.get(bufPos).getRes();
    }

    public double[] getResults() throws RemoteException {
        flag = false;

        double[] res = new double[2];               //итоговые результаты: 0 - mean, 1 - sumSquare

        for (int i=0; i < pc.size(); i++) {
            res[0]+=pc.get(i).getRes()[0];
            res[1]+=pc.get(i).getRes()[1];
        }

        res[0] = res[0]/masLen;
        res[1] = Math.sqrt(res[1]/masLen);

        System.out.printf("\n\nHere are results: \nArrLength: %d\nMean: %.3f\nSigma: %.3f\n", masLen, res[0], res[1]);
        return (res);
    }

    public boolean isExistData() throws RemoteException {
        return existData;
    }

    public boolean isFlag() throws RemoteException {
        return flag;
    }
}


/*
    public double mean(int i) throws RemoteException {
        return pc.get(pos).getMean();
    }

    public double sumSquares(int i) throws RemoteException {
        return pc.get(pos).countMean(pc.get(pos).getMean(), 2);
    }

 */