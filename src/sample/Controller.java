package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Controller {

    ArrayList<String> text = new ArrayList<>();
    ArrayList<String> wodrs = new ArrayList<>();
    ArrayList<WordCount> wordCounts = new ArrayList<>();

    @FXML TextField nameURL;
    @FXML TextArea phrases;
    @FXML Label status;


    // ---------------------------------------------------------------------- инициализация ----------------------
    public void init(){
        nameURL.setText("https://www.simbirsoft.com/");
    }

    public void start(MouseEvent event) throws Exception {
        status.setText(" Статус: попытка соединения");
        connect();
        status.setText(" Статус: обработка текста");
        treatmentParse();
        treatmentCount();
        status.setText(" Статус: успех");
        for (WordCount w:wordCounts){
            //System.out.println(w.getWord()+"-"+w.getCount());
            phrases.setText(phrases.getText()+w.getWord()+"-"+w.getCount()+"\n");
        }
    }

    private void connect() throws Exception{
        try {
            URL url = new URL(nameURL.getText());

            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputFile = urlc.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile, "UTF-8"));

            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                text.add(line);
            }
            status.setText(" Статус: соединение, успех");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            status.setText(" Статус: Error");
        }
    }

    private void treatmentParse() throws Exception{
        for (String s:text){
            //s = s.replaceAll("[^A-Za-zА-Яа-я0-9]", "");

            //внутренний массив с которым ми работаем
            char[] digits = new char[]{' ', ',', '.', '!', '?', '"',';',':','[',']','(',')','\n','\r','\t', '/', '\\', '<', '>', '=', '\'', '_', '|'};
            String word=s, newWord="";
            boolean fitsWord = false;

            for (int i=0;i<word.length();i++){

                for (int j=0;j<digits.length;j++){
                    if (word.charAt(i) == digits[j]) {
                        fitsWord=false;
                        j=digits.length;
                    } else {
                        fitsWord=true;
                    }
                }

                if(fitsWord==true){
                    newWord+=word.charAt(i);
                } else {
                    newWord = newWord.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
                    if(newWord!=""){
                        wodrs.add(newWord);
                        newWord="";
                    }
                }

            }

        }
    }

    private void treatmentCount() throws Exception{
        for (String s:wodrs){
            boolean repeat=false;
            if(wordCounts!=null){
                for (WordCount w:wordCounts){
                    if (w.getWord().equals(s)){
                        repeat=true;
                        w.setCount(w.getCount()+1);
                    }
                }
            }
            if (repeat==false){
                wordCounts.add(new WordCount(s, 1));
            }
        }
    }




}
