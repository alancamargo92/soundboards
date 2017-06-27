package com.ukdev.carcadasalborghetti.model;

/**
 * Carcada
 * Created by Alan Camargo - May 2016
 */
public class Carcada {

    private String title, length;

    /**
     * Instantiates the class
     * @param title - String
     * @param length - String
     */
    public Carcada(String title, String length) {
        this.title = title;
        this.length = length;
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

}
