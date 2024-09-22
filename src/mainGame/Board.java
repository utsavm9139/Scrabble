package mainGame;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
class Board {
    private char board[][];
    private int size;
    private ArrayList<String> stringBoard = new ArrayList<>();
    private String boardStr ="";
    public Board(){
        getStringBoard();
        size=Integer.parseInt(stringBoard.get(0));
        board = getBoard(size);
    }
    public ArrayList<String> getStringBoard() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File("src\\resources\\scrabble_board.txt")));
            String line = reader.readLine();
            while (line != null) {
                stringBoard.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBoard;
    }
    public char[][] getBoard(int size) {
        int column = 0;boolean isInteger = false;
        int position=0;board = new char[size][size];
        for (int i = 0; i < size; i++) {
            String str = stringBoard.get(i+1);
            str = str.replaceAll("\\s", "");
            for (int j = 0; j < str.length(); j++) {
                if ((position  % 2 == 0) &&Character.isDigit(str.charAt(j))==true) {
                    String temp = Character.toString(str.charAt(j));
                    int y=Integer.parseInt(temp) + 5;
                    board[i][column] = Integer.toString(y).charAt(0);
                    boardStr += Integer.toString(y);
                    isInteger = true;
                } else if ((position  % 2 == 1&& Character.isDigit(str.charAt(j)))==true) {
                    board[i][column] = str.charAt(j);
                    boardStr += Character.toString(str.charAt(j));
                    isInteger = true;
                }else if (position  % 2 == 1&& isInteger==false) {
                    boardStr =boardStr+".";
                    board[i][column] = '.';
                }
                position++;
                if (Character.isLetter(str.charAt(j))) {
                    board[i][column] = str.charAt(j);
                    boardStr += Character.toString(str.charAt(j));
                    column++;
                    position =0;
                    isInteger = false;
                } else if (position % 2 == 0 && j > 0 && !Character.isLetter(str.charAt(j))) {
                    column++;
                    isInteger = false;
                }
            }
            position =0;
            column =0;
        }
        return board;
    }
    public char[][] getBoard(){
        return board;
    }
}
class BoardCoordinate {
    private int x;
    private int y;
    public BoardCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void exchange(int x, int y) {
        this.x = y;
        this.y = x;
    }

}