package e1_e2;

import java.util.HashMap;
import java.util.Scanner;

public class Account {

    public static Scanner sc=new Scanner(System.in);
    private int id;
    private String userName;
    private double balance;
    private static HashMap<Integer,Account> m=new HashMap<>() ;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", rest=" + balance +
                '}';
    }

    public Account(){
        //初始化账户编号,用户名,余额
        System.out.print("请输入id:");id=sc.nextInt();sc.nextLine();
        userName="userName"+id;
        balance =5000;
        m.put(id,this);
    }

    public void save(){
        System.out.print("请输入存款的账户id:");int id=sc.nextInt();sc.nextLine();
        Account account=m.getOrDefault(id,null);
        if(account!=null){
            System.out.print("请输入存款:");double tem = sc.nextDouble();sc.nextLine();
            while (true) {
                if (tem <= 0) {
                    tem = sc.nextDouble();
                } else {
                    break;
                }
            }
            account.setBalance(account.getBalance()+tem);
            System.out.println(account);
        }else{
            while(true){
                System.out.print("请输入您的选项(yes/no):");String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.print("请输入存款的账户id:");id=sc.nextInt();sc.nextLine();
                    account=m.getOrDefault(id,null);
                    if(account!=null){
                        System.out.print("请输入存款:");double tem = sc.nextDouble();sc.nextLine();
                        while (true) {
                            if (tem <= 0) {
                                tem = sc.nextDouble();
                            } else {
                                break;
                            }
                        }
                        account.setBalance(account.getBalance()+tem);
                        System.out.println(account);
                        break;
                    }
                }else break;
            }
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
