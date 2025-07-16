package Structure;

import java.awt.Color;
import java.awt.Graphics;

public class DragZone {
    private int x, y, w, h;
    private Runnable action;

    public DragZone(){
        x = 0;
        y = 0;
        w = 50;
        h = 50;
        action = () -> {
            //System.out.println("Empty constructor call in DragZone.java");
        };
    }

    public DragZone(int _x, int _y, int _x2, int _y2, Runnable _action){
        x = _x;
        y = _y;
        w = _x2-x;
        h = _y2-y;
        action = _action;
    }

    public void showDragZone(Color color){
        Game.Graphics().setColor(color);
        Game.Graphics().drawRect(x, y, w, h);
    }

    public int width(){
        return w;
    }

    public int height(){
        return h;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }
}
