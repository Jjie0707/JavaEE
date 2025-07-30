package test0;

public class D1 {
    private static  int count=0;

    public static void main(String[] args) throws InterruptedException {
        Object lock=new Object();

        Thread t1=new Thread(()->{
            synchronized(lock){
                for (int i = 0; i < 50000; i++) count++;
            }
        });
        Thread t2=new Thread(()->{
            synchronized(lock){
                for (int i = 0; i < 50000; i++) count++;
            }
        });
        t2.start();
        t1.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }

}
