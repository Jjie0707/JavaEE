package _1101;

import java.util.LinkedList;

public class T1 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    public int[][] updateMatrix(int[][] mat) {
        int[][] ret=new int[mat.length][mat[0].length];
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                ret[i][j]=bfs(mat,i,j);
            }
        }
        return ret;
    }
    public int bfs(int[][] mat,int r,int c){
        int ret=-1;
        if(mat[r][c]==0) return 0;
        int len=mat.length,width=mat[0].length;
        int[][] pos=new int[len][width];
        LinkedList<int[]> q=new LinkedList<>();
        q.addLast(new int[]{r,c});pos[r][c]=1;
        int step=1;
        while(!q.isEmpty()){
            int size=q.size();
            for(;size>0;size--){
                int[] tem=q.removeFirst();
                int cr=tem[0],cc=tem[1];
                for(int i=0;i<4;i++){
                    int tr=cr+dirs[i][0],tc=cc+dirs[i][1];
                    if(0<=tr && tr<len && 0<=tc && tc<width
                        && pos[tr][tc]==0){
                        pos[tr][tc]=1;
                        q.addLast(new int[]{tr,tc});
                        if(mat[tr][tc]==0) return step;
                    }
                }
            }
            step++;
        }
        return ret;
    }
}
