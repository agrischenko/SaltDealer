/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks.model;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Player
 *
 * @author Alexey Grischenko
 */
public class Player extends GameObject
{
    private static final Logger logger = Logger.getLogger(Player.class.getName());

    public Player(int x, int y, String imageId)
    {
        super(x, y, imageId);
    }
}
