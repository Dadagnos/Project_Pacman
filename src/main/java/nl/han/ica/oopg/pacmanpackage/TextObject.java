package nl.han.ica.oopg.pacmanpackage;

import nl.han.ica.oopg.objects.GameObject;
import processing.core.PGraphics;

/**
 * @author Oorspronkelijke auteur Ralph Niels (geleend van Waterworld)
 * Wordt gebruikt om een tekst te kunnen afbeelden
 */
public class TextObject extends GameObject {

    /**
     * variabale die de text opslaat
     */
    private String text;

    /**
     * @param text
     * constructor voor een textobject
     */
    public TextObject(String text) {
        this.text = text;
    }

    /**
     * @param text
     * hiermee 'set' je de text
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void update() {

    }

    /**
     *tekent de tekst op het scherm
     */
    @Override
    public void draw(PGraphics g) {
        g.textAlign(g.LEFT, g.TOP);
        g.textSize(50);
        g.text(text, getX(), getY());
    }
}
