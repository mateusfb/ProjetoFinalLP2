package br.imd.controller;

import br.imd.Detector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SettingsScreenController {

	private Detector main; //Referência à aplicação principal
	
    @FXML
    private RadioButton euclidianCb;

    @FXML
    private ToggleGroup distances;

    @FXML
    private RadioButton manhattanCb;

    @FXML
    private RadioButton chebyshevCb;

    @FXML
    private Button applyButton;
    
    @FXML
    private Button applyCloseButton;

    @FXML
    private TextField kField;
    
	/**
	 * @param main Detector - Aplicação principal
	 * */
    public void setMainApp(Detector main) {
    	this.main = main;
    }

    @FXML
    void apply(ActionEvent event) {
    	RadioButton distance = (RadioButton) distances.getSelectedToggle();
    	int newK = 0;
    	
    	if(!(kField.getText().equals(""))) { //Checando se algo foi escrito no TextField
    		try {
    			newK = Integer.parseInt(kField.getText()); //Tenta converter o texto do TextField para int
    		}catch(NumberFormatException e) {
    			Alert alert = new Alert(AlertType.ERROR, "Valor de K inválido!");
    			alert.show();
    			e.printStackTrace();
    		}
    	}

    	
    	if(distance != null) {
    		main.getMSController().getDatasetOp().setDistance(distance.getText()); //Seta a distância
    	}
    	
    	if(newK > 0 && newK < 100) {
    		main.getMSController().getDatasetOp().setK(newK); //Seta o novo valor de k
    	}
    }
    
    @FXML
    void applyClose(ActionEvent event) {
    	this.apply(event); //Aplica as mudanças
    	
    	Stage stage = (Stage) applyCloseButton.getScene().getWindow();
    	stage.close(); //Fecha a janela
    }
}