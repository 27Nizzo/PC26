package Guiao2.ExclusaoMutua;
// Exercicio 1)
public class Counter {
    private int value = 0;

    public synchronized void increment(){
        value++;
    }

    public synchronized int get(){
        return value;
    }
}
