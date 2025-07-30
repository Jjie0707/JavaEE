package test0;

public class D4 {
//    public static void main(String[] args) {
//        Object lock=new Object();
//        Thread a=new Thread(()->{
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            synchronized(lock){
//                System.out.print("a");
//            }
//        });
//
//        Thread b=new Thread(()->{
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            synchronized(lock){
//                System.out.print("b");
//            }
//        });
//
//        Thread c=new Thread(()->{
//            synchronized(lock){
//                System.out.print("c");
//            }
//        });
//        a.start();
//        b.start();
//        c.start();
//    }
    private static int count=2;
    public static void main(String[] args) {
        Object lock=new Object();
        Thread a=new Thread(()->{
            synchronized(lock){
                while(count!=0){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("a");
            }
        });
        Thread b=new Thread(()->{
            synchronized(lock){
                while(count!=1){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("b");
                count=0;
                lock.notifyAll();
            }
        });
        Thread c=new Thread(()->{
            synchronized(lock){
                while(count!=2){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("c");
                count=1;
                lock.notifyAll();
            }
        });
        a.start();
        b.start();
        c.start();
    }
}
