package test0;

public class D2 {
    public static void main(String[] args) {
        Object l1=new Object();
        Object l2=new Object();
        Thread t1=new Thread(()->{
            synchronized(l1){
                System.out.println("get l1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized(l2){
                    System.out.println("get l2");
                }
            }
        });
        t1.start();
        Thread t2=new Thread(()->{
            synchronized(l2){
                System.out.println("get l2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized(l1){
                    System.out.println("get l1");
                }
            }
        });
        t2.start();

    }
}
