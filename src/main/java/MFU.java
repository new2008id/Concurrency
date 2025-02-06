public class MFU {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private static final int DELAY = 1000;
    private static final String PRINT = "Print";
    private static final String SCAN = "Scan";

    public void print(int count) {
        generalMethod(count, PRINT, lock1);
    }

    public void scan(int count) {
        generalMethod(count, SCAN, lock2);
    }

    private void generalMethod(int count, String action, Object generalLock) {
        synchronized (generalLock) {
            try {
                for (int i = 1; i <= count; i++) {
                    Thread.sleep(DELAY);
                    System.out.println(action + ": " + i + " pages");
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
