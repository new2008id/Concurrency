import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {

    private final Object monitor = new Object();
    private final Queue<Runnable> queue = new LinkedList<>();

    public void add(Runnable task) {
        synchronized (monitor) {
            queue.add(task);
            monitor.notify();
        }
    }

    public Runnable take() {
        synchronized (monitor) {
            try {
                while (queue.isEmpty()) {
                    monitor.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return queue.poll();
        }
    }
}
