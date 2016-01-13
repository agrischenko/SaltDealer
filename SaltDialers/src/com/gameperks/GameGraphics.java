/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * GameGraphics
 *
 * @author Alexey Grischenko
 */
public class GameGraphics
{
    private static final Logger logger = Logger.getLogger(GameGraphics.class.getName());

    private static GameGraphics graphics = new GameGraphics();

    private Map<String, Image> images = new HashMap();

    public static GameGraphics getInstance()
    {
        return graphics;
    }

    public Image getImage(String id)
    {
        return images.get(id);
    }

    public void init()
    {
        logger.info("Initialization...");

        try
        {
            Configuration cfg = Configuration.getInstance();
            loadImage("player", cfg.getImageFolder()+"/kazak.png");
            loadImage("village", cfg.getImageFolder()+"/village.png");
            loadImage("meadows", cfg.getImageFolder()+"/meadows.png");
            loadImage("forest", cfg.getImageFolder()+"/forest.png");
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, e.toString(), e);
            return;
        }

        logger.info("...Initialized");
    }

    private void loadImage(String id, String path) throws IOException
    {
        BufferedImage playerImage = ImageIO.read(new File(path));
        images.put(id, playerImage);
    }
}
