import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class WarehouseEgo {
    private Map<String, Product> map =  new HashMap<String, Product>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private class Product { int quantity = 0; }

    private Product get(String item) {
        Product p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String item, int quantity) {
        lock.lock();
        try {
            Product p = get(item);
            p.quantity += quantity;
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Egoista: 

    public void consumeEgo(Set<String> items) throws InterruptedException {
        lock.lock();
        try {
            for(String i : items) {
                Product p = get(i);
                while(p.quantity == 0) {
                    cond.await();
                }
                p.quantity--;
            }
        } finally {
            lock.unlock();
        } 
    }
}
