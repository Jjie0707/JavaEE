package test0;

import java.util.ArrayList;
import java.util.Arrays;

public class Q1 {
//    输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
//    输出：[[1,6],[8,10],[15,18]]
//    解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].

//    [[1,4],[4,5]]
    public static void main(String[] args) {
//        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][]  intervals={{1,4},{4,5}};
        int[][] ret=merge(intervals);
        for(int[] tem:ret){
            System.out.print(Arrays.toString(tem)+" ");
        }
    }
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(e1,e2)->e1[0]-e2[0]);
        //按照首个元素的大小排序

        ArrayList<int[]> list=new ArrayList<>();
        int start = intervals[0][0],end = intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0]>end){
                int[] tem={start,end};
                start=intervals[i][0];
                end=intervals[i][1];
                list.add(tem);
            }else{
                end=Math.max(end,intervals[i][1]);
            }
        }
        int[] tem={start,end};
        list.add(tem);
        int[][] ret=new int[list.size()][];
        int j=0;
        for(int[] array:list){
            ret[j++]=array;
        }
        return ret;
    }
}
