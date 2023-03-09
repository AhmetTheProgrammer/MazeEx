import java.awt.*;

public abstract class PhysicMember {

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public PhysicMember(){

    }
    public PhysicMember(int x, int y, int widht, int height, int state) {

        this.state = state;
    }
    public abstract void drawtoScreen(Graphics g );

}
