package mainGame;

import java.util.HashMap;
class Score {
    private char[] letters = {'*','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
            's','t','u','v','w','x','y','z'};

    private int[] letterScore= {0,1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    private int[] frequency =  {2,12,9,9,8,6,6,6,4,4,4,4,3,2,2,2,2,2,2,2,2,2,1,1,1,1,1};
    String leftOverString = "**";
    private HashMap<Character,LetterNode> scoreMap = new HashMap();
    public void setScore(){
        for(int i =0; i<letters.length;i++){
            scoreMap.put(letters[i],new LetterNode(letterScore[i],frequency[i]));
        }
    }
    public void setLeftString(){
        for (int i =0;i<scoreMap.size()-1;i++){
            for (int j =0; j<scoreMap.get((char)('a'+i)).getFrequency();j++){
                leftOverString =  leftOverString+ Character.toString((char)('a'+i));
            }
        }
    }
    public String  getStringTile(){
        return leftOverString;
    }
    public void putBackLetter(char c){
        leftOverString =leftOverString +Character.toString(c);
    }
    public void setStringTile(String str) {
        if (str.equals("*")) {
            leftOverString = leftOverString.replaceFirst("\\*", "");
        } else {
            leftOverString = leftOverString.replaceFirst(str, "");
        }
    }
    public int getScore(char c){
        return scoreMap.get(c).getScore();
    }
}

class LetterNode {
    private int score;
    private int frequency;
    public LetterNode(int score, int frequency){
        this.score = score;
        this.frequency = frequency;
    }
    public int  getFrequency(){
        return frequency;
    }
    public int getScore(){
        return score;
    }
}