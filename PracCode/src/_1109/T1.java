package _1109;

import java.util.HashMap;
import java.util.Map;

public class T1 {
//    public double[] dist=new double[32];
//    public static double[] pos=new double[32];
//    public void pre(){
//        pos[0]=1;
//        for(int i=1;i<32;i++) pos[i]=pos[i-1]*2;
//    }
//    public double myPow(double x, int n) {
//        if(pos[0]!=1) pre();
//        dist[0]=1;
//        for(int i=1;i<32;i++) dist[i]=dist[i-1]*x;
//        int tem=Math.abs(n);
//        if(n<0) return 1/func(x,tem);
//        else return func(x,tem);
//    }
//    public double func(double x,int n){
//        int index=-1;double ret=1.0;
//        for(int i=1;i<32;i++){
//            if(pos[i-1]<=n && n<pos[i]){
//                index=i-1;break;
//            }
//        }
//
//    }
    public double myPow(double x, int n) {
        long N = n;                                  // 先转 long，避开 MIN_VALUE
        Map<Long, Double> cache = new HashMap<>();   // 局部变量，每次调用全新
        return N >= 0 ? func(x, N, cache) : 1.0 / func(x, -N, cache);
    }

    private double func(double x, long n, Map<Long, Double> cache) {
        if (n == 0) return 1.0;
        if (n == 1) return x;
        if (n == 2) return x * x;                    // 必须保留：拦自引用
        Double hit = cache.get(n);
        if (hit != null) return hit;
        double a = func(x, n / 2 + 1, cache);
        double b = func(x, n / 2 - 1, cache);
        double r = (n % 2 == 0) ? a * b : a * b * x;
        cache.put(n, r);                             // 写自己，不是写子节点
        return r;
    }
    public double myPow_(double x, int n) {
        long N = n;
        if(N<0) N=-N;
        double r = 1.0;
        while (N > 0) {
            if ((N & 1) == 1) r *= x;        // 当前二进制位是 1，累乘
            x *= x;                          // 平方，准备下一位
            N >>= 1;
        }
        return n>=0 ? r : 1/r;
    }
}
