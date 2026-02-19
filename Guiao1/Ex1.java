package Guiao1;
import java.lang.Runnable;
import java.lang.Thread;

public class Ex1 {
    static class Printer implements Runnable {
        private final int id;
        private final int I;

        Printer(int id, int I) {
            this.id = id;
            this.I = I;
        }

        @Override 
        public void run() {
            for(int n = 1; n <= I; n++){
                System.out.printf("T%d: %d%n", id, n);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        int N = (args.length >= 1) ? Integer.parseInt(args[0]) : 3;
        int I = (args.length >= 2) ? Integer.parseInt(args[1]) : 5;

        Thread[] threads = new Thread[N];

        for(int t = 0; t < N; t++) {
            threads[t] = new Thread(new Printer(t,I));
            threads[t].start();
        }

        for(Thread th : threads){
            th.join(); // espera terminar
        }
        System.out.printf("Todas as threads terminaram!");
    }
}