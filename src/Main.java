import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Main extends JPanel{
    Light light;
    int widht ;
    int height ;
    int[][] matrix;
    int[][] solutionMatrix;
    Grid grid;
    int actionCounter = 0;
    Stack<Point> points;
    ArrayList<Point> shortPoints;
    Block[][] forControl;
    Block startingBlock;
    Block finishBlock;
    Robot robot;
    Insets workingArea;
    final int targetFPS = 60;
    BufferedImage image;
    PhysicsEngine pe;
    public static void main(String[] args){
        JFrame frame = new JFrame("Maze");
        frame.setSize(1920,1080);//Frame Boyutu
        frame.setLayout(null);//Component yerlerini elle vermek için
        //butonlar
        JButton buttonURL1 = new JButton("URL1");
        JButton buttonURL2 = new JButton("URL2");
        JButton randomButton = new JButton("RANDOM");

        //kaç kere olduğunu yazdıracağımız textfield
        JTextField text = new JTextField();
        text.setBounds(1400,400,100,25);
        frame.add(text);
        //butonların yerleri
        buttonURL1.setBounds(1400,100,100,25);
        buttonURL2.setBounds(1400,200,100,25);
        randomButton.setBounds(1400,300,100,25);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //butonlar frame'e eklendi ve gözüksün diye frame repaint edildi
        frame.add(buttonURL1);
        frame.add(buttonURL2);
        frame.add(randomButton);
        buttonURL1.addActionListener(new ActionListener() {//Butona tıklanınca ne olucak
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("URL1")){
                    int[][] matrix = ReadFromNet.readURL();
                    if(matrix.length <= 10){
                        Block.BLOCK_WIDHT = 45;
                    }
                    else if(matrix.length > 10 && matrix.length <= 20){
                        Block.BLOCK_WIDHT = 30;
                    }
                    else if(matrix.length > 20){
                        Block.BLOCK_WIDHT = 20;
                    }
                    int widht = (matrix.length * (Block.BLOCK_WIDHT + 1)) + 2;
                    int height = (matrix[0].length * (Block.BLOCK_WIDHT + 1)) + 2;
                    int[][] solutionMatrix = new int[matrix.length][matrix.length];
                    Grid grid = new Grid(matrix);
                    solutionMatrix = Functions.generateSolutionMatrix(matrix,solutionMatrix);//çözüm matrisi, 0-1 çevirdik


                    Block startingBlock = grid.setStartLocation(grid.getBlocks());
                    Block finishBlock = grid.setStartLocation(grid.getBlocks());
                    Robot robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                            (int) startingBlock.getBlock().getY(),Block.BLOCK_WIDHT,Block.BLOCK_WIDHT,4);;

                    int [] start = {robot.getPoint().getxCor(), robot.getPoint().getyCor()};
                    int [] finish = {finishBlock.getPoint().getxCor(), finishBlock.getPoint().getyCor()};

                    ArrayList<Point> shortPoint = MazeSolver.shortestPath(solutionMatrix, start, finish);
                    Collections.reverse(shortPoint);
                    for(int i = 0; i < shortPoint.size();i++){
                        int tempX = shortPoint.get(i).getyCor();
                        int tempY = shortPoint.get(i).getxCor();
                        shortPoint.get(i).setxCor(tempX);
                        shortPoint.get(i).setyCor(tempY);
                    }

                    BufferedImage image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

                    Block[][] forControl;
                    forControl = grid.getBlocks();

                    Main main = new Main(widht,height,matrix,solutionMatrix,grid,robot,startingBlock,finishBlock,shortPoint,image,forControl);
                    frame.add(main);
                    main.loop1();
                    text.setText("Geçilen Kare:"+(MazeSolver.total));
                    text.repaint();
                    main.setVisible(false);
                }
            }
        });
        buttonURL2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Panel değişkenleri set edilecek
                if(e.getActionCommand().equals("URL2")){
                    int[][] matrix = ReadFromNet.readURL2();
                    if(matrix.length <= 10){
                        Block.BLOCK_WIDHT = 45;
                    }
                    else if(matrix.length > 10 && matrix.length <= 20){
                        Block.BLOCK_WIDHT = 30;
                    }
                    else if(matrix.length > 20){
                        Block.BLOCK_WIDHT = 20;
                    }
                    int widht = (matrix.length * (Block.BLOCK_WIDHT + 1)) + 2;
                    int height = (matrix[0].length * (Block.BLOCK_WIDHT + 1)) + 2;
                    int[][] solutionMatrix = new int[matrix.length][matrix.length];
                    Grid grid = new Grid(matrix);
                    solutionMatrix = Functions.generateSolutionMatrix(matrix,solutionMatrix);//çözüm matrisi, 0-1 çevirdik

                    Block startingBlock = grid.setStartLocation(grid.getBlocks());
                    Block finishBlock = grid.setStartLocation(grid.getBlocks());
                    Robot robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                            (int) startingBlock.getBlock().getY(),Block.BLOCK_WIDHT,Block.BLOCK_WIDHT,4);;

                    int [] start = {robot.getPoint().getxCor(), robot.getPoint().getyCor()};
                    int [] finish = {finishBlock.getPoint().getxCor(), finishBlock.getPoint().getyCor()};

                    ArrayList<Point> points =  MazeSolver.shortestPath(solutionMatrix,start,finish);
                    Collections.reverse(points);
                    for(int i = 0; i < points.size();i++){
                        int tempX = points.get(i).getyCor();
                        int tempY = points.get(i).getxCor();
                        points.get(i).setxCor(tempX);
                        points.get(i).setyCor(tempY);
                    }
                    BufferedImage image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

                    Block[][] forControl;
                    forControl = grid.getBlocks();

                    Main main = new Main(widht,height,matrix,solutionMatrix,grid,robot,startingBlock,finishBlock,points,image,forControl);

                    frame.add(main);
                    main.loop1();
                    text.setText("Geçilen Kare:"+(MazeSolver.total));
                    text.repaint();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    main.setVisible(false);
                }
            }
        });
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("RANDOM")){
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Sayi Girin:");
                    int size = scan.nextInt();
                    if(size <= 10){
                        Block.BLOCK_WIDHT = 45;
                    }
                    else if(size > 10 && size <= 20){
                        Block.BLOCK_WIDHT = 30;
                    }
                    else if(size > 20){
                        Block.BLOCK_WIDHT = 20;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    RandomMaze rand = new RandomMaze(size);
                    int[][] matrix = RandomMaze.randomMatrixGenerator(size,rand);

                    int widht = ((matrix[0].length) * (Block.BLOCK_WIDHT + 1)) + 2;
                    int height = ((matrix.length) * (Block.BLOCK_WIDHT + 1)) + 2;
                    int[][] solutionMatrix = new int[matrix.length][matrix.length];//çözüm matrisi, 0-1 çevirdik
                    solutionMatrix = Functions.generateSolutionMatrix(matrix,solutionMatrix);
                    Grid grid = new Grid(matrix);

                    Block startingBlock = grid.getBlocks()[1][0];//sol üst
                    Block finishBlock = grid.getBlocks()[matrix.length -2][matrix.length -1];//sağ alt
                    Robot robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                            (int) startingBlock.getBlock().getY(),Block.BLOCK_WIDHT,Block.BLOCK_WIDHT,4);;

                    int [] start = {robot.getPoint().getxCor(), robot.getPoint().getyCor()};
                    int [] finish = {finishBlock.getPoint().getxCor(), finishBlock.getPoint().getyCor()};

                    ArrayList<Point> shortPoints =  MazeSolver.shortestPath(solutionMatrix,start,finish);
                    Collections.reverse(shortPoints);
                    for(int i = 0; i < shortPoints.size();i++){
                        int tempX = shortPoints.get(i).getyCor();
                        int tempY = shortPoints.get(i).getxCor();
                        shortPoints.get(i).setxCor(tempX);
                        shortPoints.get(i).setyCor(tempY);
                    }

                    Stack<Point> points =  rand.getWay(robot.getPoint(), finishBlock.getPoint());
                    Collections.reverse(points);

                    BufferedImage image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

                    Block[][] forControl;
                    forControl = grid.getBlocks();

                    Main main = new Main(widht,height,matrix,solutionMatrix,grid,robot,startingBlock,finishBlock,points,image,forControl, shortPoints);
                    frame.add(main);
                    main.loop2();
                    text.setText("Geçilen Kare:"+(solutionMatrix.length));
                    text.repaint();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                    main.setVisible(false);
                }
            }
        });
    }
    public Main(int widht, int height,int[][] matrix, int[][] solutionMatrix, Grid grid,Robot robot,Block startingBlock, Block finishBlock,
                ArrayList<Point> shortPoints, BufferedImage image,Block[][] forControl){
        this.widht = widht;
        this.height = height;
        this.matrix = matrix;
        this.solutionMatrix = solutionMatrix;
        this.grid = grid;
        this.robot = robot;
        this.startingBlock = startingBlock;
        this.finishBlock = finishBlock;
        this.shortPoints = shortPoints;
        this.workingArea = getInsets();
        this.image = image;
        this.pe = new PhysicsEngine();
        this.forControl = forControl;
        grid.getBlocks()[finishBlock.getPoint().getxCor()][finishBlock.getPoint().getyCor()].state = 6;//Bitiş bloğu belli olsun
        pe.addMember(grid);
        setBounds(0,0,widht,height);
        setVisible(true);
        light = new Light(this,robot);
        pe.addMember(light);
        pe.addMember(finishBlock);
        pe.addMember(robot);
    }
    public Main(int widht, int height,int[][] matrix, int[][] solutionMatrix, Grid grid,Robot robot,Block startingBlock, Block finishBlock,
               Stack<Point> points, BufferedImage image,Block[][] forControl, ArrayList<Point> shortPoints){
        this.widht = widht;
        this.height = height;
        this.matrix = matrix;
        this.solutionMatrix = solutionMatrix;
        this.grid = grid;
        this.robot = robot;
        this.startingBlock = startingBlock;
        this.finishBlock = finishBlock;
        this.points = points;
        this.shortPoints = shortPoints;
        this.workingArea = getInsets();
        this.image = image;
        this.pe = new PhysicsEngine();
        this.forControl = forControl;
        grid.getBlocks()[finishBlock.getPoint().getxCor()][finishBlock.getPoint().getyCor()].state = 6;//Bitiş bloğu belli olsun
        pe.addMember(grid);
        setBounds(0,0,widht,height);
        setVisible(true);
        light = new Light(this,robot);
        pe.addMember(light);
        pe.addMember(finishBlock);
        pe.addMember(robot);
    }
    private void loop2(){
        int actionCounter = 0;
        long targetTime = 1_000_000_000 / targetFPS;
        boolean konrolcü = true;
        while(konrolcü) {
            if(actionCounter == 0){
                updateGraphics();
                actionCounter++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            long startingOfLoop = System.nanoTime();

            if (actionCounter == 1) {
                if (points.size() != 0) {
                    points.remove(0);
                    robot.move(points.get(0),grid,robot,light,this);
                }
            }
            else if(actionCounter == 2){
                if(shortPoints.size() != 0){
                    shortPoints.remove(0);
                    robot.shortMove(robot,shortPoints.get(0),light,this);
                    paintGrid();
                }
            }
            long remainingTime = targetTime -(System.nanoTime() - startingOfLoop);
            try {
                if(remainingTime > 0)
                    Thread.sleep(remainingTime/1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateGraphics();
            if(robot.getBlock().getX() == finishBlock.getBlock().getX() && robot.getBlock().getY() == finishBlock.getBlock().getY()){
                actionCounter++;
                if(actionCounter > 1){
                    robot.setPoint(startingBlock.getPoint());
                    robot.getBlock().setLocation(startingBlock.getBlock().x,startingBlock.getBlock().y);
                    updateGraphics();
                }
                if(actionCounter > 2){
                    pe.members.remove(robot);
                    konrolcü = false;
                }
                pe.members.remove(light);
                updateGraphics();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
    private void loop1(){
        int actionCounter = 0;
        long targetTime = 1_000_000_000 / targetFPS;
        boolean konrolcü = true;
        while(konrolcü) {
            if(actionCounter == 0){
                updateGraphics();
                actionCounter++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            long startingOfLoop = System.nanoTime();

            if (actionCounter == 1) {
                if (shortPoints.size() != 0) {
                    shortPoints.remove(0);
                    robot.move(shortPoints.get(0),grid,robot,light,this);
                    paintGrid();
                }
            }
            long remainingTime = targetTime -(System.nanoTime() - startingOfLoop);
            try {
                if(remainingTime > 0)
                    Thread.sleep(remainingTime/1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            updateGraphics();
            if(robot.getBlock().getX() == finishBlock.getBlock().getX() && robot.getBlock().getY() == finishBlock.getBlock().getY()){
                pe.members.remove(light);
                konrolcü = false;
                updateGraphics();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void updateGraphics() {
        Graphics frameGraphic = getGraphics();
        Graphics bufferGraphic = this.image.getGraphics();

        bufferGraphic.setColor(Color.WHITE);
        //bufferGraphic.fillRect(0,0, widht, height);
        bufferGraphic.setColor(Color.BLACK);

        pe.drawtoScreen(bufferGraphic);

        frameGraphic.drawImage(this.image, workingArea.left, workingArea.top,this);
    }

    private void updateGameLogic() {
        pe.update();
    }
    public void paintGrid(){
        for(int i = 0; i < this.forControl.length; i++){
            for(int j = 0; j < this.forControl.length; j++){
                if(robot.getBlock().intersects(this.forControl[i][j].getBlock())){
                    this.forControl[i][j].state = 5;
                }
            }
        }
    }
}