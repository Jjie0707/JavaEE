package test0;

public class Q1 {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
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
            ret=Math.max(ret,pre);
        }
        //走到这里统计完了所有的前缀和,并且将ret更新为了最大的
        int left=0,right=pres.length-1;
        while(left<right){
            int tem=pres[right]-pres[left];
            ret=Math.max(ret,tem);
            if()
        }
        return ret;
    }
}
