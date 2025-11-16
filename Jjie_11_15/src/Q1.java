public class Q1 {
    public int searchInsert(int[] nums, int target) {
        int left=0,right=nums.length-1;
        //目的是把数组分割成两段，一段小于一段大于等于，
        // 我们只要找到那个大于等于的位置就是插入位置
        while(left<right){
            int mid=left+(right-left)/2;
            if(nums[mid]<target) left=mid+1;
            else right=mid;
        }
        //走到这里right所指定的数一定>=target
        if(nums[right]>=target) return right;
        else return right+1;
    }
}
