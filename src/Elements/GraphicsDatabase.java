package Elements;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.Base64;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import Structure.*;

/*
 * This class defines actions, appearance, and more for graphics components like buttons and screens. Buttons, 
 * TextInput, and ConditionalButton objects are assigned a unique ID that makes it faster to reference them and
 * any assets that they require (especially images).
 */
public abstract class GraphicsDatabase {
    public static Screen S01, S02, S03, S04, S05, S06, S07;
    public static Button B01, B02, B03, B04, B05, B06, B07, B08, B09, B10, B11, B12, B13, B14;
    public static TextInput T01, T02, T03, T04, T05, T06, T07, T08, T09, T10, T11, T12;
    public static Dropdown DR01;
    public static ScrollWindow W01, W02, W03, W04;
    public static CalendarWindow CL01;
    public static Color green1, green2, green3, green4, green5, green6, green7, green8, green9;

    public static void init(){
        green1 = new Color(247, 252, 245);
        green2 = new Color(229, 245, 224);
        green3 = new Color(199, 233, 192);
        green4 = new Color(161, 217, 155);
        green5 = new Color(116, 196, 118);
        green6 = new Color(65, 171, 93);
        green7 = new Color(35, 139, 69);
        green8 = new Color(0, 109, 44);
        green9 = new Color(0, 68, 27);

        W01 = new ScrollWindow(640, 135, 230, 425);
        W02 = new ScrollWindow(915, 135, 230, 425);
        W03 = new ScrollWindow(577, 24, 1152-577, 581-24);
        W04 = new ScrollWindow(696, 50, 1175-696, 602-50);

        CL01 = new CalendarWindow(8, 136,689,602-135);

        B01 = new Button("B01", 30, 70, 250, 250, () -> {
            Game.setScreen(S02);
        });
        B02 = new Button("B02", 300, 70, 250, 250, () -> {
            TaskHelper.resetViewFilter();
            Game.setScreen(S03);
        });
        B03 = new Button("B03", 300, 340, 250, 250, () -> {
            Game.setScreen(S05);
        });
        B04 = new Button("B04", 30, 340, 250, 250, () -> {
            Game.setScreen(S04);
        });
        B05 = new Button("B05", 1118, 550, 50, 50, () -> {
            ScreenScripts.saveTask();
            ScreenScripts.clearBoxes();
            Game.setScreen(S01);
        });
        B06 = new Button("B06", 1052, 550, 50, 50, () -> {
            ScreenScripts.saveTask();
            ScreenScripts.clearBoxes();
        });
        B07 = new Button("B07", 318, 90, 225, 225, () -> {
            Game.setScreen(S06);
        });
        B08 = new Button("B08",75, 90, 50, 50, () -> {
            TaskHelper.nextMonth();
        });
        B09 = new Button("B09",17, 90, 50, 50, () -> {
            TaskHelper.previousMonth();
        });
        B10 = new Button("B10", 750, 135, 175, 175, () ->{
            TaskHelper.archive.add(DataCache.task);
            TaskHelper.remove(DataCache.task);
            System.out.println("YOU DID IT!!!!!!!!!");
            Game.setScreen(S01);
            DataCache.task = null;
        });
        B11 = new Button("B11", 866, 286, 150, 150, () ->{
            Game.setScreen(S01);
            TaskHelper.remove(DataCache.task);
            DataCache.task = null;
        });
        B12 = new Button("B05", 1097, 529, 70, 70, () -> {
            ScreenScripts.updateTask();
            Game.setScreen(S01);
        });
        B13 = new Button("B13", 736, 388, 150, 150, () -> {
            DataCache.task.completePart();
        });
        B14 = new Button("B14", 736, 388, 150, 150, () -> {
            try {
                TaskHelper.addTasksToGoogleCalendar();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        T01 = new TextInput(190, 110, 50, 30, green8, false, 50, true, ""); //name
        T02 = new TextInput(260, 185, 20, 30, green8, false, 20, true, ""); //category
        T03 = new TextInput(260, 185+75, 20, 30, green8, false, 20, true, ""); //duedate
        T04 = new TextInput(260, 185+75*2, 20, 30, green8, false, 20, true, ""); //duetime
        T05 = new TextInput(260, 185+75*3, 20, 30, green8, false, 20, true, ""); //dodate
        T06 = new TextInput(425, 185+75*4, 3, 30, green8, false, 3, true, ""); //numParts
        T07 = new TextInput(190, 110, 50, 30, green8, false, 50, false, ""); //name
        T08 = new TextInput(260, 185, 20, 30, green8, false, 20, false, ""); //category
        T09 = new TextInput(260, 185+75, 20, 30, green8, false, 20, false, ""); //duedate
        T10 = new TextInput(260, 185+75*2, 20, 30, green8, false, 20, false, ""); //duetime
        T11 = new TextInput(260, 185+75*3, 20, 30, green8, false, 20, false, ""); //dodate
        T12 = new TextInput(425, 185+75*4, 3, 30, green8, false, 3, false, ""); //numParts

        DR01 = new Dropdown("", 25, 100, 250, 50, green2, green6);

        S01 = new Screen("S01"); //Home
            Button[] b01 = {B01, B02, B03, B04};
            ScrollWindow[] w01 = {W01, W02};
            S01.addButtons(b01);
            S01.addScrollWindows(w01);
            S01.addScript(() -> {
                ScreenScripts.displayTodayAndTomorrowTasks();
                Game.Graphics().setFont(new Font("Times New Roman", Font.ITALIC, 25));
                Game.Graphics().setColor(green2);
                Game.Graphics().drawString(TaskHelper.archive.size() +"", 1096, 45);
            });
        S02 = new Screen("S02"); //New task
            Button[] b02 = {B05, B06, T01, T02, T03, T04, T05, T06};
            S02.addButtons(b02);
        S03 = new Screen("S03"); //View tasks
            Button[] b03 = {B07, DR01};
            ScrollWindow[] w03 = {W03};
            S03.addButtons(b03);
            S03.addScrollWindows(w03);
        S04 = new Screen("S04"); //Tools
            Button[] b04 = {B14};
            S04.addButtons(b04);
        S05 = new Screen("S05"); //Study session setup
        S06 = new Screen("S06");
            Button[] b06 = {B08, B09};
            ScrollWindow[] w06 = {CL01, W04};
            S06.addScrollWindows(w06);
            S06.addButtons(b06);
            S06.addScript(() -> {
                ScreenScripts.displayCalendarInfo();
            });
        S07 = new Screen("S07");
            Button[] b07 = {T07, T08, T09, T10, T11, T12, B10, B11, B12, B13};
            S07.addButtons(b07);
            S07.addScript(() -> {
                ScreenScripts.displayTaskProgress();
            });
    }
}
