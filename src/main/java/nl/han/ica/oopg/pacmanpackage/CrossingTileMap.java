package nl.han.ica.oopg.pacmanpackage;

import java.util.ArrayList;

public class CrossingTileMap {
	/**
	 * 
	 */
	private PacmanSpel game;
	/**
	 * 
	 */
	private static double[][] crossingsMap;
	/**
	 * 
	 */
	private int[][] tilesmap;
	/**
	 * 
	 */
	private int horiTiles;
	/**
	 * 
	 */
	private int vertTiles;
	/**
	 * 
	 */
	private int numberOfCrossings;
	
	/**
	 * @param game
	 * @param tilesMap
	 * @param horiTiles
	 * @param vertTiles
	 */
	public CrossingTileMap(PacmanSpel game, int[][] tilesMap, int horiTiles, int vertTiles) {
		this.game = game;
		this.tilesmap = tilesMap;
		this.horiTiles = horiTiles;
		this.vertTiles = vertTiles;
		numberOfCrossings = getNumberOfCrossings(tilesMap);
		createCrossingTileMap(tilesMap);
	}

	/**
	 * @param tilesMap
	 * this creates the x and y map for the center of each crossingTile: crossingsMap[0] --> y crossingsMap[1] --> x
	 */
	public void createCrossingTileMap(int[][] tilesMap){
		crossingsMap = new double[numberOfCrossings][2];
		int counter = 0;
		if (tilesMap != null) {
			for (int i = 0; i < tilesMap.length; i++) {
				for (int j = 0; j < tilesMap[i].length; j++) {
					if (tilesMap[i][j]==-2) {
						crossingsMap[counter][0] = (j + 0.5) * game.getTileSize();
						crossingsMap[counter][1] = (i + 0.5) * game.getTileSize();
						counter++;
					}
				}
			}
		}
	}

	/**
	 * @param tilesMap
	 * @return
	 */
	public int getNumberOfCrossings(int[][] tilesMap){
		int numberOfCrossings = 0;
		if (tilesMap != null) {
			for (int i = 0; i < tilesMap.length; i++) {
				for (int j = 0; j < tilesMap[i].length; j++) {
					if (tilesMap[i][j]==-2) {
						numberOfCrossings++;
					}
				}
			} 
		}
		return numberOfCrossings;
	}
	
	/**
	 * @return
	 */
	public double[][] getCrossingsMap(){
		return crossingsMap;
	}
	
	/**
	 * checks if the object is on a crossing. Returns true or false plus the crossings-center X and Y coordinate
	 * @return
	 */
	public double[][] checkIfOnCrossings(double objectX, double objectY, int marge) {
		double x = objectX;
		double y = objectY;
		int m = marge;
		double[][] boolxy = new double[numberOfCrossings][2];
		for (int i=0; i<crossingsMap.length; i++) {
				if (x>=(crossingsMap[i][0]-m) & x<=(crossingsMap[i][0]+m) & y>=(crossingsMap[i][1]-m) & y<=(crossingsMap[i][1]+m)) {
					boolxy[i][0] = (crossingsMap[i][0]);
					boolxy[i][1] = (crossingsMap[i][1]);
					}
				}		
		return boolxy;
	}

}
