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

public class SettingsScreenController {

	private Detector main;
	
    @FXML
    private RadioButton euclidianCb;

    @FXML
    private RadioButton manhattanCb;

    @FXML
    private RadioButton chebyshevCb;

    @FXML
    private Button apply;
    
    @FXML
    private ToggleGroup distances;
    
    @FXML
    private TextField kField;
    
    public void setMainApp(Detector main) {
    	this.main = main;
    }

    @FXML
    void apply(ActionEvent event) {
    	RadioButton distance = (RadioButton) distances.getSelectedToggle();
    	int newK = 0;
    	
    	try {
    		newK = Integer.parseInt(kField.getText());
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR, "Valor de K inválido!");
    		alert.show();
    	}
    	
    	if(distance != null) {
    		main.getMSController().getDatasetOp().setDistance(distance.getText());
    	}
    	
    	if(newK > 0 || newK < 100) {
    		main.getMSController().getDatasetOp().setK(newK);
    	}
    }
}