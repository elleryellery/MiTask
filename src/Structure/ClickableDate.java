package Structure;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Elements.DataCache;
import Elements.GraphicsDatabase;
import Elements.Task;
import Elements.TaskHelper;

public class ClickableDate extends Button{
    private String dayOfWeek;
    private int dayOfMonth;
    private ArrayList<Task> tasks;
    private TextInterpreter text = new TextInterpreter();

    public ClickableDate(String dayOfWeek, int dayOfMonth, ArrayList<Task> tasks, int x, int y){
        super(dayOfWeek + " " + DataCache.month + dayOfMonth, x, y, 88, 83, () -> {
            DataCache.date = dayOfMonth;
            TaskHelper.resetCalendarFilter();
        });
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.tasks = tasks;
    }

    public ClickableDate(String dayOfWeek, int dayOfMonth, ArrayList<Task> tasks, int x, int y, int w, int h){
        super(dayOfWeek + " " + DataCache.month + dayOfMonth, x, y, w, h, () -> {
            DataCache.date = dayOfMonth;
            TaskHelper.resetCalendarFilter();
        });
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.tasks = tasks;
    }

    public void drawButton(int offset){
        if(hover()){
            Game.Graphics().setColor(GraphicsDatabase.green2);
        } else {
            Game.Graphics().setColor(GraphicsDatabase.green4);
        }

        Game.Graphics().fillRect(this.x(), this.y() + offset, this.w(), this.h());
        
        if(tasks.size()>0){
            if(tasks.size()>10){
                Game.Graphics().drawImage(new ImageIcon("Assets\\M\\T10+.png").getImage(), this.x() + 10, this.y() + 15 + offset, this.w() - 20, this.h() - 20, null);
            } else {
                Game.Graphics().drawImage(new ImageIcon("Assets\\M\\T" + tasks.size() + ".png").getImage(), this.x() + 10, this.y() + 15 + offset, this.w() - 20, this.h() - 20, null);
            }
        }
        Game.Graphics().setColor(GraphicsDatabase.green8);
		Game.Graphics().setFont(new Font("Arial", Font.PLAIN, 18));
        Game.Graphics().drawString(dayOfMonth + "", this.x() + 8, this.y() + 23 + offset);
    }
}
