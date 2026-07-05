package e5;

import java.util.Scanner;

public class ExceptionHandler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a interger：");
        int number = 0;
        try {
            number = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("输入的参数非法!程序终止!");
            throw new RuntimeException(e);
        }
        System.out.println("The number entered is：" + number);
    }
}
