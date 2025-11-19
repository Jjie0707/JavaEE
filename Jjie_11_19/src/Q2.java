import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),m=in.nextInt();
        int ret=n;
        for(int i=1;i<m;i++){
            ret=((ret%109)*((ret-1)%109))%109;
        }
        System.out.println(ret);
    }
}
