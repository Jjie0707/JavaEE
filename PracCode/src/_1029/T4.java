package _1029;

import java.util.LinkedList;

public class T4 {
    public static void main(String[] args) {
        char[][] maze={{'+', '+', '.', '+'}, {'.', '.', '.', '+'}, {'+', '+', '+', '.'}};
        System.out.println(nearestExit(maze,new int[]{1,2}));
    }
    static int[][] dirs={
            {1,0},{-1,0},{0,1},{0,-1}
    };
    //+ .
    static public int nearestExit(char[][] maze, int[] entrance) {
        int len=maze.length,width=maze[0].length;
        int[][] pos=new int[len][width];
        int ret=-1;
        LinkedList<int[]> q=new LinkedList<>();
        q.add(entrance);pos[entrance[0]][entrance[1]]=1;
        int step=0;
        while(!q.isEmpty()){
            int size=q.size();
            for(int i=0;i<size;i++){
                int[] tem=q.removeFirst();
                int r=tem[0],c=tem[1];
                for(int j=0;j<4;j++){
                    int tr=r+dirs[j][0],tc=c+dirs[j][1];
                    if((tr<0 || tr>=len || tc<0 || tc>=width)
                            && (!(r==entrance[0] && c==entrance[1])) ){
                        return step;
                    }else{
                        if(0<=tr && tr<len && 0<=tc && tc<width
                                && pos[tr][tc]==0 && maze[tr][tc]=='.'){
                            pos[tr][tc]=1;
                            q.addLast(new int[]{tr,tc});
                        }
                    }
                }
            }
            step++;
        }
        return ret;
    }
}
