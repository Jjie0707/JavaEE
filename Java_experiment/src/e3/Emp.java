package e3;

import java.util.Scanner;

public class Emp {

    public static Scanner sc=new Scanner(System.in);

    public static void count(){
        double count=0;
        System.out.print("请输入职位:");String pos=sc.nextLine();sc.nextLine();
        if(pos.equals("销售经理")){
            count=5000;
            System.out.print("请输入销售额:");double tem=sc.nextDouble();sc.nextLine();
            count+=(tem*0.01);
        }else if(pos.equals("专职销售员")){
            count=3000;
            System.out.print("请输入销售额:");double tem=sc.nextDouble();sc.nextLine();
            count+=(tem*0.05);
        }else{
            count=0;
            System.out.print("请输入工作时长(单位:h):");double tem=sc.nextDouble();sc.nextLine();
            count+=(tem*80);
        }
        System.out.println("工资为:"+count);
    }
}
