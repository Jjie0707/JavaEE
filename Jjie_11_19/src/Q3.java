import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),m=in.nextInt();
        int Xs=in.nextInt(),Ys=in.nextInt(),Xt=in.nextInt(),
                Yt=in.nextInt();
        //走完这里找到了开始点与结束点
        char[][] t=new char[n][];
        for(int i=0;i<n;i++){
            String tem=in.next();
            char[] s=tem.toCharArray();
            t[i]=s;
        }
    }
}
