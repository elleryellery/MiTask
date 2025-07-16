package Structure;

import java.awt.Shape;
import java.util.ArrayList;

import Elements.DataCache;
import Elements.GraphicsDatabase;

public class CalendarWindow extends ScrollWindow {
    private ArrayList<ClickableDate> dates = new ArrayList<ClickableDate>();
    
    public CalendarWindow(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    public void setDates(ArrayList<ClickableDate> dates){
        this.dates = dates;
    }

    public void drawWindow(){
        Shape oldClip = Game.Graphics().getClip();
        Game.Graphics().setClip(this.x(), this.y(), this.w(), this.h());
        
        for(ClickableDate b: dates){
            b.drawButton(this.offset());
        }
        Game.Graphics().setClip(oldClip);
    }

    public ArrayList<ClickableDate> dates(){
        return dates;
    }

    public void scroll(int amount){
        if(this.hover()){
            boolean canScrollUp = dates.get(dates.size()-1).y() + dates.get(dates.size()-1).h() + this.offset() > this.y() + this.h();
            boolean canScrollDown = dates.get(0).y() + this.offset() < this.y();

            if(canScrollUp && amount > 0 || canScrollDown && amount < 0){
                this.offset(-amount);
            }
        }
    }
}
