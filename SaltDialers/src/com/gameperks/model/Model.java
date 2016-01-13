/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks.model;

import com.gameperks.Configuration;

import java.awt.*;
import java.util.*;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

/**
 * Model
 *
 * @author Alexey Grischenko
 */
public class Model
{
    private static final Logger logger = Logger.getLogger(Model.class.getName());

    private static Model model = new Model();
    private Player player;

    private Set<Village> villages = new HashSet<>();
    private Map<String, GameObject> cellsToGameObjects = new HashMap<>();

    public static Model getInstance()
    {
        return model;
    }

    public void init()
    {
        logger.info("Initialization...");

        // append villages
        Configuration cfg = Configuration.getInstance();
        villages.add(new Village(cfg.getCellsHorizontal() / 2, 1, "village"));
        villages.add(new Village(cfg.getCellsHorizontal() / 2, cfg.getCellsVertical() - 2, "village"));
        villages.add(new Village(1, cfg.getCellsVertical() / 2, "village"));
        villages.add(new Village(cfg.getCellsHorizontal() - 2, cfg.getCellsVertical() / 2, "village"));

        villages.forEach(village->{
            cellsToGameObjects.put(getKey(village.getX(), village.getY()), village);
        });

        Random random = new Random(System.currentTimeMillis());
        Village village = villages.iterator().next();
        for(int i=0; i<10; i++)
        {
            int x = village.getX();
            int y = village.getY();

            while(nonNull(cellsToGameObjects.get(getKey(x, y))))
            {
                x = random.nextInt(cfg.getCellsHorizontal());
                y = random.nextInt(cfg.getCellsVertical());
            }

            cellsToGameObjects.put(getKey(x, y), new Forest(x, y, "forest"));
        }

        logger.info("Create default player");
        player = new Player(5, 5, "player");
        cellsToGameObjects.put(getKey(5, 5), player);

        logger.info("...Initialized");
    }

    private String getKey(int x, int y)
    {
        return x+","+y;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Set<Village> getVillages()
    {
        return villages;
    }

    public boolean resolveMove(Player player, Point cell)
    {
        int dx = cell.x - player.getX();
        int dy = cell.y - player.getY();
        if ((Math.abs(dx) + Math.abs(dy)) != 1)
            return false;

        player.setX(player.getX() + dx);
        player.setY(player.getY() + dy);

        return true;
    }

    public ArrayList getAllObjects()
    {
        ArrayList<GameObject> objects = new ArrayList<>();
        objects.addAll(cellsToGameObjects.values());
        return objects;
    }
}
