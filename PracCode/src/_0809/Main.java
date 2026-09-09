package _0809;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static HashMap<Integer, ArrayList<Integer>> m=new HashMap<>();

    public static void main(String[] args) {
        int[] nums={3,2,4};
        System.out.println(Arrays.toString(twoSum(nums,6)));
    }
    public static int[] twoSum(int[] nums, int target) {
        int[] ret=new int[2];
        for(int i=0;i<nums.length;i++){
            int tem=target-nums[i];
            if(!m.containsKey(tem)){//如果不包含
                ArrayList<Integer> list=m.getOrDefault(nums[i],new ArrayList<>());
                list.addLast(i);
                m.put(nums[i],list);
            }
            else{
                ret[0]=m.get(tem).getFirst();
                ret[1]=i;
                break;
            }
        }
        return ret;
    }
}
