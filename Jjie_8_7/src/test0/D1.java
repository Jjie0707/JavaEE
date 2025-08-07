package test0;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class D1 {
    public static void main(String[] args) {
        ExecutorService ex= Executors.newFixedThreadPool(4);
        for(int i=0;i<1000;i++){
            int id=i;
            ex.submit(()->{
                String name=Thread.currentThread().getName();
                System.out.println(name+","+id);
            });
        }
    }
}
