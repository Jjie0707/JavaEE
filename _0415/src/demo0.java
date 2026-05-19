import java.util.Arrays;
import java.util.Random;

public class demo0 {
    public int[] sortArray(int[] nums) {
        _sortArray(nums, -1, nums.length);
        return nums;
    }

    public void _sortArray(int[] nums, int left, int right) {
        if (right - left + 1 <= 3) return;
        int[] b = pt(nums, left, right); // b[0]=lt, b[1]=gt
        _sortArray(nums, left, b[0] +1); // < key
        _sortArray(nums, b[1] - 1, right); // > key
    }

    public int[] pt(int[] nums, int left, int right) {
        Random random = new Random();
        int r = random.nextInt((right - 1) - (left + 1) + 1) + left + 1;
        int key = nums[r];

        int lt = left;      // (left, lt] < key
        int i = left + 1;   // 扫描指针
        int gt = right;     // [gt, right) > key

        while (i < gt) {
            int tem = nums[i];
            if (tem < key) {
                nums[i++] = nums[lt + 1];
                nums[++lt] = tem;
            } else if (tem == key) {
                i++;
            } else {
                nums[i] = nums[--gt];
                nums[gt] = tem;
            }
        }
        return new int[]{lt, gt};
    }


    public void sortColors(int[] nums) {
        int left=-1,right=nums.length;
        for(int i=0;i<right;){
            int tem=nums[i];
            if(tem==0){
                nums[i++]=nums[left+1];
                nums[++left]=tem;
            }else if(tem==1){
                i++;
            }else{
                nums[i]=nums[--right];
                nums[right]=tem;
            }
        }
    }
}
