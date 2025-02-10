package carRace;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private final int carNumber;
    private final int preparationTime;
    private int roadTime1, tunnelTime, roadTime2;
    private int totalTime;

    private final Semaphore tunnelSemaphore;
    private final CyclicBarrier startBarrier;
    private final CountDownLatch finishLatch;
    private final Random random = new Random();


    public Car(int carNumber, int preparationTime, Semaphore tunnelSemaphore, CyclicBarrier startBarrier, CountDownLatch finishLatch) {
        this.carNumber = carNumber;
        this.preparationTime = preparationTime;
        this.tunnelSemaphore = tunnelSemaphore;
        this.startBarrier = startBarrier;
        this.finishLatch = finishLatch;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getCarNumber() {
        return carNumber;
    }

    @Override
    public void run() {

        try {
            tunnelTime = (int) (Math.random() * 1000 + 1000);
            roadTime2 = (int) (Math.random() * 1000 + 1000);

            System.out.println("Car #" + carNumber + " is preparing for the race");
            Thread.sleep(preparationTime);
            System.out.println("Car #" + carNumber + " is ready for the race");

            try {
                startBarrier.await();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }

            roadTime1 = random.nextInt(500) + 500;
            System.out.println("Car #" + carNumber + " drove along the first section of the road");
            Thread.sleep(roadTime1);

            System.out.println("Car #" + carNumber + " drove up to the tunnel");
            tunnelSemaphore.acquire();
            System.out.println("Car #" + carNumber + " drove into the tunnel");
            tunnelTime = random.nextInt(1000) + 1000;
            Thread.sleep(tunnelTime);
            System.out.println("Car #" + carNumber + " drove out of the tunnel");
            tunnelSemaphore.release();

            roadTime2 = random.nextInt(500) + 500;
            System.out.println("Car #" + carNumber + " drove along the second section of the road");
            Thread.sleep(roadTime2);

            totalTime = preparationTime + roadTime1 + tunnelTime + roadTime2;
            System.out.println("Car #" + carNumber + " finished the race in " + totalTime + " milliseconds");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finishLatch.countDown();
        }
    }
}
