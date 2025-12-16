import java.util.Scanner;

public class Q3 {
    static long[] g;
    static long[] f;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        g=new long[n+1];
        f=new long[n+1];
        g[1]=2;f[1]=1;
        for(int i=1;i<=n;i++){
            f[i]=(g[i-1] % 1000000007+g[i-1] % 1000000007+1) % 1000000007;
            g[i]=(f[i] % 1000000007+f[i-1] % 1000000007+1) % 1000000007;
        }
        System.out.print(f[n]+" ");
        System.out.print(g[n]);
    }
}
