package test0;

import java.util.concurrent.*;

public class D1 {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                4,                     // corePoolSize
                4,                     // maximumPoolSize
                0L, TimeUnit.SECONDS,  // keepAliveTime
                new LinkedBlockingQueue<>(),   // workQueue
                Executors.defaultThreadFactory(),// threadFactory
                new ThreadPoolExecutor.AbortPolicy()); // RejectionHandler
        for(int i=0;i<100;i++) {
            int id=i;
            pool.submit(()->{
                System.out.println(Thread.currentThread().getName()+"/"+id);
            });
        }
    }
//    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(3);
//        for (int i = 0; i < 100; i++) {
//            int id = i;
//            pool.submit(() -> {
//                System.out.println(Thread.currentThread().getName() + "/" + id);
//            });
//        }
//    }
}
