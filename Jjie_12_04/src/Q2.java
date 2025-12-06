import java.util.HashMap;

public class Q2 {
    public static void main(String[] args) {
        int[] a={1,4,7,5,6};
        System.out.println(LIS(a));
    }
    public static int LIS (int[] a) {
        // write code her
        HashMap<Integer,Integer> m=new HashMap<>();
        for (int tem : a) {
            if (m.isEmpty()) {
                m.put(1, tem);
            } else {
                int left=1,right=m.size();
                while(left<right){
                    int mid=left+(right-left+1)/2;
                    int cur=m.get(mid);
                    if(tem<cur){
                        left=mid;
                    }else{
                        right=mid-1;
                    }
                }
                m.put(left+1,tem);
            }
        }
        return m.size();
    }
//    public static int LIS (int[] a) {
//        // write code her
//        HashMap<Integer,Integer> m=new HashMap<>();
//        for (int tem : a) {
//            if (m.isEmpty()) {
//                m.put(1, tem);
//            } else {
//                int size = m.size();
//                for (int j = 1; j <= size; j++) {
//                    if (!m.containsKey(j + 1) || m.get(j + 1) > tem) {
//                        if (tem > m.get(j)) {
//                            m.put(j + 1, tem);
//                        }
//                        if (tem < m.get(j) && (j == 1 || tem > m.get(j - 1))) {
//                            m.put(j, tem);
//                        }
//                    }
//                }
//            }
//        }
//        return m.size();
//    }
}





