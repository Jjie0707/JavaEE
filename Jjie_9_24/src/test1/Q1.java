//package test0;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.InputMismatchException;
//import java.util.PriorityQueue;
//import java.util.Scanner;
//import java.util.StringTokenizer;
//
//public class Q1 {
//
//    public static void main(String[] args) throws IOException {
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
//
//        int n = Integer.parseInt(st.nextToken());
//        int k = Integer.parseInt(st.nextToken());
//
//
//        long sum=0;
//        st = new StringTokenizer(br.readLine());
//        PriorityQueue<Integer> heap=new PriorityQueue<>((e1, e2)->e2-e1);
//        for (int i = 0; i < n; i++) {
//            int num=Integer.parseInt(st.nextToken());
//            sum+= num;
//            if(num%2==0)heap.offer(num);
//        }
//        while(k>0 && !heap.isEmpty()){
//            int tem=heap.poll();
//            sum-=tem/2;
//            heap.offer(tem);
//            k--;
//        }
//        System.out.println(sum);
//    }
//
//
//    public static void main4(String[] args) throws IOException {
//        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//
//        StringTokenizer st=new StringTokenizer(br.readLine());
//
//        int n=Integer.parseInt(st.nextToken());
//        int x=Integer.parseInt(st.nextToken());
//        int len = n + 1;
//        int l = 0, r = 0;
//        int left = 0;
//        int sum = 0;
//
//        int[] nums = new int[n];
//        st=new StringTokenizer(br.readLine());
//        for (int right = 0; right < n; right++) {
//            nums[right] = Integer.parseInt(st.nextToken());
//            sum += nums[right];
//            while (sum >= x) {
//                if (right - left + 1 < len) {
//                    l = left + 1;
//                    r = right + 1;
//                    len = right - left + 1;
//                }
//                sum -= nums[left++];
//            }
//        }
//        System.out.println(l + " " + r);
//    }
//
////    public static void main(String[] args) {
////        Scanner in = new Scanner(System.in);
////        // 注意 hasNext 和 hasNextLine 的区别
////        while (in.hasNextInt()) { // 注意 while 处理多个 case
////            int n = in.nextInt();
////            int x = in.nextInt();
////
////            int len=n+1;
////            int l=0,r=0;
////            int left=0;
////            int sum=0;
////
////            int[] nums=new int[len];
////            for(int right=0;right<n;right++){
////                nums[right]=in.nextInt();
////                sum+=nums[right];
////                while(sum>=x){
////                    if(right-left+1<len){
////                        l=left+1;r=right+1;
////                        len=right-left+1;
////                    }
////                    sum-=nums[left++];
////                }
////            }
////            System.out.println(l+' '+r);
////        }
////    }
//
//    public static void main1(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            int n = in.nextInt();
//            int x = in.nextInt();
//            int[] nums=new int[n];
//
//            for(int i=0;i<n;i++){
//                nums[i]=in.nextInt();
//            }
//            //走到这里获取到了所有的数字 并存入数组
//            int left=0;int right=0;
//            int sum=0;
//            int len=n;
//            int l=n,r=n;
//            for(;right<n;right++){
//                sum+=nums[right];
//                while(sum>=x){
//                    if(right-left+1<len){
//                        l=left+1;
//                        r=right+1;
//                        len=right-left+1;
//                    }
//                    sum-=nums[left];
//                    left++;
//                }
//            }
//            System.out.println(l+" "+r);
//        }
//    }
//
//
//
////    International Collegiate Programming Contest
//
//    public static void main0(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNext()) { // 注意 while 处理多个 case
//            StringBuilder ret=new StringBuilder();
//            String s=null;
//            while(in.hasNext()){
//                s=in.next();
//                char c=s.charAt(0);
//                if(!('A'<=c && 'Z'>=c)){
//                    ret.append('A'+(c-'a'));
//                }else ret.append(c);
//            }
//            System.out.println(ret);
//        }
//    }
//}
