package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;

public class Main extends Application {

    public static String dbpath;
    @Override
    public void start(Stage primaryStage) throws Exception{
        dbpath = getClass().getResource("results").getPath();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle( "Simply snake");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getIcons().add(new Image( getClass().getResourceAsStream("logo.png") ));
        primaryStage.setResizable(false);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
