package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMyWork {

    // коллекции данных
    ArrayList<String> text = new ArrayList<>();  // полученные и необработанные строчки
    ArrayList<String> wodrs = new ArrayList<>(); // разбитые на слова
    ArrayList<WordCount> wordCounts = new ArrayList<>(); // подсчитанные слова

    @Test
    public void testConnectURL() throws Exception {
        Controller newController = new Controller();
        assertEquals(newController.booleanConnectURL("https://yandex.ru/"), true);
        assertEquals(newController.booleanConnectURL("https://www.simbirsoft.com/"), true);
        assertEquals(newController.booleanConnectURL("https://www.dafdsfasdfsdvdasfadsfea12321.com/"), false);
        assertEquals(newController.booleanConnectURL("sjfihsdkjbvkeuhi"), false);
        assertEquals(newController.booleanConnectURL("https://343243fcdsfve/"), false);
    }

    @Test
    public void testTreatmentParse() throws Exception {
        Controller newController = new Controller();
        ArrayList<String> arrayWodrs = new ArrayList<>();
        for (String s:arrayWodrs){
            assertEquals(s, "");
        }
        arrayWodrs.add("fff.ddd/768 asdsc,pdsd013%kk !ad!dsclp");
        arrayWodrs.add("asdsac ojcmas 32423 dsfas osd/sdd/fes^dfew#@-_21");
        arrayWodrs= newController.treatmentParse(arrayWodrs);
        String myString="";
        for (String s:arrayWodrs){myString+=s+", ";}
        assertEquals(myString, "fff, ddd, 768, asdsc, pdsd013kk, ad, asdsac, ojcmas, 32423, dsfas, osd, sdd, fesdfew, ");
    }

    @Test
    public void testTreatmentCount() throws Exception {
        Controller newController = new Controller();
        ArrayList<WordCount> wordCounts = new ArrayList<>();
        ArrayList<String> arrayWodrs = new ArrayList<>();
        wordCounts= newController.treatmentCount(arrayWodrs);
        for (WordCount w:wordCounts){
            assertEquals(w.toString(), "");
        }
        arrayWodrs.add("aaa");
        arrayWodrs.add("aaa");
        arrayWodrs.add("fesdfew");
        arrayWodrs.add("aaa");
        wordCounts= newController.treatmentCount(arrayWodrs);
        String myString="";
        for (WordCount w:wordCounts){
            myString+=w.toString()+", ";
        }
        assertEquals(myString, "aaa/3, fesdfew/1, ");
    }

}
