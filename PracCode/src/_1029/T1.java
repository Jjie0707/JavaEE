package _1029;

import java.util.LinkedList;

public class T1 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    public int numIslands(char[][] grid) {
        int ret=0;
        int len=grid.length,width=grid[0].length;
        int[][] pos=new int[len][width];
        for(int i=0;i<len;i++){
            for(int j=0;j<width;j++){
                if(pos[i][j]==0 && grid[i][j]=='1'){
                    LinkedList<int[]> q=new LinkedList<>();
                    q.addLast(new int[]{i,j});pos[i][j]=1;
                    ret++;
                    while(!q.isEmpty()){
                        int[] tem=q.removeFirst();
                        int r=tem[0],c=tem[1];
                        for(int k=0;k<4;k++){
                            int tr=r+dirs[k][0],tc=c+dirs[k][1];
                            if(0<=tr && tr<len && 0<=tc && tc<width
                                    && pos[tr][tc]==0 && grid[tr][tc]=='1'){
                                pos[tr][tc]=1;
                                q.addLast(new int[]{tr,tc});
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
}
