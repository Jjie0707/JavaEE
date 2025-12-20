import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int m=in.nextInt(),n=in.nextInt();
        int[][] nums=new int[n][m];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                nums[j][i]=in.nextInt();
            }
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(j!=m-1) System.out.print(nums[i][j]+" ");
                else System.out.println(nums[i][j]);
            }
        }
    }
}
