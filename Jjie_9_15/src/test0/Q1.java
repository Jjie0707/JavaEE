package test0;

import java.util.Arrays;
import java.util.LinkedList;

public class Q1 {
    public static void main(String[] args) {
        int[] temperatures={
            89, 62, 70, 58, 47, 47, 46, 76, 100, 70
        };
        System.out.println(Arrays.toString(dailyTemperatures(temperatures)));
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int n=temperatures.length;
        int[] ret=new int[n];
        if(n==0) return ret;
        LinkedList<Integer> val=new LinkedList<>();
        LinkedList<Integer> index=new LinkedList<>();
        for(int i=n-1;i>=0;i--){
            if(val.isEmpty() || val.getLast()>=temperatures[i]){
                if(val.isEmpty()){
                    ret[i]=0;
                }else{
                    ret[i]=index.getLast()-i;
                }
                val.addLast(temperatures[i]);
                index.addLast(i);
            }else{
                while(!val.isEmpty() && val.getLast()<temperatures[i]){
                    val.removeLast();
                    index.removeLast();
                }
                if(val.isEmpty()){
                    ret[i]=0;
                }else{
                    ret[i]=index.getLast()-i;
                }
                val.addLast(temperatures[i]);
                index.addLast(i);
            }
        }
        return ret;
    }

    class obj implements Comparable<obj>{
        int val;
        int index;

        @Override
        public int compareTo(obj o) {
            return this.val-o.val;
        }
        public obj(int val,int index){
            this.val=val;
            this.index=index;
        }

    }
    public int[] _dailyTemperatures(int[] temperatures) {
        int n=temperatures.length;
        int[] ret=new int[n];
        if(n==0) return ret;
        LinkedList<obj> stack=new LinkedList<>();

        for(int i=n-1;i>=0;i--){
            if(stack.isEmpty() || stack.getLast().val>=temperatures[i]) {
                stack.addLast(new obj(temperatures[i], i));
                ret[i] = 0;
            }
            else{
                while(!stack.isEmpty() && stack.getLast().val<temperatures[i]){
                    stack.removeLast();
                }
                if(stack.isEmpty()){
                    stack.addLast(new obj(temperatures[i], i));
                    ret[i] = 0;
                }else{
                    ret[i]=stack.getLast().index-i+1;
                }
            }
        }
        return ret;
    }

    public static void main2(String[] args) {
        int[] nums1={1,3},nums2={2};
        System.out.println(findMedianSortedArrays(nums1,nums2));
    }
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1=nums1.length,l2=nums2.length,len=l1+l2;
        if(len%2==1) return findMedianSortedArrays_(nums1, nums2, (l1 + l2) / 2);
        else return (findMedianSortedArrays_(nums1,nums2,(l1+l2)/2-1) + findMedianSortedArrays_(nums1,nums2,(l1+l2)/2))/2.0;
    }
    public static int findMedianSortedArrays_(int[] nums1,int[] nums2,int k){
        int l1=nums1.length,l2=nums2.length;
        if(l1>=l2){
            int[] tem=nums1;
            nums1=nums2;
            nums2=tem;
            l1=nums1.length;
            l2=nums2.length;
        }//始终保持nums1是比较短的数组

        // 一共有k个需要被排除掉的数字
        int cur1=0,cur2=0;
        while(cur1<l1 && cur2 <l2 && k>0){
            int tem=k==1 ? 1 : k/2;
            int index1=Math.min(cur1+tem-1,l1-1),
                    index2=Math.min(cur2+tem-1,l2-1);

            int num1=nums1[index1], num2=nums2[index2];
            if(num1>=num2) {
                k=k-(index2-cur2+1);
                cur2 = index2 + 1;
            }
            else {
                k=k-(index1-cur1+1);
                cur1 = index1 + 1;
            }
            //走到这里排除了k/2个数字
        }
        if(cur1<l1 && cur2<l2) return Math.min(nums1[cur1],nums2[cur2]);
        else if(cur1>=l1){
            return nums2[cur2+k];
        }else{
            return nums1[cur1+k];
        }
    }

    public boolean isValid(String s) {
//        '('，')'，'{'，'}'，'['，']'
        int length=s.length();
        if(length==0) return true;
        char[] ss=s.toCharArray();

        LinkedList<Character> stack=new LinkedList<>();
        for(int i=0;i<length;i++){
            char c=ss[i];
            if(c=='(' || c=='[' || c=='{') stack.addLast(c);
            else{
                if(c==')'){
                    if(stack.getLast()!='(') return false;
                }else if(c==']'){
                    if(stack.getLast()!='[') return false;
                }else{
                    if(stack.getLast()!='{') return false;
                }
                stack.removeLast();
            }
        }
        return stack.isEmpty();
    }

    public static void main0(String[] args) {
        String s = "10[b]";
        System.out.println(decodeString(s));
    }
    public static String decodeString(String s) {
        int length=s.length();
        if(length==0) return "";
        StringBuilder ret=new StringBuilder();

        LinkedList<Character> stack=new LinkedList<>();

        for(int i=0;i<length;i++){
            char c=s.charAt(i);
            if(c!=']') stack.addLast(c);
            else{
                StringBuilder tem=new StringBuilder();
                int count=0;
                while(!stack.isEmpty() && stack.getLast()!='['){
                    tem.append(stack.removeLast());
                }
                stack.removeLast();//这里一定有一个 '[' 要把他移除
                StringBuilder t=new StringBuilder();
                while(!stack.isEmpty()){
                    if(stack.getLast()>='0' && stack.getLast()<='9'){
                        t.append(stack.removeLast());
                    }else{
                        break;
                    }
                }
                t.reverse();
                if(Integer.parseInt(t.toString())!=0) count=Integer.parseInt(t.toString());
                tem.reverse();
                for(int j=0;j<count;j++){
                    for(int k=0;k<tem.length();k++){
                        stack.addLast(tem.charAt(k));
                    }
                }
            }
        }

        while(!stack.isEmpty()){
            ret.append(stack.removeLast());
        }
        ret.reverse();
        return ret.toString();
    }
}
