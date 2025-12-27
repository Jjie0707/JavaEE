import java.util.Arrays;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        long k=in.nextLong();

        long[] pres=new long[n+1];
        int[] nums=new int[n];

        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        Arrays.sort(nums);

        for(int i=0;i<n;i++) pres[i+1]=pres[i]+nums[i];

        int ret=0;
        int l=0;

        for(int r=0;r<n;r++){
            int mid=(l+r)/2;
            long cost=(pres[r+1]-pres[mid+1])-(pres[mid]-pres[l]);
            if((r-l+1)%2==0) cost-=nums[mid];
            while(cost>k){
                l++;
                mid=(l+r)/2;
                cost=(pres[r+1]-pres[mid+1])-(pres[mid]-pres[l]);
                if((r-l+1)%2==0) cost-=nums[mid];
            }//走出循环之后 cost一定≤k
            ret=Math.max(ret,r-l+1);
        }
        System.out.println(ret);
    }
}
