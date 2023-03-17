import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class RandomMaze {
    private int size;
    private Random random;
    private int[][] array;
    private boolean[][] visitted;
    private boolean[][] visitted_way;
    private int paths = 0;
    private int steps = 0;

    public RandomMaze(int size) {
        if(size % 2 == 0) size++;
        this.size = size;
        random = new Random();
        array = new int[size + 2][size + 2];
        visitted = new boolean[size + 2][size + 2];
        visitted_way = new boolean[size + 2][size + 2];

        generate();
    }

    public int[][] getArray() {
        return this.array;
    }

    private boolean canUp(Point Point) {
        return Point.getxCor() - 2 > 0;
    }

    private boolean canRight(Point Point) {
        return Point.getyCor() + 2 <= size;
    }

    private boolean canDown(Point Point) {
        return Point.getxCor() + 2 <= size;
    }

    private boolean canLeft(Point Point) {
        return Point.getyCor() - 2 > 0;
    }

    private Point randNext(Point current) {
        ArrayList<Integer> nexts = new ArrayList<Integer>();
        if (canUp(current))
            nexts.add(0);
        if (canRight(current))
            nexts.add(1);
        if (canDown(current))
            nexts.add(2);
        if (canLeft(current))
            nexts.add(3);

        int value = nexts.get(random.nextInt(nexts.size()));
        switch (value) {
            case 0: {

                return new Point(current.getxCor() - 2, current.getyCor());
            }
            case 1: {
                return new Point(current.getxCor(), current.getyCor() + 2);
            }
            case 2: {

                return new Point(current.getxCor() + 2, current.getyCor());
            }
            case 3: {
                return new Point(current.getxCor(), current.getyCor() - 2);
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + value);
        }
    }

    private void creatPath(Point current, Point next) {
        int x = (current.getxCor() + next.getxCor()) / 2;
        int y = (current.getyCor() + next.getyCor()) / 2;

        array[x][y] = 0;
    }

    public boolean canMoveUp(Point Point) {
        return Point.getxCor() - 1 > 0 && array[Point.getxCor() - 1][Point.getyCor()] == 0;
    }

    public boolean canMoveRight(Point Point) {
        return Point.getyCor() + 1 <= size + 1 && array[Point.getxCor()][Point.getyCor() + 1] == 0;
    }

    public boolean canMoveDown(Point Point) {
        return Point.getxCor() + 1 <= size && array[Point.getxCor() + 1][Point.getyCor()] == 0;
    }

    public boolean canMoveLeft(Point Point) {
        return Point.getyCor() - 1 > 0 && array[Point.getxCor()][Point.getyCor() - 1] == 0;
    }

    public void generate() {
        // First settup
        for (int i = 0; i < size + 2; i++) {
            array[0][i] = 1;
            array[size + 1][i] = 1;
            array[i][0] = 1;
            array[i][size + 1] = 1;
        }

        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    array[i][j] = 0;
                    visitted[i][j] = false;
                    paths++;
                    visitted_way[i][j] = false;
                } else {
                    array[i][j] = 1;
                }
            }
        }

        array[1][0] = 0;
        array[size][size + 1] = 0;

        // Draw matrix
        int x_current = 1, y_current = 1;
        Point current = new Point(x_current, y_current);
        int visitedCell = 1;
        visitted[x_current][y_current] = true;
        while (visitedCell < paths) {
            steps++;
            Point next = randNext(current);
            int x_next = next.getxCor();
            int y_next = next.getyCor();

            if (visitted[x_next][y_next] == false) {
                visitted[x_next][y_next] = true;
                visitedCell++;
                creatPath(current, next);
            }

            // Update current Point
            current.setxCor(next.getxCor());
            current.setyCor(next.getyCor());
        }
    }

    public void printVisitted() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (visitted[i][j] == true) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    public void print() {
        for (int i = 0; i < size + 2; i++) {
            for (int j = 0; j < size + 2; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean canMove(Point current, Point next) {
        ArrayList<Point> nexts = new ArrayList<Point>();
        if (canMoveUp(current))
            nexts.add(new Point(current.getxCor() - 1, current.getyCor()));
        if (canMoveRight(current))
            nexts.add(new Point(current.getxCor(), current.getyCor() + 1));
        if (canMoveDown(current))
            nexts.add(new Point(current.getxCor() + 1, current.getyCor()));
        if (canMoveLeft(current))
            nexts.add(new Point(current.getxCor(), current.getyCor() - 1));
        return nexts.contains(next);
    }

    private void resetVisittedWay() {
        for (int i = 0; i < size + 2; i++) {
            for (int j = 0; j < size + 2; j++) {
                visitted_way[i][j] = false;
            }
        }
    }

    public Stack<Point> getWay(Point start, Point end) {
        Stack<Point> visitted_pos = new Stack<Point>();
        Stack<Point> valid_pos = new Stack<Point>();

        Point current = start;
        visitted_way[1][0] = true;
        visitted_pos.push(new Point(start.getxCor(), start.getyCor()));
        while (!current.equals(end)) {
            int x = current.getxCor();
            int y = current.getyCor();
            if (canMoveUp(current) && visitted_way[x - 1][y] == false)
                valid_pos.push(new Point(x - 1, y));
            if (canMoveLeft(current) && visitted_way[x][y - 1] == false)
                valid_pos.push(new Point(x, y - 1));
            if (canMoveRight(current) && visitted_way[x][y + 1] == false)
                valid_pos.push(new Point(x, y + 1));
            if (canMoveDown(current) && visitted_way[x + 1][y] == false)
                valid_pos.push(new Point(x + 1, y));

            current = valid_pos.pop();
            visitted_pos.push(new Point(current.getxCor(), current.getyCor()));
            x = current.getxCor();
            y = current.getyCor();

            visitted_way[x][y] = true;
        }
        Stack<Point> way = new Stack<Point>();
        while (!visitted_pos.empty()) {
            way.push(visitted_pos.pop());
        }
        resetVisittedWay();
        return way;
    }

    public Stack<Point> getDirectWay(Point start, Point end) {
        Stack<Point> visitted_pos = getWay(start, end);
        Stack<Point> revrse_way = new Stack<Point>();
        while (!visitted_pos.empty()) {
            revrse_way.push(visitted_pos.pop());
        }
        visitted_pos.push(new Point(end.getxCor(), end.getyCor()));
        revrse_way.pop();
        Point current = end;
        while (!revrse_way.empty()) {
            current = revrse_way.pop();
            Point next = visitted_pos.peek();

            if (canMove(current, next)) {
                visitted_pos.push(new Point(current.getxCor(), current.getyCor()));
            }
        }
        return visitted_pos;
    }
    public void compareWays(Point start, Point end){
        Stack<Point> directWay = getDirectWay(start, end);
        Stack<Point> way = getWay(start, end);

        for(int i = 0; i < way.size(); i++){
            for(int j = 0; j < directWay.size(); j++) {

            }
        }

    }
    public static int[][] randomMatrixGenerator(int size,RandomMaze maze){
        int[][] arrayDene;
        arrayDene = maze.getArray();
        for(int i = 0; i < arrayDene.length; i++){
            for(int j  = 0; j < arrayDene.length; j++){
                //System.out.print(arrayDene[i][j]+" ");
            }
            //System.out.println();
        }
        return arrayDene;
    }

    public static void main(String[] args) {
        int size = 10;
        Point start = new Point(1, 0);
        Point end = new Point(size, size + 1);
//		int pre_time = (int) System.currentTimeMillis();

        RandomMaze maze = new RandomMaze(size);

//		Stack<Point> way = maze.getDirectWay(start, end);
//		System.out.println(way);
//		int after_time = (int) System.currentTimeMillis();

//		System.out.println(maze.steps + ": Steps");
//		System.out.println(1.0 * (after_time - pre_time) / 1000 + "s");

        //maze.print();


        System.out.println("-----------------");
        //maze.printVisitted();

    }
}

