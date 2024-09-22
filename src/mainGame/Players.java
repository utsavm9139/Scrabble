package mainGame;

class ComputerPlay {
    private String computerTray =null;
    public ComputerPlay(){
        this.computerTray=computerTray;
    }
    public String getComputerTray(){
        return computerTray;
    }
    public void setComputerTray(char c){ computerTray += Character.toString(c); }
    public void setComputerTray(String string){
        computerTray = string;
    }
    public void removeCharacter(char c){
        if(c=='*') {
            computerTray = computerTray.replaceFirst("\\*", "");
        } else {
            computerTray = computerTray.replaceFirst(Character.toString(c), "");
        }
    }
    public void removeAll(){
        computerTray = "";
    }
}
class HumanPlay {
    private String humanTray = "";
    public String getHumanTray(){
        return humanTray;
    }
    public void setHumanTray(char c){
        humanTray += Character.toString(c); }
    public void setHumanTray(String str){
        humanTray = humanTray + str;
    }
    public void renewTray(String str){
        for(int i = 0; i<str.length();i++){
            char c= str.charAt(i);
            if(c=='*'){
                humanTray = humanTray.replaceFirst("\\*", "");
            } else {
                humanTray = humanTray.replaceFirst(Character.toString(c), "");
            }
        }
    }
    public void removeAll(){
        humanTray ="";
    }
}

