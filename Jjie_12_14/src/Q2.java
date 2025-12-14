import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt(),aim=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        if(aim==0) System.out.println(0);
        else if(aim<0) System.out.println(-1);
        else{

        }
    }
}
