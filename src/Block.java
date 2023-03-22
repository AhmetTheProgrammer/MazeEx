import java.awt.*;

public class Block extends PhysicMember{
    public int state;
    public String goBack;
    private Point point;
    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public static final int BLOCK_WIDHT = 30;
    private Rectangle block;

    public Block(){

    }
    public Block(int xCor, int yCor,int x, int y, int widht, int height,int state) {
        this.state = state;
        this.point = new Point();
        this.getPoint().setxCor(xCor);
        this.getPoint().setyCor(yCor);
        block = new Rectangle(x, y , widht, height);
    }

    public void move(Point toGo,Grid grid, Robot robot,Light light, Main panel){
        if( (Math.abs(robot.getPoint().getxCor() - toGo.getxCor()) + Math.abs(robot.getPoint().getyCor() - toGo.getyCor())) == 1 ){
            whereToGo(robot, light, panel , toGo, grid);
        }
        else{//geri dön
            while (!( (Math.abs(robot.getPoint().getxCor() - toGo.getxCor()) + Math.abs(robot.getPoint().getyCor() - toGo.getyCor())) == 1 )){
                comeBack(robot, grid, light, panel);
            }
            whereToGo(robot, light, panel, toGo ,grid);
        }
    }

    public void whereToGo(Robot robot, Light light, Main panel, Point toGo, Grid grid){
        if(robot.getPoint().getxCor() == toGo.getxCor()){//i'ler eşitse
            if(robot.getPoint().getyCor() > toGo.getyCor()){//sola git
                if(grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack == null){
                    grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack = "right";
                }
                left(robot, light, panel);
            }
            else{//sağa git
                if(grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack == null){
                    grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack = "left";
                }
                right(robot, light, panel);
            }
        }
        else if(robot.getPoint().getyCor() == toGo.getyCor()){//j'ler eşitse
            if(robot.getPoint().getxCor() > toGo.getxCor()){//yukarı git
                if(grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack == null){
                    grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack = "down";
                }
                up(robot, light, panel);
            }
            else{//aşağı git
                if(grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack == null){
                    grid.getBlocks()[toGo.getxCor()][toGo.getyCor()].goBack = "up";
                }
                down(robot, light, panel);
            }
        }
    }
    public void comeBack(Robot robot, Grid grid,Light light, Main panel){
        if(grid.getBlocks()[robot.getPoint().getxCor()][robot.getPoint().getyCor()].goBack.equals("right")){
            right(robot, light, panel);
        }
        else if(grid.getBlocks()[robot.getPoint().getxCor()][robot.getPoint().getyCor()].goBack.equals("left")){
            left(robot, light, panel);
        }
        else if(grid.getBlocks()[robot.getPoint().getxCor()][robot.getPoint().getyCor()].goBack.equals("down")){
            down(robot, light, panel);
        }
        else if(grid.getBlocks()[robot.getPoint().getxCor()][robot.getPoint().getyCor()].goBack.equals("up")){
            up(robot, light, panel);
        }
        else{
            System.out.println("NULL AMINA KOYİM");
        }
    }
    public void left(Robot robot, Light light,Main panel){
        int moveController = 0;
        robot.getPoint().setyCor(robot.getPoint().getyCor() - 1);
        while (moveController <= Block.BLOCK_WIDHT){
            moveController++;
            robot.getBlock().setLocation((int) (robot.getBlock().getX() - 1), (int) (robot.getBlock().getY()));
            light.updateLightPos(robot);
            panel.updateGraphics();
        }
    }
    public void right(Robot robot, Light light,Main panel){
        int moveController = 0;
        robot.getPoint().setyCor(robot.getPoint().getyCor() + 1);
        while (moveController <= Block.BLOCK_WIDHT){
            moveController++;
            robot.getBlock().setLocation((int) (robot.getBlock().getX() + 1), (int) (robot.getBlock().getY()));
            light.updateLightPos(robot);
            panel.updateGraphics();
        }
    }
    public void up(Robot robot, Light light,Main panel){
        int moveController = 0;
        robot.getPoint().setxCor(robot.getPoint().getxCor() - 1);
        while (moveController <= Block.BLOCK_WIDHT){
            moveController++;
            robot.getBlock().setLocation((int) (robot.getBlock().getX()), (int) (robot.getBlock().getY() - 1));
            light.updateLightPos(robot);
            panel.updateGraphics();
        }
    }
    public void down(Robot robot, Light light,Main panel){
        int moveController = 0;
        robot.getPoint().setxCor(robot.getPoint().getxCor() + 1);
        while (moveController <= Block.BLOCK_WIDHT){
            moveController++;
            robot.getBlock().setLocation((int) (robot.getBlock().getX()), (int) (robot.getBlock().getY() + 1));
            light.updateLightPos(robot);
            panel.updateGraphics();
        }
    }
    public void shortMove(Robot robot, Point toGo,Light light, Main panel){
        if(robot.getPoint().getxCor() == toGo.getxCor()){//i'ler eşitse
            if(robot.getPoint().getyCor() > toGo.getyCor()){//sola git
                left(robot, light, panel);
            }
            else{//sağa git
                right(robot, light, panel);
            }
        }
        else if(robot.getPoint().getyCor() == toGo.getyCor()){//j'ler eşitse
            if(robot.getPoint().getxCor() > toGo.getxCor()){//yukarı git
                up(robot, light, panel);
            }
            else{//aşağı git
                down(robot, light, panel);
            }
        }
    }
    public void drawtoScreen(Graphics g) {
        if(this.state == 0){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        if(this.state == 1){
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) block.getX() , (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 2){
            g.setColor(Color.GREEN);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 3){
            g.setColor(Color.RED);
            g.fillRect((int) block.getX(), (int) block.getY() , (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 4){
            g.setColor(Color.BLUE);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 5){
            g.setColor(Color.yellow);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 6){
            g.setColor(Color.magenta);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
    }
    public Rectangle getBlock() {
        return block;
    }

    public void setBlock(Rectangle block) {
        this.block = block;
    }
}
