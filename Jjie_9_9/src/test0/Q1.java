package test0;

public class Q1 {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1=nums1.length,l2=nums2.length;
        //总是确保nums1是比较短的数组
        if(l1>l2){
            int[] nums=nums1;
            int tem=l1;
            nums1=nums2;
            nums2=nums;
            l1=l2;
            l2=tem;
        }
        int k=(l1+l2)/2;
        int cur1=0,cur2=0;

        //合并之后数组长度为奇数的情况
        if(k%2==1){
            while(k>1){
                int tem1 = Math.min(cur1 + k / 2 - 1, nums1.length - 1), tem2 = Math.min(cur2 + k / 2-1, nums2.length - 1);
                if (nums1[tem1]>=nums2[tem2]){
                    cur2=
                }
            }
        }
        //偶数的情况
        else{

        }
    }
}
