import java.util.Arrays;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[][] arr=new int[n][2];
        for(int i=0;i<n;i++){
            int a=in.nextInt(),b=in.nextInt();
            arr[i]=new int[]{a,b};
        }
        Arrays.sort(arr,(e1,e2)->{
            if(e1[0]==e2[0]) return 0;
            else if(e1[0]>e2[0]) return 1;
            else return -1;
        });
        int count=1;
        int end=arr[0][1];
        for(int i=1;i<n;i++){
            int s=arr[i][0];
            if(s<=end) end=Math.min(end,arr[i][1]);
            else{
                count++;
                end=arr[i][1];
            }
        }
        System.out.println(count);
    }
}
