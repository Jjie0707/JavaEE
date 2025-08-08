package test0;

public class D1 {
    public static void main(String[] args) {
//        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] nums={5,4,-1,7,8};
        System.out.println(maxSubArray(nums));
    }
    public static int maxSubArray(int[] nums) {
        int ret=Integer.MIN_VALUE;
        int length=nums.length;
        int[] pres=new int[length+1];
        pres[0]=0;
        int pre=0;
        for(int i=1;i<pres.length;i++){
            pre+=nums[i-1];
            pres[i]=pre;
        }
        //走到这里统计完了所有的前缀和
        int left=pres[0],right=pres[1];
        ret=Math.max(right-left,ret);
        for(int i=2;i<pres.length;i++){
            if(right<left) {
                left = right;
            }
            right=pres[i];
            ret=Math.max(right-left,ret);
        }
        return ret;
    }
}
