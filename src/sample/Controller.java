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

    // коллекции данных
    ArrayList<String> text = new ArrayList<>();  // полученные и необработанные строчки
    ArrayList<String> wodrs = new ArrayList<>(); // разбитые на слова
    ArrayList<WordCount> wordCounts = new ArrayList<>(); // подсчитанные слова

    // внешние эл. в отображаемой часте
    @FXML TextField nameURL;  // ввод нашего URL
    @FXML TextArea phrases; // вывод наших значений
    @FXML Label status; // статус действий


    // ---------------------------------------------------------------------- инициализация ----------------------
    public void init(){
        nameURL.setText("https://www.simbirsoft.com/");
    } // ссылка по умолчанию

    public void start(MouseEvent event) throws Exception {

        zeroing(); // команда обнуления
        status.setText(" Статус: попытка соединения"); // статус

        if(connectURL()){ // соединение
            status.setText(" Статус: обработка текста");// статус
            wodrs = treatmentParse(text); // разбиение на слова (парсим текст)

            wordCounts = treatmentCount(wodrs); // подсчёт слов
            status.setText(" Статус: подсчёт слов, успех!");// статус

            for (WordCount w:wordCounts){ // TextArea, циклк для вывода наших значений
                //System.out.println(w.getWord()+"-"+w.getCount());
                phrases.setText(phrases.getText()+w.getWord()+"-"+w.getCount()+"\n");
            }
        }
    }

    // ---------------------------------------------------------------------- команда обнуления ----------------------
    private void zeroing() throws Exception{
        text = new ArrayList<>();
        wodrs = new ArrayList<>();
        wordCounts = new ArrayList<>();
        status.setText("");
        phrases.setText("");
    }

    // ---------------------------------------------------------------------- соединение с URL ----------------------
    private boolean connectURL() throws Exception{
        try {
            URL url = new URL(nameURL.getText()); // закидываем нашу ссылку
            URLConnection urlc = url.openConnection(); // открыть соединение
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) "); // дефолтные настройки браузера соединения

            InputStream inputFile = urlc.getInputStream(); // стрим данных приёма
            status.setText(" Статус: соединение, успех"); // статус

            readerURLtoText(inputFile); // считывание текста из URL
            status.setText(" Статус: считывание текста из URL, успех"); // статус
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage()); // ошибка (￣ ￣|||)
            status.setText(" Статус: Error"); // статус
            return false;
        }
    }

    // ---------------------------------------------------------------------- считывание текста из URL
    private void readerURLtoText(InputStream inputFile) throws Exception{
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile, "UTF-8")); //буфер чтения

            while (true) { // цикл на чтение данных из буфера, по строчке
                String line = reader.readLine();
                if (line == null) // если строка ничего не имеет
                    break; // закончить
                text.add(line); // запись полученой сроки в коллекцию строк
            }
        } catch (Exception e) {
        System.out.println(e.getMessage()); // ошибка (￣ ￣|||)
        status.setText(" Статус: Error"); // статус
        }
    }

    // ---------------------------------------------------------------------- разбиение на слова (парсим текст) ----------------------
    private ArrayList<String> treatmentParse(ArrayList<String> text) throws Exception{
        ArrayList<String> arrayWodrs = new ArrayList<>();
        try {
            for (String s:text){

                //внутренний массив с которым ми работаем
                char[] digits = new char[]{' ', ',', '.', '!', '?', '"',';',':','[',']','(',')','\n','\r','\t', '/', '\\', '<', '>', '=', '\'', '_', '|'};
                String word=s, newWord=""; // присваеваем
                boolean fitsWord = false;

                for (int i=0;i<word.length();i++){ // цикл по перебору символов в строке
                    for (int j=0;j<digits.length;j++){ // цикл по символам
                        if (word.charAt(i) == digits[j]) { // совпали? (￢_￢)
                            fitsWord=false; // тогда это не нужный сивол
                            j=digits.length; // заканчиваем костылём, цикл, потому что можем (」°ロ°)」, да, да я знаю спец команды, но так эффектние!
                        } else {
                            fitsWord=true; // инаце это новый символ
                        }
                    }

                    if(fitsWord==true){ // есть новый символ?
                        newWord+=word.charAt(i); // до? добавь его в будущее слово! (」°ロ°)」
                    } else {
                        newWord = newWord.replaceAll("[^A-Za-zА-Яа-я0-9]", ""); // очишаем слово от лишнего (＃￣ω￣)

                        if(newWord!=""){ // если слово есть, то ...
                            arrayWodrs.add(newWord); // закидываем полученное слово в коллекцию
                            newWord=""; // обнуляем новое слово
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // ошибка (￣ ￣|||)
            status.setText(" Статус: Error"); // статус

        }
        return arrayWodrs; // возвращаем коллекцию
    }

    // ---------------------------------------------------------------------- подсчёт слов ----------------------
    private ArrayList<WordCount> treatmentCount(ArrayList<String> wodrs) throws Exception{
        ArrayList<WordCount> wordCounts = new ArrayList<>();

        for (String s:wodrs){ // цикл по перебору символов в строке
            boolean repeat=false; // повторился

            if(wordCounts!=null){ // если он не пустой
                for (WordCount w:wordCounts){ // пройтись по новой коллекции
                    if (w.getWord().equals(s)){ // сравнивая слово с нашими
                        repeat=true; // нашли, повторение
                        w.setCount(w.getCount()+1); // увеличили число  (＃￣ω￣)
                    }
                }
            }
            if (repeat==false){ // не нашли? (￢_￢)
                wordCounts.add(new WordCount(s, 1)); // добавили слово  (＃￣ω￣)
            }
        }

        return wordCounts;// возвращаем коллекцию
    }




}
