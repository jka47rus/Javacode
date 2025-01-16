package main;

public class Main {
    public static void main(String[] args) {

        Creator creator = new Creator();

        String first = "Hello ";
        String second = "World";
        String third = "!";

        creator.saveSnapshot(first);
        creator.saveSnapshot(second);
        creator.saveSnapshot(third);


        creator.showSnapshots();
        creator.restoreSnapshot(2);
        creator.printTillTheIndex(3);

    }
}



