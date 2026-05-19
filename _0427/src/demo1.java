import java.util.ArrayList;
import java.util.List;

public class demo1 {
    static int[] count;
    static int[] index;
    static int[] temNums;
    static int[] temIndex;
    public List<Integer> countSmaller(int[] nums) {
        count=new int[nums.length];
        index=new int[nums.length];
        temNums= new int[nums.length];
        temIndex=new int[nums.length];
        for(int i=0;i<nums.length;i++) index[i]=i;

        List<Integer> ret=new ArrayList<>();
        mergeSort(nums,0,nums.length-1);
        for(int num:count) ret.add(num);
        return ret;
    }

    public void mergeSort(int[] nums, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left+1) / 2;
        mergeSort(nums, left, mid-1);
        mergeSort(nums, mid, right);
        merge(nums, left, right);
    }

    public void merge(int[] nums, int left, int right) {
        int mid = left + (right - left+1) / 2;
        int r1 = left, r2 = mid;
        int i=left;
        while (r1 < mid && r2 <= right) {
            if (nums[r1] > nums[r2]) {
                count[index[r1]]+=(right-r2+1);
                temNums[i]=nums[r1];
                temIndex[i]=index[r1];
                r1++;i++;
            } else {//nums[r1]<=nums[r2]
                temNums[i] = nums[r2];
                temIndex[i]=index[r2];
                r2++;i++;
            }
        }
        while (r1 < mid) {
            temNums[i] = nums[r1];
            temIndex[i]=index[r1];
            i++;r1++;
        }
        while (r2 <= right) {
            temNums[i] = nums[r2];
            temIndex[i]=index[r2];
            i++;r2++;
        }
        for(int j = left; j <=right ; j++) {
            nums[j] = temNums[j];
            index[j]=temIndex[j];
        }
    }
}
