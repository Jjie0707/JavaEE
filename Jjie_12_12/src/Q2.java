import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Q2 {
    public ArrayList<Integer> findOrder (int numProject, ArrayList<ArrayList<Integer>> groups) {
        // write code here
        ArrayList<Integer> ret = new ArrayList<>();
        HashMap<Integer, Integer> m = new HashMap<>();
        for (ArrayList<Integer> tem : groups) {
            int a = tem.get(0), b = tem.get(1);
            if (m.get(b) == a) return new ArrayList<>();
            m.put(a, b);
        }
        for (int i = 0; i < numProject; i++) {
            if (!m.containsKey(i)) ret.add(i);
            else {//m.containsKey(i)
                LinkedList<Integer> s = new LinkedList<>();
                int cur = i;
                while (m.containsKey(cur)) {
                    s.addLast(cur);
                    cur = m.get(cur);
                    if()
                }
                while (!s.isEmpty()) ret.add(s.removeLast());
            }
        }
        if (ret.size() == numProject) return ret;
        else return new ArrayList<>();
    }
}
