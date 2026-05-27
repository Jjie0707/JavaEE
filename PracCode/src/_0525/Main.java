package _0525;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.printf(""+func());
    }
     static char func() throws IOException {
        return (char)System.in.read();
    }
}
