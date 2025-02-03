import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger value1 = new AtomicInteger();

    public int getValue1() {
        return value1.intValue();
    }

    public void increment() {
        value1.getAndIncrement();
    }

    public void decrement() {
        value1.getAndDecrement();
    }
}
