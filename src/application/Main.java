package application;
	
import application.view.game.Game;
import application.view.mainmenu.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {		
			primaryStage.setTitle("HEX");
			
			Game game = new Game();
			MainMenu mainMenu = new MainMenu(game);
			mainMenu.init(primaryStage);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
