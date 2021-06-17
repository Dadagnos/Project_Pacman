package nl.han.ica.oopg.pacmanpackage;

public class CrossingTile{

	/**
	 * 
	 */
	private float x;
	/**
	 * 
	 */
	private float y;

	/**
	 * @param game
	 */
	public CrossingTile(PacmanSpel game) {
		int size = game.getTileSize();
	}
	
	/**
	 * @param x
	 */
	public void setX(float x){
		this.x = x;
	}
	
	/**
	 * @param y
	 */
	public void setY(float y){
		this.y = y;
	}
	
	/**
	 * @return
	 */
	public float getX(){
		return this.x;
	}
	
	/**
	 * @return
	 */
	public float getY(){
		return y;
	}

}
