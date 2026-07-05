package e4;

public class Square implements TwoDimensionalShape{

    double side;
    public Square(){
        System.out.print("请输入正方形边长:");
        side=sc.nextDouble();
    }
    @Override
    public double getArea() {
        sc.nextLine();
        return side*side;
    }

    @Override
    public double getPerimeter() {
        return side*4;
    }

    @Override
    public String toString() {
        return "square`s area="+this.getArea()+
                ",perimeter="+this.getPerimeter();
    }
}
