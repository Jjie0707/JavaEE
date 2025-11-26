import java.util.ArrayList;
import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        long x=in.nextLong();
        ArrayList<Integer> list=new ArrayList<>();
        int sum=0;
        while(x>0){
            sum+=x%10;
            list.add((int)(x%10));
            x/=10;
        }
        if(sum%2!=0){
            System.out.println("No");
        }else {
            int target=sum/2;
            int n=list.size();
            boolean[][] dp=new boolean[n+1][target+1];
            dp[0][0]=true;
            for(int i=1;i<=n;i++){
                for(int j=1;j<=target;j++){
                    int cur=list.get(i-1);
                    if(j-cur>=0) {
                        dp[i][j] = dp[i - 1][j] || dp[i - 1][j - cur];
                    }else{
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
            if(dp[n][target]) System.out.println("Yes");
            else System.out.println("No");
        }
    }
}
