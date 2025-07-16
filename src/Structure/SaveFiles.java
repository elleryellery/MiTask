package Structure;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import Elements.DataCache;
import Elements.GraphicsDatabase;
import Elements.ScreenScripts;
import Elements.Task;
import Elements.TaskHelper;

public abstract class SaveFiles {
    public static boolean createFile() {
        if(TaskHelper.tasks.size() > 0){
            Path saveFilePath = Paths.get(System.getProperty("user.home"), "Documents", "MiTask", "data.txt");
            Path archiveFilePath = Paths.get(System.getProperty("user.home"), "Documents", "MiTask", "archive.txt");

            try {
                Files.createDirectories(saveFilePath.getParent());
            } catch (IOException e){
                e.printStackTrace();
            }
            File file = saveFilePath.toFile();
            File archive = archiveFilePath.toFile();
            try {
                if(file.createNewFile()){
                    System.out.println("Save File Created");
                    saveFile();
                    return true;
                }
                if(archive.createNewFile()){
                    System.out.println("Archive File Created");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
	}

	public static void saveFile(){
        File activeTasks = Paths.get(System.getProperty("user.home"), "Documents", "MiTask", "data.txt").toFile();

		try {
			FileWriter myWriter = new FileWriter(activeTasks);
			
			myWriter.write(TaskHelper.getTaskFile());

			myWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}

        File archive = Paths.get(System.getProperty("user.home"), "Documents", "MiTask", "archive.txt").toFile();

		try {
			FileWriter myWriter = new FileWriter(archive);
			
			myWriter.write(TaskHelper.getArchiveFile());

			myWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void readFromFile(){
        File activeTasks = Paths.get(System.getProperty("user.home"), "Documents", "MiTask", "data.txt").toFile();

        if(activeTasks.exists()){
            try{
                Scanner sc = new Scanner(activeTasks);
                int numTasks = Integer.valueOf(sc.nextLine());
                for(int i = 0; i < numTasks; i++){
                    String name = sc.nextLine();
                    String category = sc.nextLine();
                    String dueDate = sc.nextLine();
                    String doDate = sc.nextLine();
                    String numParts = sc.nextLine();
                    String partsCompleted = sc.nextLine();
                    Task t = new Task(name, category);
                    t.addDueDate(dueDate);
                    t.addDoDate(doDate);
                    t.addParts(Integer.valueOf(numParts));
                    t.setPartsCompleted(Integer.valueOf(partsCompleted));
                    TaskHelper.add(t);
                }
                sc.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("No save files found.");
        }

        File archive = Paths.get(System.getProperty("user.home"), "Documents", "MiTask", "archive.txt").toFile();

        if(archive.exists()){
            try{
                Scanner sc = new Scanner(archive);
                int numTasks = Integer.valueOf(sc.nextLine());
                for(int i = 0; i < numTasks; i++){
                    String name = sc.nextLine();
                    String category = sc.nextLine();
                    String dueDate = sc.nextLine();
                    String doDate = sc.nextLine();
                    String numParts = sc.nextLine();
                    String partsCompleted = sc.nextLine();
                    Task t = new Task(name, category);
                    t.addDueDate(dueDate);
                    t.addDoDate(doDate);
                    t.addParts(Integer.valueOf(numParts));
                    t.setPartsCompleted(Integer.valueOf(partsCompleted));
                    TaskHelper.archive.add(t);
                }
                sc.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("No archive file found.");
        }
	
	}

	public static String[] toStringArray(ArrayList<String> input){
		String[] s = new String[input.size()];
		for(int i = 0; i < input.size(); i ++){
			s[i] = input.get(i);
		}
		return s;
	}
}
