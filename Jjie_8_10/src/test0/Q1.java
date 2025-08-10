package test0;

import java.util.Arrays;

public class Q1 {
    public static void main(String[] args) {
        int[] nums={1,2,3,4};
        System.out.println(Arrays.toString(productExceptSelf(nums)));
    }
    public static int[] productExceptSelf(int[] nums) {
        int[] pres = new int[nums.length];
        int[] sufs = new int[nums.length];
        pres[0]=1;
        sufs[sufs.length-1]=1;

        int tem = 1;
        for (int i = 1; i < pres.length; i++) {
            tem*=nums[i-1];
            pres[i]=tem;
        }
        tem=1;
        for(int i=sufs.length-2;i>=0;i--){
            tem*=nums[i+1];
            sufs[i]=tem;
        }
        int[] ret=new int[nums.length];
        for(int i=0;i<nums.length;i++){
            ret[i]=sufs[i]*pres[i];
        }
        return ret;
    }
}
