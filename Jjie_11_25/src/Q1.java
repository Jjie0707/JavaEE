import java.util.PriorityQueue;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),k=in.nextInt();
        PriorityQueue<Integer> heap=new PriorityQueue<>();
        for(int i=0;i<n;i++){
            heap.offer(in.nextInt());
        }
        //走到这里完成了所有口罩舒适度的统计
        int ret=0,sum=0;
        while(!heap.isEmpty()){
            int a=heap.poll();
            sum+=a;
            if(sum<=k) ret++;
            else break;
            heap.offer(2*a);
        }
        System.out.println(ret);
    }

}
