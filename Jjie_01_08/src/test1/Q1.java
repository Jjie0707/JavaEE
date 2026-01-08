package test1;

import java.util.ArrayList;

public class Q1 {
    static void main() {
        ArrayList<Integer> coins=new ArrayList<>();
        coins.add(5);
        coins.add(6);
        coins.add(4);
        coins.add(8);
        System.out.println(getCoins(coins));
    }
    public static int getCoins (ArrayList<Integer> coins) {
        // write code here
        int n=coins.size();
        int[][] dp=new int[n][n];
        for(int i=n-1;i>=0;i--){
            for(int j=0;j<n;j++){
                int max=0;
                for(int k=i;k<=j;k++){
                    int tem=0;
                    if(k!=i) tem=tem+dp[i][k-1];
                    tem=tem+coins.get(k)*(i-1>=0 ? coins.get(i-1) : 1)*(j+1<n ? coins.get(j+1) : 1);
                    if(k!=j) tem=tem+dp[k+1][j];
                    max=Math.max(tem,max);
                }
                dp[i][j]=max;
            }
        }
        return dp[0][n-1];
    }
}
