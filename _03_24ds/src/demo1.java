public class demo1 {
    public static void main(String[] args) {
        System.out.println(takeAttendance(new int[] {0}) );
    }
    public static int takeAttendance(int[] nums) {
        int n=nums.length;
        int left=0,right=n-1;
        while(left<right){
            int mid=left+(right-left)/2;
            if(nums[mid]>mid){
                right=mid;
            }else{
                left=mid+1;
            }
        }
        return nums[left]<=left ? left+1: left;
    }

//    1.哈希表 O(n)
//    2.直接用index和nums[index]对比 O(n)
//    3.高斯求和 O(1)
//    4.异或运算 O(n)
//    5.二分查找  O(log n)
}
