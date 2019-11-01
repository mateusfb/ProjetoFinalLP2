package br.imd.controller;

import br.imd.Detector;
import br.imd.model.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

public class MainScreenController {
	
	private Dataset dataset;
	private DatasetOp datasetOp;
	private String imgPath;
	private Detector main;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button imgCapture;
    
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
    
    public DatasetOp getDatasetOp() {
    	return datasetOp;
    }
    
    public void setMainApp(Detector main) {
    	this.main = main;
    }

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
    void capture(ActionEvent event) {
    	VideoCapture capture = new VideoCapture(0);
    	WritableImage writableImage = null;
    	
    	Mat matrix = new Mat();
    	capture.read(matrix);
    	
    	if(capture.isOpened()) {
    		if(capture.read(matrix)) {
    			BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
    			
    			 WritableRaster raster = image.getRaster();
    	         DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
    	         byte[] data = dataBuffer.getData();
    	         matrix.get(0, 0, data);
    	         
    	         writableImage = SwingFXUtils.toFXImage(image, null);
    	     }
    	}
    	
    	String saveLocation = new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\capture.png";
    	Imgcodecs.imwrite(saveLocation, matrix);
    	image.setImage(writableImage);
    	imgPath = saveLocation;	
		labelSC.setText(null);
		
		capture.release();
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
    void openSettings(ActionEvent event) {
    	try {
			main.settings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        dataset = new Dataset(new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\dataset.csv");
        
        try {
			dataset.readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        datasetOp = new DatasetOp(dataset);
        imgPath = null;
        
    }
}
