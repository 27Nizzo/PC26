package Guiao2.ExclusaoMutua;
import java.util.Arrays;
// Exercicio 2)

public class Bank {
    private final int[] accounts;
    
    public Bank(int n) {
        this.accounts = new int[n];
    }

    private boolean valid(int id){
        return id >= 0 && id < accounts.length;
    }

    public synchronized int balanced(int id){
        if(!valid(id)) return 0;
        return accounts[id];
    }

    public synchronized boolean deposit(int id, int value) {
        if(!valid(id) || value < 0) return false;
        accounts[id] += value;
        return true;
    }

    public synchronized boolean withdraw(int id, int value){
        if(!valid(id) || value < 0) return false;
        if(accounts[id] < value) return false;
        accounts[id] -= value;
        return true;
    }

    // Exercicio 3) 
    public synchronized boolean transfer(int from, int to, int value) {
        if(value < 0 || !valid(from) || !valid(to) || from == to) return false;

        if(!withdraw(from, value)) return false;
        return deposit(to, value);
    }

    public synchronized int totalBalance() {
        int sum = 0;
        for(int v : accounts) sum += v;
        return sum;
    }
}
