public class Q1 {
    static void main() {
        System.out.println(solve("youzan", "zanyou"));
    }
    public static boolean func(StringBuilder a,StringBuilder b){
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)!=b.charAt(i)) return false;
        }
        return true;
    }
    public static boolean solve (String A, String B) {
        // write code here
        if (A.equals(B)) return true;
        else {
            int l1 = A.length(), l2 = B.length();
            if (l1 != l2) return false;
            StringBuilder b = new StringBuilder(B);
            for (int i = 0; i < l1; i++) {
                StringBuilder a = new StringBuilder(A.substring(i+1,l1));
                a.append(A, 0, i+1);
                if(func(a,b)){
                    return true;
                }
            }
            return false;
        }
    }
}
