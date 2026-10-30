package _1029;

import java.util.ArrayList;
import java.util.LinkedList;

public class T3 {
    int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    //'X','O'
    public void solve(char[][] board) {
        int len=board.length,width=board[0].length;
        int[][] pos=new int[len][width];
        for(int i=0;i<len;i++){
            for(int j=0;j<width;j++){
                if(board[i][j]=='O' && pos[i][j]==0){
                    LinkedList<int[]> q=new LinkedList<>();
                    q.addLast(new int[]{i,j});pos[i][j]=1;
                    ArrayList<int[]> unset=new ArrayList<>();boolean flag=true;
                    while(!q.isEmpty()){
                        int[] tem=q.removeFirst();
                        unset.add(tem);
                        int r=tem[0],c=tem[1];
                        for(int k=0;k<4;k++){
                            int tr=r+dirs[k][0],tc=c+dirs[k][1];
                            if(tr<0 || tr>=len || tc<0 || tc>=width){
                                flag=false;
                            }else if(tr>=0 && tr<len && tc>=0 && tc<width
                                        && pos[tr][tc]==0 && board[tr][tc]=='O'){
                                pos[tr][tc]=1;
                                q.addLast(new int[]{tr,tc});
                            }
                        }
                    }
                    if(flag){
                        for(int[] tem:unset){
                            board[tem[0]][tem[1]]='X';
                        }
                    }
                }
            }
        }
    }
}
