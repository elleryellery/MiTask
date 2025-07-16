package Structure;

import java.awt.Image;

import javax.swing.ImageIcon;

import Elements.DataCache;

public class Draggable extends Button{

    Image image;

    public Draggable(){
        super();
    }

    public Draggable(String _tag, int _x, int _y, int _w, int _h){
        super(_tag, _x, _y, _w, _h, () -> {
            
        }); //TODO add default draggable script

        String base = "Assets\\D\\";

        this.image = new ImageIcon(base + _tag + ".png").getImage();
    }

    public Draggable(int _x, int _y, int _w, int _h, Image image){
        super("000", _x, _y, _w, _h, () -> {});

        this.image = image; //TODO modify regular image to indicate that it is being selected (e.g. add low-opacity white overlay)
    }

    public void snapTo(DragZone dragZone){
        int x = (int)(dragZone.width()/2 - this.w()/2) + dragZone.x();
        int y = (int)(dragZone.height()/2 - this.h()/2) + dragZone.y();

        this.setX(x);
        this.setY(y);
    }

    public void setCoords(int _x, int _y){
        this.setX(_x);
        this.setY(_y);
    }

    public void drawButton(){
        Game.Graphics().drawImage(image, this.x(), this.y(), this.w(), this.h(), null);
        if(DataCache.debug){
            Game.Graphics().drawRect(this.x(), this.y(), this.w(), this.h());
        }
    }

    public String toString(){
        return "Draggable: " + this.x() + " " + this.y() + " " + this.w() + " " + this.h();
    }

}
