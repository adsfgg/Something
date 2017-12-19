package helpers;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class InputHelper
{
    private static Scanner fileScanner;
    
    public static String readConsoleLine() {
        return new Scanner(System.in).nextLine();
    }
    
    public static ArrayList<String> readConsole(int lineCount) {
        return readConsole("", lineCount);
    }
    
    public static ArrayList<String> readConsole(String exiton) {
        return readConsole(exiton, Integer.MAX_VALUE - 1);
    }
    
    public static ArrayList<String> readConsole(String exiton, int lineCount) {
        ArrayList<String> result = new ArrayList<>();
        Scanner s = new Scanner(System.in).reset();
        String line;
        int c = 0;
        
        while((c < lineCount) && s.hasNext() && !(line = s.nextLine()).equals(exiton)) {
            result.add(line);
            c++;
        }
        
        return result;
    }
    
    public static boolean hasFileNext() throws IOException {
        if(fileScanner == null) throw new IOException("No file opened.");
        return fileScanner.hasNext();
    }
    
    public static String getNextLineFile() throws IOException {
        if(fileScanner == null) throw new IOException("No file opened.");
        return fileScanner.nextLine();
    }
    
    public static void openFile(String path) throws IOException {
        openFile(Paths.get(path));
    }
    
    public static void openFile(Path path) throws IOException {
        if(fileScanner == null) fileScanner = new Scanner(path);
        else throw new IOException("Cannot open two files at the same time.");
    }
    
    public static void closeFile() throws IOException {
        if(fileScanner == null) throw new IOException("File is already closed.");
//        fileScanner.close(); // idk do i need this
        fileScanner = null;
    }
    
    public static List<String> readFile(String path) throws IOException {
        return readFile(Paths.get(path));
    }
    
    public static List<String> readFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}