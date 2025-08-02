package test0;
class Singleton{
    //禁止掉指令重排序
    private volatile static Singleton instance;
    private static Object lock=new Object();

    public static Singleton getInstance(){
        //合适的时间加锁
        if(instance==null){
            synchronized(lock){
                if (instance == null)  return (instance = new Singleton());
            }
        }
        return instance;
    }

    private Singleton(){}
}
public class D2 {
    private static Singleton s1;
    private static Singleton s2;

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            s1=Singleton.getInstance();
        });
        Thread t2=new Thread(()->{
            s2=Singleton.getInstance();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(s1==s2);
    }
}
