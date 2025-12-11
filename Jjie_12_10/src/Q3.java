import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int T=in.nextInt();
        for(int i=0;i<T;i++){
            int ret=Integer.MAX_VALUE;
            int n=in.nextInt(),a=in.nextInt(),b=in.nextInt();
            int count=0;
            while(count*3<=n){
                int tem=n;
                int cos=0;
                cos=cos+count*b;
                tem-=count*3;
                cos=cos+a*tem/2+(tem%2==0 ? 0 : Math.min(a,b));
                ret=Math.min(cos,ret);
                count++;
            }
            System.out.println(ret);
        }
    }
}
