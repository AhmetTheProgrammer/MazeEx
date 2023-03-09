import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Main extends JPanel implements KeyListener,ActionListener{
        JButton button;
        Block[][] forControl;
        Robot robot;
        final static int widht = 1440;
        final static int height = 900;
        Insets workingArea;
        final int targetFPS = 60;
        BufferedImage image;
        PhysicsEngine pe = new PhysicsEngine();
    public static void main(String[] args){
        JFrame frame = new JFrame("Maze");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Main main = new Main();

        frame.add(main);
        frame.pack();
        frame.addKeyListener(main);
        main.loop();
    }
    public Main(){
        button = new JButton("URL DEĞİŞ");
        button.setBounds(700,300,400,25);
        System.out.println(button.getX());
        add(button);
        button.addActionListener(this);

        setPreferredSize(new Dimension(widht,height));//panel boyutu
        addKeyListener(this);
        workingArea = getInsets();
        setSize(widht + workingArea.right + workingArea.left,
                height + workingArea.bottom + workingArea.top);
        setVisible(true);

        int[][] matrix = ReadFromNet.readURL();//Urlyi oku
        image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        Grid grid = new Grid(matrix);//ızgara oluştur

        forControl = new Block[matrix.length][matrix.length];//kesişimler için kontrol blokları
        forControl = grid.getBlocks();
        pe.addMember(grid);
        Block startingBlock = grid.setStartLocation(forControl);

        robot = new Robot((int) startingBlock.getBlock().getX() + 2, (int) startingBlock.getBlock().getY() + 1,33,33,4);
        pe.addMember(robot);
    }
    private void loop(){
        long targetTime = 1000000000 / targetFPS;
        while(true){
            long startingOfLoop = System.nanoTime();

            updateGameLogic();
            updateGraphics();
            long remainingTime = targetTime -(System.nanoTime() - startingOfLoop);

            try {
                if(remainingTime > 0)
                    Thread.sleep(remainingTime / 1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateGraphics() {
        Graphics frameGraphic = getGraphics();
        Graphics bufferGraphic = image.getGraphics();
        Graphics buttonG = button.getGraphics();

        bufferGraphic.setColor(Color.WHITE);
        bufferGraphic.fillRect(0,0, widht, height);
        bufferGraphic.setColor(Color.BLACK);
        button.paint(buttonG);
        pe.drawtoScreen(bufferGraphic);


        frameGraphic.drawImage(image, workingArea.left, workingArea.top,this);
    }

    private void updateGameLogic() {
        pe.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == e.VK_RIGHT){
            robot.move(35, 0, forControl );
        }
        if(e.getKeyCode() == e.VK_LEFT){
            robot.move(-35, 0, forControl);
        }
        if(e.getKeyCode() == e.VK_UP){
            robot.move(0, -35 , forControl);
        }
        if(e.getKeyCode() == e.VK_DOWN){
            robot.move(0, 35 , forControl);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("URL DEĞİŞ")){
            System.out.println("sa");
        }
    }
}
