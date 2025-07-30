package test0;

public class D3 {
    private static int count=0;
    private static int times=10;


    public static void main(String[] args) {
        Object lock=new Object();
        Thread A=new Thread(()->{
           for(int i=0;i<times;i++){
               synchronized(lock){
                   while(count%3!=0){
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   System.out.print("A");
                   lock.notifyAll();
                   count=1;
               }
           }
        });
        Thread B=new Thread(()->{
            for(int i=0;i<times;i++){
                synchronized(lock){
                    while(count%3!=1){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("B");
                    lock.notifyAll();;
                    count=2;
                }
            }
        });
        Thread C=new Thread(()->{
            for(int i=0;i<times;i++){
                synchronized(lock){
                    while(count%3!=2){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("C");
                    lock.notifyAll();
                    count=0;
                }
            }
        });
        A.start();
        B.start();
        C.start();
    }
//    public static void main(String[] args) throws InterruptedException {
//        Object lock=new Object();
//        Thread A=new Thread(()->{
//            for(int i=0;i<10;i++){
//                synchronized(lock){
//                    System.out.print("A");
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });
//
//        Thread B=new Thread(()->{
//            for(int i=0;i<10;i++){
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                synchronized(lock){
//                    System.out.print("B");
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });
//
//        Thread C=new Thread(()->{
//            for(int i=0;i<10;i++){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                synchronized(lock){
//                    System.out.print("C");
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });
//        A.start();
//        B.start();
//        C.start();
//        for(int i=0;i<10;i++){
//            Thread.sleep(1500);
//            synchronized(lock){
//                System.out.println();
//                lock.notifyAll();
//            }
//        }
//    }
//
}
