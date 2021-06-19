package nl.han.ica.oopg.pacmanpackage;

import java.util.ArrayList;

/**
 * @author Sarin
 */
public class CrossingTileMap {

	private PacmanSpel game;
	/**
	 * de crossingsmap variabale wordt gevuld met de centrale x en y posities van alle kruisingen op het scherm
	 */
	private static double[][] crossingsMap;
	/**
	 * de tilesmap bevat gewoon de tileMap zoals deze bij de start geinitialiseerd wordt
	 */
	private int[][] tilesmap;
	/**
	 * het aantal horizontale tiles
	 */
	private int horiTiles;
	/**
	 * het aantal verticale tiles
	 */
	private int vertTiles;
	/**
	 * het aantal kruispunten
	 */
	private int numberOfCrossings;
	
	/**
	 * @param game
	 * @param tilesMap (de tilesmap bevat gewoon de tileMap zoals deze bij de start geinitialiseerd wordt)
	 * @param horiTiles (het aantal horizontale tiles)
	 * @param vertTiles (het aantal verticale tiles)
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
	 * @return numberOfCrossings (aantal kruispunten op de kaart)
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
	 * @return crossingsMap
	 */
	public double[][] getCrossingsMap(){
		return crossingsMap;
	}

}
