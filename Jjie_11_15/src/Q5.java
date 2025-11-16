public class Q5 {
    //以D点作为分段点
//    public int findMin(int[] nums) {
//        int left=0,right=nums.length-1;
//        int n=nums.length;
//        while(left<right){
//            int mid=left+(right-left)/2;
//            if(nums[mid]>=nums[n-1]) left=mid+1;
//            else right=mid;
//        }
//        return nums[left];
//    }
    //以A点作为分段点
    public int findMin(int[] nums) {
        int left=0,right=nums.length-1;
        int n=nums.length;
        if(nums[left]<nums[right]) return nums[0];
        while(left<right){
            int mid=left+(right-left)/2;
            if(nums[mid]>=nums[0]) left=mid+1;
            else right=mid;
        }
        return nums[left];
    }
}

