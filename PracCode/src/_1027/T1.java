package _1027;

import java.util.LinkedList;

public class T1 {
    public static void main(String[] args) {
        int[][] image=new int[][]{{1,1,1},{1,1,0},{1,0,1}};
        floodFill(image,1,1,2);
    }
    static public int[][] dir=new int[][]{
            {-1,0},{1,0},{0,-1},{0,1}
    };
    static public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int width=image[0].length,len= image.length;
        //初始都是0,默认没有被访问过,访问过之后记为1
        int[][] pos=new int[len][width];
        LinkedList<int[]> q=new LinkedList<>();
        q.addLast(new int[]{sr,sc});int target=image[sr][sc];
        image[sr][sc]=color;
        while(!q.isEmpty()){
            int[] tem=q.removeFirst();
            int r=tem[0],c=tem[1];
            for(int i=0;i<4;i++){
                int tr=r+dir[i][0],tc=c+dir[i][1];
                if(tc<width &&tc>=0 && tr<len && tr>=0){
                    if(pos[tr][tc]==0) {
                        pos[tr][tc] = 1;
                        if (image[tr][tc] == target) {
                            q.addLast(new int[]{tr, tc});
                            image[tr][tc] = color;
                        }
                    }
                }
            }
        }
        return image;
    }
}
