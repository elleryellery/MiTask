//Annotated
package Elements;

import java.awt.Color;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Calendar;


import Structure.*;

//This class contains variables that are used by many different classes. This reduces the amount
//of excess code.
public class DataCache {
    public static boolean debug = false;
    public static Screen myScreen;
    public static boolean inputStatus = false;
    public static TextInput inputBox;
    public static boolean showTaskCount = true;
    public static int month = Calendar.getInstance().get(Calendar.MONTH);
    public static int date = 0;
    public static int year = Calendar.getInstance().get(Calendar.YEAR);
    public static Task task;
}
