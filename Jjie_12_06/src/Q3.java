import java.util.Arrays;
import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),k=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        Arrays.sort(nums);
        int left=0,ret=0;
        for(int i=0;i<n;i++){
            while(nums[i]-nums[left]>k){
                left++;
            }
            ret=Math.max(ret,i-left+1);
        }
        System.out.println(ret);
    }
}
