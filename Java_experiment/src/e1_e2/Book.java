package e1_e2;

import java.util.Scanner;

public class Book {
    public static Scanner sc =new Scanner(System.in);
    private int[] rate;
    private String bookName;
    private int numRated5;
    private int numRated4;
    private int numRated3;
    private int numRated2;
    private int numRated1;

    public Book(){
        rate =new int[5];
        //映射numRated1~5
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setNumRated5(int numRated5) {
        this.numRated5 = numRated5;
    }

    public void setNumRated4(int numRated4) {
        this.numRated4 = numRated4;
    }

    public void setNumRated3(int numRated3) {
        this.numRated3 = numRated3;
    }

    public void setNumRated2(int numRated2) {
        this.numRated2 = numRated2;
    }

    public void setNumRated1(int numRated1) {
        this.numRated1 = numRated1;
    }

    public String getBookName() {
        return bookName;
    }

    public int getNumRated5() {
        return numRated5;
    }

    public int getNumRated4() {
        return numRated4;
    }

    public int getNumRated3() {
        return numRated3;
    }

    public int getNumRated2() {
        return numRated2;
    }

    public int getNumRated1() {
        return numRated1;
    }

    public void addRating(){
        int count=10;
        int r=sc.nextInt();
        while((!(r>=1 && r<=5)) && count>0){
            count--;
            r=sc.nextInt();
        }
        rate[r-1]++;
    }
    public int getAverage(){
        int point=0,count=0;
        for(int i=0;i<5;i++){
            point+=rate[i]*(i+1);
            count+=rate[i];
        }
        return count==0 ? 0 : point/count;
    }

    @Override
    public String toString() {
        return bookName+",Average Rating:"+getAverage();
    }
}
