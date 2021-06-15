package sample;

import javafx.fxml.FXML;
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

public class Controller {

    @FXML TextField nameURL;
    @FXML TextArea theWords;

    // ----------------------------------------- инициализация ----------------------
    public void init(){
        nameURL.setText("https://www.simbirsoft.com/");
    }

    public void start(MouseEvent event) throws Exception { // добавление названий групп
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
            System.out.println(line);
        }
    }


}
