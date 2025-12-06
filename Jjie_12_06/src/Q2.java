import java.util.Scanner;

public class Q2 {
    public static boolean func(long n){
        if(n==1) return false;
        for(int i=2;i<Math.pow(n,0.5)+1;i++){
            if(n%i==0) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        for(int i=0;i<n;i++){
            long a=in.nextLong(),b=in.nextLong();
            if(a<b){//确保a一直比b大
                long tem=a;
                a=b;b=tem;
            }

            if(func(a) && func(b)){
                if(a%b==0) System.out.println("YES");
                else System.out.println("NO");
            }else if(b==1 && func(a)) System.out.println("YES");
            else System.out.println("NO");
        }
    }
}
