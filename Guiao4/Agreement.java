package Guiao4;

public class Agreement {
    private final int n;
    private int count = 0;
    private int max = Integer.MAX_VALUE;
    private int generation = 0;

    public Agreement(int n) {
        this.n = n;
    }

    public synchronized int propose(int choice) throws InterruptedException {
        int myGeneration = generation;
        if(choice > max)
            max = choice;
        count++;


        if(count == n) {
            int result = max;
            generation++;
            count = 0;
            max = Integer.MAX_VALUE;

            notifyAll();
            return result;
        } else {
            while(myGeneration == generation) {
                wait();
            }
            return max;
        }
    }
}
