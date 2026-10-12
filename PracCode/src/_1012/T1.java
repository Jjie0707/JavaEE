package _1012;

import java.util.PriorityQueue;

public class T1 {
    public static void main(String[] args) {
        System.out.println(lastStoneWeight(new int[] {2,7,4,1,8,1}));
    }
    public static  int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> q=new PriorityQueue<>((e1,e2)->e2-e1);
        for(int tem:stones) q.offer((tem));
        while(q.size()>1){
            //num1>=num2
            int num1=q.poll(),num2=q.poll();
            if(num1>num2) q.offer(num1-num2);
        }
        return q.isEmpty() ? 0 : q.peek();
    }
}
