import java.util.Scanner;

public class Q1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] dp=new int[n+1];
        dp[0]=0;   //dp[i]表示，能凑成当前i所需要的最少的完全平方数的个数
        for(int i=1;i<dp.length;i++) dp[i]=Integer.MAX_VALUE;
        for(int i=1;i<=n;i++){
            for(int j=1;j*j<=n;j++){
                if(i-j*j>=0) dp[i]=Math.min(dp[i],dp[i-j*j]+1);
            }
        }
        System.out.println(dp[n]);
    }
}
