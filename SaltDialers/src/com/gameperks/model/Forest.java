/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks.model;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Forest
 *
 * @author Alexey Grischenko
 */
public class Forest extends GameObject
{
    private static final Logger logger = Logger.getLogger(Forest.class.getName());

    public Forest(int x, int y, String imageId)
    {
        super(x, y, imageId);
    }
}
