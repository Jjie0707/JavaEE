package e1_e2;

public class BookReview {
    public static void main(String[] args) {
        Book book=new Book();
        System.out.print("请输入name:");book.setBookName(Book.sc.next());
        for(int i=0;i<6;i++){
            book.addRating();
        }
        System.out.println(book);
    }
}
