package test0;

public class Q1 {
    public int searchInsert(int[] nums, int target) {
        int left=0,right=nums.length-1;
        while(left<right){
            int mid=left+(right-left)/2;
            int cur=nums[mid];
            if(cur<target) left=mid+1;
            else right=mid;
        }
        if(nums[left]==target) return left;
        else if(nums[left]>target) return left;
        else return left+1;

    }
    public int[] searchRange(int[] nums, int target) {
        int ret_l=-1,ret_r=-1;
        int left=0,right=nums.length-1;
        //寻找左端点
        while(left<right){
            int mid=left+(right-left)/2;
            int cur=nums[mid];
            if(cur==target) right=mid;
            else if(cur<target) left=mid+1;
            else right=mid-1;
        }//走到这里left和right一定相等
        if(nums[left]==target) ret_l=left;

        left=0;right=nums.length-1;
        //寻找右端点
        while(left<right){
            int mid=left+(right-left+1)/2;
            int cur=nums[mid];
            if(cur==target) right=mid;
            else if(cur<target) left=mid+1;
            else right=mid-1;
        }
        if(nums[left]==target) ret_r=left;
        return new int[] {ret_l,ret_r};
    }
}
