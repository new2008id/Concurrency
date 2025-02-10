package carRace;

public class Main {
    public static void main(String[] args) {
        Race race = new Race();
        race.startRace();


        try {
            race.finishLatch.await();
            race.printResults();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
