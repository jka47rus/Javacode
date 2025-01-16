package test;

import main.Creator;
import main.Snapshot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatorTests {

    private final static Creator creator = new Creator();

    @BeforeAll
    static void beforeEach(){
        creator.saveSnapshot("Word ");
        creator.saveSnapshot("is written!");
    }

    @Test
    void saveSnapshotAndPrintTillTheIndex(){
        String expected = "Word is written!";
        String actual = creator.printTillTheIndex(1);

        assertEquals(expected, actual);

    }

    @Test
    void showSnapshots(){
        String expected = """
                Индекс: 0; Содержимое: Word\s
                Индекс: 1; Содержимое: is written!
                """;
        String actual = creator.showSnapshots();

        assertEquals(expected, actual);
    }

    @Test
    void restoreSnapshot(){
        String expected = "is written!";
        String actual = creator.restoreSnapshot(1);

        assertEquals(expected, actual);
    }

    @Test
    void incorrectIndex(){
        String expected = "Некорректный индекс!";
        String actual1 = creator.restoreSnapshot(3);
        String actual2 = creator.printTillTheIndex(3);

        assertEquals(expected, actual1);
        assertEquals(expected, actual2);
    }

}
