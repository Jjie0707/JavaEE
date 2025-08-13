package test0;

public class Test {
    public static void main(String[] args) {
        int[] nums={10,5,2,6};
        int k=100;
        System.out.println(numSubarrayProductLessThanK(nums, k));
    }
    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        int ret=0;
        int left=0,right=0;

        long pre=1;
        for(;right<nums.length;right++){
            pre*=nums[right];
            while(pre>=k){
                int tem=nums[left];
                pre/=tem;
                left++;
            }
            ret+=(right-left+1);
        }
        return ret;
    }
}
