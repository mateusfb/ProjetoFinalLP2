package br.imd.controller;

import br.imd.Detector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
    
    public void setMainApp(Detector main) {
    	this.main = main;
    }

    @FXML
    void apply(ActionEvent event) {
    	RadioButton distance = (RadioButton) distances.getSelectedToggle();
    	
    	main.getMSController().getDatasetOp().setDistance(distance.getText());
    }
}