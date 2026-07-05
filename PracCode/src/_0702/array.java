package _0702;

import java.util.Arrays;

public class array {
    public static void main(String[] args) {
        int[] nums1={2, 5, 1, 9, 11};
        System.out.println(Arrays.toString(nums1));
        int[] nums2=new int[5];
        for(int i=0;i<nums1.length;i++) nums2[i]=nums1[i];
        for(int tem:nums2){
            tem=tem*2;
        }
        System.out.println(Arrays.toString(nums2));

    }
}
