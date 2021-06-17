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

public class Pacman extends SpriteObject implements ICollidableWithTiles, Besturing {
	/**
	 * 
	 */
	private PacmanSpel game;
	private PacmanStatus pacmanStatus = PacmanStatus.ALIVE;
	
	/**
	 * @param game
	 * @param speed
	 */
	public Pacman(PacmanSpel game, int speed) {
	super(new Sprite(PacmanSpel.MEDIA_URL.concat("pacman7.png")));
	this.game = game;
	}



	/**
	 *
	 */
	@Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;
        Sprite invisible = new Sprite(PacmanSpel.MEDIA_URL.concat("invisible.png"));
        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof SnoepTile) {
            	if (ct.getTile().getSprite().equals(invisible)) {
            	} else {
            		ct.getTile().setSprite(invisible);
            		PVector thetile = game.getTileMap().getTileIndex(ct.getTile());
            		game.getTileMap().setTile((int)thetile.x, (int)thetile.y, -1);
					game.setSnoepjesScore(game.getSnoepjesScore() + 1);
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
	 *
	 */
	@Override
	public void update() {
		
	}
	
	/**
	 *
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
	 *
	 */
	public void keyReleased(int keyCode, char key) {
		if (keyCode == LEFT || keyCode == RIGHT || keyCode == UP || keyCode == DOWN) {
		game.pacman.setSpeed(0);
		}
	}

	public PacmanStatus getPacmanStatus() {
		return pacmanStatus;
	}

	public void setPacmanStatus(PacmanStatus status) {
		pacmanStatus = status;
	}

}
