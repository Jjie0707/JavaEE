import java.util.Scanner;

public class Q2 {

    public static int[] nums;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),aim=in.nextInt();
        nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        int[][] dp=new int[n+1][aim+1];
        dp[0][0]=0;
        for(int i=1;i<=aim;i++){
            dp[0][i]=-1;//
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=aim;j++){
                int cur=nums[i-1];
                if(j-cur>=0){
                    if(dp[i-1][j]==-1 && dp[i][j-cur]==-1) dp[i][j]=-1;
                    else if(dp[i-1][j]==-1) dp[i][j]=dp[i][j-cur]+1;
                    else if(dp[i][j-cur]==-1) dp[i][j]=dp[i-1][j];
                    else dp[i][j]=Math.min(dp[i][j]=dp[i][j-cur]+1,dp[i][j]=dp[i-1][j]);
                }else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        System.out.println(dp[n][aim]);
    }
}
