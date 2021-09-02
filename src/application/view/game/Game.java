package application.view.game;

import java.util.ArrayList;
import java.util.List;
import application.Main;
import application.ai.Ai;
import application.ai.Ai.Node;
import application.view.game.HexGrid.Tile;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Game {
	private Stage primaryStage;
	private Scene scene;
	private AnchorPane mainLayout;
	private HexGrid hexGrid;
	private Text textCurrentPlayer;
	private boolean gameEnded;

	private ArrayList<ArrayList<Tile>> connectedPathsListWhite = new ArrayList<ArrayList<Tile>>();
	private ArrayList<ArrayList<Tile>> connectedPathsListBlack = new ArrayList<ArrayList<Tile>>();

	private String playerWhite;
	private String playerBlack;
	private Color playerWhiteColor = Color.LIGHTSTEELBLUE;
	private Color playerBlackColor = Color.BLACK.brighter();
	private int aiDepth;

	private int currentPlayer;

	public void init(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/game/GameView.fxml"));
			mainLayout = loader.load();

			GameViewController gameViewController = (GameViewController) loader.getController();

			this.scene = new Scene(mainLayout);

			this.primaryStage.setScene(scene);
			this.primaryStage.show();

			gameViewController.getSplitPane().lookupAll(".split-pane-divider").stream()
					.forEach(div -> div.setMouseTransparent(true));

			hexGrid = gameViewController.getHexGrid();
			hexGrid.createHexGrid();
			hexGrid.printBoardStatus();

			gameEnded = false;
			currentPlayer = 0;
			playerBlack = "Anonymous";
			playerWhite = "Anonymous";
			aiDepth = 5;

			textCurrentPlayer = gameViewController.getTextCurrentPlayer();
			textCurrentPlayer.setText("Black's turn" + " (" + playerBlack + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);

		for (int x = 0; x < hexGrid.getTilesPerRow(); x++) {
			for (int y = 0; y < hexGrid.getRowCount(); y++) {
				hexGrid.getTileCoordArray()[x][y].addEventFilter(MouseEvent.MOUSE_CLICKED, tileClickedEvent);
				hexGrid.getTileCoordArray()[x][y].setOnMouseEntered((new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						scene.setCursor(Cursor.HAND);
					}
				}));
				hexGrid.getTileCoordArray()[x][y].setOnMouseExited((new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						scene.setCursor(Cursor.DEFAULT);
					}
				}));
			}
		}
	}

	EventHandler<MouseEvent> tileClickedEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			if (!gameEnded) {
				Object target = e.getTarget();
				if (target instanceof Tile) {
					if (((Tile) target).getStonePlaced() == 0) {

						/**
						 * if (currentPlayer % 2 == 1) { hexGrid.drawStone((Tile) target,
						 * playerWhiteColor); ((Tile) target).setStonePlaced(currentPlayer % 2 + 1);
						 * checkAdjacentHexes((Tile) target); currentPlayer++;
						 * textCurrentPlayer.setText("Black's turn" + " (" + playerBlack + ")"); }
						 **/

						if (currentPlayer % 2 == 0) {
							hexGrid.drawStone((Tile) target, playerBlackColor);
							((Tile) target).setStonePlaced(currentPlayer % 2 + 1);
							checkAdjacentHexes((Tile) target);
							currentPlayer++;
							textCurrentPlayer.setText("White's turn" + " (" + playerWhite + ")");

							aiMove();
						}

					} else
						System.out.println("There already is a stone" + " (" + ((Tile) target).getStonePlaced() + ") "
								+ "on this tile");
				}
			}
		}
	};

	private void aiMove() {
		Ai ai = new Ai(hexGrid);

		System.out.println("Current advantage: ");
		System.out.println(
				ai.minimaxAb(hexGrid.intFromTile(), aiDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true));
		System.out.println("Amount of Alpha cut-offs: " + ai.getAlphaCutoffCounter());
		System.out.println("Amount of Beta cut-offs: " + ai.getBetaCutoffCounter());
		System.out.println("Current best AI move: ");
		System.out.println(ai.currentBestMove[0] + " " + ai.currentBestMove[1]);

		if (ai.currentBestMove[0] != -2 && ai.currentBestMove[1] != -2) {
			hexGrid.drawStone(hexGrid.getTile(ai.currentBestMove[0], ai.currentBestMove[1]), playerWhiteColor);
			hexGrid.getTile(ai.currentBestMove[0], ai.currentBestMove[1]).setStonePlaced(currentPlayer % 2 + 1);
		}

		List<Node> aiShortestPathFilteredList;
		List<Node> playerShortestPathFilteredList;

		aiShortestPathFilteredList = ai.getComputerShortestPath(hexGrid.intFromTile());
		playerShortestPathFilteredList = ai.getPlayerShortestPath(hexGrid.intFromTile());

		System.out.println("Shortest path for AI: " + aiShortestPathFilteredList.size());
		for (Node node : aiShortestPathFilteredList) {
			System.out.println(node.getXHexCoord() + " " + node.getYHexCoord());
		}

		System.out.println("Shortest path for player: " + playerShortestPathFilteredList.size());
		for (Node node : playerShortestPathFilteredList) {
			System.out.println(node.getXHexCoord() + " " + node.getYHexCoord());
		}

		currentPlayer++;
		textCurrentPlayer.setText("Black's turn" + " (" + playerBlack + ")");
	}

	private void closeWindowEvent(WindowEvent event) {
		System.out.println("Window close request ...");
	}

	private void checkAdjacentHexes(Tile tile) {
		ArrayList<Tile> adjacentTiles = tile.getAdjacentTilesOfSameColour(hexGrid.getTileCoordArray());
		ArrayList<Tile> tempPath = new ArrayList<Tile>();
		boolean preexistingPath = false;

		System.out.println("Adjacent tiles:");
		for (int i = 0; i < adjacentTiles.size(); i++)
			System.out.println(adjacentTiles.get(i).getXHexCoord() + " " + adjacentTiles.get(i).getYHexCoord());

		adjacentTiles.add(tile);

		if (tile.getStonePlaced() == 2) {
			for (int i = 0; i < connectedPathsListWhite.size(); i++) {
				for (int j = 0; j < adjacentTiles.size(); j++) {
					if (connectedPathsListWhite.get(i).contains(adjacentTiles.get(j))) {
						tempPath.addAll(connectedPathsListWhite.get(i));
						tempPath.addAll(adjacentTiles);
						connectedPathsListWhite.get(i).clear();
						preexistingPath = true;
						if (connectedPathsListWhite.size() <= i)
							break;
					}
				}
			}

			connectedPathsListWhite.removeIf(p -> p.isEmpty());

			if (preexistingPath == false) {
				connectedPathsListWhite.add(adjacentTiles);
			} else {
				ArrayList<Tile> tempPathDuplicatesRemoved = new ArrayList<Tile>();

				for (int i = 0; i < tempPath.size(); i++) {
					if (!tempPathDuplicatesRemoved.contains(tempPath.get(i)))
						tempPathDuplicatesRemoved.add(tempPath.get(i));
				}

				connectedPathsListWhite.add(tempPathDuplicatesRemoved);
			}

			System.out.println("All (White) connected paths:");
			for (int i = 0; i < connectedPathsListWhite.size(); i++) {
				for (int j = 0; j < connectedPathsListWhite.get(i).size(); j++) {
					System.out.println(connectedPathsListWhite.get(i).get(j).getXHexCoord() + " "
							+ connectedPathsListWhite.get(i).get(j).getYHexCoord());
				}
				System.out.println();
			}

			boolean containsLeft;
			boolean containsRight;
			for (int i = 0; i < connectedPathsListWhite.size(); i++) {
				containsLeft = containsRight = false;
				for (int j = 0; j < connectedPathsListWhite.get(i).size(); j++) {
					if (connectedPathsListWhite.get(i).get(j).getXHexCoord() == 0)
						containsLeft = true;
					if (connectedPathsListWhite.get(i).get(j).getXHexCoord() == hexGrid.getTilesPerRow() - 1)
						containsRight = true;
				}
				if (containsLeft && containsRight) {
					System.out.println("White has won");
					gameEnded = true;
				}
			}
		}

		if (tile.getStonePlaced() == 1) {
			for (int i = 0; i < connectedPathsListBlack.size(); i++) {
				for (int j = 0; j < adjacentTiles.size(); j++) {
					if (connectedPathsListBlack.get(i).contains(adjacentTiles.get(j))) {
						tempPath.addAll(connectedPathsListBlack.get(i));
						tempPath.addAll(adjacentTiles);
						connectedPathsListBlack.get(i).clear();
						preexistingPath = true;
						if (connectedPathsListBlack.size() <= i)
							break;
					}
				}
			}

			connectedPathsListBlack.removeIf(p -> p.isEmpty());

			if (preexistingPath == false) {
				connectedPathsListBlack.add(adjacentTiles);
			} else {
				ArrayList<Tile> tempPathDuplicatesRemoved = new ArrayList<Tile>();

				for (int i = 0; i < tempPath.size(); i++) {
					if (!tempPathDuplicatesRemoved.contains(tempPath.get(i)))
						tempPathDuplicatesRemoved.add(tempPath.get(i));
				}

				connectedPathsListBlack.add(tempPathDuplicatesRemoved);
			}

			System.out.println("All (Black) connected paths:");
			for (int i = 0; i < connectedPathsListBlack.size(); i++) {
				for (int j = 0; j < connectedPathsListBlack.get(i).size(); j++) {
					System.out.println(connectedPathsListBlack.get(i).get(j).getXHexCoord() + " "
							+ connectedPathsListBlack.get(i).get(j).getYHexCoord());
				}
				System.out.println();
			}

			boolean containsTop;
			boolean containsBottom;
			for (int i = 0; i < connectedPathsListBlack.size(); i++) {
				containsTop = containsBottom = false;
				for (int j = 0; j < connectedPathsListBlack.get(i).size(); j++) {
					if (connectedPathsListBlack.get(i).get(j).getYHexCoord() == 0)
						containsTop = true;
					if (connectedPathsListBlack.get(i).get(j).getYHexCoord() == hexGrid.getRowCount() - 1)
						containsBottom = true;
				}
				if (containsTop && containsBottom) {
					System.out.println("Black has won");
					gameEnded = true;
				}
			}
		}
	}
}
