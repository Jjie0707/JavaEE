package test0;

import java.util.ArrayList;
import java.util.List;

public class Q1 {
    private static int[][] dirs={{0,1},{1,0},{0,-1},{-1,0}};
    private static int MOD=Integer.MAX_VALUE;

    public static void main(String[] args) {
//        int[][] matrix={{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        int[][] matrix={{2, 3}};
        List<Integer> list=spiralOrder(matrix);
        for(int x:list) System.out.print(x+" ");
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ret=new ArrayList<>();
        int l=matrix.length,w=matrix[0].length;
        int len=0;
        int dir=0;

        int x=0,y=0;
        while(len<l*w){
            ret.add(matrix[y][x]);
            matrix[y][x]=MOD;
            len++;
            if(     (x==w-1 && y==0)
                    || (y==l-1 && x==w-1)
                    || (x==0 && y==l-1 && y!=0)
                    || ( (y+dirs[dir][0]>=0 && y+dirs[dir][0]<l) && (x+dirs[dir][1]>=0 && x+dirs[dir][1]<w) && (matrix[y+dirs[dir][0]][x+dirs[dir][1]]==MOD) )
            ){
                dir=(dir+1)%4;
            }
            x=x+dirs[dir][1];
            y=y+dirs[dir][0];
        }
        return ret;
    }
}
