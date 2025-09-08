package test0;

import java.util.ArrayList;

public class Q1 {
    public static void main(String[] args) {
        int[] nums1={1,2},nums2={3,4};
        System.out.println(findMedianSortedArrays(nums1,nums2));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1=nums1.length,l2=nums2.length;
        int k=(l1+l2)/2;
        int cur1=0,cur2=0;

        //数组长度为奇数的情况
        if(k%2==1){
            while(k>1){
                int tem1=Math.min(cur1+k/2,l1-1),tem2=Math.min(cur2+k/2,l2-1);
                if(nums1[tem1]<=nums2[tem2]) cur1=tem1;
                else cur2=tem2;
                k/=2;
                if(cur1>=l1 || cur2>=l2) break;
            }
            //走到这里说明一定可以取到答案
            if(cur1>=l1) return nums2[cur2+k];
            else if(cur2>=l2) return 
        }else{//o偶数的情况

        }
    }

    //暴力求法 直接合并数组
    public static double findMedianSortedArrays_(int[] nums1, int[] nums2) {
        int length1=nums1.length, length2=nums2.length,  length=length1+length2;

        int cur1=0,cur2=0;

        ArrayList<Integer> list=new ArrayList<>();

        while(cur1<length1 && cur2<length2){
            if(nums1[cur1]<=nums2[cur2])  list.add(nums1[cur1++]);
            else list.add(nums2[cur2++]);
        }
        while(cur1<length1){
            list.add(nums1[cur1++]);
        }
        while(cur2<length2){
            list.add(nums2[cur2++]);
        }
        //走到这里合并完了两个数组
        if((length1+length2)%2==1) return list.get(length/2);
        else return (list.get(length/2)+list.get(length/2-1))/2.0;
    }


    public static int findMin(int[] nums) {
        int left=0,right=nums.length-1;
        //假设把数组二分，左边一部分大于等于左边界，右边部分小于左边界
        while(left<right){
            int mid=left+(right-left)/2;
            //向左逼近
            int cur=nums[mid];
            if(cur>=nums[0]) left=mid+1;
            else right=mid;
        }
        if(nums[nums.length-1]>nums[0]) return nums[0];
        return nums[left];
    }
//       11,13,15,17
//        0  1  2  3
}
