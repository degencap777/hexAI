package application.view.mainmenu;

import application.Main;
import application.view.game.Game;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenu {
	private Stage primaryStage;
	private AnchorPane mainLayout;
	private Game game;
	
	public MainMenu(Game game) {
		this.game = game;
	}
	
	public void init(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/mainmenu/MainMenuView.fxml"));
			mainLayout = loader.load();

			MainMenuViewController mainMenuViewController = (MainMenuViewController) loader.getController();

			Scene scene = new Scene(mainLayout);

			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
			mainMenuViewController.getHexGridBackground().createHexGrid();
			
			mainMenuViewController.getButtonNewGame().setOnMouseClicked((new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					game.init(primaryStage);
					game.run();
				}
			}));
			
			mainMenuViewController.getButtonNewGame().setOnMouseEntered((new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					scene.setCursor(Cursor.HAND);
				}
			}));
			mainMenuViewController.getButtonNewGame().setOnMouseExited((new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					scene.setCursor(Cursor.DEFAULT);
				}
			}));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
