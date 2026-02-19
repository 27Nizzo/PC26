package Threads;
import java.util.Scanner;

import javax.swing.plaf.TreeUI;

import java.lang.Thread;
import java.lang.Runnable; 

public class Main {
      public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                  System.out.println("you have 10 seconds to answer");

                  MyRunnable myRunnable = new MyRunnable();
                  Thread thread = new Thread(myRunnable);
                  thread.setDaemon(true);
                  thread.start();

                  
                  System.out.print("Enter Your name: ");
                  String name = scanner.nextLine();
                  System.out.println("Hello " + name);
            }
      }

}

class MyRunnable implements Runnable{
    @Override
    public void run(){
        for(int i = 1; i <= 10; i++){
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                System.out.println("Thread was interrupted");
            }
            if(i == 10) {
                System.out.println("\n");
                System.out.println("Time's up");
                System.exit(0);
            }
        }
    }
}