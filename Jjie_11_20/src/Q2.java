import java.util.ArrayList;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        for(int i=0;i<n;i++){
            int tem=in.nextInt();//每次询问的数

            if(tem%2==0){
                System.out.println(tem);
            }else{
                ArrayList<Integer> list=new ArrayList<>();
                while (tem != 0) {
                    list.add(tem % 10);
                    tem /= 10;
                }

                int size = list.size();
                boolean flag = true;
                for (int j = size-1; j >=0; j--) {
                    int x = list.get(i);
                    if (x % 2 == 0) {
                        flag = false;
                        list.set(j, list.get(0));
                        list.set(0, x);
                        int ret = 0;
                        for (int k = size-1; k>=0; k--) {
                            ret *= 10;
                            ret += list.get(k);
                        }
                        System.out.println(ret);
                        break;
                    }
                }
                if (flag) {
                    System.out.println(-1);
                }
            }
        }
    }
}
