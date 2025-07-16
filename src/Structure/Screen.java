package Structure;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Elements.DataCache;
import Elements.Settings;

public class Screen {

	private ImageIcon back;
    private String tag;
    //private SoundPlayer sfx;
    private boolean startPlayer = true;
    private boolean includeInHistory = true;
    Button[] buttons = {};
    Button[] tempButtons;
    ScrollWindow[] scrollWindows = {};
    //DragZone[] dragZones;
    Runnable script = () -> {};
    public static TextInterpreter text = new TextInterpreter();

    public Screen() {
        back = new ImageIcon("");
        tag = "S00";
        script = () -> {
            //System.out.println("Empty constructor.");
        };
	}

	public Screen(String _tag) {
        tag = _tag;
        back = new ImageIcon("Assets\\S\\" + tag + ".png");
	}

    public void drawScreen(Graphics g2d, int screenWidth, int screenHeight){ //Draws the screen's background image, features, and buttons and, if necessary, starts the background music
        g2d.drawImage(back.getImage(),0,0,screenWidth,screenHeight,null);
        
        script.run();
        if(buttons.length > 0){
            for(Button b: buttons){
                b.drawButton();
            }
        }

        for(ScrollWindow w: scrollWindows){
            w.drawWindow();
        }
    }

    public void overrideImage(String fileName){
        back = new ImageIcon("Assets\\S\\" + fileName);
    }

    public void excludeFromHistory(){
        includeInHistory = false;
    }

    public boolean includeInHistory(){
        return includeInHistory;
    }
    
    public String tag() {
        return tag;
    }

    public ImageIcon back() {
        return back;
    }

    public void addButtons(Button[] _buttons){
        buttons = _buttons;
    }

    public void addScrollWindows(ScrollWindow[] windows){
        scrollWindows = windows;
    }

    public ScrollWindow[] scrollWindows(){
        return scrollWindows;
    }

    public void addTempButtons(Button[] _buttons){
        tempButtons = buttons;
        buttons = new Button[_buttons.length + tempButtons.length];
        for(int i = 0; i < tempButtons.length; i ++){
            buttons[i] = tempButtons[i];
        }
        for(int i = 0; i < _buttons.length; i++){
            buttons[i + tempButtons.length] = _buttons[i];
        }
    }

    public void addTempButtons(ArrayList<Button> _buttons){
        tempButtons = buttons;
        buttons = new Button[_buttons.size() + tempButtons.length];
        for(int i = 0; i < tempButtons.length; i ++){
            buttons[i] = tempButtons[i];
        }
        for(int i = 0; i < _buttons.size(); i++){
            buttons[i + tempButtons.length] = _buttons.get(i);
        }
    }

    public void addTempClickables(ArrayList<ClickableTask> _buttons){
        tempButtons = buttons;
        buttons = new Button[_buttons.size() + tempButtons.length];
        for(int i = 0; i < tempButtons.length; i ++){
            buttons[i] = tempButtons[i];
        }
        for(int i = 0; i < _buttons.size(); i++){
            buttons[i + tempButtons.length] = _buttons.get(i);
        }
    }

    public void removeTempButtons(){
        if(tempButtons != null){
            buttons = tempButtons;
            tempButtons = null;
        }
    }

    /*public void addDragZones(DragZone[] _dragZones){
        dragZones = _dragZones;
    }*/

    public void addScript(Runnable _script){
        script = _script;
    }

    public void setBack(ImageIcon _back){
        back = _back;
    }

    public Button[] buttons(){
        return buttons;
    }

    public boolean startPlayer(){
        return startPlayer;
    }
    public void setStartPlayer(boolean _startPlayer){
        startPlayer = _startPlayer;
    }

    public String toString(){
        return tag;
    }

    public void rearrangeToLast(Button button){
        Button[] temp = new Button[buttons.length];
        int index = 0;
        for(int i = 0; i < buttons.length; i++){
            if(!(buttons[i] == button)){
                temp[index] = buttons[i];
                index ++;
            }
        }
        temp[buttons.length - 1] = button;
        buttons = temp;
    }
}


