package application.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;

public class MainViewController implements Initializable {
	@FXML
	private Group tileMap;

	@FXML
	private StackPane gridContainer;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		HexGrid hexGrid = new HexGrid(tileMap);
		hexGrid.createHexGrid();
		hexGrid.printBoardStatus();
	}
}
