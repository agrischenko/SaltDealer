/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks.model;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Village
 *
 * @author Alexey Grischenko
 */
public class Village extends GameObject
{
    private static final Logger logger = Logger.getLogger(Village.class.getName());

    public Village(int x, int y, String imageId)
    {
        super(x, y, imageId);
    }
}
