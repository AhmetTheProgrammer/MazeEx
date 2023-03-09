import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PhysicsEngine {
    ArrayList<PhysicMember> members;

    public PhysicsEngine(){
        members = new ArrayList<PhysicMember>();
    }
    public void addMember(PhysicMember member){
        members.add(member);
    }
    public void drawtoScreen(Graphics g ){
        Iterator<PhysicMember> iterator = members.iterator();
        while (iterator.hasNext()){
            PhysicMember current = iterator.next();
            current.drawtoScreen(g);
        }
    }
    public void update(){

    }
}
