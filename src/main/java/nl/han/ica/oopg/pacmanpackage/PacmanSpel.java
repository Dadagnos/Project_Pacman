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
	
	/**
	 * Lokale var: slaat de staat van het programma op
	 */
	private State state = State.RUN;
	public Pacman pacman;
	private Monster monster1;
	private Monster monster2;
	private Monster monster3;
	private TextObject dashboardText;
	/**
	 * slaat de score op
	 */
	private int snoepjesScore;
	private int worldWidth;
    private int worldHeight;
    private int tileSize;
	/**
	 * slaat een array op van alle kruispunten in de tilemap
	 */
	public CrossingTileMap crossingTileMap;
	private int countSnoep;
	/**
	 * Shortcut om makkelijker naar de directory met afbeeldingen te kunnen verwijzen.
	 */
	public static String MEDIA_URL = "src/main/java/pacmanpackage/media/";

	/**
	 * @param args
	 * constructor voor het spel
	 */
	public static void main(String[] args) {
		PacmanSpel pg = new PacmanSpel();
		pg.runSketch();
	}
	
	/**
	 *set alles klaar om het spel te kunnen draaien
	 */
	@Override
	public void setupGame() {
		setWorldWidth(1020);
        setWorldHeight(780);
        createDashboard(getWorldWidth(), 70);
		initializeTileMap();
		createViewWithoutViewport(getWorldWidth(), getWorldHeight());
		drawGame(state);
	}
	
	/**
	 * @param worldWidth2
	 * @param worldHeight2
	 * maakt een view zonder viewport
	 */
	private void createViewWithoutViewport(int worldWidth2, int worldHeight2) {
		View view = new View(getWorldWidth(), worldHeight);
		setView(view);
		size(getWorldWidth(), worldHeight);
		view.setBackground(loadImage(PacmanSpel.MEDIA_URL.concat("background_v2.jpg")));
	}

	/**
	 * maakt een tileMap aan en initialiseert ook de CrossingTileMap() methode waarmee de kruisingen in kaart gebracht worden
	 * en opgeslagen in de crossingTileMap
	 */
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
	    setCountSnoep(countDiamonds());
	}
	
	/**
     * initialiseert het totale aantal diamantSnoepjes
     *
     */
    private int countDiamonds() {
    	int number = 0;
    	for (int[] n : this.getTileMap().getTileMap()) {
    		for (int m : n) {
    			if (m==1) {
    				number++;
    			}
    		}
    	}
		return number;
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
   
	/**
	 * tekent alle objecten op het speelscherm
	 */
	public void drawGame(State gameState) {
	    	pacman = new Pacman(this, 5);
			monster1 = new Monster(this, 2);
			monster2 = new Monster(this, 2);
			monster3 = new Monster(this, 2);
	        addGameObject(pacman, getWorldWidth()/17, getWorldWidth()/17);
	        addGameObject(monster1, getWorldWidth() - 2 * (getWorldWidth()/17 - 2), getWorldWidth()/17 + 2);
	        addGameObject(monster2, getWorldWidth() - 2 * (getWorldWidth()/17), worldHeight - 2 * (getWorldWidth()/17));
	        addGameObject(monster3, getWorldWidth()/17, worldHeight - 2 * (getWorldWidth()/17));
	}
	
	/**
	 *voert de functies uit die continu geupdate moeten worden tijdens het draaien:
	 *- houdt score bij
	 *- controleert of het afgelopen is
	 *en bevat een switch/case wat aan de hand van de "state" reageert door het eindscherm te tonen.
	 */
	@Override
	public void update() {
		dashboardText.setText("Score: " + snoepjesScore);
		checkIfGameOver();
		switch (state) {
		case RUN:
		break;
		case GAME_OVER:
			this.deleteGameObject(pacman);
			dashboardText.setText("GAME_OVER! Score:" + snoepjesScore);
		break;
		case WON:
			this.deleteGameObject(monster1);
			this.deleteGameObject(monster2);
			this.deleteGameObject(monster3);
			dashboardText.setText("GEWONNEN! Dit is je score:" + snoepjesScore);
		break;
		default:
	        throw new RuntimeException("Unknown state: " + state);
	    }
		System.out.println(state);
		System.out.println(getCountSnoep());
    }

	/**
	 * checkt of het spel afgelopen is en verandert de "state"
	 */
	private void checkIfGameOver() {
		if (pacman!=null) {
			if (pacman.getPacmanStatus()==PacmanStatus.EATEN) {
				this.setState(State.GAME_OVER);
			}
			if (countSnoep==0) {
				this.setState(State.WON);
			}
		}
	}

	/**
	 * @param setState
	 */
	private void setState(State setState) {
		state = setState;
	}

	/**
	 * @return tileSize
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * @param tileSize
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	/**
	 * @return worldWidth
	 */
	public int getWorldWidth() {
		return worldWidth;
	}

	/**
	 * @param worldWidth
	 */
	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}
	
	/**
	 * @return worldHeight
	 */
	public int getWorldHeight() {
		return worldHeight;
	}

	/**
	 * @param worldHeight
	 */
	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

	/**
	 * @return snoepjesScore
	 */
	public int getSnoepjesScore() {
		return snoepjesScore;
	}

	/**
	 * @param snoepjesScore
	 */
	public void setSnoepjesScore(int snoepjesScore) {
		this.snoepjesScore = snoepjesScore;
	}

	/**
	 * @return countSnoep
	 */
	public int getCountSnoep() {
		return countSnoep;
	}

	/**
	 * @param countSnoep
	 */
	public void setCountSnoep(int countSnoep) {
		this.countSnoep = countSnoep;
	}

}
