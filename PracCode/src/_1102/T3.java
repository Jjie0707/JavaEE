package _1102;

import java.util.LinkedList;

public class T3 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    int[][] dist;
    public int maxDistance(int[][] grid) {
        int ret=-1;
        //从陆地出发
        int len=grid.length,width=grid[0].length;
        dist=new int[len][width];
        LinkedList<int[]> q=new LinkedList<>();
        for(int i=0;i<len;i++){
            for(int j=0;j<width;j++){
                if(grid[i][j]==1){
                    dist[i][j]=1;q.addLast(new int[]{i,j});
                }
            }
        }
        int step=1;
        while(!q.isEmpty()){
            for(int size=q.size();size>0;size--){
                int[] tem=q.removeFirst();
                int r=tem[0],c=tem[1];
                for(int i=0;i<4;i++){
                    int tr=r+dirs[i][0],tc=c+dirs[i][1];
                    if(0<=tr && tr<len && 0<=tc && tc<width
                            && dist[tr][tc]==0){
                        dist[tr][tc]=1;
                        q.addLast(new int[]{tr,tc});
                        ret=Math.max(ret,step);
                    }
                }
            }
            step++;
        }
        return ret;
    }
}
