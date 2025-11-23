import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=in.nextInt();
        int max=nums[0],min=nums[0];
        int ret=1;
        for(int i=1;i<n;i++){
            int tem=nums[i];
            if(tem>=max){
                max=tem;
            }else if(tem<=min){
                min=tem;
            }else{
                ret++;
                min=tem;
                max=tem;
            }
        }
        System.out.println(ret);
    }
}
