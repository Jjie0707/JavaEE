import java.util.Arrays;
import java.util.Scanner;

public class Q2 {
    public static int[] nums;
    public static int func1(int left,int right,int x,int target){
        int mid;
        while(left<right){
            mid=left+(right-left)/2;
            if(nums[mid]-target<x) left=mid+1;
            else right=mid;
        }
        return Math.min(left,nums.length-1);
    }
    public static int func2(int left,int right,int x,int target){
        int mid;
        while(left<right){
            mid=left+(right-left+1)/2;
            if(nums[mid]-target>x) right=mid-1;
            else left=mid;
        }
        return Math.min(left,nums.length-1);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),l=in.nextInt(),r=in.nextInt();
        long ret=0;
        nums=new int[n];
        for(int i=0;i<n;i++) nums[i] = in.nextInt();
        Arrays.sort(nums);
        for(int i=0;i<n;i++){
            int i1=func1(i+1,n-1,l,nums[i]);
            int i2=func2(i+1,n-1,r,nums[i]);
            if(i1==i2 && !(l<=nums[i2]-nums[i] && nums[i2]-nums[i]<=r))ret+=0;
            else ret+=(i2-i1+1);
        }
        System.out.println(ret);
    }
}
