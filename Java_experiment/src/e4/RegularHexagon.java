package e4;

public class RegularHexagon implements TwoDimensionalShape{

    double side;
    public RegularHexagon(){
        System.out.print("请输入六边形边长:");
        side=sc.nextDouble();
    }

    @Override
    public double getArea() {
        return side*side*3*Math.sqrt(3)/2;
    }

    @Override
    public double getPerimeter() {
        return 6*side;
    }

    @Override
    public String toString() {
        return "RegularHexagon`s area="+this.getArea()+
                ",perimeter="+this.getPerimeter();
    }
}
