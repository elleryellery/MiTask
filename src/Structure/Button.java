package Structure;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import Elements.DataCache;
import Elements.GraphicsDatabase;

public class Button {
    
    private String tag;
    private int x,y,w,h;
    public ImageIcon icon = new ImageIcon("");
    private ImageIcon iconRegular;
    private ImageIcon iconHover;
    private Runnable action;
    private boolean enableHoverOutline = false;
    private Color hoverOutlineColor;

    public Button(){
        tag = "B00";
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        icon = new ImageIcon("No Image");
    }

    public Button(String _tag, int _x, int _y, int _w, int _h, Runnable _action){
        tag = _tag;
        iconRegular = new ImageIcon("Assets\\B\\"+tag+".png");
        iconHover = new ImageIcon("Assets\\B\\"+tag+"_.png");
        icon = iconRegular;
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        action = _action;
    }

    public void executeButtonAction(){
        action.run();
    }

    public void enableHoverOutline(Color hoverColor){
        hoverOutlineColor = hoverColor;
        enableHoverOutline = true;
    }

    public void drawButton (){
        Game.Graphics().setColor(GraphicsDatabase.green8);

        if(hover() && enableHoverOutline){
            Game.Graphics().setColor(hoverOutlineColor);
            Game.Graphics().fillRect(x, y, w, h);
            Game.Graphics().setColor(Color.WHITE);
        }

        if(enableHoverOutline){
            Game.Graphics().drawString(tag, x + 15,y + 30);
        }

        Game.Graphics().drawImage(icon.getImage(),x,y,w,h,null);
        if(DataCache.debug){
            Game.Graphics().drawRect(x, y, w, h);
            Game.Graphics().drawString(tag, x, y-20);
        }

    }

    public boolean hover(){
        return icon.equals(iconHover);
    }

    public boolean check(int mouseX, int mouseY){
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y,w,h);

        if(mouse.intersects(me)){
            executeButtonAction();
        }

        return mouse.intersects(me);
    }

    public void setRunnable(Runnable inputAction){
        action = inputAction;
    }
    public void setH(int inputH){
        h = inputH;
    }
    public void setW(int inputW){
        w = inputW;
    }
    public void addY(int inputY){
        y+=inputY;
    }
    public void checkHover(int mouseX, int mouseY){ //Changes the button's appearance if the user is hovering over the button
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y,w,h);

        if(mouse.intersects(me)){
            icon = iconHover;
        } else {
            icon = iconRegular;
        }
        
    }

    public boolean check(int mouseX, int mouseY, int offset){
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y + offset,w,h);

        if(mouse.intersects(me)){
            executeButtonAction();
        }

        return mouse.intersects(me);
    }

    public void checkHover(int mouseX, int mouseY, int offset){ //Changes the button's appearance if the user is hovering over the button
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y + offset,w,h);

        if(mouse.intersects(me)){
            icon = iconHover;
        } else {
            icon = iconRegular;
        }
        
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

    public String tag(){
        return tag;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void overrideImage(ImageIcon _iconRegular, ImageIcon _iconHover){
        iconRegular = _iconRegular;
        iconHover = _iconHover;
    }

    public void setAction(Runnable inputAction){
        action = inputAction;
    }
}
