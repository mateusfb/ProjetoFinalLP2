package br.imd.controller;

import br.imd.model.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class MainScreenController {
	
	private DatasetOp datasetOp;
	private String imgPath;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button selectImage;

    @FXML
    private Button detect;

    @FXML
    private Circle result;

    @FXML
    private ImageView image;
    
    @FXML
    private Label labelSC;

    @FXML
    void detect(ActionEvent event) {
    	if(imgPath != null) {
    		HogExtractor hog = new HogExtractor(imgPath);
    		float[] imgAtributes = hog.extract();
    		
    		if(datasetOp.isPerson(imgAtributes)) {
    			result.setFill(Color.GREEN);
    		} else {
    			result.setFill(Color.RED);
    		}
    	}
    }

    @FXML
    void selectImage(ActionEvent event) {
    	FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
    	File selectedFile = fc.showOpenDialog(null);
    	
    	if(selectedFile != null) {
    		image.setImage(new Image(selectedFile.toURI().toString()));
    		imgPath = selectedFile.getAbsolutePath();
    		labelSC.setText(null);
    	} else {
    		
    	}
    }

    @FXML
    void initialize() {
        assert selectImage != null : "fx:id=\"selectImage\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";
        assert detect != null : "fx:id=\"detect\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";
        assert result != null : "fx:id=\"result\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";

        Dataset dataset = new Dataset(new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\dataset.csv");
        
        try {
			dataset.readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        datasetOp = new DatasetOp(dataset);
        imgPath = null;
        
    }
}
