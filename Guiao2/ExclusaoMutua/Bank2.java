package Guiao2.ExclusaoMutua;
/*
Class que melhora a eficiencia: exclusao mutua por conta + evita deadloack
*/
public class Bank2 {
    private static class Account {
        int balance;
        final Object lock = new Object();
    }

    private final Account[] accounts;

    public Bank2(int n) {
        accounts = new Account[n];
        for (int i = 0; i < n; i++) accounts[i] = new Account();
    }

    private boolean valid(int id) {
        return id >= 0 && id < accounts.length;
    }

    public int balance(int id) {
        if (!valid(id)) return 0;
        Account a = accounts[id];
        synchronized (a.lock) {
            return a.balance;
        }
    }

    public boolean deposit(int id, int value) {
        if (!valid(id) || value < 0) return false;
        Account a = accounts[id];
        synchronized (a.lock) {
            a.balance += value;
            return true;
        }
    }

    public boolean withdraw(int id, int value) {
        if (!valid(id) || value < 0) return false;
        Account a = accounts[id];
        synchronized (a.lock) {
            if (a.balance < value) return false;
            a.balance -= value;
            return true;
        }
    }
    
    public boolean transfer(int from, int to, int value){
        if(value < 0 || !valid(from) || !valid(to) || from == to) {
            return false;
        }
        int first = Math.min(from, to);
        int second = Math.max(from, to);

        Account a1 = accounts[first];
        Account a2 = accounts[second];

        synchronized(a1.lock) {
            synchronized(a2.lock) {
                Account src = accounts[from];
                Account dst = accounts[to];

                if(src.balance < value) return false;
                src.balance -= value;
                dst.balance += value;
                return true;
            }
        }
    }

    public int totalBalance() {
            for(int i = 0; i < accounts.length; i++) {
                synchronized(accounts[i].lock) {
                    // está vazio porque estamos a adquirir locks em sequencia
                }
            }
            return totalBalanceWithAllLocks();
        }

        private int totalBalanceWithAllLocks(){
            Object[] locks = new Object[accounts.length];
            for(int i = 0; i < accounts.length; i++) locks[i] = accounts[i].lock;
            return lockAllAndSum(0);
        }

        private int lockAllAndSum(int idx){
            if(idx == accounts.length) {
                int sum = 0;
                for(Account a : accounts) sum+=a.balance;
                return sum;
            }
            synchronized(accounts[idx].lock) {
                return lockAllAndSum(idx + 1);
            }
        }
}