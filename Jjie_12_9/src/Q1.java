import java.util.LinkedList;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        //走到这里完成了美味度的统计
        int q=in.nextInt();
        LinkedList<Integer> queue=new LinkedList<>();
        queue.addLast(nums[0]);
        int[] pre=new int[n+1];
        for(int i=1;i<n;i++){
            if(queue.size()<2){//始终保证大的在前面
                if(nums[i]<=queue.getFirst()) queue.addLast(nums[i]);
                else queue.addFirst(nums[i]);
            }else{
                if(nums[i]>=queue.getFirst()){
                    queue.removeLast();
                    queue.addFirst(nums[i]);
                }else if(nums[i]>=queue.getLast()){
                    queue.removeLast();
                    queue.addLast(nums[i]);
                }
            }
            pre[i+1]=queue.getLast();
        }
        for(int i=0;i<q;i++){
            System.out.println(pre[in.nextInt()]);
        }
    }
}
