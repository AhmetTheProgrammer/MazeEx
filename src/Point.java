public class Point {
    private int xCor;
    private int yCor;
    Point(){

    }
    Point(int xCor, int yCor){
        this.xCor = xCor;
        this.yCor = yCor;
    }
    public int getxCor() {
        return xCor;
    }

    public void setxCor(int xCor) {
        this.xCor = xCor;
    }

    public int getyCor() {
        return yCor;
    }

    public void setyCor(int yCor) {
        this.yCor = yCor;
    }
    @Override
    public boolean equals(Object obj) {
        Point other = (Point) obj;
        return xCor == other.getxCor() && yCor == other.getyCor();
    }

    @Override
    public String toString() {
        return "Postion(" + xCor + ", " + yCor + ")";
    }

    public static void main(String[] args) {
        Point current = new Point(0, 1);
        Point next = new Point(0, 1);

        System.out.println(current.equals(next));
    }
}
