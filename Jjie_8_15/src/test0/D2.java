package test0;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class MyThreadPool{
    //一个线程数目固定为4的自定义线程池
    private static BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
    public MyThreadPool(){
        Thread[] threads=new Thread[4];
        for(int i=0;i<4;i++){
            threads[i]=new Thread(()->{
                while(true){
                    try {
                        Runnable task=queue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        for(int i=0;i<threads.length;i++){
            threads[i].start();
        }
    }
    public void submit(Runnable task){
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class D2 {
    public static void main(String[] args) {
        MyThreadPool pool=new MyThreadPool();
        for(int i=0;i<150;i++){
            int id=i;
            pool.submit(()->{
                System.out.println(Thread.currentThread().getName()+"/"+id);
            });
        }
    }
}
