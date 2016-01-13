/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks.model;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * GameObject
 *
 * @author Alexey Grischenko
 */
public class GameObject
{
    private static final Logger logger = Logger.getLogger(GameObject.class.getName());

    Integer x;
    Integer y;
    private String imageId;
    private Boolean explored = false;

    public GameObject(int x, int y, String imageId)
    {
        setX(x);
        setY(y);
        setImageId(imageId);
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    public String getImageId()
    {
        return imageId;
    }

    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }


}
