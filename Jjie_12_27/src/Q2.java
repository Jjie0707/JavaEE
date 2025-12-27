import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),x=in.nextInt();
        long ret=0;
        PriorityQueue<Integer> heap=new PriorityQueue<>((e1,e2)->e2-e1);
        if(x>n){
            for(int i=0;i<n;i++) ret+=in.nextInt();
        }else{
            for (int i = 0; i < n; i++) heap.offer(in.nextInt());
            ArrayList<Integer> list=new ArrayList<>();
            for(int i=0;i<x-1;i++) list.add(heap.poll());
            long max=heap.peek();
            ret+=max*2;
            for(int tem:list) ret+=(tem-max);
        }
        System.out.println(ret);
    }
}
