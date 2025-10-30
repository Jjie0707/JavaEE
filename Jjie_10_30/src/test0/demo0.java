package test0;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class demo0 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),m=in.nextInt();
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<n;i++){
            int tem=in.nextInt();
            map.put(tem,map.getOrDefault(tem,0)+1);
        }
        //走到这里完成了所有数字的统计
        if(map.size()>m) System.out.println(-1);
        else{
            int max=0;
            PriorityQueue<Integer> heap=new PriorityQueue<>();
            for(int tem: map.values()) heap.offer(tem);
            int num1=n/m,num2=n%m;
            for(int i=0;i<num2 && !heap.isEmpty();i++){
                if(heap.peek()>num1+1){
                    int tem=heap.poll()-(num1+1);
                    heap.offer(tem);
                    if(num1+1>max) max=num1+1;
                }else if(heap.peek()==num1+1){
                    heap.poll();
                    if(num1+1>max) max=num1+1;
                }else{
                    int tem=heap.poll();
                    if(tem>max) max=tem;
                }
            }
            for(int i=num2;i<m && !heap.isEmpty();i++){
                if(heap.peek()>num1){
                    int tem=heap.poll()-num1;
                    heap.offer(tem);
                    if(num1>max) max=num1;
                }else if(heap.peek()==num1){
                    heap.poll();
                    if(num1>max) max=num1;
                }else{
                    int tem=heap.poll();
                    if(tem>max) max=tem;
                }
            }
            System.out.println(max);
        }
    }
}
