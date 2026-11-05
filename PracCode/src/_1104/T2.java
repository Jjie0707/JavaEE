package _1104;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class T2 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] pre=new int[numCourses];
        ArrayList<ArrayList<Integer>> map=new ArrayList<>();
        for(int i=0;i<numCourses;i++) map.add(new ArrayList<>());
        ArrayDeque<Integer> q=new ArrayDeque<>();
        for(int[] tem:prerequisites){           // (要上的课,先行的课)
            pre[tem[0]]+=1;
            map.get(tem[1]).add(tem[0]);
        }
        for(int i=0;i<numCourses;i++){
            if(pre[i]==0)q.addLast(i);
        }
        int[] ret=new int[numCourses];int index=0;
        while(!q.isEmpty()){
            int cur=q.removeFirst();ret[index++]=cur;
            for(int next:map.get(cur)){
                pre[next]-=1;
                if(pre[next]==0)q.addLast(next);
            }
        }
        return index==numCourses ? ret : new int[]{};
    }
}
