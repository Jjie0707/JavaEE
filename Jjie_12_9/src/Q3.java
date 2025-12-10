import java.util.Scanner;

public class Q3 {



    static int[][] nums,len;//nums存矩阵 len做记忆化搜索
    static boolean[][] visit;
    static int n,m;


    static int[][] dir={{-1,0},{1,0},{0,-1},{0,1}};
    static int ret=0;

    static int dfs(int x,int y){
        int max=0;
        visit[x][y]=true;
        for(int i=0;i<dir.length;i++){
            int xi=x+dir[i][0],yi=y+dir[i][1];
            if(0<=xi && xi<n && 0<=yi && yi<m
                    && nums[xi][yi]<nums[x][y]
                    && !visit[xi][yi]){
                if(len[xi][yi]==0){
                    max=Math.max(max,dfs(xi,yi));
                }else{
                    max=Math.max(max,len[xi][yi]);
                }
            }
        }
        visit[x][y]=false;
        ret=Math.max(ret,len[x][y]=max+1);
        return max+1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        n=in.nextInt();m=in.nextInt();
        visit=new boolean[n][m];nums=new int[n][m];len=new int[n][m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                nums[i][j]=in.nextInt();
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++) {
                dfs(i, j);
            }
        }
        System.out.println(ret);
    }
}
