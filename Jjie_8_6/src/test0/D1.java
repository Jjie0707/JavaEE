package test0;

class Singleton{
    private static Object lock=new Object();
    private volatile static Singleton instance ;

    public static Singleton getInstance(){
        if(instance==null){
            synchronized(lock){
                if(instance==null){
                    instance=new Singleton();
                }
            }
        }
        return instance;
    }
    private Singleton(){};
}
public class D1 {
//    编写代码, 实现线程安全版本的单例模式
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
