import java.util.Scanner;

public class demo0 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n =in.nextInt(),m=in.nextInt(),q=in.nextInt();
        long[][] dp=new long[n+1][m+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                dp[i][j]=in.nextInt()+dp[i][j-1]+dp[i-1][j]-dp[i-1][j-1];
            }
        }
        for(int i=0;i<q;i++){
            int x1=in.nextInt(),y1=in.nextInt(),x2=in.nextInt(),y2=in.nextInt();
            long ret=dp[x2][y2]-dp[x2][y1-1]-dp[x1-1][y2]+dp[x1-1][y1-1];
            System.out.println(ret);
        }
    }
}
