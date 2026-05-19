import java.util.Scanner;
public class demo2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),q=in.nextInt();
        long[] dp=new long[n+1];
        for(int i=1;i<=n;i++){
            dp[i]=in.nextLong() + dp[i-1];
        }
        for(int i=0;i<q;i++){
            int l=in.nextInt(),r=in.nextInt();
            System.out.println(dp[r]-dp[l-1]);
        }
    }
}
