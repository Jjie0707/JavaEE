package jjie._0420.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class demo0 {
    static int count_;
    public static void main(String[] args) {
        int[] nums=new int[] {1,9,7,8,5};
        List<Integer> list=countSmaller(nums);
        System.out.println(Arrays.toString(nums));
        for(int num:list) System.out.print(num+" ");
    }

    static int[] count;
    static int[] index;
    public static List<Integer> countSmaller(int[] nums) {
        count=new int[nums.length];
        index=new int[nums.length];
        for(int i=0;i<nums.length;i++) index[i]=i;

        List<Integer> ret=new ArrayList<>();
        mergeSort(nums,0,nums.length-1);
        for(int num:count) ret.add(num);
        return ret;
    }

    public static void mergeSort(int[] nums, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left+1) / 2;
        mergeSort(nums, left, mid-1);
        mergeSort(nums, mid, right);
        merge(nums, left, right);
    }

    public static void merge(int[] nums, int left, int right) {
        int mid = left + (right - left+1) / 2;
        int r1 = left, r2 = mid;
        int[] tem = new int[nums.length];

        int i=left;
        while (r1 < mid && r2 <= right) {
            if (nums[r1] > nums[r2]) {
                count[index[r1]]+=(right-r2+1);
                int pos=index[i];
                index[i]=index[r1];index[r1]=pos;
                tem[i] = nums[r1];
                r1++;i++;
            } else {//nums[r1]<=nums[r2]\
                tem[i] = nums[r2];
                int pos=index[i];
                index[i]=index[r2];index[r2]=pos;
                r2++;i++;
            }
        }
        while (r1 < mid) {
            tem[i++] = nums[r1++];
        }
        while (r2 <= right) {
            tem[i++] = nums[r2++];
        }
        for(int j = left; j < nums.length; j++) nums[j] = tem[j];
    }



    public static int reversePairs(int[] record) {
        count_ =0;
        sortArray(record);
        return count_;
    }


    public static int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length-1);
        return nums;
    }
                                                             


    static Random random=new Random();

    public int[] smallestK(int[] arr, int k) {
        int[] tem=pt(arr,0,arr.length-1);
        //直接分区去找第k大的元素所在的区间
        int a= tem[0]+1,b=tem[1]-tem[0]-1,c=arr.length-tem[1];
        if(a>=k){
            sort(arr,0,tem[0]);
        }else if(a+b>=k){
            sort(arr,0,tem[0]);
        }else{
            sort(arr,0,arr.length-1);
        }
        return Arrays.copyOfRange(arr, 0, k);
    }


    public void sort(int[] nums,int left,int right){
        if(left>=right) return;
        int[] tem=pt(nums,left,right);
        sort(nums,left,tem[0]);sort(nums,tem[1],right);
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
