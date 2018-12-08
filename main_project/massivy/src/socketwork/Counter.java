package socketwork;

import massiv.Container;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * используется для объявления методов,
 * которые ClientCounter может вызывать у удаленного объекта (наши данные)
 */

public interface Counter extends Remote{

    /*добвление подпоследовательности из массива данных по id*/
    public void addPC(Container c) throws RemoteException;

    /*извещение о подключении польззователя к сети*/
    public void sayHello(String id) throws RemoteException;

    /*заполнение массива результатов*/
    public double[] fillResults() throws RemoteException;

    /*для вывода результатов у клиента*/
    public double[] getResults() throws RemoteException;

    /*для доступа к флагам извне*/
    public boolean isExistData() throws RemoteException;        //маркер, что еще есть дданные для обработки
    public boolean isFlag() throws RemoteException;             //маркер, что еще не подсчитан итоговый результат
}

/*
    //вычисление среднего
    public double mean(int i) throws RemoteException;

    //вычисление суммы квадратов разностей (для вычисления sigma)
    public double sumSquares(int i) throws RemoteException;
    */