package Elements;

import java.awt.Graphics;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;

import Structure.CalendarQuickstart;
import Structure.ClickableDate;
import Structure.ClickableTask;
import Structure.TextInterpreter;

public class TaskHelper {
    public static ArrayList<Task> tasks = new ArrayList<Task>();
    public static ArrayList<Task> today = new ArrayList<Task>();
    public static ArrayList<Task> tomorrow = new ArrayList<Task>();
    public static ArrayList<String> categories = new ArrayList<String>();
    private static TextInterpreter text = new TextInterpreter();

    public static ArrayList<ClickableTask> todayClickables = new ArrayList<ClickableTask> ();
    public static ArrayList<ClickableTask> tomorrowClickables = new ArrayList<ClickableTask> ();

    public static ArrayList<ClickableDate> calendarClickables = new ArrayList<ClickableDate> ();

    public static ArrayList<Task> archive = new ArrayList<Task>();

    public static void refresh(){
        today.clear();
        tomorrow.clear();
        categories.clear();

        Collections.sort(tasks, new Comparator<Task>() {
            public int compare(Task o1, Task o2) {
                return o1.preferredDate().compareTo(o2.preferredDate());
            }
        });

        Calendar td = Calendar.getInstance(TimeZone.getTimeZone("CST"));        

        today = getTasksForDate(td);
        td.add(Calendar.DATE, 1);
        tomorrow = getTasksForDate(td);

        if(tasks.size()>0){
            for(Task t: tasks){
                if(!categories.contains(t.category())){
                    categories.add(t.category());
                }
            }
        }

        Collections.sort(categories, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        categories.add(0, "All");

        GraphicsDatabase.DR01.setOptions(categories);

        todayClickables = getClickableTasks(today, 640, 135, 230, false);
        tomorrowClickables = getClickableTasks(tomorrow, 915, 135, 230, false);

        GraphicsDatabase.W01.setButtons(todayClickables);
        GraphicsDatabase.W02.setButtons(tomorrowClickables);
        generateCalendar();
    }

    public static ArrayList<Task> getTasksForDate(Calendar date){
        ArrayList<Task> result = new ArrayList<Task>();
        Calendar dueDate = Calendar.getInstance(TimeZone.getTimeZone("CST"));
        if(tasks.size()>0){
            for(Task t: tasks){
                dueDate.setTime(t.preferredDate());
                if(dueDate.get(5) == date.get(5) && dueDate.get(2) == date.get(2) && dueDate.get(1) == date.get(1)){
                    result.add(t);
                }
            }
        }
        return result;
    }

    public static void resetViewFilter(){
        String category = GraphicsDatabase.DR01.getChoice();
        int x = GraphicsDatabase.W03.x();
        int y = GraphicsDatabase.W03.y();
        int w = GraphicsDatabase.W03.w();
        if(category.equals("All")){
            GraphicsDatabase.W03.setButtons(getClickableTasks(tasks, x, y, w, true));
        } else {
            ArrayList<Task> tasksInCategory = new ArrayList<Task>();
            for(Task t: tasks){
                if(t.category().equals(category)){
                    tasksInCategory.add(t);
                }
            }
            GraphicsDatabase.W03.setButtons(getClickableTasks(tasksInCategory, x, y, w, true));
        }
    }

    public static void add(Task task){
        tasks.add(task);
        refresh();
    }

    public static void remove(Task task){
        tasks.remove(task);
        refresh();
    }

    public static String getTaskFile(){
        String result = tasks.size() + "\n";
        for(Task t: tasks){
            result += t.fileString();
        }
        return result;
    }

    public static String getArchiveFile(){
        String result = archive.size() + "\n";
        for(Task t: archive){
            result += t.fileString();
        }
        return result;
    }

    public static void addTasksToGoogleCalendar() throws GeneralSecurityException{
        refresh();
        for(Task t: today){
            try{
                CalendarQuickstart.addEvent(t.toString());
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<ClickableTask> getClickableTasks(ArrayList<Task> tasks, int xOffset, int yOffset, int width, boolean showDetails){
        ArrayList<ClickableTask> clickables = new ArrayList<ClickableTask> ();
        int y = yOffset;
        if(tasks.size() == 0){
            clickables.add(new ClickableTask(xOffset, y, width, 60));
        } else {
            for(Task t: tasks){
                int simHeight;
                if(showDetails){
                    simHeight = 25 + text.simulateLines(t.details(), 50)*18 + 23*text.simulateLines(t.toString(),26);
                } else {
                    simHeight = 15 + 23*text.simulateLines(t.toString(),26);
                }
                if(t.numParts() > 1){
                    simHeight += 17;
                }
                clickables.add(new ClickableTask(t, xOffset, y, width, simHeight, showDetails));
                y += simHeight + 10;
            }
        }
        return clickables;
    }

    public static void generateCalendar(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1); 
        cal.set(Calendar.MONTH, DataCache.month);
        cal.set(Calendar.YEAR, DataCache.year);
        int x = 16 + 96*(cal.get(Calendar.DAY_OF_WEEK)-1);
        int y = 145;
        String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        while(cal.get(Calendar.MONTH) == DataCache.month){
            calendarClickables.add(
                new ClickableDate(days[cal.get(Calendar.DAY_OF_WEEK) - 1], cal.get(Calendar.DAY_OF_MONTH), getTasksForDate(cal), x, y)
            );
            if(cal.get(Calendar.DAY_OF_WEEK) == 7){
                y += 91;
                x = 16;
            } else {
                x += 96;
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);

        }
        GraphicsDatabase.CL01.setDates(calendarClickables);
    }

    public static void resetCalendarFilter(){
        calendarClickables.clear();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, DataCache.month);
        if(DataCache.date > 0){
            cal.set(Calendar.DATE, DataCache.date);
            ArrayList<ClickableTask> bs = getClickableTasks(getTasksForDate(cal), 696+8, 50+8, 1175-696-16, false);
            GraphicsDatabase.W04.setButtons(bs);
        }
        generateCalendar();
    }

    public static void nextMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, DataCache.month);
        cal.set(Calendar.YEAR, DataCache.year);
        cal.add(Calendar.MONTH, 1);
        DataCache.year = cal.get(Calendar.YEAR);
        DataCache.month = cal.get(Calendar.MONTH);
        resetCalendarFilter();
    }

    public static void previousMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, DataCache.month);
        cal.set(Calendar.YEAR, DataCache.year);
        cal.add(Calendar.MONTH, -1);
        DataCache.year = cal.get(Calendar.YEAR);
        DataCache.month = cal.get(Calendar.MONTH);
        resetCalendarFilter();
    }
}
