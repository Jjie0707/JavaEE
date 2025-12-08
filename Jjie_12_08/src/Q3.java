import java.util.Scanner;

public class Q3 {
    public static int[][] dir={{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),m=in.nextInt();
        int[][] map =new int[n][m];
        int[][] len=new int[n][m];
        int ans=Integer.MIN_VALUE;
        for(int i=0;i<n;i++){
            for(int j = 0; j< m; j++){
                map[i][j]=in.nextInt();
            }
        }
        for(int i=0;i<n;i++){
            for(int j = 0; j< m; j++){
                boolean[][] visit=new boolean[n][m];
                int x=i,y=j;
                visit[x][y]=true;
                int ret=1;
                boolean flag=true;
                while(flag){
                    int tem = Integer.MIN_VALUE, xt = -1, yt = -1;
                    for (int p = 0; p < dir.length; p++) {
                        int xi = x + dir[p][0], yi = y + dir[p][1];
                        if (xi < n && xi >= 0 && yi >= 0 && yi < m && !visit[xi][yi]) {
                            if (map[xi][yi] >= tem && map[xi][yi]<map[x][y]) {
                                tem = map[xi][yi];
                                xt = xi;
                                yt = yi;
                            }
                        }
                    }
                    if (xt < 0) {//无路可走的情况
                        flag = false;
                        len[i][j] = ret;
                        ans=Math.max(ans,ret);
                    } else {//还可以向下一个节点移动 看是否存了节点的链 直接拼接就可以
                        visit[xt][yt] = true;
                        if (len[xt][yt] == 0) {
                            ret++;
                            x = xt;
                            y = yt;
                        } else {
                            ret += len[xt][yt];
                            len[i][j] = ret;
                            ans=Math.max(ans,ret);
                            flag = false;
                        }
                    }
                }
            }
        }
        System.out.println(ans);
    }
}
