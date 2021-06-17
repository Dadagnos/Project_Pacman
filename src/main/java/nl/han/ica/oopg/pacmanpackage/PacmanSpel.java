package nl.han.ica.oopg.pacmanpackage;

import nl.han.ica.oopg.dashboard.Dashboard;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;

/**
 * @author David Bartenstein
 */

@SuppressWarnings("serial")
public class PacmanSpel extends nl.han.ica.oopg.engine.GameEngine {
	
	private State state = State.RUN;
	public Pacman pacman;
	private Monster monster1;
	private Monster monster2;
	private Monster monster3;
	private TextObject dashboardText;
	private int snoepjesScore;
	private int worldWidth;
    private int worldHeight;
    private int tileSize;
	public CrossingTileMap crossingTileMap;
	// Door deze regel kun je makkelijker naar de directory met afbeeldingen verwijzen.
	public static String MEDIA_URL = "src/main/java/pacmanpackage/media/";

	public static void main(String[] args) {
		PacmanSpel pg = new PacmanSpel();
		pg.runSketch();
	}
	
	@Override
	public void setupGame() {
		setWorldWidth(1020);
        setWorldHeight(780);
        createDashboard(getWorldWidth(), 70);
		initializeTileMap();
		createViewWithoutViewport(getWorldWidth(), getWorldHeight());
		drawGame();
	}
	
	private void createViewWithoutViewport(int worldWidth2, int worldHeight2) {
		View view = new View(getWorldWidth(), worldHeight);
		setView(view);
		size(getWorldWidth(), worldHeight);
		view.setBackground(loadImage(PacmanSpel.MEDIA_URL.concat("background_v2.jpg")));
	}

	private void initializeTileMap() {
	    // Load Sprites
	    Sprite floorSprite = new Sprite(PacmanSpel.MEDIA_URL.concat("tile1.png"));
	    Sprite snoepSprite = new Sprite(PacmanSpel.MEDIA_URL.concat("snoepDiamant.png"));
	    // Create tile types with the right Tile class and sprite
	    TileType<FloorTile> floorTileType = new TileType<>(FloorTile.class, floorSprite);
	    TileType<SnoepTile> snoepTileType = new TileType<>(SnoepTile.class, snoepSprite);

	    TileType[] tileTypes = {floorTileType, snoepTileType};
	    // de tilemap is 17 bij 13 //
	    int horiTiles = 17;
	    int vertTiles = 13;
	    
	    setTileSize((int) (getWorldWidth()/horiTiles));
	    int tilesMap[][] = {
	            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, },
	            {0, -1, -1,  1, -1, -2, -3,  1,  1,  1,  1, -2, -1,  1, -1, -1,  0, },
	            {0, -1,  0,  0,  0, -1,  0,  0,  0,  0,  0, -1,  0,  0,  0, -1,  0, },
	            {0, -2,  1, -2, -1, -2,  0,  1,  1,  1,  0, -2, -1, -2,  1, -2,  0, },
	            {0,  1,  0,  1,  0, -1,  0,  1,  0,  1,  0, -1,  0,  1,  0,  1,  0, },
	            {0,  1,  0,  1,  0, -2,  1,  1,  0,  1,  1, -2,  0,  1,  0,  1,  0, },
	            {0,  1,  0,  1,  0,  1,  0,  0,  0,  0,  0,  1,  0,  1,  0,  1,  0, },
	            {0,  1,  0,  1,  0, -2,  1,  1,  0,  1,  1, -2,  0,  1,  0,  1,  0, },
	            {0,  1,  0,  1,  0, -1,  0,  1,  0,  1,  0, -1,  0,  1,  0,  1,  0, },
	            {0, -2,  1, -2, -1, -2,  0,  1,  1,  1,  0, -2, -1, -2,  1, -2,  0, },
	            {0, -1,  0,  0,  0, -1,  0,  0,  0,  0,  0, -1,  0,  0,  0, -1,  0, },
	            {0, -1, -1,  1, -1, -2,  1,  1,  1,  1,  1, -2, -1,  1, -1, -1,  0, },
	            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, },
	    };
	    //this creates the x and y map for the center of each crossingTile: crossingsMap[0] --> y crossingsMap[1] --> x
	    crossingTileMap = new CrossingTileMap(this, tilesMap, horiTiles, vertTiles);
	    tileMap = new TileMap(getTileSize(), tileTypes, tilesMap);
	}
	
    /**
     * Maakt het dashboard aan
     *
     * @param dashboardWidth  Gewenste breedte van dashboard
     * @param dashboardHeight Gewenste hoogte van dashboard
     */
    private void createDashboard(int dashboardWidth, int dashboardHeight) {
        Dashboard dashboard = new Dashboard(0, 0, dashboardWidth, dashboardHeight);
        dashboardText = new TextObject("Score: " + snoepjesScore);
        dashboard.addGameObject(dashboardText);
        addDashboard(dashboard);
    }    
   
	public void drawGame() {
		switch(state)
	    {
	    case INTRO:
	        //start scherm eventueel
	        break;
	    case RUN:
	    	pacman = new Pacman(this, 5);
			monster1 = new Monster(this, 2);
			monster2 = new Monster(this, 2);
			monster3 = new Monster(this, 2);
	        addGameObject(pacman, getWorldWidth()/17, getWorldWidth()/17);
	        addGameObject(monster1, getWorldWidth() - 2 * (getWorldWidth()/17 - 2), getWorldWidth()/17 + 2);
	        addGameObject(monster2, getWorldWidth() - 2 * (getWorldWidth()/17), worldHeight - 2 * (getWorldWidth()/17));
	        addGameObject(monster3, getWorldWidth()/17, worldHeight - 2 * (getWorldWidth()/17));
	        break;
	    case PAUSE:
	        //pauze scherm eventueel
	        break;
	    case GAME_OVER:
	    	this.deleteAllGameOBjects();
//	    	dashboardText.setText("GAME OVER...DIT IS JE SCORE:" + snoepjesScore);
//	        TextObject gameOver = new TextObject("Game Over");
//	        TextObject eindScore = new TextObject("Dit is je score: " + snoepjesScore);
	        break;
	    default:
	        throw new RuntimeException("Unknown state: " + state);
	    }
	}
	
	@Override
	public void update() {
		dashboardText.setText("Score: " + snoepjesScore);
		System.out.println(pacman.getPacmanStatus());
		System.out.println(state);
		checkIfGameOver();
    }

	private void checkIfGameOver() {
		if (pacman!=null) {
			if (pacman.getPacmanStatus()==PacmanStatus.EATEN) {
				setState(State.GAME_OVER);
			}
		}
	}

	private void setState(State setState) {
		state = setState;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	
	public int getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

	public int getSnoepjesScore() {
		return snoepjesScore;
	}

	public void setSnoepjesScore(int snoepjesScore) {
		this.snoepjesScore = snoepjesScore;
	}

}
