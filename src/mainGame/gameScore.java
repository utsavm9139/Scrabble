package mainGame;

public class gameScore {
    private BoardCoordinate firstLetter;

    private String word;

    private int score;

    private String direction;

    public void resetAll(){
        this.word ="";
        this.score =0;
        this.firstLetter = new BoardCoordinate(0,0);
    }


    public int getScore(){
        return score;
    }


    public void setAll(BoardCoordinate firstLetter, String word, int score, String direction){
        this.score= score;
        this.firstLetter = firstLetter;
        this.word =word;
        this.direction = direction;
    }


    public void print(){
        System.out.println("this is the best word " + word);
        System.out.println("this is firstletter x= "+firstLetter.getY()+" and y = "+firstLetter.getX());
        System.out.println("this is score "+ score);
        System.out.println("this is the direction "+ direction);
    }

    public int row(){
        return firstLetter.getX();
    }

    public int col(){
        return firstLetter.getY();
    }
}
