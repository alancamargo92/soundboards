package com.ukdev.classes;

/**
 * Carcada
 * Created by Alan Camargo - May 2016
 */
public class Carcada
{

    private String title, length;
    private int audioFile;

    /**
     * Instantiates the class
     * @param title - String
     * @param audioFile - int
     * @param length - String
     */
    public Carcada (String title, int audioFile, String length)
    {
        this.title = title;
        this.audioFile = audioFile;
        this.length = length;
    }

    /**
     * Gets the title
     * @return title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the audio file (reference)
     * @return audio file
     */
    public int getAudioFile()
    {
        return audioFile;
    }

    /**
     * Gets the length
     * @return length
     */
    String getLength()
    {
        return length;
    }

}
