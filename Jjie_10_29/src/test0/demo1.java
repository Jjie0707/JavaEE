package test0;

import java.util.Arrays;
import java.util.Scanner;

public class demo1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n*3];
        for(int i=0;i<nums.length;i++) nums[i]=in.nextInt();
        int left=0,right=nums.length-1;
        Arrays.sort(nums);
        long ret=0;
        while(right-left+1>=3){
            ret+=nums[right-1];
            left++;
            right-=2;
        }
        System.out.println(ret);
    }
}
