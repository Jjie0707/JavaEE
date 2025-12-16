import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),m=in.nextInt();
        double ret=1;
        for(int i=n;i>m;i--) ret=ret*i;
        for(int i=m-1;i>=2;i--) ret=ret/i;
        ret=ret*Math.pow(0.8,m)*Math.pow(0.2,n-m);
        System.out.printf("%.4f",ret);
    }
}
