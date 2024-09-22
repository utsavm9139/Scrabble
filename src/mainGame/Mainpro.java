package mainGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Mainpro {
    public static ArrayList<String> stringScrabble = new ArrayList<>();
    public static ArrayList<String> boardCond = new ArrayList<>();
    public static String[][] grid = null;

    public static void main(String[] args) {

        try {
            Scanner myReader = new Scanner(new File("src\\resources\\scrabble.txt"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                boardCond.add(data);
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(boardCond);

    }






}
