package e6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayListTest {

    public static Scanner sc=new Scanner(System.in);

    public static void main(String[] args) {
        List<Integer> list=new ArrayList();
        for(int i=0;i<7;i++) {
            System.out.print("请输入考试分数:");
            list.add(sc.nextInt());
        }
        double num=0;
        for(int tem:list) num+=tem;
        System.out.println("平均分:"+num/7);
    }
}
