package mainGame;
import java.io.*;
import java.util.Scanner;
class dictRead {
    public String filePath ;
    Trie tree = new Trie();
    public dictRead(String filePath){
        this.filePath=filePath;
        dictionaryFileReader();
    }
    public  void dictionaryFileReader()  {
        try {
            Scanner myReader = new Scanner(new File(filePath));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                tree.insert(data.toLowerCase());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
class Trie {
    private  Node root;
    public Trie() { this.root=new Node('\0'); }
    public void insert( String word) {
        Node curr = root;
        for(int i=0; i<word.length();i++){
            char c = word.charAt(i);
            if(curr.children[c-'a']==null){
                curr.children[c-'a']= new Node(c);
            }
            curr=curr.children[c-'a'];
        }
        curr.isWord=true;

    }
    public boolean search(String word){
        Node node= getNode(word);
        return node!=null&& node.isWord;
    }
    public boolean startsWith(String perfix){
        if(getNode(perfix)!=null){
            return true;
        }
        return false;
    }
    private Node getNode(String word){
        Node curr = root;
        for(int i=0;i<word.length();i++){
            char c= word.charAt(i);
            if(curr.children[c-'a']==null) {
                return null;
            }
            curr = curr.children[c-'a'];
        }
        return curr;
    }

}
class Node {
    public char c;
    public boolean isWord;
    public Node[] children;
    public Node(char c){
        this.c= c;
        isWord=false;
        children= new Node[26];
    }
}
