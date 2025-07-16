package Structure;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import Elements.DataCache;
import Elements.GraphicsDatabase;

public class ScrollWindow {
    private int offset;
    private ArrayList<ClickableTask> buttons = new ArrayList<ClickableTask>();
    private int x, y, w, h;
    private boolean hover;

    public ScrollWindow(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void drawWindow(){
        Shape oldClip = Game.Graphics().getClip();
        Game.Graphics().setClip(x, y, w, h);
        for(ClickableTask b: buttons){
            b.drawButton(offset);
        }
        if(DataCache.showTaskCount){
            Game.Graphics().setColor(GraphicsDatabase.green9);
            Game.Graphics().fillRect(x + (int)(w/2), y+h-45, (int)(w-10)/2, 40);
            Game.Graphics().setColor(GraphicsDatabase.green1);
            Game.Graphics().setFont(new Font("Arial", Font.ITALIC, 15));
            Game.Graphics().drawString(buttons.size() +" tasks", x+10 + (int)(w/2), y+h-20);
        }
        Game.Graphics().setClip(oldClip);
    }

    public void checkHover(int x, int y){
        Rectangle me = new Rectangle(this.x, this.y, w, h);
        Rectangle mouse = new Rectangle(x, y, 1, 1);
        hover = mouse.intersects(me);
    }

    public void scroll(int amount){
        if(hover){
            boolean canScrollUp = buttons.get(buttons.size()-1).y() + buttons.get(buttons.size()-1).h() + offset > y + h;
            boolean canScrollDown = buttons.get(0).y() + offset < y;

            if(canScrollUp && amount > 0 || canScrollDown && amount < 0){
                offset -= amount;
            }
        }
    }

    public ArrayList<ClickableTask> buttons(){
        return buttons;
    }

    public void setButtons(ArrayList<ClickableTask> buttons){
        offset = 0;
        this.buttons = buttons;
    }

    public int offset(){
        return offset;
    }

    public void offset(int amount){
        offset += amount;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }
    
    public int w(){
        return w;
    }

    public int h(){
        return h;
    }

    public boolean hover(){
        return hover;
    }
}
