import java.awt.*;
import java.util.ArrayList;

public class Block extends PhysicMember{
    public int state;
    private Point point;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public static final int BLOCK_WIDHT =35;
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
    public String  move(int xMove, int yMove, Block[][] others, Point toGo){
        Rectangle temp = new Rectangle(block);
        temp.setLocation((int)temp.getX() + xMove, (int)temp.getY() + yMove);
        boolean intersect = false;

        for(int i = 0; i < others.length; i++){
            for(int j = 0; j < others.length; j ++){
                if(temp.intersects(others[i][j].getBlock())){
                    if(others[i][j].state != 0)
                        intersect = true;
                }
            }
        }

        if(this.getPoint().getxCor() == toGo.getxCor() && this.getPoint().getyCor() == toGo.getyCor()){
            System.out.println("ilk adım");
        }
        else{
            if(this.getPoint().getxCor() == toGo.getxCor()){
                if(this.getPoint().getyCor() > toGo.getyCor()){
                    //this.block.setLocation(this.block.x -xMove , this.block.y);
                    this.point.setyCor(this.getPoint().getyCor() - 1);
                    return "sol";
                }
                else if(this.getPoint().getyCor() < toGo.getyCor()){
                    //this.block.setLocation(this.block.x + xMove, this.block.y);
                    this.point.setyCor(this.getPoint().getyCor() + 1);
                    return "sağ";
                }
            }
            if(this.getPoint().getyCor() == toGo.getyCor()){
                if(this.getPoint().getxCor() > toGo.getxCor()){
                    //this.block.setLocation(this.block.x, this.block.y - xMove);
                    this.point.setxCor(this.getPoint().getxCor() - 1);
                    return "yukarı";
                }
                else if(this.getPoint().getxCor() < toGo.getxCor()){
                    //this.block.setLocation(this.block.x, this.block.y + xMove);
                    this.point.setxCor(this.getPoint().getxCor() + 1);
                    return "aşağı";
                }
            }
        }
        /*for(int i = 0; i < points.size(); i++){
            if(this.getPoint().getxCor() == points.get(i).getxCor() && this.getPoint().getyCor() == points.get(i).getyCor()){
                System.out.println("ilk adım");
            }
            else{
                if(this.getPoint().getxCor() == points.get(i).getxCor()){
                    if(this.getPoint().getyCor() > points.get(i).getyCor()){
                        this.block.setLocation(this.block.x -xMove , this.block.y);
                        this.point.setyCor(this.getPoint().getyCor() - 1);
                        System.out.println("sola");
                    }
                    else if(this.getPoint().getyCor() < points.get(i).getyCor()){
                        this.block.setLocation(this.block.x + xMove, this.block.y);
                        this.point.setyCor(this.getPoint().getyCor() + 1);
                        System.out.println("sağa");
                    }
                }
                if(this.getPoint().getyCor() == points.get(i).getyCor()){
                    if(this.getPoint().getxCor() > points.get(i).getxCor()){
                        this.block.setLocation(this.block.x, this.block.y - xMove);
                        this.point.setxCor(this.getPoint().getxCor() - 1);
                        System.out.println("yukarı");
                    }
                    else if(this.getPoint().getxCor() < points.get(i).getxCor()){
                        this.block.setLocation(this.block.x, this.block.y + xMove);
                        this.point.setxCor(this.getPoint().getxCor() + 1);
                        System.out.println("aşağı");
                    }
                }
            }
        }*/
        return "sa";
    }
    public void moveSlide(int hareket){
        this.block.setLocation(this.block.x + hareket,this.block.y);
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
