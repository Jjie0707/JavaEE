package test0;

public class Q2 {
//    nums = [3,4,-1,1]
    public static void main(String[] args) {
//        int[] nums={3,4,-1,1};
        int[] nums={-1,4,2,1,9,10};
        System.out.println(firstMissingPositive(nums));
    }
    public static int firstMissingPositive(int[] nums) {
        int length=nums.length;
        for(int i=0;i<length;i++){
            if(nums[i]>=1 && nums[i]<length){
                if(nums[nums[i]-1]!=nums[i]){
                    int tem=nums[nums[i]-1];
                    nums[nums[i]-1]=nums[i];
                    nums[i]=tem;
                }
            }
        }
        for(int i=0;i<length;i++){
            if(nums[i]!=i+1){
                return i+1;
            }
        }

        return length+1;
    }
}
