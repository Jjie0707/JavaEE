package test0;

import java.util.Arrays;

public class Q2 {
//    输入: nums = [1,2,3,4,5,6,7], k = 3
//    输出: [5,6,7,1,2,3,4]
//    解释:
//    向右轮转 1 步: [7,1,2,3,4,5,6]
//    向右轮转 2 步: [6,7,1,2,3,4,5]
//    向右轮转 3 步: [5,6,7,1,2,3,4]
    public static void main(String[] args) {
        int[] nums={1,2,3,4,5,6,7};
        int k=3;
        rotate(nums,k);
        System.out.println(Arrays.toString(nums));
    }
    public static void rotate(int[] nums, int k) {
        int left=0,right=nums.length-k-1;
        while(left<right){
            int tem=nums[left];
            nums[left]=nums[right];
            nums[right]=tem;
           left++;right--;
       }
        left=nums.length-k;right=nums.length-1;
        while(left<right){
            int tem=nums[left];
            nums[left]=nums[right];
            nums[right]=tem;
            left++;right--;
        }
        left=0;right=nums.length-1;
        while(left<right){
            int tem=nums[left];
            nums[left]=nums[right];
            nums[right]=tem;
            left++;right--;
        }
    }
}
