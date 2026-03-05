import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class WarehouseCoop {
    private Map<String, Product> map =  new HashMap<String, Product>();
    private ReentrantLock lock = new ReentrantLock();
    

    private class Product {
        int quantity = 0;
        private Condition cond = lock.newCondition();
     }

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
            p.cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Cooperativo:
    public void consumeCoop(Set<String> items) throws InterruptedException {
        lock.lock();
        try {
            Product[] prods = new Product[items.size()];
            int i = 0;
            for(String it : items)
                prods[i++] = get(it);

            for(;;) {
                Product p = available(prods);
                if(p == null)
                    break;
                p.cond.await();
            }

            for(i = 0; i < prods.length; ) {
                if(prods[i].quantity == 0) {
                    prods[i].cond.await();
                    i = 0;
                } else {
                    i++;
                }

            }

            for(Product p : prods)
                p.quantity--;
         } finally {
            lock.unlock();
         }
    }

    private Product available(Product[] prods) {
        for(Product p : prods) 
            if(p.quantity == 0)
                return p;
        return null;
    }
}
