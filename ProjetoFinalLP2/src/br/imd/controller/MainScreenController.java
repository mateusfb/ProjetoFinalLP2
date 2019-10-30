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
    private Circle greenLight;

    @FXML
    private Circle redLight;
    
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
    			greenLight.setFill(Color.GREEN);
    			redLight.setFill(Color.GRAY);
    		} else {
    			redLight.setFill(Color.RED);
    			greenLight.setFill(Color.GRAY);
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
        Dataset dataset = new Dataset(new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\dataset.csv");
        
        try {
			dataset.readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        datasetOp = new DatasetOp(dataset);
        imgPath = null;
        
        redLight.setFill(Color.GRAY);
        greenLight.setFill(Color.GRAY);
        
    }
}
