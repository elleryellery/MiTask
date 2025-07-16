package Structure;

import java.awt.Font;

import Elements.DataCache;
import Elements.GraphicsDatabase;
import Elements.Task;

public class ClickableTask extends Button{
    private Task task;
    private TextInterpreter text = new TextInterpreter();
    private boolean showDetails = true;

    public ClickableTask(int x, int y, int w, int h){
        super("Nothing to see here!", x, y, w, h, () -> {});
        task = new Task("Nothing to see here!", "Nice job!");
    }

    public ClickableTask(Task t, int x, int y, int w, int h, boolean showDetails){
        super(t.toString(), x, y, w, h, () -> {
            GraphicsDatabase.T07.setContents(t.name());
            GraphicsDatabase.T08.setContents(t.category());
            GraphicsDatabase.T09.setContents(t.dueDateString());
            GraphicsDatabase.T11.setContents(t.doDateString());
            GraphicsDatabase.T12.setContents(t.numParts() + "");

            DataCache.task = t;

            Game.setScreen(GraphicsDatabase.S07);
        });
        task = t;
        this.showDetails = showDetails;
    }

    public void drawButton (int offset){
        if(hover()){
            Game.Graphics().setColor(GraphicsDatabase.green2);
        } else {
            Game.Graphics().setColor(GraphicsDatabase.green4);
        }

        Game.Graphics().fillRoundRect(this.x(), this.y() + offset, this.w(), this.h(), 5, 5);
        
        Game.Graphics().setColor(GraphicsDatabase.green8);
		Game.Graphics().setFont(new Font("Arial", Font.PLAIN, 18));
        text.drawText(Game.Graphics(), task.toString(), this.x() + 8, this.y() + 23 + offset, 26, 23);
        if(showDetails){
            Game.Graphics().setFont(new Font("Arial", Font.ITALIC, 15));
            text.drawText(Game.Graphics(), task.details(), this.x() + 8, this.y() + 25  + offset + 23*text.simulateLines(task.toString(),26), 30, 18);
        }
        if(task.numParts() > 1){
            Game.Graphics().setColor(GraphicsDatabase.green6);
            Game.Graphics().fillRoundRect(this.x()+5, this.y() + this.h() - 15 + offset, this.w()-10, 10, 3, 3);
            Game.Graphics().setColor(GraphicsDatabase.green1);
            Game.Graphics().fillRoundRect(this.x()+7, this.y() + this.h() - 13 + offset, (int)((this.w()-14)*(task.percentCompleted()/100.0)), 6, 3, 3);
        }
    }

}
