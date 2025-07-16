package Structure;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

import Elements.DataCache;
import Elements.TaskHelper;

public class Dropdown extends Button{
    private ArrayList<String> options = new ArrayList<String>();
    private int selectedOption = 0;
    private boolean selectMode = false;
    private Color baseColor;
    private Color outlineColor;
    private ArrayList<Button> clickOptions = new ArrayList<Button>();
    private int optionsWidth = 50;

    public Dropdown(String _tag, int _x, int _y, int _w, int _h, Color base, Color outline){
        super(_tag, _x, _y, _w, _h, ()-> {

        });
        this.setRunnable(() -> {
            selectMode = !selectMode;
            if(selectMode){
                this.setH(this.h() + optionsWidth*options.size());
                int i = 1;
                for(String s: options){
                    Button b = new Button(s, this.x(), this.y() + i*optionsWidth, this.w(), optionsWidth, () -> {
                        selectedOption = options.indexOf(s);
                        TaskHelper.resetViewFilter();
                    });
                    b.enableHoverOutline(outline);
                    clickOptions.add(b);
                    i++;
                }
                DataCache.myScreen.addTempButtons(clickOptions);
            } else {
                clickOptions.clear();
                DataCache.myScreen.removeTempButtons();
                this.setH(this.h() - optionsWidth*options.size());
                TaskHelper.resetViewFilter();
            }
        });
        baseColor = base;
        outlineColor = outline;
    }

    public void addOptions(String[] options){
        for(String s: options){
            this.options.add(s);
        }
    }

    public String getChoice(){
        return options.get(selectedOption);
    }

    public void setOptions(ArrayList<String> options){
        this.options = options;
    }


    public void drawButton(){
        Game.Graphics().setColor(baseColor);
        Game.Graphics().fillRect(this.x(), this.y(), this.w(), this.h());
        Game.Graphics().setColor(outlineColor);
        Game.Graphics().drawRect(this.x(), this.y(), this.w(), this.h());
        if(options.size()>0){
            Game.Graphics().drawString(options.get(selectedOption), this.x() + 15, this.y() + 30);
            int[] ys = {this.y() + 16, this.y() + 16, this.y() + 36};
            int[] xs = {this.x() + this.w() - 40, this.x() + this.w() - 16, this.x() + this.w() - 28};
            Game.Graphics().fillPolygon(new Polygon(xs, ys, 3));
        }
    }
}
