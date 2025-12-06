import java.util.HashMap;

public class Q1 {
    static void main() {
        int[] a={1,4,7,5,6};
        System.out.println(LIS(a));
    }
    public static int LIS (int[] a) {
        // write code here
        HashMap<Integer,Integer> m=new HashMap<>();
        for (int cur : a) {
            if (m.isEmpty()) {
                m.put(1, cur);
            } else {
                int left = 1, right = m.size()+1;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (m.get(mid) < cur) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                m.put(left, cur);
            }
        }
        return m.size();
    }
}
