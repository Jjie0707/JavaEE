package test0;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Q1 {


//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        int n=in.nextInt();
//        if(n<=1) System.out.println(1);
//        else{
//            System.out.println(1);
//            int[] pre=new int[1];
//            pre[0]=1;
//            for(int i=2;i<=n;i++){
//                int[] tem=new int[i];
//                for(int j=0;j<i;j++){
//                    if(j==0){
//                        tem[j]=1;
//                        System.out.print(1+" ");
//                    }else if(j==i-1){
//                        tem[j]=1;
//                        System.out.println(1);
//                    }else{
//                        tem[j]=pre[j-1]+pre[j];
//                        System.out.print(tem[j]+" ");
//                    }
//                }
//                pre=tem;
//            }
//
//        }
//    }






    private int[][] dire={{-1,0},{1,0},{0,-1},{0,1}};
    boolean[][] visited;
    public boolean exist (String[] board, String word) {
        // write code here
        if(board.length==0) return false;

        LinkedList<Integer> pre=new LinkedList<>();

        char[] ch=word.toCharArray();
        char[][] boards=new char[board.length][board[0].length()];
        int index=0;
        for(String tem:board){
            boards[index++]=tem.toCharArray();
        }

        ArrayList<int[]> list=new ArrayList<>();

        for(int i=0;i<boards.length;i++){
            for(int j=0;j<boards[0].length;j++){
                if(boards[i][j]==ch[0]) list.add(new int[] {i,j});
            }
        }
        for(int[] tem:list){
            visited=new boolean[board.length][boards[0].length];
            if(search(ch,boards,tem[0],tem[1],0)) return true;
        }
        return false;
    }
    public static boolean search(char[] ch,char[][] boards,int x,int y,int index){

    }



//    private static int[] fib;
//    public static void main(String[] args) {
//        //预处理，如果已经处理过了不用再考虑
//        if(fib==null){
//            fib=new int[30];
//
//            fib[0]=0;
//            fib[1]=1;
//            for(int i=2;i<30;i++){
//                fib[i]=fib[i-1]+fib[i-2];
//            }
//        }
//        System.out.println(fib[28]);
//        Scanner in = new Scanner(System.in);
//        int n=in.nextInt();
//        int ret=Integer.MAX_VALUE;
//        for(int i=0;i<30;i++){
//            int tem=Math.abs(fib[i]-n);
//            if(tem<=ret){
//                ret=tem;
//            }
//        }
//        System.out.println(ret);
//    }

}
