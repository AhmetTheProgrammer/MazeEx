import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends JPanel implements KeyListener,ActionListener{

        static int urlCount = 0;
        String direction;
        int actionCounter = 0;
        static ArrayList<Point> points = new ArrayList<>();
        JButton button;
        static Block[][] forControl;
        static Robot robot;
        Robot finisha;
        static int widht ;
        static int height ;
        Insets workingArea;
        final int targetFPS = 60;
        BufferedImage image;
        PhysicsEngine pe = new PhysicsEngine();
    public static void main(String[] args){
        JFrame frame = new JFrame("Maze");
        frame.setSize(1440,900);
        frame.setLayout(null);
        JButton button = new JButton("URL DEĞİŞ");

        button.setBounds(1280,300,100,25);
        System.out.println(button.getX());


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Main main = new Main();

        button.addActionListener(main);
        frame.add(button);
        frame.add(main);
        frame.addKeyListener(main);
        main.loop();
    }
    public Main(){
        int[][] matrix = ReadFromNet.readURL();//Urlyi oku
        int[][] solutionMatrix = new int[matrix.length][matrix.length];//çözüm matrisi, 0-1 çevirdik
        widht = ((matrix.length) * 36) + 2;
        height = (matrix[0].length * 36) + 2;
        setPreferredSize(new Dimension(widht,height));//panel boyutu
        addKeyListener(this);
        workingArea = getInsets();
        setSize(widht + workingArea.right + workingArea.left,
                height + workingArea.bottom + workingArea.top);
        setVisible(true);
        for(int i = 0; i < matrix.length ; i ++){
            for(int j = 0; j < matrix.length; j++){
                if(matrix[i][j] == 1){
                    solutionMatrix[i][j] = 0;
                }
                else if(matrix[i][j] == 2){
                    solutionMatrix[i][j] = 0;
                }
                else if(matrix[i][j] == 3){
                    solutionMatrix[i][j] = 0;
                }
                else{
                    solutionMatrix[i][j] = 1;
                }
            }
        }
        for(int i = 0; i< solutionMatrix.length; i++){
            for(int j = 0; j < solutionMatrix.length; j++){
                System.out.print(solutionMatrix[i][j]);
            }
            System.out.println();
        }

        image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        Grid grid = new Grid(matrix);//ızgara oluştur //labirent


        forControl = new Block[matrix.length][matrix.length];//kesişimler için kontrol blokları
        forControl = grid.getBlocks();
        pe.addMember(grid);

        Block startingBlock = grid.setStartLocation(grid.getBlocks());
        Block finishBlock = grid.setStartLocation(grid.getBlocks());

        finisha = new Robot(finishBlock.getPoint().getxCor(),finishBlock.getPoint().getyCor(),(int) finishBlock.getBlock().getX(),
                (int) finishBlock.getBlock().getY(),35,35,6);

        robot = new Robot(startingBlock.getPoint().getxCor(),startingBlock.getPoint().getyCor(),(int) startingBlock.getBlock().getX(),
                (int) startingBlock.getBlock().getY(),35,35,4);

        int [] start = {robot.getPoint().getxCor(), robot.getPoint().getyCor()};
        int [] finish = {finisha.getPoint().getxCor(), finisha.getPoint().getyCor()};


        points =  MazeSolver.shortestPath(solutionMatrix,start,finish);
        Collections.reverse(points);
        for(int i = 0; i < points.size();i++){
            int tempX = points.get(i).getyCor();
            int tempY = points.get(i).getxCor();
            points.get(i).setxCor(tempX);
            points.get(i).setyCor(tempY);
        }

        System.out.println("Başlangıç noktası x: " + startingBlock.getPoint().getxCor() + " y:" + startingBlock.getPoint().getyCor());
        System.out.println("Çıkış noktası x: " + finisha.getPoint().getxCor() + " y:" + finisha.getPoint().getyCor());
        pe.addMember(finisha);
        pe.addMember(robot);
    }
    private void loop(){
        long targetTime = 1000000000 / targetFPS;
        while(true) {
            String direction = "sa";
            long startingOfLoop = System.nanoTime();
            actionCounter++;
            if (actionCounter %1 == 0) {
                if (points.size() != 0) {
                    direction = robot.move(36, 36, forControl, points.get(0));
                    System.out.println(direction);
                    points.remove(0);
                }
                updateGameLogic();

            int moveController = 0;
            if (direction.equals("sağ")) {
                while (moveController <= 35) {
                    moveController++;
                    robot.getBlock().setLocation((int) (robot.getBlock().getX() + 1), (int) (robot.getBlock().getY()));

                    updateGraphics();
                }
                paintGrid();
            }
            if (direction.equals("sol")) {
                while (moveController <= 35) {
                    moveController++;
                    robot.getBlock().setLocation((int) (robot.getBlock().getX() - 1), (int) (robot.getBlock().getY()));

                    updateGraphics();
                }
                paintGrid();
            }
            if (direction.equals("yukarı")) {
                while (moveController <= 35) {
                    moveController++;
                    robot.getBlock().setLocation((int) (robot.getBlock().getX()), (int) (robot.getBlock().getY() - 1));

                    updateGraphics();
                }
                paintGrid();
            }
            if (direction.equals("aşağı")) {
                while (moveController <= 35) {
                    moveController++;
                    robot.getBlock().setLocation((int) (robot.getBlock().getX()), (int) (robot.getBlock().getY() + 1));

                    updateGraphics();
                }
                paintGrid();
            }
        }
            updateGraphics();
            long remainingTime = targetTime -(System.nanoTime() - startingOfLoop);

            try {
                if(remainingTime > 0)
                    Thread.sleep(remainingTime/1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateGraphics() {
        Graphics frameGraphic = getGraphics();
        Graphics bufferGraphic = image.getGraphics();

        bufferGraphic.setColor(Color.WHITE);
        //bufferGraphic.fillRect(0,0, widht, height);
        bufferGraphic.setColor(Color.BLACK);

        pe.drawtoScreen(bufferGraphic);

        frameGraphic.drawImage(image, workingArea.left, workingArea.top,this);
    }

    private void updateGameLogic() {
        pe.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    public static void paintGrid(){
        for(int i = 0; i < Main.forControl.length; i++){
            for(int j = 0; j < Main.forControl.length; j++){
                if(robot.getBlock().intersects(Main.forControl[i][j].getBlock())){
                    Main.forControl[i][j].state = 5;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == e.VK_SPACE){
            //robot.moveSlide(36,36, forControl, points);
        }
        /*
        if(e.getKeyCode() == e.VK_RIGHT){
            robot.moveSlide(36, 0, forControl,);
        }
        if(e.getKeyCode() == e.VK_LEFT){
            robot.moveSlide(-36, 0, forControl);
        }
        if(e.getKeyCode() == e.VK_UP){
            robot.moveSlide(0, -36 , forControl);
        }
        if(e.getKeyCode() == e.VK_DOWN){
            robot.moveSlide(0, 36 , forControl);
        }
        */
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("URL DEĞİŞ")){
            urlCount++;
            System.out.println(urlCount);
        }
    }
}
