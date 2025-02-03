import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        // interface, под который нужно подставить определенную реализацию
//        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // работает точно также, как newFixedThreadPool, но его пул состоит из одного потока, когда первая задача выполнена,
        // то берется следующая задача из очереди и т.д.
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // создаёт новые потоки по мере необходимости, когда ему подаётся задача, он смотрит, есть ли свободные потоки,
        // если есть, то отдаёт задачу ему, если нет, то создаёт новый поток.
        // Причем после завершения задачи, этот пул - не уменьшается.
        // Может привести к утечке ресурсов
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int counter = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Start - " + counter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Finish - " + counter);
                    countDownLatch.countDown();
                }
            });
        }
        executorService.shutdown(); // с этого момента в него уже нельзя передавать новые задачи
        try {
            countDownLatch.await(); // wait for all threads to finish
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("All threads are terminated");
    }
}
