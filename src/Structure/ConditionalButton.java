package Structure;

import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class ConditionalButton extends Button{
    private Condition condition;
    private ImageIcon regular;
    private ImageIcon regular_;
    private ImageIcon conditional;
    private ImageIcon conditional_;

    public ConditionalButton(String _tag, int _x, int _y, int _w, int _h, Condition _condition, Runnable _action){
        super(_tag, _x, _y, _w, _h, _action);

        String base = "Assets\\C\\" + _tag;
        regular = new ImageIcon(base + ".png");
        regular_ = new ImageIcon(base + "_.png");
        conditional = new ImageIcon(base + "+.png");
        conditional_ = new ImageIcon(base + "+_.png");

        condition = _condition;

        if(condition.evaluate()){
            super.icon = conditional;
        } else {
            super.icon = regular;
        }
    }
    
    public interface Condition {
        boolean evaluate();
    }

    public boolean check(int mouseX, int mouseY){
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(super.x(), super.y(), super.w(), super.h());

        if(mouse.intersects(me)){
            executeButtonAction();
        }

        startAppearance(mouse.intersects(me));

        return mouse.intersects(me);
    }

    private void startAppearance(boolean hover){
        if(hover){
            if(condition.evaluate()){
                super.icon = conditional_;
            } else {
                super.icon = regular_;
            }
        } else {
            if(condition.evaluate()){
                super.icon = conditional;
            } else {
                super.icon = regular;
            }
        }
    }

    public void checkHover(int mouseX, int mouseY){
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(super.x(), super.y(), super.w(), super.h());

        startAppearance(mouse.intersects(me));
    }
}
