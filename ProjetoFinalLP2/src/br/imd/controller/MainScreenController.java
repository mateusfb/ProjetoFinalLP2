package br.imd.controller;

import br.imd.Detector;
import br.imd.model.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

/**Classe de controle da tela principal*/
public class MainScreenController {
	
	private volatile boolean isCapturing; //flag da thread de captura por tempo
	private Dataset dataset; //Dataset com as instâncias de imagem
	private DatasetOp datasetOp; //Operador de dataset
	private String imgPath; //Caminho para a imagem a ser analisada
	private Detector main; //Referência para a aplicação principal
	
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
    
    @FXML
    private ComboBox<Integer> minuteCb;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;
    
    @FXML
    private Menu fileMenu;

    @FXML
    private Menu editMenu;

    @FXML
    private Menu settingsMenu;
    
    @FXML
    private Label onOffLabel;
    
    @FXML
    private Rectangle acPanel;
    
	/**
	 * @return DatasetOp - Operador do dataset
	 */
    public DatasetOp getDatasetOp() {
    	return datasetOp;
    }
    
	/**
	 * @param main Detector - Aplicação principal
	 * */
    public void setMainApp(Detector main) {
    	this.main = main;
    }

    @FXML
    void detect(ActionEvent event) {
    	if(imgPath != null) {
    		HogExtractor hog = new HogExtractor(imgPath); 
    		float[] imgAtributes = hog.extract(); //Extraindo e armazenando atributos da imagem
    		
    		//Checando se a imagem é de pessoa ou não e reproduzindo resultado na interface
    		if(datasetOp.isPerson(imgAtributes)) {
    			greenLight.setFill(Color.GREEN);
    			redLight.setFill(Color.BLACK);
    			onOffLabel.setText("LIGADO");
    			onOffLabel.setTextFill(Color.GREEN);
    			acPanel.setHeight(17);
    		} else {
    			redLight.setFill(Color.RED);
    			greenLight.setFill(Color.BLACK);
    			onOffLabel.setText("DESLIGADO");
    			onOffLabel.setTextFill(Color.RED);
    			acPanel.setHeight(48);
    		}
    	}
    }
    
    @FXML
    void capture(ActionEvent event) {
    	VideoCapture capture = new VideoCapture(0);
    	WritableImage writableImage = null;
    	
    	Mat matrix = new Mat(); //Matriz da imagem
    	
    	if(capture.isOpened()) { //Checando se a captura foi inicializada com sucesso
    		if(capture.read(matrix)) { //Capturando o próximo frame
    			//Criando uma BufferedImage a partir da matriz
    			BufferedImage bufferedImage = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
    			
    			WritableRaster raster = bufferedImage.getRaster();
    			DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
    	        byte[] data = dataBuffer.getData();
    	        matrix.get(0, 0, data);
    	        
    	        //Criando uma WritableImage
    	        writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
    	        
    	        //Salvando a imagem
    	        String saveLocation = new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\capture.png";
    	     	Imgcodecs.imwrite(saveLocation, matrix);
    	     	image.setImage(writableImage);
    	     	imgPath = saveLocation;

    	 		capture.release();
    	     }
    	} else {
    		Alert alert = new Alert(AlertType.ERROR, "Não há nenhuma câmera conectada!");
    		alert.show();
    	}
    }

    @FXML
    void selectImage(ActionEvent event) {
    	FileChooser fc = new FileChooser(); //Instanciando o FileChooser
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png")); //Adicionando o filtro para selecionar apenas imagens em PNG
    	File selectedFile = fc.showOpenDialog(null);
    	
    	if(selectedFile != null) { //Checando se alguma imagem foi selecionada
    		image.setImage(new Image(selectedFile.toURI().toString())); //Setando a imagem na interface
    		imgPath = selectedFile.getAbsolutePath(); //Setando o caminho da imagem
    		labelSC.setText(null);;
    	}
    }
    
    @FXML
    void openSettings(ActionEvent event) {
    	try {
			main.settings();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void start(ActionEvent event){
    	//Hanilitando o botão Parar e desabilitando os demais
    	stopButton.setDisable(false);
    	startButton.setDisable(true);
    	imgCapture.setDisable(true);
    	selectImage.setDisable(true);
    	detect.setDisable(true);
    	fileMenu.setDisable(true);
    	editMenu.setDisable(true);
    	settingsMenu.setDisable(true);
    	
    	//Iniciando uma thread para fazer a captura e detecção das imagens
    	Thread t = new Thread(new Runnable(){
    		@Override
    		public void run(){ 
    			isCapturing = true;
    			while(isCapturing){ //Loop funciona até que a flag isCapturing seja false
    				capture(event); //Capturando imagem
    				Platform.runLater(new Runnable() {
    				    @Override
    				    public void run() {
    				    	detect(event); //Detectando
    				    }
    				});
    				
    	        	  
    				try {
    					int i = 0;
    					while(i < minuteCb.getSelectionModel().getSelectedItem() * 60000 && isCapturing) { //Checa a cada 10 segundos se a thread ainda está viva
    						Thread.sleep(10000);
    						i += 10000;
    					}
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	});
    	t.setDaemon(true);
    	t.start();
    }

    @FXML
    void stop(ActionEvent event) {
    	isCapturing = false; //Setando a flag para false
    	
    	//Desabilitando o botão Parar e habilitando os demais
    	stopButton.setDisable(true);
    	startButton.setDisable(false);
    	imgCapture.setDisable(false);
    	selectImage.setDisable(false);
    	detect.setDisable(false);
    	fileMenu.setDisable(false);
    	editMenu.setDisable(false);
    	settingsMenu.setDisable(false);
    	
    }

    @FXML
    void initialize() {
        dataset = new Dataset(new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\dataset.csv"); //Instanciando o dataset
        
        try {
			dataset.readData(); //Lendo o dataset
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR, "Erro ao ler dataset!");
			alert.show();
			e.printStackTrace();
		}
        
        datasetOp = new DatasetOp(dataset); //Instanciando o operador do dataset
        imgPath = null;
        
        List<Integer> minutes = new ArrayList<Integer>();
        minutes.add(1);
        minutes.add(5);
        minutes.add(10);
        minutes.add(20);
        minutes.add(30);
        
        //Inicializando o Combo box
        minuteCb.setItems(FXCollections.observableArrayList(minutes));
        minuteCb.getSelectionModel().selectFirst();
    }
}
