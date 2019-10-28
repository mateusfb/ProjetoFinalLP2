package br.imd;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Detector extends Application {

	private Stage initial;
	private Pane mainScreen;
	
	@Override
	public void start(Stage primaryStage) {
		this.initial = primaryStage;
		initial.setTitle("Detector de Pessoas");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Detector.class.getResource("view/MainScreen.fxml"));
			mainScreen = (Pane) loader.load();
			
			Scene scene = new Scene(mainScreen);
			initial.setScene(scene);
			initial.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}
}
