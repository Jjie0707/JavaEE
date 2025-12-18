import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        int left=0;
        int ret=1;
        for(int i=1;i<n;i++){
            int tem=nums[i];
            if(tem-nums[i-1]<9) ret=Math.max(i-left+1,ret);
            else left=i;
        }
        System.out.println(ret);
    }
}
