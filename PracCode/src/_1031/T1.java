package _1031;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        List<Integer> l1=new ArrayList<>();
        l1.add(1);l1.add(2);l1.add(3);
        List<Integer> l2=new ArrayList<>();
        l2.add(0);l2.add(0);l2.add(4);
        List<Integer> l3=new ArrayList<>();
        l3.add(7);l3.add(6);l3.add(5);
        List<List<Integer>> list=new ArrayList<>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        System.out.println(cutOffTree(list));
    }
//    static class Pair implements Comparable<Pair>{
//        int val;
//        int[] index;
//        public Pair(int i, int[] ints) {
//            this.val=i;this.index=ints;
//        }
//        @Override
//        public int compareTo(Pair o) {
//            return this.val-o.val;
//        }
//    }
int[][] dirs={
        {1,0},{-1,0},{0,1},{0,-1}
};
    public int cutOffTree(List<List<Integer>> forest) {

        int len=forest.size(),width=forest.get(0).size();
        int[][] f=new int[len][width];
        ArrayList<int[]> list=new ArrayList<>();

        for(int i=0;i<len;i++){
            List<Integer> tem=forest.get(i);
            for(int j=0;j<width;j++){
                int num=tem.get(j);
                f[i][j]=num;
                if(num!=0 && num!=1) list.add(new int[]{i,j,num});
            }
        }
        list.sort((a,b)->a[2]-b[2]);
        int sr=0,sc=0;
        int ret=0;
        for(int[] tem:list){
            int r=tem[0],c=tem[1];
            int d=bfs(f,sr,sc,r,c);
            if(d==-1){
                return -1;
            }
            ret+=d;
            sr=r;sc=c;
        }
        return ret;
    }
    public int bfs(int[][] nums,int sr,int sc,int r,int c){
        if(sr==r && sc==c) return 0;
        int[][] pos=new int[nums.length][nums[0].length];
        LinkedList<int[]> q=new LinkedList<>();
        q.addLast(new int[]{sr, sc});pos[sr][sc]=1;
        int step=1;
        while(!q.isEmpty()){
            for(int size=q.size();size>0;size--){
                int[] tem=q.removeFirst();
                int cr=tem[0],cc=tem[1];
                for(int i=0;i<4;i++){
                    int tr=cr+dirs[i][0],tc=cc+dirs[i][1];
                    if(0<=tr && tr<nums.length && 0<=tc && tc<nums[0].length
                            && pos[tr][tc]==0 && nums[tr][tc]!=0){
                        q.addLast(new int[]{tr,tc});pos[tr][tc]=1;
                        if(tr==r && tc==c) return step;
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
