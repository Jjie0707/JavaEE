package e4;

public class TwoDimensionalShapeTest {
    public static void main(String[] args) {
        TwoDimensionalShape[] arr=new TwoDimensionalShape[3];
        arr[0]=new RegularHexagon();
        arr[1]=new Square();
        arr[2]=new RegularTriangle();
        for(TwoDimensionalShape shape:arr){
            System.out.println(shape);
        }
    }
}
