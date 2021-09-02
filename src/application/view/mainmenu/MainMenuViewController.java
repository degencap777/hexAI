package application.view.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;

import application.view.game.HexGrid;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class MainMenuViewController implements Initializable {
	@FXML
	private Button buttonNewGame;
	
	@FXML
	private Group hexBackground;
	
	private HexGrid hexGridBackground;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		hexGridBackground = new HexGrid(hexBackground, 50, 30, -600, 0);
	}
	
	public HexGrid getHexGridBackground() {
		return hexGridBackground;
	}
	
	public Button getButtonNewGame() {
		return buttonNewGame;
	}
}
