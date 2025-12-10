import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int T=in.nextInt();
        for(int i=0;i<T;i++){
            long ret=0;
            int n=in.nextInt(),a=in.nextInt(),b=in.nextInt();
            long tem1,tem2;
            tem1=(n/3)*b+( n%3>0 ? Math.min(a,b) : 0 );
            tem2=(n/2)*a+( n%2>0 ? Math.min(a,b) : 0 );
            ret+=Math.min(tem1,tem2);
            System.out.println(ret);
        }
    }
}
