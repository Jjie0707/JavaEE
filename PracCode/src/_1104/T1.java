package _1104;

import java.util.HashSet;
import java.util.LinkedList;

public class T1 {
    public static void main(String[] args) {
        System.out.println(canFinish(2,new int[][]{{1,0},{0,1}}));
    }
    static private HashSet<Integer> s;
    static private HashSet<Integer> l;
    static public boolean canFinish(int numCourses, int[][] prerequisites) {
        l=new HashSet<>();s=new HashSet<>();

        for(int i=0;i<numCourses;i++) s.add(i);

        //删除入度不为0的课程
        for(int i=0;i<prerequisites.length;i++){
            int tem=prerequisites[i][0];
            if(s.contains(tem)){
                l.add(tem);s.remove(tem);
            }
        }
        //再获取所有入度为0的

        int count=0;
        while(!s.isEmpty()) {
            for (int tem : s) {
                count++;
                for (int i = 0; i < prerequisites.length; i++) {
                    if(prerequisites[i][0]!=-1){
                        if(prerequisites[i][1]==tem){
                            prerequisites[i][0]=-1;
                            prerequisites[i][1]=-1;
                        }
                    }
                }
            }
            s.clear();
            HashSet<Integer> tem=new HashSet<>();
            for(int i=0;i<prerequisites.length;i++){
                if(prerequisites[i][0]!=-1){
                    if(l.contains(prerequisites[i][0])){
                        tem.add(prerequisites[i][0]);
                        l.remove(prerequisites[i][0]);
                    }
                }
            }
            s.addAll(l);l.clear();l.addAll(tem);
        }
        return count==numCourses;
    }
}
