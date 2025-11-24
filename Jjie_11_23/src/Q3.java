import java.util.HashMap;
import java.util.Scanner;

public class Q3 {
    private static int func(int n){
        int ret=1;
        for(int i=1;i<=n;i++){
            ret*=i;
        }
        return ret;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n=in.nextInt();
        int[] nums=new int[n+1];
        for(int i=1;i<=n;i++) nums[i]=in.nextInt();
        boolean[] st=new boolean[n+1];
        HashMap<Integer,Integer> m=new HashMap<>();
        for(int i=1;i<=n;i++){
            if(!st[i]){//如果这个数没有被访问过才继续
                st[i]=true;

                if(nums[i]==i){//没有诉求的情况
                    m.put(i,1);
                }else{
                    int next=nums[i];//他的诉求是放在next号的前面
                    if(st[next]){//如果他的诉求已经被使用了
                        int count=m.get(next);
                        m.remove(next);
                        m.put(i,count+1);
                    }else{//如果他的诉求没有被使用
                        int len=1;
                        while(!st[next]){
                            st[next]=true;
                            len++;
                            if(nums[next]==next){
                                m.put(i,len);
                                break;
                            }else{
                                next=nums[next];
                            }
                        }
                    }
                }
            }
        }
        int ret=func(n);
        for(int tem:m.values()){
            ret=ret/func(tem);
        }
        System.out.println(ret);
    }
}
