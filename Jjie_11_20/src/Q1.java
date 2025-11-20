import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n];
        int sum=0;
        for(int i=0;i<n;i++) sum+=(nums[i]=in.nextInt());
        if(sum%2!=0) System.out.println(false);
        else{
            boolean[][] dp=new boolean[n+1][(sum/2)+1];
            //dp数组 dp[i][j]表示走到当前i位置是否能取到和j
            dp[0][0]=true;
            for(int i=1;i<=n;i++){
                for(int j=1;j<=sum/2;j++){
                    if(j - nums[i - 1]>=0) {
                        dp[i][j] = (dp[i - 1][j] || dp[i - 1][j - nums[i - 1]]);
                    }else{
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
            System.out.println(dp[n][sum/2]);
        }
    }
}
