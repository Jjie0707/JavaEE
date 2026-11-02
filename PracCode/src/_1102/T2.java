package _1102;

import java.util.LinkedList;

public class T2 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    int[][] dist;
    public int[][] highestPeak(int[][] isWater) {
        int len = isWater.length,width=isWater[0].length;
        int[][] ret=new int[len][width];
        LinkedList<int[]> q=new LinkedList<>();
        dist=new int[len][width];
        for(int i=0;i<len;i++){
            for(int j=0;j<width;j++){
                if(dist[i][j]==0 && isWater[i][j]==1){
                    q.addLast(new int[]{i,j});
                    dist[i][j]=1;
                }
            }
        }
        int step=1;
        while(!q.isEmpty()){
            for(int size=q.size();size>0;size--){
                int[] tem=q.removeFirst();
                int r=tem[0],c=tem[1];
                for(int j=0;j<4;j++){
                    int tr=r+dirs[j][0],tc=c+dirs[j][1];
                    if(0<=tr && tr<len && 0<=tc && tc<width
                            && dist[tr][tc]==0){
                        ret[tr][tc]=step;
                        dist[tr][tc]=1;
                        q.addLast(new int[]{tr,tc});
                    }
                }
            }
            step++;
        }
        return ret;
    }
}
