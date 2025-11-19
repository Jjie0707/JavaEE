import java.util.ArrayList;

public class Q2 {
    public boolean hostschedule (ArrayList<ArrayList<Integer>> schedule) {
        // write code here
        schedule.sort((e1,e2)->e1.getFirst()-e2.getFirst());
        int size = schedule.size();
        for(int i=1;i<size;i++){
            ArrayList<Integer> tem=schedule.get(i);
            if(tem.get(1)<=schedule.get(i-1).get(1)) return false;
        }
        return true;
    }
}
