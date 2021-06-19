package nl.han.ica.oopg.pacmanpackage;

/**
 * @author David Bartenstein
 *met deze enum kun je opslaan in welke staat het programma verkeerd
 */
public enum State
{
    /**
     * mogelijk startscherm
     */
    INTRO,
    /**
     * speelscherm
     */
    RUN,
    /**
     * mogelijk pauzemenu
     */
    PAUSE,
    /**
     * mogelijk eindscherm
     */
    GAME_OVER,
    /**
     * mogelijk eindscherm
     */
    WON;
}
