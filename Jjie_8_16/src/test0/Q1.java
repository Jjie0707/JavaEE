package test0;

import java.util.Arrays;

public class Q1 {
    public static void main(String[] args) {
        int[][] matrix= {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotate(matrix);
        for(int[] tem:matrix){
            System.out.println(Arrays.toString(tem));
        }
    }
    public static void rotate(int[][] matrix) {
        int n= matrix.length;
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                int tem=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=tem;
            }
        }//走到这里完成了矩阵的转置
        //接下来完成列的交换、
        int left=0,right=n-1;
        while(left<right){
            for(int i=0;i<n;i++){
                int tem=matrix[i][left];
                matrix[i][left]=matrix[i][right];
                matrix[i][right]=tem;
            }
            left++;right--;
        }
    }
}
