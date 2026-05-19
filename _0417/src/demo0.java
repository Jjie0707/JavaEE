import java.util.Random;

public class demo0 {
    static Random random=new Random();
    public int findKthLargest(int[] nums, int k) {
        int[] tem=pt(nums,0,nums.length-1);
        //直接分区去找第k大的元素所在的区间
        int a= tem[0]+1,b=tem[1]-tem[0]-1,c=nums.length-tem[1];

        if(c>=k) sort(nums, tem[1], nums.length - 1, k);
        else if(b+c>=k) ;
        else sort(nums, 0, tem[0], k - b - c);

        return nums[nums.length - k];
    }
    public void sort(int[] nums,int left,int right,int k){
        if(left>=right) return ;
        int[] tem=pt(nums,left,right);
        sort(nums,left,tem[0],k);sort(nums,tem[1],right,k);

    }
    public int[] pt(int[] nums,int left,int right){
        int r=random.nextInt(right-left+1)+left;
        left--;right++;
        int key=nums[r];
        for(int i=left+1;i<right;){
            int tem=nums[i];
            if(tem<key){
                left++;
                nums[i]=nums[left]; i++;
                nums[left]=tem;
            }else if(tem==key) {
                i++;
            }else{
                right--;
                nums[i]=nums[right];
                nums[right]=tem;
            }
        }
        return new int[] {left,right};
    }
}
