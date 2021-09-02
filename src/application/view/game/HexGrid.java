package application.view.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class HexGrid {

	private Group tileMap;
	private Lighting lighting;
	private DropShadow dropShadow;
	private Tile[][] tileCoordArray;

	private final static double r = 40; // outer circle radius of hexagon
	private final static double n = r * Math.sqrt(3) / 2; // inner circle radius of hexagon
	private final static double TILE_HEIGHT = 2 * r;
	private final static double TILE_WIDTH = 2 * n;
	private final static double BORDER_WIDTH = 10.0;
	private int rowCount;
	private int tilesPerRow;
	private int xStartOffset = 0;
	private int yStartOffset = 0;

	public HexGrid(Group tileMap, int tilesPerRow, int rowCount, int xStartOffset, int yStartOffset) {
		this.tileMap = tileMap;
		this.tileCoordArray = new Tile[tilesPerRow][rowCount];

		// Light.Distant light = new Light.Distant();
		// light.setAzimuth(-135.0);
		Light.Point light = new Light.Point();
		light.setX(5);
		light.setY(2);
		light.setZ(2);
		lighting = new Lighting();
		lighting.setLight(light);
		dropShadow = new DropShadow();

		dropShadow.setBlurType(BlurType.GAUSSIAN);
		dropShadow.setColor(Color.BLACK);
		dropShadow.setHeight(5);
		dropShadow.setWidth(5);
		dropShadow.setRadius(5);
		dropShadow.setOffsetX(3);
		dropShadow.setOffsetY(2);
		// dropShadow.setSpread(5);

		this.rowCount = rowCount;
		this.tilesPerRow = tilesPerRow;
		this.xStartOffset = xStartOffset;
		this.yStartOffset = yStartOffset;
	}

	public Group createHexGrid() {
		for (int x = 0; x < tilesPerRow; x++) {
			for (int y = 0; y < rowCount; y++) {
				double xCoord = x * TILE_WIDTH + y * n + xStartOffset;
				double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

				if (x == 0 && y == 0) {
					Tile tile = new Tile(xCoord, yCoord, 5, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (x == 0 && y == rowCount - 1) {
					Tile tile = new Tile(xCoord, yCoord, 6, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (x == tilesPerRow - 1 && y == 0) {
					Tile tile = new Tile(xCoord, yCoord, 7, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (x == tilesPerRow - 1 && y == rowCount - 1) {
					Tile tile = new Tile(xCoord, yCoord, 8, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (y == 0) {
					Tile tile = new Tile(xCoord, yCoord, 1, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (y == rowCount - 1) {
					Tile tile = new Tile(xCoord, yCoord, 2, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (x == 0) {
					Tile tile = new Tile(xCoord, yCoord, 3, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else if (x == tilesPerRow - 1) {
					Tile tile = new Tile(xCoord, yCoord, 4, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				} else {
					Tile tile = new Tile(xCoord, yCoord, 0, x, y);
					tileMap.getChildren().add(tile);
					tileCoordArray[x][y] = tile;
				}
			}
		}

		return tileMap;
	}

	public void createBorder(double x, double y, int borderType, int xHexCoord, int yHexCoord) {
		Polygon borderLeft = new Polygon();
		Polygon borderRight = new Polygon();
		Polygon borderTop = new Polygon();
		Polygon borderBottom = new Polygon();
		Text borderNumber = new Text();
		Text borderLetter = new Text();

		switch (borderType) {
		case 0:
			break;
		case 1:
			borderTop.getPoints().addAll(x, y, x + n, y - r * 0.5, x + TILE_WIDTH, y,

					x + TILE_WIDTH, y - BORDER_WIDTH, x + n, y - r * 0.5 - BORDER_WIDTH, x, y - BORDER_WIDTH);
			borderTop.setStrokeWidth(1);
			borderTop.setStroke(Color.BLACK);
			tileMap.getChildren().add(borderTop);
			break;
		case 2:
			borderBottom.getPoints().addAll(x, y + r, x + n, y + r * 1.5, x + TILE_WIDTH, y + r,

					x + TILE_WIDTH, y + r + BORDER_WIDTH, x + n, y + r * 1.5 + BORDER_WIDTH, x, y + r + BORDER_WIDTH);
			borderBottom.setStrokeWidth(1);
			borderBottom.setStroke(Color.BLACK);
			tileMap.getChildren().add(borderBottom);
			borderLetter.setText(determineBorderLetter(xHexCoord));
			borderLetter.setX(x + TILE_WIDTH - 4);
			borderLetter.setY(y + r * 1.5 + 20);
			tileMap.getChildren().add(borderLetter);
			break;
		case 3:
			borderLeft.getPoints().addAll(x, y, x, y + r, x + n, y + r * 1.5,

					x + n - BORDER_WIDTH, y + r * 1.5 + 5, x - BORDER_WIDTH, y + r + 5, x - BORDER_WIDTH, y + 5);
			borderLeft.setFill(Color.LIGHTSTEELBLUE);
			borderLeft.setStrokeWidth(1);
			borderLeft.setStroke(Color.LIGHTSTEELBLUE);
			tileMap.getChildren().add(borderLeft);
			borderNumber.setText(determineBorderNumber(yHexCoord));
			borderNumber.setX(x - 40);
			borderNumber.setY(y + 0.5 * r + 5);
			tileMap.getChildren().add(borderNumber);
			break;
		case 4:
			borderRight.getPoints().addAll(x + n, y - r * 0.5, x + TILE_WIDTH, y, x + TILE_WIDTH, y + r,

					x + TILE_WIDTH + BORDER_WIDTH, y + r - 5, x + TILE_WIDTH + BORDER_WIDTH, y - 5,
					x + n + BORDER_WIDTH, y - r * 0.5 - 5);
			borderRight.setFill(Color.LIGHTSTEELBLUE);
			borderRight.setStrokeWidth(1);
			borderRight.setStroke(Color.LIGHTSTEELBLUE);
			tileMap.getChildren().add(borderRight);
			break;
		case 5:
			borderTop.getPoints().addAll(x, y, x + n, y - r * 0.5, x + TILE_WIDTH, y,

					x + TILE_WIDTH, y - BORDER_WIDTH, x + n, y - r * 0.5 - BORDER_WIDTH, x, y - BORDER_WIDTH);
			borderTop.setStrokeWidth(1);
			borderTop.setStroke(Color.BLACK);
			tileMap.getChildren().add(borderTop);

			borderLeft.getPoints().addAll(x, y - BORDER_WIDTH, x, y + r, x + n, y + r * 1.5,

					x + n - BORDER_WIDTH, y + r * 1.5 + 5, x - BORDER_WIDTH, y + r + 5, x - BORDER_WIDTH, y - 5);
			borderLeft.setFill(Color.LIGHTSTEELBLUE);
			borderLeft.setStrokeWidth(1);
			borderLeft.setStroke(Color.LIGHTSTEELBLUE);
			tileMap.getChildren().add(borderLeft);
			borderNumber.setText(determineBorderNumber(yHexCoord));
			borderNumber.setX(x - 40);
			borderNumber.setY(y + 0.5 * r + 5);
			tileMap.getChildren().add(borderNumber);
			break;
		case 6:
			borderBottom.getPoints().addAll(x, y + r, x + n, y + r * 1.5, x + TILE_WIDTH, y + r,

					x + TILE_WIDTH, y + r + BORDER_WIDTH, x + n, y + r * 1.5 + BORDER_WIDTH, x, y + r + BORDER_WIDTH);
			borderBottom.setStrokeWidth(1);
			borderBottom.setStroke(Color.BLACK);
			tileMap.getChildren().add(borderBottom);
			borderLetter.setText(determineBorderLetter(xHexCoord));
			borderLetter.setX(x + TILE_WIDTH - 4);
			borderLetter.setY(y + r * 1.5 + 20);
			tileMap.getChildren().add(borderLetter);

			borderLeft.getPoints().addAll(x, y, x, y + r + BORDER_WIDTH,

					x - BORDER_WIDTH, y + r + 5, x - BORDER_WIDTH, y + 5);
			borderLeft.setFill(Color.LIGHTSTEELBLUE);
			borderLeft.setStrokeWidth(1);
			borderLeft.setStroke(Color.LIGHTSTEELBLUE);
			tileMap.getChildren().add(borderLeft);
			borderNumber.setText(determineBorderNumber(yHexCoord));
			borderNumber.setX(x - 40);
			borderNumber.setY(y + 0.5 * r + 5);
			tileMap.getChildren().add(borderNumber);
			break;
		case 7:
			borderTop.getPoints().addAll(x, y, x + n, y - r * 0.5, x + TILE_WIDTH, y,

					x + TILE_WIDTH, y - BORDER_WIDTH, x + n, y - r * 0.5 - BORDER_WIDTH, x, y - BORDER_WIDTH);
			borderTop.setStrokeWidth(1);
			borderTop.setStroke(Color.BLACK);
			tileMap.getChildren().add(borderTop);

			borderRight.getPoints().addAll(x + TILE_WIDTH, y - BORDER_WIDTH, x + TILE_WIDTH, y + r,

					x + TILE_WIDTH + BORDER_WIDTH, y + r - 5, x + TILE_WIDTH + BORDER_WIDTH, y - 5);
			borderRight.setFill(Color.LIGHTSTEELBLUE);
			borderRight.setStrokeWidth(1);
			borderRight.setStroke(Color.LIGHTSTEELBLUE);
			tileMap.getChildren().add(borderRight);
			break;
		case 8:
			borderBottom.getPoints().addAll(x, y + r, x + n, y + r * 1.5, x + TILE_WIDTH, y + r,

					x + TILE_WIDTH, y + r + BORDER_WIDTH, x + n, y + r * 1.5 + BORDER_WIDTH, x, y + r + BORDER_WIDTH);
			borderBottom.setStrokeWidth(1);
			borderBottom.setStroke(Color.BLACK);
			tileMap.getChildren().add(borderBottom);
			borderLetter.setText(determineBorderLetter(xHexCoord));
			borderLetter.setX(x + TILE_WIDTH - 4);
			borderLetter.setY(y + r * 1.5 + 20);
			tileMap.getChildren().add(borderLetter);

			borderRight.getPoints().addAll(x + n, y - r * 0.5, x + TILE_WIDTH, y, x + TILE_WIDTH, y + r + BORDER_WIDTH,

					x + TILE_WIDTH + BORDER_WIDTH, y + r + 5, x + TILE_WIDTH + BORDER_WIDTH, y - 5,
					x + n + BORDER_WIDTH, y - r * 0.5 - 5);
			borderRight.setFill(Color.LIGHTSTEELBLUE);
			borderRight.setStrokeWidth(1);
			borderRight.setStroke(Color.LIGHTSTEELBLUE);
			tileMap.getChildren().add(borderRight);
			break;
		}
	}

	private String determineBorderNumber(int yHexCoord) {
		return Integer.toString(yHexCoord + 1);
	}

	private String determineBorderLetter(int xHexCoord) {
		String letter = "";

		switch (xHexCoord) {
		case 0:
			letter = "A";
			break;
		case 1:
			letter = "B";
			break;
		case 2:
			letter = "C";
			break;
		case 3:
			letter = "D";
			break;
		case 4:
			letter = "E";
			break;
		case 5:
			letter = "F";
			break;
		case 6:
			letter = "G";
			break;
		case 7:
			letter = "H";
			break;
		case 8:
			letter = "I";
			break;
		case 9:
			letter = "J";
			break;
		case 10:
			letter = "K";
			break;
		}

		return letter;
	}

	public void printBoardStatus() {
		for (int x = 0; x < tilesPerRow; x++) {
			for (int y = 0; y < rowCount; y++) {
				System.out.println(determineBorderLetter(this.tileCoordArray[x][y].getXHexCoord()) + " "
						+ determineBorderNumber(this.tileCoordArray[x][y].getYHexCoord()) + " "
						+ this.tileCoordArray[x][y].getStonePlaced());
			}
		}
	}

	public Tile getTile(int xCoord, int yCoord) {
		return tileCoordArray[xCoord][yCoord];
	}

	public List<int[]> getPossibleMoves(int[][] tileCoordArray, int myColor) {

		List<int[]> possibleMoves = new LinkedList<>();

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < tilesPerRow; j++) {
				if (tileCoordArray[j][i] == 0) {

					int[] newMove = new int[2];
					newMove[0] = j;
					newMove[1] = i;
					
					if (hasMoveAdjacentTile(tileCoordArray, myColor, newMove)) possibleMoves.add(0, newMove);
					else possibleMoves.add(newMove);
				}
			}
		}

		return possibleMoves;
	}
	
	public boolean hasMoveAdjacentTile(int[][] tileCoordArray, int myColor, int[] move) {

		if ((move[0] + 1 < tilesPerRow && move[1] - 1 >= 0) && (tileCoordArray[move[0] + 1][move[1] - 1] == myColor))
			return true;
		else if ((move[0] + 1 < tilesPerRow) && (tileCoordArray[move[0] + 1][move[1]] == myColor))

			return true;
		else if ((move[1] + 1 < rowCount) && (tileCoordArray[move[0]][move[1] + 1] == myColor))

			return true;
		else if ((move[0] - 1 >= 0 && move[1] + 1 < rowCount) && (tileCoordArray[move[0] - 1][move[1] + 1] == myColor))

			return true;
		else if ((move[0] - 1 >= 0) && (tileCoordArray[move[0] - 1][move[1]] == myColor))

			return true;
		else if ((move[1] - 1 >= 0) && (tileCoordArray[move[0]][move[1] - 1] == myColor))
			return true;
		else
			return false;
	}

	public void drawStone(Tile tile, Color color) {

		tileMap.getChildren().add(new Stone(tile).drawStone(color));
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getTilesPerRow() {
		return tilesPerRow;
	}

	public Tile[][] getTileCoordArray() {
		return tileCoordArray;
	}

	public int[][] intFromTile() {
		int[][] intTileCoordArray = new int[getTilesPerRow()][getRowCount()];
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getTilesPerRow(); j++) {
				intTileCoordArray[j][i] = this.tileCoordArray[j][i].getStonePlaced();
			}
		}

		return intTileCoordArray;
	}

	public class Tile extends Polygon {
		private int stonePlaced;
		private int xHexCoord;
		private int yHexCoord;
		private double centerX;
		private double centerY;

		Tile(double x, double y, int borderType, int xHexCoord, int yHexCoord) {
			getPoints().addAll(x, y, x, y + r, x + n, y + r * 1.5,

					x + TILE_WIDTH, y + r, x + TILE_WIDTH, y, x + n, y - r * 0.5);

			this.centerX = x + n;
			this.centerY = y + r * 0.5;

			setFill(Color.ANTIQUEWHITE);
			setStrokeWidth(1);
			setStroke(Color.BLACK);
			this.stonePlaced = 0;
			this.xHexCoord = xHexCoord;
			this.yHexCoord = yHexCoord;

			createBorder(x, y, borderType, xHexCoord, yHexCoord);
		}

		public ArrayList<Tile> getAdjacentTilesOfSameColour(Tile[][] tileCoordArray) {
			ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();

			if (this.xHexCoord + 1 < tilesPerRow && this.yHexCoord - 1 >= 0) {
				if (tileCoordArray[this.xHexCoord + 1][this.yHexCoord - 1].getStonePlaced() == this.getStonePlaced())
					adjacentTiles.add(tileCoordArray[this.xHexCoord + 1][this.yHexCoord - 1]);
			}
			if (this.xHexCoord + 1 < tilesPerRow) {
				if (tileCoordArray[this.xHexCoord + 1][this.yHexCoord].getStonePlaced() == this.getStonePlaced())
					adjacentTiles.add(tileCoordArray[this.xHexCoord + 1][this.yHexCoord]);
			}
			if (this.yHexCoord + 1 < rowCount) {
				if (tileCoordArray[this.xHexCoord][this.yHexCoord + 1].getStonePlaced() == this.getStonePlaced())
					adjacentTiles.add(tileCoordArray[this.xHexCoord][this.yHexCoord + 1]);
			}
			if (this.xHexCoord - 1 >= 0 && this.yHexCoord + 1 < rowCount) {
				if (tileCoordArray[this.xHexCoord - 1][this.yHexCoord + 1].getStonePlaced() == this.getStonePlaced())
					adjacentTiles.add(tileCoordArray[this.xHexCoord - 1][this.yHexCoord + 1]);
			}
			if (this.xHexCoord - 1 >= 0) {
				if (tileCoordArray[this.xHexCoord - 1][this.yHexCoord].getStonePlaced() == this.getStonePlaced())
					adjacentTiles.add(tileCoordArray[this.xHexCoord - 1][this.yHexCoord]);
			}
			if (this.yHexCoord - 1 >= 0) {
				if (tileCoordArray[this.xHexCoord][this.yHexCoord - 1].getStonePlaced() == this.getStonePlaced())
					adjacentTiles.add(tileCoordArray[this.xHexCoord][this.yHexCoord - 1]);
			}

			return adjacentTiles;
		}

		public int getXHexCoord() {
			return this.xHexCoord;
		}

		public int getYHexCoord() {
			return this.yHexCoord;
		}

		public int getStonePlaced() {
			return this.stonePlaced;
		}

		public double getCenterX() {
			return this.centerX;
		}

		public double getCenterY() {
			return this.centerY;
		}

		public void setStonePlaced(int stonePlaced) {
			this.stonePlaced = stonePlaced;
		}

		public int getDistance() {
			if (this.getStonePlaced() == 0)
				return 1;
			else
				return 0;
		}
	}

	public class Stone extends Circle {
		double centerX;
		double centerY;
		int radius = 22;

		Stone(Tile tile) {
			this.centerX = tile.getCenterX();
			this.centerY = tile.getCenterY();
		}

		public Circle drawStone(Color color) {
			Circle stone = new Circle(centerX, centerY, radius);
			stone.setFill(color);
			stone.setEffect(lighting);
			stone.setEffect(dropShadow);

			return stone;
		}
	}
}
