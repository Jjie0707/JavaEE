package test0;

import java.util.Arrays;

public class demo1 {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        int n=in.nextInt();
//        System.out.println(func(n));
        int[] nums={0,3,2,6,4};
        System.out.println(IsContinuous(nums));
    }
    public static int func(int n){
        if(n==0 || n==1) return 1;
        else return func(n-2) +func(n-1);
    }
    public  static boolean IsContinuous (int[] numbers) {
        // write code here
        int count=0;
        for(int i=0;i<5;i++) if(numbers[i]==0) count++;
        Arrays.sort(numbers);
        for(int i=count;i<numbers.length-1;i++){
            if(numbers[i+1]==numbers[i]) return false;
            if(numbers[i+1]!=numbers[i]+1){
                if(numbers[i+1]-numbers[i]<=count+1)  count=count-(numbers[i+1]-numbers[i])+1;
                else return false;
            }
        }
        return true;
    }
}
