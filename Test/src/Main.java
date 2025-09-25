import java.io.File;
import java.util.Scanner;

public class Main {
    static void main() {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入需要搜索的根目录：");
        String rootPath=sc.next();
        if(rootPath.isEmpty()) {
            System.out.println("输入非法，退出程序！");
            return;
        }
        File root=new File(rootPath);
        if(!root.isDirectory()){
            System.out.println("输入的目录是非法的！！！退出程序。");
            return ;
        }else {
            System.out.println("请输入需要查找的文件名的关键字：");
            String key=sc.next();
            if(key.isEmpty()){
                System.out.println("非法关键字，退出程序！");
                return;
            }
            search(root,key);
        }
    }
    private static void search(File file,String key){
        if(file.isFile()){
            String temName=file.getAbsolutePath();
            if(temName.contains(key)) System.out.println(temName);
        }else {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            } else {
                for (File tem : files) {
                    search(tem, key);
                }
            }
        }
    }
}
