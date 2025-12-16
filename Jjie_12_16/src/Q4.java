import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        Arrays.sort(nums);
        int ret=Integer.MAX_VALUE;
        for(int i=0;i<n-1;i++){
            int num1=nums[i];
            int num2=nums[i+1];
            ret=Math.min(ret,num2-num1);
        }
        System.out.println(ret);
    }
}
