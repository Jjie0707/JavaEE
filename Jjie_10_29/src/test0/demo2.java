package test0;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class demo2 {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        HashMap<Integer,Integer> m=new HashMap<>();
        for(int i=0;i<n;i++){
            int tem=in.nextInt();
            m.put(tem, m.getOrDefault(tem,0)+1);
        }
        Set<Integer> s=m.keySet();
        int size=s.size();

        int[] nums=new int[size];
        int k=0;
        for(int tem:s) nums[k++]=tem;

        if(size==0){
            System.out.println(0);
        }else if(size==1){
            System.out.println(m.get(nums[0])*nums[0]);
        }
        else{
            int slow = 0, fast = 1;
            int ret=0;
            while(slow<size && fast<size){
                if(size+2<size){
                    int num1=m.get(nums[slow])*nums[slow]+m.get(nums[slow+2])*nums[slow+2];
                    int num2=m.get(nums[fast])*nums[fast];
                    if(num1>=num2){
                        ret+=num1;
                        slow+=2;
                        fast+=2;
                    }else{
                        ret+=num2;
                        slow+=3;
                        fast+=3;
                    }
                }else{
                    int num1=m.get(nums[slow])*nums[slow];
                    int num2=m.get(nums[fast])*nums[fast];
                    if(num1>=num2){
                        ret+=num1;
                        slow+=2;
                        fast+=2;
                    }else{
                        ret+=num2;
                        slow+=3;
                        fast+=3;
                    }
                }
            }
            System.out.println(ret);
        }
    }
}
