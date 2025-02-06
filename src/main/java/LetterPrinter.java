public class LetterPrinter implements Runnable {
    private final char letter;
    private final int count;
    private final Object lock;
    private static int nextThread = 1;
    private final int threadNumber;
    private int printedCount = 0;

    public LetterPrinter(char letter, int count, Object lock, int threadNumber) {
        this.letter = letter;
        this.count = count;
        this.lock = lock;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        while (printedCount < count) {
            synchronized (lock) {
                while (nextThread != threadNumber) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.print(letter);
                printedCount++;
                nextThread = (nextThread % 3) + 1;
                lock.notifyAll();
            }
        }
    }
}


//    @Override
//    public void run() {
//        while (threadNumber < count) {
//            synchronized (lock) {
//                while (nextThread != getThreadNumber()) {
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        return;
//                    }
//                }
//
//                System.out.print(letter);
//                threadNumber++;
//                nextThread = (nextThread % 3) + 1;
//                lock.notifyAll();
//            }
//        }
//    }

//private int getThreadNumber() {
//    if (letter == 'A') return 1;
//    if (letter == 'B') return 2;
//    return 3;
//}
