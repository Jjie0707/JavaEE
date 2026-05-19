package daily;

public class _0509 {
    public static void main(String[] args) {
        _0509 demo=new _0509();
        System.out.println(demo.reversePairs(new int[]{1,3,2,3,1}));
    }

    static int count;
    static int[] arr;
    public int reversePairs(int[] nums) {
        count = 0; arr = new int[nums.length];
        mergeSort(nums,0,nums.length-1);
        return count;
    }
    public void mergeSort(int[] nums,int left,int right){
        if(left>=right) return;
        int mid=left+(right-left+1)/2;
        mergeSort(nums,left,mid-1);
        mergeSort(nums,mid,right);
        merge(nums,left,right);
    }
    public void merge(int[] nums,int left,int right){
        int mid = left+(right-left+1)/2;
        int r1 = left,r2 = mid;
        int tem=left;

        int cur = mid;
        for (int i = left; i < mid; i++) {
            while (cur <= right && (long) nums[i] > 2L * nums[cur]) {
                cur++;
            }
            count += cur - mid;//没有移动的时候不算 所以不用+1
        }
        while(r1<mid && r2<=right){
            if(nums[r1]>nums[r2]) arr[tem++]=nums[r2++];
            else arr[tem++]=nums[r1++];
        }
        while(r1<mid) arr[tem++]=nums[r1++];
        while(r2<=right) arr[tem++]=nums[r2++];

        for(int i=left;i<=right;i++) nums[i]=arr[i];
    }
}
