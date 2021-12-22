package com.example.ap_project;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static Stage s;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setTitle("Snakes and ladders!");
        Image icon = new Image("LOGO.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        String css = this.getClass().getResource("styling.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.show();
        s=stage;

//        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("X   =  "+event.getSceneX());
//                System.out.println("Y   =  "+event.getSceneY());
//            }
//        });
    }

    public static void closeStage(){
        s.close();
    }

    public static void main(String[] args) {
        launch();
    }
}