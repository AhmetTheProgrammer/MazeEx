import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    Block[][] forControl;
    Block finishBlock;
    Robot robot;
    Insets workingArea;
    final int targetFPS = 60;
    BufferedImage image;
    PhysicsEngine pe;
    public static void main(String[] args){
        JFrame frame = new JFrame("Maze");
        frame.setSize(1440,900);//Frame Boyutu
        frame.setLayout(null);//Component yerlerini elle vermek için
        //butonlar
        JButton buttonURL1 = new JButton("URL1");
        JButton buttonURL2 = new JButton("URL2");
        JButton randomButton = new JButton("RANDOM");

        //kaç kere olduğunu yazdıracağımız textfield
        JTextField text = new JTextField();
        text.setBounds(800,300,100,25);
        frame.add(text);
        //butonların yerleri
        buttonURL1.setBounds(1280,300,100,25);
        buttonURL2.setBounds(1080,300,100,25);
        randomButton.setBounds(1080,400,100,25);

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
                    int widht = (matrix.length * (Block.BLOCK_WIDHT + 1)) + 2;
                    int height = (matrix[0].length * (Block.BLOCK_WIDHT + 1)) + 2;
                    int[][] solutionMatrix = new int[matrix.length][matrix.length];//çözüm matrisi, 0-1 çevirdik
                    solutionMatrix = Functions.generateSolutionMatrix(matrix,solutionMatrix);

                    Grid grid = new Grid(matrix);
                    Block startingBlock = grid.setStartLocation(grid.getBlocks());
                    Block finishBlock = grid.setStartLocation(grid.getBlocks());
                    Robot robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                            (int) startingBlock.getBlock().getY(),Block.BLOCK_WIDHT,Block.BLOCK_WIDHT,4);;

                    int [] start = {robot.getPoint().getxCor(), robot.getPoint().getyCor()};
                    int [] finish = {finishBlock.getPoint().getxCor(), finishBlock.getPoint().getyCor()};

                    ArrayList<Point> points = MazeSolver.shortestPath(solutionMatrix,start,finish);
                    Collections.reverse(points);
                    for(int i = 0; i < points.size();i++){
                        int tempX = points.get(i).getyCor();
                        int tempY = points.get(i).getxCor();
                        points.get(i).setxCor(tempX);
                        points.get(i).setyCor(tempY);
                    }

                    Stack<Point> points1 = new Stack<>();

                    points1.addAll(points);

                    BufferedImage image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

                    Block[][] forControl;
                    forControl = grid.getBlocks();

                    Main main = new Main(widht,height,matrix,solutionMatrix,grid,robot,finishBlock,points1,image,forControl);
                    frame.add(main);
                    main.loop();
                    text.setText("Geçilen Kare:"+(MazeSolver.total));
                    text.repaint();
                    main.setVisible(false);
                }
            }
        });/*
        buttonURL2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Panel değişkenleri set edilecek
                if(e.getActionCommand().equals("URL2")){
                    int[][] matrix = ReadFromNet.readURL2();
                    int widht = ((matrix.length) * 36) + 2;
                    int height = (matrix[0].length * 36) + 2;
                    int[][] solutionMatrix = new int[matrix.length][matrix.length];//çözüm matrisi, 0-1 çevirdik
                    solutionMatrix = Functions.generateSolutionMatrix(matrix,solutionMatrix);

                    Grid grid = new Grid(matrix);
                    Block startingBlock = grid.setStartLocation(grid.getBlocks());
                    Block finishBlock = grid.setStartLocation(grid.getBlocks());
                    Robot robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                            (int) startingBlock.getBlock().getY(),35,35,4);;

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

                    Main main = new Main(widht,height,matrix,solutionMatrix,grid,robot,finishBlock,points,image,forControl);
                    frame.add(main);
                    main.loop();
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
        });*/
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("RANDOM")){

                    RandomMaze rand = new RandomMaze(7);
                    int[][] matrix = RandomMaze.randomMatrixGenerator(7,rand);
                    for(int i = 0; i < matrix.length; i++){
                        for(int j = 0; j < matrix.length; j++){
                            System.out.print(matrix[i][j]+" ");
                        }
                        System.out.println();
                    }
                    int widht = ((matrix.length) * (Block.BLOCK_WIDHT + 1)) + 2;
                    int height = ((matrix[0].length) * (Block.BLOCK_WIDHT + 1)) + 2;
                    int[][] solutionMatrix = new int[matrix.length][matrix.length];//çözüm matrisi, 0-1 çevirdik
                    solutionMatrix = Functions.generateSolutionMatrix(matrix,solutionMatrix);

                    Grid grid = new Grid(matrix);
                    Block startingBlock = grid.getBlocks()[1][0];//sol üst
                    Block finishBlock = grid.getBlocks()[matrix.length -2][matrix.length -1];//sağ alt
                    Robot robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                            (int) startingBlock.getBlock().getY(),Block.BLOCK_WIDHT,Block.BLOCK_WIDHT,4);;

                    int [] start = {robot.getPoint().getxCor(), robot.getPoint().getyCor()};
                    int [] finish = {finishBlock.getPoint().getxCor(), finishBlock.getPoint().getyCor()};

                    Stack<Point> points =  rand.getWay(robot.getPoint(),finishBlock.getPoint());
                    Collections.reverse(points);

                    for(int i = 0; i < points.size(); i++){
                        System.out.println(points.get(i));
                    }

                    BufferedImage image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

                    Block[][] forControl;
                    forControl = grid.getBlocks();

                    Main main = new Main(widht,height,matrix,solutionMatrix,grid,robot,finishBlock,points,image,forControl);
                    frame.add(main);
                    main.loop();
                    text.setText("Geçilen Kare:"+(MazeSolver.total));
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
    public Main(int widht, int height,int[][] matrix, int[][] solutionMatrix, Grid grid,Robot robot,Block finishBlock,
               Stack<Point> points, BufferedImage image,Block[][] forControl){
        this.widht = widht;
        this.height = height;
        this.matrix = matrix;
        this.solutionMatrix = solutionMatrix;
        this.grid = grid;
        this.robot = robot;
        this.finishBlock = finishBlock;
        this.points = points;
        this.workingArea = getInsets();
        this.image = image;
        this.pe = new PhysicsEngine();
        this.forControl = forControl;
        grid.getBlocks()[finishBlock.getPoint().getxCor()][finishBlock.getPoint().getyCor()].state = 6;//Bitiş bloğu belli olsun
        pe.addMember(grid);
        setSize(widht + workingArea.right + workingArea.left,
                height + workingArea.bottom + workingArea.top);
        setVisible(true);
        light = new Light(this,robot);
        pe.addMember(light);
        pe.addMember(finishBlock);
        pe.addMember(robot);
    }
    private void loop(){
        long targetTime = 1_000_000_000 / targetFPS;
        boolean konrolcü = true;
        while(konrolcü) {
            updateGraphics();
            if(actionCounter == 0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            String direction = "sa";
            long startingOfLoop = System.nanoTime();

            actionCounter++;
            if (actionCounter %1 == 0) {//niye var çözemedim
                if (points.size() != 0) {
                    direction = robot.move(Block.BLOCK_WIDHT, Block.BLOCK_WIDHT, forControl, points.get(0));
                    points.remove(0);
                }
                updateGameLogic();

                int moveController = 0;
                if (direction.equals("sağ")) {
                    while (moveController <= Block.BLOCK_WIDHT) {
                        moveController++;
                        robot.getBlock().setLocation((int) (robot.getBlock().getX() + 1), (int) (robot.getBlock().getY()));
                        light.updateLightPos(robot);
                        updateGraphics();
                    }
                    paintGrid();
                }
                if (direction.equals("sol")) {
                    while (moveController <= Block.BLOCK_WIDHT) {
                        moveController++;
                        robot.getBlock().setLocation((int) (robot.getBlock().getX() - 1), (int) (robot.getBlock().getY()));
                        light.updateLightPos(robot);
                        updateGraphics();
                    }
                    paintGrid();
                }
                if (direction.equals("yukarı")) {
                    while (moveController <= Block.BLOCK_WIDHT) {
                        moveController++;
                        robot.getBlock().setLocation((int) (robot.getBlock().getX()), (int) (robot.getBlock().getY() - 1));
                        light.updateLightPos(robot);
                        updateGraphics();
                    }
                    paintGrid();
                }
                if (direction.equals("aşağı")) {
                    while (moveController <= Block.BLOCK_WIDHT) {
                        moveController++;
                        robot.getBlock().setLocation((int) (robot.getBlock().getX()), (int) (robot.getBlock().getY() + 1));
                        light.updateLightPos(robot);
                        updateGraphics();
                    }
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
            if(robot.getBlock().getX() == finishBlock.getBlock().getX() && robot.getBlock().getY() == finishBlock.getBlock().getY()){
                pe.members.remove(light);
                updateGraphics();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                konrolcü = false;
            }
        }
    }

    private void updateGraphics() {
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