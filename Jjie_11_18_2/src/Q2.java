import java.util.Scanner;

public class Q2 {
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        int n=in.nextInt();
//        if(n==1){
//            System.out.println(in.nextInt());
//        }else{
//            int pre1=in.nextInt(),pre2=in.nextInt();
//            if(n==2){
//                System.out.println(Math.max(pre1,pre2));
//            }else{
//                int ret=0;
//                for(int i=0;i<n-2;i++){
//                    int tem=in.nextInt();
//                    int sum=tem+pre1;
//                    ret=Math.max(ret,sum);
//                    ret=Math.max(ret,pre2);
//                    pre1=pre2;
//                    pre2=tem;
//                }
//                System.out.println(ret);
//            }
//        }
//    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n =in.nextInt();
        int[] nums=new int[n];
        for(int i=0;i<n;i++){
            nums[i]=in.nextInt();
        }//走到这里完成了所有数字的存
        //思考状态方程
        int[] f=new int[n+1],g=new int[n+1];
        //假设走到位置i,f表示必定选择该位置的值，g表示必定不选择该位置的值
        f[0]=0;g[0]=0;
        for(int i=1;i<=n;i++){
            int tem=nums[i-1];
            if(i-2<0){
                f[i]=tem+g[i-1];
                g[i]=Math.max(f[i-1],g[i-1]);
            }else{
                f[i]=tem+Math.max(f[i-2],g[i-1]);
                g[i]=Math.max(f[i-1],g[i-1]);
            }
        }
        System.out.println(Math.max(f[n],g[n]));
    }
}
