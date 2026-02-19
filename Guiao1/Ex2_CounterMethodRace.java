package Guiao1;

public class Ex2_CounterMethodRace {
    static class Counter {
        private int value = 0;
        
        public void increment(){
            value++;
        }

        public int get(){
            return value;
        }
    }

    static class Worker implements Runnable {
        private final Counter counter;
        private final int I;

        Worker(Counter counter, int I){
            this.counter = counter;
            this.I = I;
        }
        @Override
        public void run() {
            for(int k = 0; k < I; k++){
                counter.increment();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int N = (args.length >= 1) ? Integer.parseInt(args[0]) : 8;
        int I = (args.length >= 2) ? Integer.parseInt(args[1]) : 100000;
        
        Counter counter = new Counter();
        Thread[] threads = new Thread[N];

        for(int t = 0; t < N; t++) {
            threads[t] = new Thread(new Worker(counter, I));
            threads[t].start();
        }

        for(Thread th : threads) th.join();
        
        int expected = N * I;
        int actual = counter.get();
        
        System.out.printf("Expected=%d, Actual=%d%n", expected, actual);

    }
}
