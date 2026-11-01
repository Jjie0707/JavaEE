package _1101;

import java.util.LinkedList;

public class T2 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };

    public int[][] updateMatrix(int[][] mat) {
        int[][] ret=new int[mat.length][mat[0].length];
        int[][] pos=new int[mat.length][mat[0].length];
        LinkedList<int[]> q=new LinkedList<>();
        for(int  i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                if(mat[i][j]==0){
                    q.addLast(new int[]{i,j});
                    pos[i][j]=1;
                }
            }
        }
        int step=1;
        while(!q.isEmpty()){
            int size=q.size();
            for(int i=0;i<size;i++){
                int[] tem=q.removeFirst();
                int cr=tem[0],cc=tem[1];
                pos[cr][cc]=1;
                for(int j=0;j<4;j++){
                    int tr=cr+dirs[j][0],tc=cc+dirs[j][1];
                    if(0<=tr && tr<mat.length && 0<=tc && tc<mat[0].length
                        && pos[tr][tc]==0){
                        pos[tr][tc]=1;
                        ret[tr][tc]=step;
                        q.addLast(new int[]{tr,tc});
                    }
                }
            }
            step++;
        }
        return ret;
    }
}
