package e3;

public class CollegeGraduate extends Person{

    private double gpa;
    private int graduateYear;

    @Override
    public String toString() {
        return "CollegeGraduate{" +

                "name="+super.getName()+
                ",age="+(2026-super.getYear())+
                ",gpa=" + this.gpa +
                ", graduateYear=" + this.graduateYear +

                '}';
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }
}
