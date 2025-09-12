package test0;

public class Q2 {
    public int findPeakElement(int[] nums) {
        //由题目易知 nums[n-1]位置的元素一定是峰顶或者峰顶右侧的元素(递减)
        int left=0,right=nums.length-2;
        while(left<=right){
            int mid=left+(right-left)/2;
            if(nums[mid]<nums[mid+1]){
                left=mid+1;
            }else{
                right=mid-1;
            }
        }
        return nums[left];

    }
}
