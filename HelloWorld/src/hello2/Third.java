package hello2;
import java.awt.*;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import hello1.*;

abstract class Action {
    protected int x, y;
    public Action(int x, int y) {
        this.x=x; this.y=y;
    }
    public void moveTo(Graphics g, int x1, int y1) {
        erase(g); x=x1; y=y1; draw(g);
    }
    public abstract void erase(Graphics g);
    public abstract void draw(Graphics g);
}

class DrawCircle extends Action {
    int radius;
    public DrawCircle(int x, int y, int r) {
        super(x, y); radius=r;
    }
    public void draw(Graphics g) {
        System.out.println("Draw circle at (" + x + ", " + y + ")");
        g.drawOval(x-radius, y-radius, 2*radius, 2*radius);
    }
    public void erase(Graphics g) {
        System.out.println("Erase circle at (" + x + ", " + y +")");
    }
}

public class Third {
    public static void main(String[] args) {

    }
}
