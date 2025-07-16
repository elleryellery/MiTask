package Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Structure.TextInterpreter;

public class Task {
    private String name;
    private String category;
    private Date dueDate;
    private Date doDate;
    private ArrayList<Link> links;
    private int numParts;
    private int partsCompleted = 0;

    public Task(){
        name = "Undefined";
        category = "Undefined";
    }

    public Task(String name, String category){
        this.name = name;
        this.category = category;
    }

    public void addDueDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/d/yy h:mm a", Locale.ENGLISH);

        try{
            dueDate = formatter.parse(date);
        } catch(ParseException e){
            dueDate = null;
        }
    }

    public void addDoDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);

        try{
            doDate = formatter.parse(date);
        } catch(ParseException e){
            doDate = null;
        }
    }

    public void addLink(String title, String link){
        links.add(new Link(title, link));
    }

    public void addParts(int parts){
        numParts = parts;
    }

    public String dueDateString(){
        if(dueDate == null){
            return "never";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/d/yy h:mm a", Locale.ENGLISH);
        return formatter.format(dueDate);
    }

    public String doDateString(){
        if(doDate == null){
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);
        return formatter.format(doDate);
    }
    
    public Date dueDate(){
        return dueDate;
    }

    public Date doDate(){
        return doDate;
    }

    public Date preferredDate(){
        if(doDate != null){
            return doDate;
        } else if(dueDate != null){
            return dueDate;
        } else{
            return new Date();
        }
    }

    public String category(){
        return category;
    }

    public String fileString(){
        String text = 
            name + "\n"
            + category + "\n"
            + dueDateString() + "\n"
            + doDateString() + "\n"
            + numParts + "\n"
            + partsCompleted + "\n"
            ;

        return text;
    }

    public String toString(){
        return name + " (" + category + ")";
    }

    public String details(){
        String s = "";
        if(doDate != null){
            s += "Do: " + doDateString() + TextInterpreter.newLineKey;
        }
        if(dueDate != null){
            s += "Due: " + dueDateString() + TextInterpreter.newLineKey;
        }
        if(numParts > 1){
            s += numParts + " parts";
        }

        return s;
    }

    public String name(){
        return name;
    }

    public int numParts(){
        return numParts;
    }

    public int partsCompleted(){
        return partsCompleted;
    }

    public void completePart(){
        if(partsCompleted < numParts){
            partsCompleted ++;
            System.out.println(partsCompleted);
        }
    }

    public void setPartsCompleted(int amount){
        partsCompleted = amount;
    }

    public int percentCompleted(){
        return (int)((partsCompleted/(double)numParts)*100);
    }

    public String status(){
        return percentCompleted() + "% completed (" + partsCompleted + "/" + numParts + ")";
    }
}
