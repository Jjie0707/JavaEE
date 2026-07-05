package e4;

public class RegularTriangle implements TwoDimensionalShape{

    double side;

    public RegularTriangle(){
        System.out.print("请输入三角形边长:");
        side=sc.nextDouble();
    }

    @Override
    public double getArea() {
        return side*side*Math.sqrt(3)/4;
    }

    @Override
    public double getPerimeter() {
        return side*3;
    }

    @Override
    public String toString() {
        return "RegularTriangle`s area="+this.getArea()+
                ",perimeter="+this.getPerimeter();
    }
}
