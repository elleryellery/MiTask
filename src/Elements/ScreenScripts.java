package Elements;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.time.Month;
import java.util.ArrayList;

import Structure.*;

public class ScreenScripts {
    private static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static void saveTask(){
        String name = GraphicsDatabase.T01.contents();
        String category = GraphicsDatabase.T02.contents();
        String dueDate = GraphicsDatabase.T03.contents();
        String dueTime = GraphicsDatabase.T04.contents();
        String doDate = GraphicsDatabase.T05.contents();
        String numParts = GraphicsDatabase.T06.contents();
        int numPartsInt;

        if(name.length() > 0){
            if(category.length() == 0){
                category = "Other";
            }
            if(dueDate.length() == 0){
                dueDate = "None";
            }
            if(dueTime.length() == 0){
                dueTime = "11:59 PM";
            }
            if(doDate.length() == 0){
                doDate = "None";
            }
            if(numParts.length() == 0){
                numPartsInt = 1;
            } else{
                numPartsInt = Integer.valueOf(numParts);
            }

            Task task = new Task(name, category);
            task.addDueDate(dueDate + " " + dueTime);
            task.addDoDate(doDate);
            task.addParts(numPartsInt);
            TaskHelper.add(task);
        }

    }

    public static void updateTask(){
        String name = GraphicsDatabase.T07.contents();
        String category = GraphicsDatabase.T08.contents();
        String dueDate = GraphicsDatabase.T09.contents();
        String doDate = GraphicsDatabase.T11.contents();
        String numParts = GraphicsDatabase.T12.contents();
        System.out.println(numParts);
        int partsCompleted = DataCache.task.partsCompleted();

        Task task = new Task(name, category);
            task.addDueDate(dueDate);
            task.addDoDate(doDate);
            task.addParts(Integer.parseInt(numParts));
            task.setPartsCompleted(partsCompleted);
        TaskHelper.remove(DataCache.task);
        TaskHelper.add(task);
        TaskHelper.refresh();
    }

    public static void clearBoxes(){
        GraphicsDatabase.T01.clear();
        GraphicsDatabase.T02.clear();
        GraphicsDatabase.T03.clear();
        GraphicsDatabase.T04.clear();
        GraphicsDatabase.T05.clear();
        GraphicsDatabase.T06.clear();
    }

    public static void displayTodayAndTomorrowTasks(){
        // String today = "";
        // if(TaskHelper.today.size() > 0) {
        //     for(Task t: TaskHelper.today){
        //         today += " » " + t + "`";
        //     }
        // } else {
        //     today = "All done for today! Nice job!";
        // }

        // String tomorrow = "";
        // if(TaskHelper.tomorrow.size() > 0) {
        //     for(Task t: TaskHelper.tomorrow){
        //         tomorrow += " » " + t + "`";
        //     }
        // } else {
        //     tomorrow = "Nothing to do tomorrow!";
        // }

        // Game.Graphics().setColor(GraphicsDatabase.green3);
		// Game.Graphics().setFont(new Font("Arial", Font.PLAIN, 18));

        // TextInterpreter text = new TextInterpreter();
        // text.drawText(Game.Graphics(), today, 647, 150, 26, 20);

        // Game.Graphics().setColor(GraphicsDatabase.green9);
        // text.drawText(Game.Graphics(), tomorrow, 925, 150, 26, 20);

    }

    public static void displayCalendarInfo(){
        String month = months[DataCache.month];
        Game.Graphics().setColor(GraphicsDatabase.green1);
        Game.Graphics().setFont(new Font("Times New Roman", Font.ITALIC, 50));
        Game.Graphics().drawString(month + " " + DataCache.year, 150, 130);
        if(DataCache.date > 0){
            Game.Graphics().setFont(new Font("Times New Roman", Font.ITALIC, 30));
            Game.Graphics().drawString(month + " " + DataCache.date, 710, 40);
        }
    }

    public static void displayTaskProgress(){
        if(DataCache.task.numParts() > 1){
            Game.Graphics().setFont(new Font("Times New Roman", Font.ITALIC, 18));
            Game.Graphics().setColor(GraphicsDatabase.green6);
            Game.Graphics().drawString(DataCache.task.status(),35, 555);
            Game.Graphics().fillRoundRect(35, 564, 691-35, 592-564, 3, 3);
            Game.Graphics().setColor(GraphicsDatabase.green1);
            Game.Graphics().fillRoundRect(35+2, 564+2, (int)((691-35-4)*(DataCache.task.percentCompleted()/100.0)), 592-564-4, 3, 3);        }
    }
}
