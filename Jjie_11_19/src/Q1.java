import java.util.Arrays;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt(),p=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        Arrays.sort(nums);
        int left=1,right=n;
        int ret=Integer.MIN_VALUE;
        while(left<right){
            int mid=left+(right-left+1)/2;
            boolean flag=false;
            for(int i=0;i+mid-1<n;i++){
                if(nums[i+mid-1]-p<=nums[i]+p) {
                    ret = Math.max(ret, mid);
                    flag=true;
                }
            }
            if(flag) left=mid;
            else right=mid-1;
        }
        System.out.println(ret);
    }
}
