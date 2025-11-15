package test0;

public class Q1 {
    public int searchInsert(int[] nums, int target) {
        int left=0,right=nums.length-1;
        while(left<right){
            int mid=left+(right-left+1)/2;
            if(nums[mid]>=target) right=mid-1;
            else left=mid;
        }

        if(nums[left]>=target) return Math.max(0,left-1);
        else return left+1;

    }
}
