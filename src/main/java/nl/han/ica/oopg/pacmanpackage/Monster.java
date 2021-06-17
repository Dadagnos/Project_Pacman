package nl.han.ica.oopg.pacmanpackage;

import java.util.List;
import java.util.Random;

import nl.han.ica.oopg.collision.CollidedTile;
import nl.han.ica.oopg.collision.CollisionSide;
import nl.han.ica.oopg.collision.ICollidableWithGameObjects;
import nl.han.ica.oopg.collision.ICollidableWithTiles;
import nl.han.ica.oopg.exceptions.TileNotFoundException;
import nl.han.ica.oopg.objects.GameObject;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.objects.SpriteObject;
import processing.core.PVector;

public class Monster extends SpriteObject implements ICollidableWithTiles, ICollidableWithGameObjects {
	/**
	 * 
	 */
	private PacmanSpel game;
	/**
	 * 
	 */
	private float speed;
	/**
	 * 
	 */
	private int direction = getRandomInt();
	public Object crossingTileMap;

	/**
	 * @param game
	 * @param speed
	 */
	public Monster(PacmanSpel game, int speed) {
		super(new Sprite(PacmanSpel.MEDIA_URL.concat("ghost3.png")));
		this.game = game;
		this.speed = speed;
		monsterMoves(direction);
	    }
	
	
	/**
	 * @param direction
	 */
	public void monsterMoves(int direction) {
		switch (direction) {
		case 1:
			setX(getX()+speed);
		break;
		case 2:
			setX(getX()-speed);
		break;
		case 3:
			setY(getY()+speed);
		break;
		case 4:
			setY(getY()-speed);
		break;
		}
	}
	
	/**
	 * @param crossingsMap
	 */
	public void checkForCrossings(double[][] crossingsMap ) {
		double x = this.getCenterX();
		double y = this.getCenterY();
		int m = 4;
		for (int i=0; i<crossingsMap.length; i++) {
			for (int j=0; j<crossingsMap[i].length; j++) {
				if (x>=(crossingsMap[i][0]-m) & x<=(crossingsMap[i][0]+m) & y>=(crossingsMap[i][1]-m) & y<=(crossingsMap[i][1]+m)) {
					setNewDirection();
				}
			}
		}
	}

	
	/**
	 *
	 */
	@Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;

        for (CollidedTile ct : collidedTiles) {
            if (ct.getTile() instanceof FloorTile ) {
                if (CollisionSide.TOP.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setY(vector.y - game.getTileSize());
                        setNewDirection();
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (CollisionSide.BOTTOM.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setY(vector.y + game.getTileSize());
                        setNewDirection();
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (CollisionSide.LEFT.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setX(vector.x - game.getTileSize());
                        setNewDirection();
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (CollisionSide.RIGHT.equals(ct.getCollisionSide())) {
                    try {
                        vector = game.getTileMap().getTilePixelLocation(ct.getTile());
                        setX(vector.x + game.getTileSize());
                        setNewDirection();
                    } catch (TileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
	
	
	
	/**
	 * @return
	 */
	private int getRandomInt() {
		return(int) ((Math.random() * (4 - 1)) + 1);
	}
	
	/**
	 *
	 */
	@Override
	public void update() {
		checkForCrossings(game.crossingTileMap.getCrossingsMap());
		monsterMoves(direction);
	}

	/**
	 *
	 */
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
	        if (go instanceof Pacman) {
	            game.deleteGameObject(go);
	            game.addGameObject(game.pacman, game.getWorldWidth()/17, game.getWorldWidth()/17);
	            game.pacman.setPacmanStatus(PacmanStatus.EATEN);
	        }
		}
	}
	
	/**
	 * 
	 */
	public void setNewDirection(){
		Random random = new Random();
		int Rnumber1 = random.nextInt(5);
		int Rnumber2 = getRandomInt();
		int Rnumber3 = getRandomInt();
		int newdirection = Rnumber1 + Rnumber2;
		
		if (newdirection > 4) {
		newdirection = (newdirection - (Rnumber3));
		} else {
			direction = newdirection;
		}
	}

}
