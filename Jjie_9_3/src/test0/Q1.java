package test0;

public class Q1 {
    public int search(int[] nums, int target) {
        int left=0,right=nums.length-1;
        while(left<right){
            int mid=left+(right-left+1)/2;
            int cur=nums[mid];
            if(cur<nums[0]) right=mid-1;
            else left=mid;
        }//走到这里找到了旋转的终点()
        int ret1=search(nums,0,left,target);
        int ret2=search(nums,left+1,nums.length-1,target);
        if(ret1==-1 && ret2==-1) return -1;
        else if(ret1==-1) return ret2;
        else return ret1;

    }
    private int search(int[] nums,int start,int end,int target){
        int left=start,right=end;
        while(left<right){
            int mid=left+(right-left)/2;
            if(nums[mid]<target) left=mid+1;
            else right=mid;
        }
        if(nums[left]==target) return left;
        else return -1;
    }


    public int[] searchRange(int[] nums, int target) {
        if(nums.length==0) return new int[] {-1,-1};
        int l=-1,r=-1;
        int left=0,right=nums.length-1;
        while(left<right){
            int mid=left+(right-left)/2;
            int cur=nums[mid];
            if(cur<target) left=mid+1;
            else right=mid;
        }
        if(nums[left]==target) l=left;
        left=0;right=nums.length-1;
        while(left<right){
            int mid=left+(right-left+1)/2;
            int cur=nums[mid];
            if(cur>target) right=mid-1;
            else left=mid;
        }
        if(nums[left]==target) r=left;
        return new int[] {l,r};
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int len= matrix.length,wid=matrix[0].length;
        int[] nums=null;
        for(int i=0;i<len;i++){
            int left=matrix[i][0],right=matrix[i][wid-1];
            if(target>=left && target<=right) nums=matrix[i];
        }
        if(nums==null) return false;
        int left=0,right=wid-1;
        while(left<right){
            int mid=left+(right-left)/2;
            int cur=nums[mid];
            if(cur<target) left=mid+1;
            else right=mid;
        }
        if(nums[left]==target) return true;
        else return false;
    }
}
