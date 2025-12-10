import java.util.Scanner;

public class Q2 {
    public static int[][] nums;
    public static int n;

    public static void func1(int[][] nums){//顺时针旋转
        for(int i=0;i<n;i++){
            int left=0,right=n-1;
            while(left<right){
                int tem=nums[i][left];
                nums[i][left]=nums[i][right];
                nums[i][right]=tem;
                left++;right--;
            }
        }
        func2(nums);
    }

    public static void func2(int[][] nums){//关于行对称
        int left=0,right=n-1;
        while(left<right){
            for(int i=0;i<n;i++){
                int tem=nums[left][i];
                nums[left][i]=nums[right][i];
                nums[right][i]=tem;
            }
            left++;right--;
        }
    }
    public static void print(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(j!=n-1) System.out.print(nums[i][j]+" ");
                else System.out.println(nums[i][j]);
            }
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        n=in.nextInt();
        nums=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                nums[i][j]=in.nextInt();
            }
        }

        int q=in.nextInt();
        int count1=0,count2=0;
        for(int i=0;i<q;i++){
            int tem=in.nextInt();

            if(tem==1) count1++;
            else count2++;
        }
        if(count1%2==1) func1(nums);
        if(count2%2==1) func2(nums);
        print();
    }
}
