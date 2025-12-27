import java.util.HashSet;
import java.util.Scanner;

public class Q3 {

    public static HashSet<Integer> xs,ys,b1,b2;

    public static boolean func(int x,int y){
        // 返回false说明碰撞
        if(xs.contains(x) || ys.contains(y)) return false;
        int b=x-y;
        if(b1.contains(b)) return false;
        b=x+y;
        if(b2.contains(b)) return false;
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        xs=new HashSet<>();ys=new HashSet<>();
        b1=new HashSet<>();b2=new HashSet<>();
        int k=in.nextInt();
        String[] ret=new String[k];
        for(int i=0;i<k;i++){
            int x=in.nextInt(),y=in.nextInt();
            if(func(x,y)) ret[i]="No";//返回yes说明不碰撞
            else ret[i]="Yes";
            xs.add(x);ys.add(y);
            b1.add(x-y);b2.add(x+y);
        }
        int t=in.nextInt();
        for(int i=0;i<t;i++){
            int tem=in.nextInt()-1;
            System.out.println(ret[tem]);
        }
    }
}
