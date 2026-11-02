package _1102;

import java.util.LinkedList;

public class T1 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    int[][] dist;
    public int numEnclaves(int[][] grid) {
        int len=grid.length,width=grid[0].length;
        dist=new int[len][width];
        LinkedList<int[]> q=new LinkedList<>();
        int ret=0;
        for(int i=0;i<len;i++){
            for(int j=0;j<width;j++){
                if(dist[i][j]==0 && grid[i][j]==1){
                    ret=ret+bfs(grid,i,j);
                }
            }
        }
        return ret;
    }
    public int bfs(int[][] grid,int cr,int cc){
        int ret=1;
        boolean flag=true;
        LinkedList<int[]> q=new LinkedList<>();
        q.addLast(new int[]{cr,cc});dist[cr][cc]=1;
        while(!q.isEmpty()){
            for(int size=q.size();size>0;size--){
                int[] tem=q.removeFirst();
                int r=tem[0],c=tem[1];
                for(int j=0;j<4;j++){
                    int tr=r+dirs[j][0],tc=c+dirs[j][1];
                    if(tr<0 || tr>=grid.length || tc<0 || tc>=grid[0].length){
                        flag=false;
                    }
                    if(0<=tr && tr< grid.length && 0<=tc && tc<grid[0].length
                        && dist[tr][tc]==0 && grid[tr][tc]==1){
                        q.addLast(new int[]{tr,tc});
                        dist[tr][tc]=1;
                        ret++;
                    }
                }
            }
        }
        return flag ? ret : 0;
    }
}
