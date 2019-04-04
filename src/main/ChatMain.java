package main;

import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//MAIN PAGE
public class ChatMain extends Application {
    public static final Client client = new Client();

    public static Stage primaryStage = null;

    public static void main(String[] args) {
        new Thread(ChatMain.client).start();

        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        ChatMain.primaryStage = primaryStage;
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));

            primaryStage.setTitle("Say Hi!");
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (Exception e){
            System.out.println("something missing");
        }

    }
}
