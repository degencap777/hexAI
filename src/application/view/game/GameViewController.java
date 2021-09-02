package application.view.game;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameViewController implements Initializable {
	@FXML
	private Group tileMap;

	@FXML
	private StackPane gridContainer;
	
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private Text textCurrentPlayer;
	
	public HexGrid hexGrid;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		hexGrid = new HexGrid(tileMap, 5, 5, 0, 0);
	}
	
	public HexGrid getHexGrid() {
		return hexGrid;
	}
	public Text getTextCurrentPlayer() {
		return textCurrentPlayer;
	}
	
	public SplitPane getSplitPane() {
		return splitPane;
	}
}
