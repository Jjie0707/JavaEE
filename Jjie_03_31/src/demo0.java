import java.util.HashMap;

public class demo0 {
    public static void main(String[] args) {

    }

    public int[][] matrixBlockSum(int[][] mat, int k) {
        int m=mat.length,n=mat[0].length;
        int[][] dp=new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                dp[i][j]=dp[i][j-1]+dp[i-1][j]-dp[i-1][j-1]+dp[i-1][j-1];
            }
        }//走到这里统计完了前缀和
        int[][] ret=new int[m][n];
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                int x1=Math.min(i+k,m),y1=Math.min(j+k,n);
                int x2=Math.max(0,i-k-1),y2=Math.max(j-k-1,0);
                ret[i-1][j-1]=dp[x1][y1]-dp[x2][y1]-dp[x1][y2]+dp[x2][y2];
            }
        }
        return ret;
    }





//        int[] nums={0,1,0};
//        System.out.println(findMaxLength(nums));





//        int[] nums={4,5,0,-2,-3,1};
//        System.out.println(subarraysDivByK(nums,5));
//        System.out.println(-3%-4);
//        System.out.println(-7/5);
//        System.out.println(-7%5);
//        System.out.println(3%5);

    public static int findMaxLength(int[] nums) {
        int n=nums.length;
        HashMap<Integer,Integer> m=new HashMap<>();
        m.put(0,0);
        int pre=0;
        int ret=0;
        for(int i=1;i<=n;i++){
            pre=pre+(nums[i-1]==0 ? -1 :1);
            if(m.getOrDefault(pre,-1)!=-1){
                ret=Math.max(i-m.get(pre),ret);
            }
            m.put(pre,Math.min(m.getOrDefault(pre,i),i));
        }
        return ret;
    }




    public static  int subarraysDivByK(int[] nums, int k) {
        int n=nums.length;
        HashMap<Integer,Integer> m=new HashMap<>();
        m.put(0,1);
        int ret=0;long pre=0;
        for (int num : nums) {
            pre = pre + num;
            int tem = (int) (pre % k + k) % k;
            ret = ret + m.getOrDefault(tem, 0);
            m.put(tem, m.getOrDefault(tem, 0) + 1);
        }
        return ret;
    }
}
