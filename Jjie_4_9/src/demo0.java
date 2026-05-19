import java.util.HashMap;

public class demo0 {
    static HashMap<Character,Integer> m;
    public int minNumberOfFrogs(String croakOfFrogs) {
        if(m == null){
            m =new HashMap<>();
            m.put('c',0);
            m.put('r',1);
            m.put('o',2);
            m.put('a',3);
            m.put('k',4);
        }
        int[] count=new int[5];
        char[] c=croakOfFrogs.toCharArray();
        for(int i=0;i<c.length;i++){
            char tem=c[i];int index=m.get(tem);
            if(tem=='c'){
                if(count[4]!=0) {
                    count[4]--;
                    count[index]++;
                }else{
                    count[index]++;
                }
            }else{
                int target=index-1;
                if(count[target]==0) return -1;
                else{
                    count[target]--;
                    count[index]++;
                }
            }
        }
        for(int i=0;i<count.length-1;i++){
            if(count[i]!=0) return -1;
        }
        return count[4];
    }
}
