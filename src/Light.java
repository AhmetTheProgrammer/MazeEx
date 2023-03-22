import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Light extends PhysicMember{
    final int circleSize = (Block.BLOCK_WIDHT * 3) + 2;
    Area lightArea, screenArea;
    Shape circle;

    public Light(Main panel, Robot robot){

        screenArea = new Area(new Rectangle(0,0, panel.widht, panel.height));

        int centerX = (int) (robot.getBlock().getX() + (Block.BLOCK_WIDHT / 2));
        int centerY = (int) (robot.getBlock().getY() + (Block.BLOCK_WIDHT / 2));

        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        circle = new Ellipse2D.Double(x, y, circleSize, circleSize);

        lightArea = new Area(circle);
    }
    public void updateLightPos(Robot robot){
        int centerX = (int) (robot.getBlock().getX() + (Block.BLOCK_WIDHT / 2));
        int centerY = (int) (robot.getBlock().getY() + (Block.BLOCK_WIDHT / 2));
        int x = centerX - (circleSize / 2); //100 circle size
        int y = centerY - (circleSize / 2);

        Shape newCircle = new Ellipse2D.Double(x , y , circleSize , circleSize);
        Area newArea = new Area(newCircle);
        //Efektif değil
        this.circle = newCircle;
        this.lightArea = new Area(circle);

        newCircle = null;//Garbage collector toplar belki diye
        newArea = null;
    }

    @Override
    public void drawtoScreen(Graphics g) {
        screenArea.subtract(lightArea);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0,0,0,0.99f));
        g2.fill(screenArea);

    }
}
