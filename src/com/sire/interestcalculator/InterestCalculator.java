/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.interestcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author balza
 */
public class InterestCalculator extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("interestFXML.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(com.sire.interestcalculator.InterestCalculator.class.getResource("Style.css").toExternalForm());
        stage.setTitle("Kamatmágus");
        stage.getIcons().add(new Image("8tracks-icon.png"));
        stage.setScene(scene);
        stage.show(); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
