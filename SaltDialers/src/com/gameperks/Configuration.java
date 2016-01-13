/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks;

import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Configuration
 *
 * @author Alexey Grischenko
 */
public class Configuration
{
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    private static Configuration configuration = new Configuration();
    private int cellsHorizontal;
    private int cellsVertical;
    private String imageFolder;
    private Color grassColor;
    private String background;

    public static Configuration getInstance()
    {
        return configuration;
    }

    private Configuration()
    {

    }


    public int getCellsHorizontal()
    {
        return cellsHorizontal;
    }

    public void setCellsHorizontal(int cellsHorizontal)
    {
        this.cellsHorizontal = cellsHorizontal;
    }

    public int getCellsVertical()
    {
        return cellsVertical;
    }

    public void setCellsVertical(int cellsVertical)
    {
        this.cellsVertical = cellsVertical;
    }

    public String getImageFolder()
    {
        return imageFolder;
    }

    public void setImageFolder(String imageFolder)
    {
        this.imageFolder = imageFolder;
    }

    public Color getGrassColor()
    {
        return grassColor == null ? new Color(30, 100, 30) : grassColor;
    }

    public void setGrassColor(Color grassColor)
    {
        this.grassColor = grassColor;
    }

    public String getBackground()
    {
        return background == null ? "meadows" : background;
    }

    public void setBackground(String background)
    {
        this.background = background;
    }
}
