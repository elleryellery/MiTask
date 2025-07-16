package Structure;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.*; 

import Elements.*;

public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	private BufferedImage back; 
	private static Graphics g2d;

	public Game() {
		
		//Create a new thread that handles input from the keyboard and mouse
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);

		GraphicsDatabase.init();
		DataCache.myScreen = GraphicsDatabase.S01;			
	}

	//Run the thread
	public void run(){
	   	try{
	   		while(true){
	   		   Thread.currentThread().sleep(5);
	        	repaint();
	        }
	    } catch(Exception e) {
	   			
	    }
	}
	
	public void paint(Graphics g){
		
		//Graphics setup
		Graphics2D twoDgraph = (Graphics2D) g; 
		if(back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
				
		g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g2d.setColor(Color.GREEN);
		((Graphics2D) g2d).setStroke(new BasicStroke(10));
		
		DataCache.myScreen.drawScreen(g2d, getWidth(), getHeight());
		//System.out.println(DataCache.myShip.myNotebook().entries().get(0));
		
		twoDgraph.drawImage(back, null, 0, 0);
		//System.out.println("a: " + DataCache.coordinates1 + " b: " + DataCache.coordinates2);
	}

	public static Graphics Graphics(){
		return g2d;
	}

	public static boolean includes(int[] array, int element){
		for(int i: array){
			if(i == element){
				return true;
			}
		}
		return false;
	}

	public static void setScreen(Screen _screen){
		DataCache.myScreen = _screen;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key= e.getKeyCode();
		char keyChar = e.getKeyChar();	

		if(key == 27){
			setScreen(GraphicsDatabase.S01);
			ScreenScripts.clearBoxes();
		}

		int[] unsupportedKeys = {16, 17, 525, 524, 18, 34, 33, 36, 35, 155, 127, 38, 40, 144, 111, 106, 110};

		if(DataCache.inputStatus){
			if(key == 8){
				if(DataCache.inputBox.contentsLength()>0) {
					DataCache.inputBox.deleteCharacter();
				}
			} else if(key == 10) {
				if(DataCache.inputBox.multiLineEnabled()){
					DataCache.inputBox.newLine();
				}
			} else if(key == 37){
				DataCache.inputBox.arrowLeft();
			} else if(key == 39){
				DataCache.inputBox.arrowRight();
			} else if(!includes(unsupportedKeys, key)){
				DataCache.inputBox.addCharacter(keyChar);
			}
		} else if(keyChar == '1'){
			DataCache.showTaskCount = !DataCache.showTaskCount;
		}

		if(keyChar == '~'){
			DataCache.debug = !DataCache.debug;
			if(DataCache.debug){
				System.out.println("Currently viewing: " + DataCache.myScreen.tag());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for(Button b: DataCache.myScreen.buttons()){
			b.checkHover(e.getX(), e.getY());
		}
		for(ScrollWindow w: DataCache.myScreen.scrollWindows){
			if(w instanceof CalendarWindow){
				for(Button b: ((CalendarWindow)w).dates()){
					b.checkHover(e.getX(), e.getY(), w.offset());
				}
			} else {
				for(Button b: w.buttons()){
					b.checkHover(e.getX(), e.getY(), w.offset());
				}
			}
			w.checkHover(e.getX(), e.getY());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(Button b: DataCache.myScreen.buttons()){
			if(b.check(e.getX(), e.getY()) && !(b instanceof Dropdown)){
				return;
			}
		}
		for(int i = DataCache.myScreen.buttons().length - 1; i >= 0; i--){
			Button b = DataCache.myScreen.buttons()[i];
			if(b instanceof Draggable){
				// if(b.check(e.getX(), e.getY())){
				// 	DataCache.holding = (Draggable)b;
				// 	DataCache.dragXOffset = e.getX()-b.x();
				// 	DataCache.dragYOffset = e.getY()-b.y();
				// 	DataCache.myScreen.rearrangeToLast(b);
				// 	DataCache.myShip.retrieveData().getDrawingFromDraggable((Draggable)b).setOrder(DataCache.myShip.retrieveData().ShipSketches().length);
				// 	break;
				// }
				System.out.println("Reminder: Set up draggables! Game.java 167");
			} else {
				b.checkHover(e.getX(), e.getY());
			}
		}
		if(DataCache.debug){
			System.out.println("DEBUG: Mouse coordinates: (" + e.getX() + ", " + e.getY() + ")");
		}
		if(DataCache.inputStatus && !DataCache.inputBox.check(e.getX(), e.getY())){
			DataCache.inputStatus = false;
			// if(DataCache.inputBox.contents().equals("")){
			// 	DataCache.inputBox.setContents("Type here...");
			// }
		}
		for(ScrollWindow w: DataCache.myScreen.scrollWindows){
			if(w instanceof CalendarWindow){
				for(Button b: ((CalendarWindow)w).dates()){
					b.checkHover(e.getX(), e.getY(), w.offset());
					if(b.check(e.getX(), e.getY(), w.offset())){
						return;
					};
				}
			} else {
				for(Button b: w.buttons()){
					b.checkHover(e.getX(), e.getY(), w.offset());
					b.check(e.getX(), e.getY(), w.offset());
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//DataCache.holding = null;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for(ScrollWindow w: DataCache.myScreen.scrollWindows){
			w.scroll(e.getWheelRotation() * 20);
		}
	}
}
