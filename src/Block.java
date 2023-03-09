import javax.swing.*;
import java.awt.*;
public class Block extends PhysicMember{
    public static final int BLOCK_WIDHT =35;
    private Rectangle block;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Block(){

    }
    public Block(int x, int y, int widht, int height,int state) {
        block = new Rectangle(x, y , widht, height);
        this.state = state;
    }
    public void move(int x, int y, Block[][] others){
        Rectangle temp = new Rectangle(block);
        temp.setLocation((int)temp.getX() + x, (int)temp.getY() + y);
        boolean intersect = false;

        for(int i = 0; i < others.length; i++){
            for(int j = 0; j < others.length; j ++){
                if(temp.intersects(others[i][j].getBlock())){
                    if(others[i][j].getState() != 0)
                        intersect = true;
                }
            }
        }
        if(intersect == false){
            block.setLocation((int) (block.getX() + x), (int) block.getY() + y);
        }
    }
    public void drawtoScreen(Graphics g) {
        if(this.state == 0){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        if(this.state == 1){
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 2){
            g.setColor(Color.GREEN);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 3){
            g.setColor(Color.RED);
            g.fillRect((int) block.getX(), (int) block.getY(), (int) block.getWidth(), (int) block.getHeight());
        }
        else if(this.state == 4){
            g.setColor(Color.PINK);
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
