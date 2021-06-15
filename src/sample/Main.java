package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(); // создаем загрузку FXML
        loader.setLocation(getClass().getResource("design.fxml")); // добавляем нашу FXML

        Parent root = loader.load(); // создаем панель и загружаем наши данные

        Controller controller = loader.getController(); // создаем контроллер
        controller.init(); // запускаем в контроллере метод init

        primaryStage.setTitle("Статистика по количиству уникальных слов в тексте"); // заголовок формы
        primaryStage.setScene(new Scene(root)); //наша панель, в форму(окно)
        primaryStage.show(); // показать форму

    }


    public static void main(String[] args) {
        launch(args);
    }
}
