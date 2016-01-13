/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks.model;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Tile
 *
 * @author Alexey Grischenko
 */
public class Tile extends GameObject
{
    private static final Logger logger = Logger.getLogger(Tile.class.getName());

    public Tile(int x, int y, String imageId)
    {
        super(x, y, imageId);
    }
}
