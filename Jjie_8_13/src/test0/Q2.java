package test0;

import java.util.Arrays;

public class Q2 {
    public static void main(String[] args) {
        int[][] matrix= {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        setZeroes(matrix);
        for(int[] tem:matrix){
            System.out.println(Arrays.toString(tem));
        }
    }
//    int l=matrix.length,w=matrix[0].length;
//    for(int i=0;i<l;i++){
//        for(int j=0;j<w;j++){
//            int tem=matrix[i][j];
//            if((i-1<0 && tem==0) || (i-1>=0 && matrix[i-1][j]!=0 && tem==0)){
//                for(int k=0;k<l;k++){
//                    matrix[k][j]=0;
//                }
//                for(int k=0;k<w;k++){
//                    matrix[i][k]=0;
//                }
//                break;
//            }
//        }
//    }
    public static void setZeroes(int[][] matrix) {
        int l=matrix.length,w=matrix[0].length;
        int[][] tem=new int[l][w];
        for(int i=0;i<l;i++){
            for(int j=0;j<w;j++){

            }
        }
    }
}
