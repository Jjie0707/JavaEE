import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] nums=new int[n];
        int sum=0;
        for(int i=0;i<n;i++){
            sum+=(nums[i]=in.nextInt());
        }
        if(sum%2!=0){
            System.out.println("false");
        }else{
            int target=sum/2;
            //问题变成了 能否找到若干个数使得他们的和等于这个target

        }
    }
}
