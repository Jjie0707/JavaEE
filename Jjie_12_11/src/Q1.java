import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int T=in.nextInt();
        for(int i=0;i<T;i++){
            long ret=0;
            long n=in.nextLong(),a=in.nextLong(),b=in.nextLong();
            if(n<3) System.out.println(Math.min(a,b));
            else if(3*a<=2*b){//双人船更划算，优先考虑
                ret=ret+a*(n/2);
                if(n%2>0) ret=ret+Math.min(a,b-a);
                System.out.println(ret);
            }else{
                //三人划算，优先考虑
                ret=ret+b*(n/3);
                if(n%3==1){
                    ret=ret+Math.min(Math.min(a,b),2*a-b);
                }else if(n%3==2){
                    ret=ret+Math.min(Math.min(a,b),3*a-b);
                }
                System.out.println(ret);
            }

        }
    }
}
