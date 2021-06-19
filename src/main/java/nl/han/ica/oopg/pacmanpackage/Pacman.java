package nl.han.ica.oopg.pacmanpackage;

import java.util.List;
import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import processing.core.PVector;

/**
 * @author David Bartenstein
 * De pacmanklasse die door de speler te besturen is
 */
public class Pacman extends SpriteObject implements ICollidableWithTiles {

	private PacmanSpel game;
	/**
	 * pacmanStatus slaat de toestand van pacman op aan de hand van de enum PacmanStatus
	 */
	private PacmanStatus pacmanStatus = PacmanStatus.ALIVE;
	
	/**
	 * @param game (het spel dat op dit moment aan het draaien is)
	 * @param speed (de snelheid waarmee pacman kan bewegen)
	 */
	public Pacman(PacmanSpel game, int speed) {
	super(new Sprite(PacmanSpel.MEDIA_URL.concat("pacman7.png")));
	this.game = game;
	}

	/**
	 *in deze tileCollision functie worden verschillende checks uitgevoerd. O.a:
	 * - of er een Snoeptile geraakt is, en zo ja:
	 *			-wordt de score verhoogd en het aantal nog op te eten SnoepTiles met verlaagd
	 * - of er een FloorTile geraakt wordt, en zo ja:
	 * 			-wordt er op de crossingTileMap gekeken of het vlakbij een kruispunt is,
	 * 			-zo ja:
	 * 					-wordt pacman mooi gecentreerd om makkelijker de bochten te kunnen nemen
	 * 			-zo nee:
	 * 					-wordt pacman tegengehouden omdat hij niet door muren mag
	 */
	@Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;
        Sprite invisible = new Sprite(PacmanSpel.MEDIA_URL.concat("invisible.png"));
        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof SnoepTile) {
            	int countBackwards = game.getCountSnoep();
            	if (ct.getTile().getSprite().equals(invisible)) {
            	} else {
            		ct.getTile().setSprite(invisible);
            		PVector thetile = game.getTileMap().getTileIndex(ct.getTile());
            		game.getTileMap().setTile((int)thetile.x, (int)thetile.y, -1);
					game.setSnoepjesScore(game.getSnoepjesScore() + 1);
					countBackwards--;
					game.setCountSnoep(countBackwards);
				}	
            }
            if (ct.getTile() instanceof FloorTile) {
            	double[][] crossroads = game.crossingTileMap.getCrossingsMap();
            	int m = 16;
            	for (double[] c : crossroads) {
            		
            		if ((this.getX()+(width/2))>=(c[0]-m) & (this.getX()+(width/2))<=(c[0]+m) & (this.getY()+(width/2))>=(c[1]-m) & (this.getY()+(width/2))<=(c[1]+m)) {
            			this.setX((float) c[0] - (width/2)+2);
            			this.setY((float) c[1] - (width/2)+2);
            		}
            	}
                if (CollisionSide.TOP.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setY(vector.y - game.getTileSize());
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (CollisionSide.BOTTOM.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setY(vector.y + game.getTileSize());
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (CollisionSide.LEFT.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setX(vector.x - game.getTileSize());
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (CollisionSide.RIGHT.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setX(vector.x + game.getTileSize());
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

	/**
	 * de update functie van Pacman
	 */
	@Override
	public void update() {
	}
	
	/**
	 *de keyPressed methode registreert of er pijltjestoetsen ingedrukt worden en bestuurt pacman
	 */
	@Override
	public void keyPressed(int keyCode, char key) {
    	game.pacman.setSpeed(5);
        if (keyCode == LEFT) {
        	setDirectionSpeed(270, game.pacman.getSpeed());
        } else if (keyCode == RIGHT) {
            setDirectionSpeed(90, game.pacman.getSpeed());
        } else if (keyCode == UP) {
            setDirectionSpeed(360, game.pacman.getSpeed());
        } else if (keyCode == DOWN) {
            setDirectionSpeed(180, game.pacman.getSpeed());
        } else {
        	setDirectionSpeed(90, 0);
        }
    }
	
	/**
	 *de keyReleased methode laat pacman stoppen als de toetsen niet ingedrukt worden
	 */
	public void keyReleased(int keyCode, char key) {
		if (keyCode == LEFT || keyCode == RIGHT || keyCode == UP || keyCode == DOWN) {
		game.pacman.setSpeed(0);
		}
	}

	/**
	 * @return pacmanStatus
	 */
	public PacmanStatus getPacmanStatus() {
		return pacmanStatus;
	}

	/**
	 * @param pacmanStatus
	 */
	public void setPacmanStatus(PacmanStatus status) {
		pacmanStatus = status;
	}

}
