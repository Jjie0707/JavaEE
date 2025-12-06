import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int T=in.nextInt();
        for(int i=0;i<T;i++){
            int h=in.nextInt();
            int ret=0,a=1;
            while(h>0){
                h-=a;
                ret++;
                if(h%(2*a)==0) a*=2;
            }
            System.out.println(ret);
        }
    }
}
