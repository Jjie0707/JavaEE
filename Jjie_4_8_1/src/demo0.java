public class demo0 {
    public int minNumberOfFrogs(String croakOfFrogs) {

    }


    static String[] s=new String[31];
    public String countAndSay(int n) {
        if(!s[0].equals("1")){
            s[0]="1";
            StringBuilder tem;
            for(int i=1;i<=30;i++){
                int left=0;tem=new StringBuilder();
                char[] ss=s[i-1].toCharArray();
                int count=1;char pre=ss[left];
                for(int right=1;right<ss.length;right++){
                    if(ss[left]==ss[right]) count++;
                    else{
                        tem.append(count);tem.append(pre);
                        count=1;pre=ss[right];
                        left=right;
                    }
                }
                tem.append(count);tem.append(pre);
                s[i]=tem.toString();
            }
            return s[n];
        }else{
            return s[n];
        }
    }



    public String convert(String s, int numRows) {
        int d=numRows*2-2;
        if(d==0) return s;
        char[] ss=s.toCharArray();
        int n=ss.length;
        StringBuilder[] str=new StringBuilder[numRows];
        for(int i=0;i<str.length;i++) str[i]=new StringBuilder();
        for(int i=0;i<n;i++){
            int tem=i%d;
            if(tem<numRows) str[tem].append(ss[i]);
            else str[d-tem].append(ss[i]);
//            for(int j=0;j<numRows;j++){
//                if(i==j) str[j].append(ss[i]);
//                else if((j-i)%d==0) str[j].append(ss[i]);
//                else if((j+i)%d==0) str[j].append(ss[i]);
//            }
        }
        StringBuilder ret=new StringBuilder();
        for(int i=0;i<numRows;i++){
            ret=ret.append(str[i]);
        }
        return ret.toString();
    }
}

