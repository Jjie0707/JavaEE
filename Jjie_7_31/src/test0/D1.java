package test0;
//class Singleton{
//    private static Singleton instance;
//    private static Object lock=new Object();
//
//    //线程安全的懒汉式单例模式
//    public synchronized static Singleton getInstance(){
//        return instance==null ? instance=(new Singleton()) : instance;
//    }
//
//    private Singleton(){
//    }
//}
//
//public class D1 {
//    private static Singleton s1;
//    private static Singleton s2;
//
//    public static void main(String[] args) throws InterruptedException {
//        Thread t1=new Thread(()->{
//            s1=Singleton.getInstance();
//        });
//        Thread t2=new Thread(()->{
//            s2=Singleton.getInstance();
//        });
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        System.out.println(s1==s2);//true
//    }
//}
