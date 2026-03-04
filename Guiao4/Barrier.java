package Guiao4;

public class Barrier {

    /*
1) Escreva uma abstracção para permitir que Nthreads se sincronizem:

    class Barrier {
Barrier (int N) { ... }
void await() throws InterruptedException { ... }
}

A operação await deverá bloquear até que as Nthreads o tenham invocado; nesse momento o
método deverá retornar em cada thread. Escreva duas versões desta abstracção:
    a. Suponha que cada thread apenas vai invocar await uma vez sobre o objecto.
    b. Permita que a operação possa ser usada várias vezes por cada thread (barreira reutilizável),
de modo a suportar a sincronização no fim de cada uma de várias fases de computação.
1
    */
    private final int n;
    private int count = 0;
    private int generation = 0;
    
    Barrier(int n) {
        this.n = n;
    }

    public synchronized void await() throws InterruptedException {
        count++; 
        if(count < n) {
            wait();
        } else {
            notifyAll();
        }
    }

    public synchronized void await2() throws InterruptedException {
        int myGeneration = generation;
        count ++;
        if(count == n) {
            generation++;
            count = 0;
            notifyAll();
        } else {
            while(myGeneration == generation) {
                wait();
            }
        }
    }
    
}
