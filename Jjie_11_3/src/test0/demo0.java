package test0;

import java.util.ArrayList;
import java.util.HashMap;

public class demo0 {
    public static void main(String[] args) {
        ArrayList<Integer> a=new ArrayList<>();
        int[] nums={2,9,2,4,2,1};
        for(int tem :nums) a.add(tem);
        System.out.println(boredom(a));
    }
    public static int boredom (ArrayList<Integer> a) {
        // write code here

        HashMap<Integer, Integer> m = new HashMap<>();
        for (int tem : a) {
            m.put(tem, m.getOrDefault(tem, 0) + tem);
        }

        int[] nums1 = new int[10001];
        int[] nums2 = new int[10001];
        nums1[0] = 0;
        nums2[0] = 0;

        for (int i = 1; i <= 10000; i++) {
            if(i-2>=0){
                nums1[i]=m.getOrDefault(i,0)+Math.max(nums2[i-1],nums1[i-2]);
                nums2[i]=Math.max(nums1[i-1],nums2[i-1]);
            }else{
                nums1[i]=m.getOrDefault(i,0)+nums2[i-1];
                nums2[i]=Math.max(nums1[i-1],nums2[i-1]);
            }
        }

        return Math.max(nums1[10000], nums2[10000]);
    }
}
