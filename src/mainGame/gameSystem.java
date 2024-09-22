package mainGame;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class gameSystem {
    private int boardSize;

    private dictRead data;

    private char[][] board;

    private int scoreLeft =0;

    private int scoreRight =0;

    private boolean transpose =false;

    private ArrayList<BoardCoordinate> characterPositions = new ArrayList<BoardCoordinate>();

    private ArrayList<BoardCoordinate> anchorLeft = new ArrayList<>();

    private ArrayList<BoardCoordinate> anchorRight = new ArrayList<>();

    private ArrayList<BoardCoordinate> anchorUp = new ArrayList<>();

    private ArrayList<BoardCoordinate> anchorDown = new ArrayList<>();

    private ArrayList<BoardCoordinate> humanPlayPosition = new ArrayList<>();

    private ArrayList<gameScore> allScores = new ArrayList<>();

    private ArrayList<String> allFoundWords = new ArrayList<>();

    private String fixedString ="";

    private String direction = "";

    private char[][] boardStore;

    private ArrayList<BoardCoordinate> stringPositionLeft = new ArrayList<>();

    private ArrayList<BoardCoordinate> stringPositionRight = new ArrayList<>();

    private int firstPositionY;

    private int firstPositionX;

    private String tray;

    private boolean asterisk = false;


    private HashSet<String> totalWords = new HashSet<>();

    private ArrayList<String> totalList = new ArrayList<>();

    private char[][] temp;

    public Score letterScore = new Score();

    private int letterFromTray =0;

    public HumanPlay human = new HumanPlay();

    ComputerPlay computer = new ComputerPlay();

    Random rand = new Random();

    private int humanScore = 0;

    private int computerScore =0;

    char[][] oldBoard;

    int eachScore =0;

    char[][] highestBoard;

    String computerPlayed ="";


    String computerBestPlayed ="";


    gameSystem( char[][] board, String dictionary) {
        data = new dictRead(dictionary);
        this.boardSize = board[0].length;
        this.boardStore = board;
        this.board = new char[boardSize][boardSize];
        this.temp = new char[boardSize][boardSize];
        oldBoard = new char[boardSize][boardSize];
        letterScore.setScore();
        letterScore.setLeftString();
    }


    public void setOldBoard(char[][] oldBoard) {
        this.oldBoard = oldBoard;
    }

    public void setNewBoard(char[][] newBoard){
        this.board = newBoard;
    }



    public void setFinalHumanScore(){
        for(int i = 0; i<computer.getComputerTray().length();i++){
            humanScore = humanScore + letterScore.getScore(computer.getComputerTray().charAt(i));
        }
    }

    public void setComputerFinalScore(){
        for(int i = 0; i<human.getHumanTray().length();i++){
            computerScore = computerScore + letterScore.getScore(human.getHumanTray().charAt(i));
        }
    }


    public void setComputerHumanFinalScore(){
        for(int i = 0; i<human.getHumanTray().length();i++){
            humanScore = humanScore - letterScore.getScore(human.getHumanTray().charAt(i));
        }

        for(int i = 0; i<computer.getComputerTray().length();i++){
            computerScore = computerScore - letterScore.getScore(computer.getComputerTray().charAt(i));
        }


    }


    public int getHumanScore(){
        for (int i =0; i<boardSize;i++){
            for(int j = 0; j<boardSize;j++){
                if(!(oldBoard[i][j]==board[i][j])){
                    humanPlayPosition.add(new BoardCoordinate(i,j));
                }
            }
        }

        String direction = "";
        if(humanPlayPosition.size()>1){
            if(humanPlayPosition.get(0).getX()==humanPlayPosition.get(1).getX()){
                direction = "horizontal";
            } else {
                direction ="vertical";
            }
        }

        if(direction.equals("vertical")) {
            checkVertical(humanPlayPosition.get(0));


            for(int j =0; j<humanPlayPosition.size(); j++){
                checkSideWise(humanPlayPosition.get(j));
            }
        }

        if(direction.equals("horizontal")) {
            checkSideWise(humanPlayPosition.get(0));
            //also check sidewise for each element
            for(int j =0; j<humanPlayPosition.size(); j++){
                checkVertical(humanPlayPosition.get(j));
            }
        }
        if(direction.equals("")){
            checkSideWise(humanPlayPosition.get(0));
            checkVertical(humanPlayPosition.get(0));
        }
        humanScore = humanScore +eachScore;
        humanPlayPosition.clear();
        return humanScore;
    }

    public void checkVertical(BoardCoordinate position){
        eachScore =0;
        int mulitplier = 1;
        int localScoreX = 0;
        int i = 0;
        int x = position.getX();
        int y = position.getY();
        int counter =0;

        while (x - i >= 0 && Character.isAlphabetic(board[x - i][y])) {
            counter++;
            if (Character.isDigit(oldBoard[x - i][y])) {
                if (Character.getNumericValue(oldBoard[x - i][y]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x - i][y]) - 5;
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x - i][y]) * Character.getNumericValue(oldBoard[x - i][y]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x - i][y]);
            }
            i++;
        }


        i = 1;

        while (x + i < boardSize && Character.isAlphabetic(board[x + i][y])) {
            counter++;
            if (Character.isDigit(oldBoard[x + i][y])) {
                if (Character.getNumericValue(oldBoard[x + i][y]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x + i][y]) - 5;
                    localScoreX = localScoreX + letterScore.getScore(board[x + i][y]);
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x + i][y]) * Character.getNumericValue(oldBoard[x + i][y]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x + i][y]);
            }
            i++;
        }

        localScoreX = localScoreX * mulitplier;

        if(counter ==1){
            localScoreX =0;
        }
        humanScore =humanScore +localScoreX;
    }



    public void checkSideWise(BoardCoordinate position){
        eachScore =0;
        int i = 0;
        int counter =0;
        int mulitplier =1;
        int x = position.getX();
        int y = position.getY();
        int localScoreX =0;

        while (y - i >= 0 && Character.isAlphabetic(board[x ][y-i])) {
            counter++;

            if (Character.isDigit(oldBoard[x][y-i])) {
                if (Character.getNumericValue(oldBoard[x][y-i]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x][y-i]) - 5;
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x][y-i]) * Character.getNumericValue(oldBoard[x][y-i]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x][y-i]);
            }
            i++;
        }
        i = 1;

        while (y +i < boardSize && Character.isAlphabetic(board[x][y+i])) {

            counter ++;
            if (Character.isDigit(oldBoard[x][y+i])) {
                if (Character.getNumericValue(oldBoard[x][y+i]) > 5) {
                    mulitplier = Character.getNumericValue(oldBoard[x][y+i]) - 5;
                    localScoreX = localScoreX + letterScore.getScore(board[x][y+i]);
                } else {
                    localScoreX = localScoreX + letterScore.getScore(board[x][y+i]) * Character.getNumericValue(oldBoard[x][y+i]);
                }
            } else {
                localScoreX = localScoreX + letterScore.getScore(board[x][y+i]);
            }
            i++;
        }

        if(counter ==1){
            localScoreX =0;
        }

        localScoreX = localScoreX * mulitplier;
        humanScore =humanScore +localScoreX;

    }



    public  int getHumanFinalScore(){
        return humanScore;
    }


    public int getComputerSCore(){
        return computerScore;
    }

    public void setUpTiles(){

        while (human.getHumanTray().length()<7 &&letterScore.leftOverString.length()>0){
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length());
            human.setHumanTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }

        while (computer.getComputerTray().length()<7 &&letterScore.leftOverString.length()>0){
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length());
            computer.setComputerTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }
    }


    public void updateBoard(){
        temp = new char[boardSize][boardSize];
        highestBoard= new char[boardSize][boardSize];
        for(int g =0;g<boardSize;g++){
            for(int h =0; h<boardSize;h++){
                highestBoard[h][g]= board[h][g];
                boardStore[h][g]= board[h][g];
                temp[h][g]= board[h][g];
                oldBoard[h][g]= board[h][g];
            }
        }
        System.out.println("board used for computation is given below");
        printBoard(board);
        System.out.println("printing best information below");
        findAnchor();
    }



    public char[][] getHighestBoard(){
        setBoardStore();
        drawLetters();
        if(computerBestPlayed.length()==0){
            exchangeTray();
        }
        return highestBoard;
    }

    public void exchangeTray(){
        for(int i =0; i <computer.getComputerTray().length(); i++){
            letterScore.putBackLetter(computer.getComputerTray().charAt(i));
        }
        computer.removeAll();


        while (computer.getComputerTray().length()<7 && letterScore.leftOverString.length()>0) {
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length() - 1);
            computer.setComputerTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }

    }

    public void setBoardStore(){
        for(int g =0;g<boardSize;g++){
            for(int h =0; h<boardSize;h++){
                boardStore[h][g]= highestBoard[h][g];
            }
        }


    }


    public void drawLetters(){


        for (int i = 0; i< computerBestPlayed.length(); i++){
            computer.removeCharacter(computerBestPlayed.charAt(i));
        }

        //this while loop stops if computer gets 7 letters or the bag does not have the letters in it.
        while (computer.getComputerTray().length()<7 && letterScore.leftOverString.length()>0){
            String localStr = letterScore.getStringTile();
            int randomInteger = rand.nextInt(localStr.length());
            computer.setComputerTray(localStr.charAt(randomInteger));
            letterScore.setStringTile(Character.toString(localStr.charAt(randomInteger)));
        }

    }

    public void findAnchor(){
        characterPositions.clear();
        anchorUp.clear();
        anchorRight.clear();
        anchorDown.clear();
        anchorLeft.clear();
        allScores.clear();




        for(int g =0;g<boardSize;g++){
            for(int h =0; h<boardSize;h++){
                board[h][g]= boardStore[h][g];
            }
        }

        for(int i = 0; i<boardSize;i++){
            for (int j = 0; j<boardSize;j++){
                if(Character.isLetter(board[i][j])){
                    characterPositions.add(new BoardCoordinate(i,j));
                }
            }
        }

        for(int i =0; i<characterPositions.size();i++){
            int leftX =characterPositions.get(i).getX();
            int leftY = characterPositions.get(i).getY()-1;

            int rightX = characterPositions.get(i).getX();
            int rightY = characterPositions.get(i).getY()+1;

            int upX = characterPositions.get(i).getX()-1;
            int upY = characterPositions.get(i).getY();

            int downX = characterPositions.get(i).getX()+1;
            int downY = characterPositions.get(i).getY();
            if(leftX>=0&&leftY>=0 && !Character.isLetter(board[leftX][leftY])){
                anchorLeft.add(new BoardCoordinate(leftX,leftY));
                anchorUp.add(new BoardCoordinate(leftX,leftY));
                // System.out.println("x is "+ (leftX)+ " and  y is "+leftY);
            }
            if(rightX<boardSize && rightY<boardSize&&!Character.isLetter(board[rightX][rightY])){
                anchorRight.add(new BoardCoordinate(rightX,rightY));
                anchorDown.add(new BoardCoordinate(rightX,rightY));
                // System.out.println("x is "+ (rightX)+ " and  y is "+rightY);
            }

            if(upY>=0 && upX>=0 && !Character.isLetter(board[upX][upY])) {
                anchorUp.add(new BoardCoordinate(upX,upY));
                anchorLeft.add(new BoardCoordinate(upX,upY));
            }
            if(downY<boardSize && downX<boardSize&&!Character.isLetter(board[downX][downY])){
                anchorDown.add(new BoardCoordinate(downX,downY));
                anchorRight.add(new BoardCoordinate(downX,downY));
            }
        }


        findAllPossibleWords();

    }


    public void findAllPossibleWords() {
        tray = computer.getComputerTray();


        for (int i = 0; i < tray.length(); i++) {
            if (tray.charAt(i) == '*') {
                tray = tray.replaceAll("\\*", "");
                asterisk = true;
            }
        }

        allScores.clear();
        allScores.add(new gameScore();
        allScores.get(0).resetAll();


        for (int i = 0; i < anchorLeft.size(); i++) {
            String str = "";
            int j = 1;

            while (anchorLeft.get(i).getY()+j < boardSize &&Character.isLetter(board[anchorLeft.get(i).getX()][anchorLeft.get(i).getY()+j])
            ) {
                str = str + Character.toString(board[anchorLeft.get(i).getX()][anchorLeft.get(i).getY()+j]);
                j++;
            }
            fixedString = str;
            direction = "left";
            getString(str, anchorLeft.get(i),"left");
        }


        for (int i = 0; i < anchorRight.size(); i++) {
            String str = "";
            int j = 1;
            while (anchorRight.get(i).getY() - j >= 0 &&Character.isLetter(board[anchorRight.get(i).getX()][anchorRight.get(i).getY() - j])
            ) {
                str = str + Character.toString(board[anchorRight.get(i).getX()][anchorRight.get(i).getY() - j]);
                j++;
            }
            fixedString = str;
            direction ="right";
            getString(str, anchorRight.get(i), "right");
        }


        for (int i = 0; i < anchorUp.size(); i++) {
            String str = "";
            int j = 1;

            while (anchorUp.get(i).getX()+j < boardSize &&Character.isLetter(board[anchorUp.get(i).getX()+j][anchorUp.get(i).getY()])) {
                str = str + Character.toString(board[anchorUp.get(i).getX()+j][anchorUp.get(i).getY()]);
                j++;
            }
            //System.out.println("this is the string from up anchor"+ str);
            fixedString = str;
            direction = "up";
            anchorUp.get(i).exchange(anchorUp.get(i).getX(),anchorUp.get(i).getY());
            //getString(str, anchorUp.get(i),"up");
        }


        for (int i = 0; i < anchorDown.size(); i++) {
            String str = "";
            int j = 1;
            while (anchorDown.get(i).getY()-j >= 0 &&Character.isLetter(board[anchorDown.get(i).getY()-j][anchorDown.get(i).getX()])
            ) {
                str = str + Character.toString(board[anchorDown.get(i).getY()-j ][anchorDown.get(i).getX()]);
                j++;
            }
            fixedString = str;
            direction = "down";
            anchorDown.get(i).exchange(anchorDown.get(i).getX(),anchorDown.get(i).getY());
            //getString(str, anchorDown.get(i),"down");
        }


        if(computerBestPlayed!="") {
            computerScore = computerScore + allScores.get(0).getScore();
        }
        allScores.get(0).print();

        System.out.println("this is the size of all score "+allScores.size());


    }

    public boolean isWinner() {
        int storeComputerScore =computerScore;
        String storeComputerTray = computer.getComputerTray();
        if (letterScore.leftOverString.length() == 0 && (human.getHumanTray().length() == 0 || computer.getComputerTray().length() == 0)) {
            return true;
        }
        if (letterScore.leftOverString.length() == 0) {
            char[][] localBoard = new char[boardSize][boardSize];
            for (int m = 0; m < boardSize; m++) {
                for (int n = 0; n < boardSize; n++) {
                    localBoard[m][n] = board[m][n];
                }
            }
            findAnchor();

            if (!computerBestPlayed.equals("")) {

                for (int m = 0; m < boardSize; m++) {
                    for (int n = 0; n < boardSize; n++) {
                        board[m][n] = localBoard[m][n];
                    }
                }
                computerScore = storeComputerScore;
                return false;
            }


            computer.setComputerTray(human.getHumanTray());
            findAnchor();

            if(!computerBestPlayed.equals("")){
                for (int m = 0; m < boardSize; m++) {
                    for (int n = 0; n < boardSize; n++) {
                        board[m][n] = localBoard[m][n];
                    }
                }
                computer.setComputerTray(storeComputerTray);
                computerScore = storeComputerScore;
                return false;
            }
            computer.setComputerTray(storeComputerTray);



        } else {
            computerScore = storeComputerScore;
            return false;
        }
        computerScore = storeComputerScore;
        return true;
    }


    public ArrayList<String>  possibleWords(String str){

        allFoundWords.clear();
        String combined =str+tray;
        if(asterisk ==false) {
            allFoundWords.addAll(data.trie.findWord(combined, data.trie.root,str));
        } else {
            for (int i =0; i<26;i++){
                String letter =Character.toString((char)(i+'a'));
                allFoundWords.addAll(data.trie.findWord((combined+letter),data.trie.root,str));
            }
        }


        return allFoundWords;
    }


    public void getString(String string, BoardCoordinate boardCoordinate, String str){
        transpose = false;

        if (str.equals("up")||str.equals("down")){
            transpose = true;
        }
        totalWords.clear();
        totalList.clear();
        totalWords.addAll(possibleWords(string));
        totalList.addAll(totalWords);


        for(int i =0; i<totalList.size();i++){

            for(int p =0;p<boardSize;p++){
                for(int j =0; j<boardSize;j++){
                    board[p][j]= boardStore[p][j];
                }
            }
            for(int m =0;m<boardSize;m++){
                for(int n =0; n<boardSize;n++){
                    temp[m][n]= boardStore[m][n];
                }
            }


            if(transpose){
                for(int p =0;p<boardSize;p++){
                    for(int j =0; j<boardSize;j++){
                        board[p][j]= boardStore[j][p];
                    }
                }
                for(int p =0;p<boardSize;p++){
                    for(int j =0; j<boardSize;j++){
                        temp[p][j]= boardStore[j][p];
                    }
                }

            }

            if(str.equals("left")||str.equals("up")) {
                if (isLegalLeft(totalList.get(i), string, boardCoordinate)) {
                    if (isLegal(temp)) {
                        scoreLeft = 0;


                        calculateScoreLeft(totalList.get(i), firstPositionX, firstPositionY);


                        getAdditionalScoreLeftRight();


                        if(letterFromTray==7){
                            scoreLeft =scoreLeft+50;
                        }


                        if(!allScores.isEmpty()) {


                            if (allScores.get(0).getScore() < scoreLeft &&
                                    !Character.isAlphabetic(board[allScores.get(0).row()][allScores.get(0).col()]) ) {

                                computerBestPlayed = computerPlayed;
                                allScores.get(0).setAll(new BoardCoordinate(firstPositionX, firstPositionY),
                                        totalList.get(i),scoreLeft,direction);
                                scoreLeft=0;
                                highestBoard =new char[boardSize][boardSize];


                                for(int k =0; k<boardSize;k++){
                                    for (int m = 0; m< boardSize;m++){
                                        highestBoard[k][m] = temp[k][m];
                                    }
                                }
                            }
                        }
                    } else {
                    }
                } else {
                }
            }

            if(str.equals("right")||str.equals("down")){

                if(isLegalRight(totalList.get(i),string, boardCoordinate)) {
                    if (isLegal(temp)) {
                        scoreRight = 0;

                        calculateScoreRight(totalList.get(i), firstPositionX, firstPositionY);


                        getAdditionalScoreLeftRight();


                        if(letterFromTray==7){
                            scoreRight =scoreRight+50;
                        }
                        if(!allScores.isEmpty()) {


                            if (allScores.get(0).getScore() < scoreRight) {
                                computerBestPlayed = computerPlayed;
                                allScores.get(0).setAll(new BoardCoordinate(firstPositionX, firstPositionY),totalList.get(i),scoreRight,direction);
                                scoreRight = 0;


                                highestBoard =new char[boardSize][boardSize];
                                for(int k =0; k<boardSize;k++){
                                    for (int m = 0; m< boardSize;m++){
                                        highestBoard[k][m] = temp[k][m];
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }
    }

    public void getAdditionalScoreLeftRight() {
        boolean canAdd = false;
        String local="";


        if(!(direction.equals("up")||direction.equals("down"))) {
            for (int i = 0; i < boardSize; i++) {
                String str = "";
                for (int j = 0; j < boardSize; j++) {
                    if (Character.isLetter(temp[j][i])) {
                        str = str + Character.toString(temp[j][i]);


                        for (int s = 0; s < stringPositionRight.size(); s++) {
                            if (stringPositionRight.get(s).getX() == j && stringPositionRight.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;
                            }
                        }


                        for (int s = 0; s < stringPositionLeft.size(); s++) {
                            if (stringPositionLeft.get(s).getX() == j && stringPositionLeft.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;
                            }
                        }
                    } else {

                        if (str.length() > 1) {
                            if (canAdd) {
                                scoreRight = scoreRight + calculateNormalString(str);
                                scoreLeft = scoreLeft + calculateNormalString(str);
                                local = "";
                            }
                        }
                        canAdd = false;
                        str = "";
                    }
                }
            }
        } else {


            for (int j = 0; j < boardSize; j++) {
                String str = "";
                for (int i = 0; i < boardSize; i++) {
                    if (Character.isLetter(temp[j][i])) {
                        str = str + Character.toString(temp[j][i]);

                        for (int s = 0; s < stringPositionRight.size(); s++) {
                            if (stringPositionRight.get(s).getX() == j && stringPositionRight.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;
                            }
                        }

                        for (int s = 0; s < stringPositionLeft.size(); s++) {
                            if (stringPositionLeft.get(s).getX() == j && stringPositionLeft.get(s).getY() == i) {
                                local = local + Character.toString(temp[j][i]);
                                canAdd = true;
                            }
                        }
                    } else {
                        if (str.length() > 1) {
                            if (canAdd) {
                                scoreRight = scoreRight - calculateNormalString(local);
                                scoreRight = scoreRight + calculateNormalString(str);
                                scoreLeft = scoreLeft + calculateNormalString(str);
                                local = "";
                            }
                        }
                        canAdd = false;
                        str = "";
                    }
                }
            }
        }
        stringPositionLeft.clear();
        stringPositionRight.clear();
    }

    public int  calculateNormalString(String string){
        int localScore =0;
        for(int i = 0; i<string.length();i++){
            localScore = localScore+letterScore.getScore(string.charAt(i));
        }
        return localScore;
    }



    public void calculateScoreRight(String str, int firstPositionX, int firstPositionY){
        int multiplier =1;
        scoreRight = scoreRight+ calculateNormalString(fixedString);
        for(int i =0; i<str.length();i++){


            if(!Character.isLetter(board[firstPositionX][firstPositionY+i])){


                if(Character.isDigit(board[firstPositionX][firstPositionY+i])) {


                    if (Character.getNumericValue(board[firstPositionX][firstPositionY+i]) < 5) {
                        scoreRight = scoreRight + letterScore.getScore(str.charAt(i)) * Character.getNumericValue(board[firstPositionX][firstPositionY+i]);

                    }
                    else if (Character.getNumericValue(board[firstPositionX][firstPositionY + i]) >= 5) {
                        multiplier = Character.getNumericValue(board[firstPositionX][firstPositionY+i]) - 5;
                    }
                } else {
                    scoreRight = scoreRight + letterScore.getScore(str.charAt(i));
                }
            }
        }
        scoreRight = scoreRight*multiplier;
    }


    public boolean isLegal(char[][] temp){


        for(int i =0; i<boardSize;i++) {
            String str = "";
            for (int j = 0; j < boardSize; j++) {
                if (Character.isLetter(temp[i][j])) {
                    str = str + Character.toString(temp[i][j]);
                }

                else if (str.length() > 1) {
                    if (!data.trie.search(str)){
                        //if (!data.tree.isWord(data.tree, str.toLowerCase())) {
                        return false;
                    } else {
                        str ="";
                    }
                } else {
                    str ="";
                }
            }


            if (str.length() > 1) {
                if (!data.trie.search(str)){
                    // if (!data.tree.isWord(data.tree,str)) {
                    return false;
                }

            }

        }


        for(int i =0; i<boardSize;i++) {
            String str = "";
            for (int j = 0; j < boardSize; j++) {
                if (Character.isLetter(temp[j][i])) {
                    str = str + Character.toString(temp[j][i]);
                }

                else if (str.length() > 1) {
                    if (!data.trie.search(str)){
                        //  if (!data.tree.isWord(data.tree, str)) {
                        return false;
                    } else {
                        str ="";
                    }
                } else {
                    str ="";
                }
            }


            if (str.length() > 1) {
                if (!data.trie.search(str)){
                    // if (!data.tree.isWord(data.tree, str)) {
                    return false;
                }

            }

        }

        return true;
    }

    public boolean isLegalRight(String str, String fixedstr, BoardCoordinate boardCoordinate){
        computerPlayed = "";
        stringPositionRight.clear();
        letterFromTray=0;

        int position = str.indexOf(fixedstr);
        firstPositionY = boardCoordinate.getY()-fixedstr.length();
        firstPositionX= boardCoordinate.getX();


        for(int i = 0; i<position; i++) {
            if (boardCoordinate.getY() -1- i-fixedstr.length() >= 0) {
                if (!Character.isLetter(temp[boardCoordinate.getX()][boardCoordinate.getY()-i-1-fixedstr.length()])) {
                    firstPositionY = boardCoordinate.getY()-i-1-fixedstr.length();
                    stringPositionRight.add(new BoardCoordinate(boardCoordinate.getX(), boardCoordinate.getY()-i-1-fixedstr.length()));
                    letterFromTray++;


                    temp[boardCoordinate.getX()][boardCoordinate.getY()-i-fixedstr.length()-1]=str.charAt(position-1-i);
                    computerPlayed = computerPlayed + str.charAt(position-1-i);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }


        for (int i =0;i<(str.length()-position-fixedstr.length());i++) {
            if (boardCoordinate.getY() + i < boardSize) {
                if (!Character.isLetter(temp[boardCoordinate.getX()][boardCoordinate.getY() + i])) {
                    temp[boardCoordinate.getX()][boardCoordinate.getY() + i] = str.charAt(position + fixedstr.length() + i);
                    letterFromTray++;
                    computerPlayed = computerPlayed + str.charAt(position + fixedstr.length() + i);
                    stringPositionRight.add(new BoardCoordinate(boardCoordinate.getX(), boardCoordinate.getY() + i));
                } else {
                    return false;
                }
            } else return false;
        }


        char[][] transposeB = new char[boardSize][boardSize];
        if(transpose) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    transposeB[i][j] = temp[i][j];
                }
            }
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    temp[i][j] = transposeB[j][i];
                }
            }
            for (int g = 0; g < boardSize; g++) {
                for (int h = 0; h < boardSize; h++) {
                    board[h][g] = boardStore[h][g];
                }
            }
        }
        //boardObject.printBoard(temp,boardSize);
        return true;
    }


    public void calculateScoreLeft(String str, int firstPositionX, int firstPositionY){
        int multiplier =1;
        scoreLeft = scoreLeft+ calculateNormalString(fixedString);
        for(int i =0; i<str.length();i++){
            if(!Character.isLetter(board[firstPositionX][firstPositionY+i])){
                if(Character.isDigit(board[firstPositionX][firstPositionY+i])) {
                    if (Character.getNumericValue(board[firstPositionX][firstPositionY+i]) < 5) {
                        scoreLeft = scoreLeft + letterScore.getScore(str.charAt(i)) * Character.getNumericValue(board[firstPositionX][firstPositionY + i]);

                    } else if (Character.getNumericValue(board[firstPositionX][firstPositionY + i]) >= 5) {

                        multiplier = Character.getNumericValue(board[firstPositionX][firstPositionY+i]) - 5;

                    }
                } else {
                    scoreLeft = scoreLeft + letterScore.getScore(str.charAt(i));

                }
            } else{
                scoreLeft = scoreLeft + letterScore.getScore(str.charAt(i));

            }
        }
        scoreLeft = scoreLeft*multiplier;

    }



    public boolean isLegalLeft(String str, String fixedstr, BoardCoordinate boardCoordinate){

        computerPlayed = "";
        for(int i =0;i<boardSize;i++){
            for(int j =0; j<boardSize;j++){
                temp[i][j]= board[i][j];
            }
        }
        letterFromTray =0;
        stringPositionLeft.clear();
        int position = str.indexOf(fixedstr);
        firstPositionY = boardCoordinate.getY()+1;
        firstPositionX= boardCoordinate.getX();


        for(int i = 0; i<position; i++) {
            if (boardCoordinate.getY() - i >= 0) {
                if (!Character.isLetter(temp[boardCoordinate.getX()][boardCoordinate.getY()-i])) {
                    firstPositionY = boardCoordinate.getY()-i;
                    stringPositionLeft.add(new BoardCoordinate(boardCoordinate.getX(), boardCoordinate.getY()-i));
                    temp[boardCoordinate.getX()][boardCoordinate.getY()-i]=str.charAt(position-1-i);
                    computerPlayed = computerPlayed + str.charAt(position-1-i);
                    letterFromTray++;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }


        if(fixedstr.length()>0) {
            for (int i = fixedstr.length(); i < (str.length() - position); i++) {

                if (boardCoordinate.getY() + 1 + i < boardSize) {

                    if (!Character.isLetter(temp[boardCoordinate.getX()][boardCoordinate.getY() + 1 + i])) {
                        //System.out.println("this is char assigned 1133" + Character.toString(str.charAt(i))+"at x"+boardPosition.getX()+"and y :"+(boardPosition.getY()+1+i));
                        temp[boardCoordinate.getX()][boardCoordinate.getY() + 1 + i] = str.charAt(position + i);
                        letterFromTray++;
                        computerPlayed = computerPlayed +str.charAt(position + i);
                        stringPositionLeft.add(new BoardCoordinate(boardCoordinate.getX(), boardCoordinate.getY() + 1 + i));
                    } else {
                        return false;
                    }
                } else return false;
            }
        } else {


            for (int i = fixedstr.length(); i < (str.length() - position); i++) {

                if (boardCoordinate.getY() + 1 + i < boardSize) {

                    if (!Character.isLetter(temp[boardCoordinate.getX()][boardCoordinate.getY() + i])) {
                        temp[boardCoordinate.getX()][boardCoordinate.getY() + i] = str.charAt(position + i);
                        computerPlayed = computerPlayed +str.charAt(position + i);
                        letterFromTray++;
                        stringPositionLeft.add(new BoardCoordinate(boardCoordinate.getX(), boardCoordinate.getY() + i));
                    } else {
                        return false;
                    }
                } else return false;
            }
        }



        char[][] transposeB = new char[boardSize][boardSize];
        if(transpose) {
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    transposeB[i][j] = temp[i][j];
                }
            }
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    temp[i][j] = transposeB[j][i];
                }
            }
            for (int g = 0; g < boardSize; g++) {
                for (int h = 0; h < boardSize; h++) {
                    board[h][g] = boardStore[h][g];
                }
            }
        }
        //boardObject.printBoard(temp,boardSize);
        return true;
    }

    public void printBoard(char[][] board) {
        int sizeOfBoard = board[0].length;
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
                char c = board[i][j];
                String temp =""+c;
                if(Character.isDigit(c)) {
                    if (Integer.parseInt(temp) > 5) {
                        System.out.print("" + (Integer.parseInt(temp)-5) + ". ");
                    } else {
                        System.out.print("."+c+" ");
                    }
                }

                if(Character.isAlphabetic(c)){
                    System.out.print(" "+c+ " ");
                }
                if(temp.charAt(0)=='.'){
                    System.out.print(".. ");
                }
            }
            System.out.println("\n");

        }

    }
}
}
