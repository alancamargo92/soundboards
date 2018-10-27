package com.ukdev.carcadasalborghetti.model;

/**
 * Carcada
 * Created by Alan Camargo - May 2016
 */
public class Carcada {

    private String title, length;
    private int sound;

    /**
     * Instantiates the class
     * @param title - String
     * @param length - String
     */
    public Carcada(String title, String length, int sound) {
        this.title = title;
        this.length = length;
        this.sound = sound;
    }

    /**
     * Gets the title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the length
     * @return length
     */
    public String getLength() {
        return length;
    }

    /**
     * Gets the sound
     * @return the sound
     */
    public int getSound() {
        return sound;
    }

}
