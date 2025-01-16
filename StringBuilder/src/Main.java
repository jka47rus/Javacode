import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<String> list = new ArrayList<>();


    public static void main(String[] args) {

        addWords("Some");
        addWords(" ");
        addWords("Words");
        addWords("!");

        undo();

        printWords();


    }

    public static void addWords(String words){
        list.add(words);

    }

    public static void undo(){
        list.removeLast();

    }

    public static String printWords(){
        StringBuilder sb = new StringBuilder();
        list.forEach(sb::append);
        System.out.println(sb.toString());
        return sb.toString();
    }



}