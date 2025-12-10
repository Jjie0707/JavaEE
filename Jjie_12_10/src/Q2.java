import java.util.Scanner;

public class Q2 {
    public static int[] nums;
    public static void main(String[] args) {
        if(nums==null){
            nums=new int[100];
            for(int  i=0;i<100;i++) nums[i]=(i+1)*(i+1);
        }
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();

    }
}
