package e1_e2;

public class AccountTest {
    public static void main(String[] args) {
        Account[] arr=new Account[5];
        for(int i=0;i<5;i++){
            Account tem=new Account();
            arr[i]=tem;
        }
        Account account=arr[3];
        for(int i=0;i<3;i++){
            account.save();
        }
    }
}
