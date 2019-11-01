package br.imd;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import br.imd.controller.MainScreenController;
import br.imd.controller.SettingsScreenController;

public class Detector extends Application {

	private Stage initial;
	private Pane mainScreen;
	private MainScreenController mSController;
	private Pane settingScreen;
	private SettingsScreenController sSController;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		this.initial = primaryStage;
		initial.setTitle("Detector de Pessoas");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Detector.class.getResource("view/MainScreen.fxml"));
		mainScreen = loader.load();
			
		Scene scene = new Scene(mainScreen);
		initial.setScene(scene);
		initial.show();
		
		mSController = loader.getController();
		mSController.setMainApp(this);
	}

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		launch(args);
	}
	
	public MainScreenController getMSController() {
		return mSController;
	}
	
	public void settings() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Detector.class.getResource("view/SettingsScreen.fxml"));
		settingScreen = loader.load();
			
		Stage stage = new Stage();
		stage.setTitle("Preferências");
		Scene scene = new Scene(settingScreen);
		stage.setScene(scene);
		stage.show();
		
		sSController = loader.getController();
		sSController.setMainApp(this);
	}
}
