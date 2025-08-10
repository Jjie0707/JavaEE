package test0;

public class Q2 {
    public static void main(String[] args) {
        int[] nums={3,4,-1,1};
        System.out.println(firstMissingPositive(nums));
    }
    public static int firstMissingPositive(int[] nums) {
        int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
        int pre=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                pre+=nums[i];
                min=Math.min(min,nums[i]);
                max=Math.max(max,nums[i]);
            }
        }
        int tem=(min+max)*(max-min+1)/2;
        int ret=tem-pre;

        return min>1 ? 1 : ret;
    }
}
