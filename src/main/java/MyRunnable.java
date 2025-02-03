
// второй способ реализации многопоточности, реализовать interface Runnable
public class MyRunnable implements Runnable {
    // код, который пишется в другом потоке, пишется в методе run()
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.print(i);
        }
    }
}

//// первый способ реализации многопоточности, наследоваться от класса Thread
//public class MyThread extends Thread {
//    // код, который пишется в другом потоке, пишется в методе run()
//    @Override
//    public void run() {
//        for (int i = 0; i < 1000; i++) {
//            System.out.print(i);
//        }
//    }
//}
