package carRace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Race {
    private final int NUMBER_OF_CARS = 10;
    private final int TUNNEL_CAPACITY = 3;

    private Semaphore tunnelSemaphore;
    private CyclicBarrier startBarrier;
    private List<Car> cars;
    public static CountDownLatch finishLatch;

    public Race(Semaphore tunnelSemaphore, CyclicBarrier startBarrier, List<Car> cars, CountDownLatch finishLatch) {
        tunnelSemaphore = new Semaphore(TUNNEL_CAPACITY, true);
        startBarrier = new CyclicBarrier(NUMBER_OF_CARS);
        finishLatch = new CountDownLatch(NUMBER_OF_CARS);
        cars = new ArrayList<>();
        prepareCars();
    }

    public Race() {
        tunnelSemaphore = new Semaphore(TUNNEL_CAPACITY, true);
        startBarrier = new CyclicBarrier(NUMBER_OF_CARS);
        finishLatch = new CountDownLatch(NUMBER_OF_CARS);
        cars = new ArrayList<>();
        prepareCars();
    }

    public void prepareCars() {

        cars = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_CARS; i++) {
            cars.add(new Car(i, 1000, tunnelSemaphore, startBarrier, finishLatch));
        }
    }

    public void startRace() {
        for (Car car : cars) {
            new Thread(car).start();
        }
    }

    // Метод для определения победителя после завершения гонки
    public Car getWinner() {
        Car winner = null;
        int minTime = Integer.MAX_VALUE;
        for (Car car : cars) {
            if (car.getTotalTime() < minTime) {
                minTime = car.getTotalTime();
                winner = car;
            }
        }
        return winner;
    }

    public void printResults() {
        // Метод для вывода результатов гонки.
        System.out.println("Result race:");
        System.out.println("----------------------------------");
        System.out.println("| Location  | Car Number |  Time  |");
        System.out.println("----------------------------------");

        Collections.sort(cars, (car1, car2) -> car1.getTotalTime() - car2.getTotalTime());

        int place = 1;
        for (Car car : cars) {
            System.out.printf("| %3d | %16d | %7d |\n", place, car.getCarNumber(), car.getTotalTime());
            place++;
        }

        System.out.println("----------------------------------");

        Car winner = getWinner();
        System.out.println("Winner: Car #" + winner.getCarNumber() + " is time " + winner.getTotalTime());
    }


}

