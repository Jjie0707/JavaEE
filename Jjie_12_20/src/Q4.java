import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),m=in.nextInt();
        if(n!=m){
            int sum=0;
            for(int i=0;i<n-m;i++) sum+=in.nextInt();
            System.out.printf("%.4f ", (sum+m)/(0.0+n) );
            System.out.printf("%.4f", (sum+5*m)/(0.0+n) );
        }else{
            System.out.println(1.000+" "+5.000);
        }
    }
}
