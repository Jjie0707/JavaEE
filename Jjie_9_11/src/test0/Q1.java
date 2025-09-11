package test0;


import java.util.PriorityQueue;

class TimerTask{
    public Runnable task;
    public long time;//毫秒级别的时间戳

    public TimerTask(Runnable task,long delay ){
        this.task=task;
        this.time=System.currentTimeMillis()+delay;//程序真正执行的时间
    }
    public long getTime(){
        return this.time;
    }
    public void run(){
        this.task.run();
    }
}

class MyTimer{
    public static Object lock=new Object();
    private PriorityQueue<TimerTask> heap =new PriorityQueue<>(
            (e1,e2)->(int)(e1.time-e2.time));

    public MyTimer(){
        Thread t=new Thread(()->{
            try {
                synchronized (lock){
                    while (true) {
                        TimerTask task = heap.peek();
                        while (task == null) {
                            lock.wait();
                            task = heap.peek();
                        }
                        if (System.currentTimeMillis() >= task.getTime()) {
                            task.run();
                            heap.poll();
                        } else {
                            lock.wait(task.getTime() - System.currentTimeMillis());
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }

    public void schedule(Runnable runnable,long delay){
        synchronized(lock){
            TimerTask task=new TimerTask(runnable,delay);
            heap.offer(task);
            lock.notifyAll();
        }
    }
}

public class Q1 {
    public static void main(String[] args) {
        MyTimer timer=new MyTimer();
        timer.schedule(()-> {
            System.out.println("爱你3000");
        },3000);
        timer.schedule(()-> {
            System.out.println("爱你2000");
        },2000);
        timer.schedule(()-> {
            System.out.println("爱你1000");
        },1000);
    }
}
