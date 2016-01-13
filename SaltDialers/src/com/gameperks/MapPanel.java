/*
 * Copyright 2014 InFocus Corporation. All rights reserved.
 */
package com.gameperks;

import com.gameperks.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Map
 *
 * @author Alexey Grischenko
 */
public class MapPanel extends JPanel
{
    private static final Logger logger = Logger.getLogger(MapPanel.class.getName());
    public static final int FREE_SPACE = 35;

    Dimension cellSize = new Dimension(100, 100);
    int cells_x;
    int cells_y;

    Point selectedCell;

    final Dimension frameSize;

    volatile boolean showPlayer = true;
    volatile boolean painting = false;

    class PlayerBlinckTimer implements ActionListener

    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            showPlayer = !showPlayer;
            fireRepaint();
        }
    }

    private void fireRepaint()
    {
        if (painting)
            return;
        repaint();
    }

    MapPanel()
    {
        cells_x = Configuration.getInstance().getCellsHorizontal();
        cells_y = Configuration.getInstance().getCellsVertical();
        Dimension size = new Dimension(cells_x * cellSize.width + FREE_SPACE * 2, cells_y * cellSize.height + FREE_SPACE * 2);
        frameSize = new Dimension(size);

        Dimension parentSize = new Dimension(size.width, size.height);
        setPreferredSize(parentSize);
        setMaximumSize(parentSize);
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                // determine selected cell
                Point mousePoint = e.getPoint();
                if (mousePoint.x < FREE_SPACE || mousePoint.y < FREE_SPACE
                        || mousePoint.x > (frameSize.width - FREE_SPACE) || mousePoint.y > (frameSize.height - FREE_SPACE))
                {
                    if (isNull(selectedCell))
                        return;
                    selectedCell = null;
                    repaint();
                    return;
                }

                Point mouseCellPoint = new Point((mousePoint.x - FREE_SPACE) / cellSize.width,
                                             (mousePoint.y - FREE_SPACE) / cellSize.height);

                if (isNull(selectedCell) || !selectedCell.equals(mouseCellPoint))
                {
                    selectedCell = mouseCellPoint;
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (isNull(selectedCell))
                    return;

                if (!Model.getInstance().resolveMove(Model.getInstance().getPlayer(), selectedCell))
                    return;

                showPlayer = true;
                repaint();
            }
        });

        //new Timer(300, new PlayerBlinckTimer()).start();
    }

    @Override
    public void paint(Graphics g)
    {
        painting = true;
        BufferedImage image = redrawFrame();
        g.drawImage(image, 0, 0, this);
        g.dispose();
        painting = false;
    }

    private BufferedImage redrawFrame()
    {
        BufferedImage image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);

        // border around
        Graphics2D g = image.createGraphics();
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, frameSize.width, frameSize.height);
        g.drawRect(0, 0, frameSize.width, frameSize.height);

        g.setColor(Configuration.getInstance().getGrassColor());
        g.fillRect(FREE_SPACE, FREE_SPACE, cells_x * cellSize.width, cells_y * cellSize.height);

        Model model = Model.getInstance();

        // draw tile set
        for(int x=0; x<cells_x; x++)
        {
            for(int y=0; y<cells_y; y++)
            {
                if (nonNull(selectedCell) && selectedCell.x == x && selectedCell.y == y)
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(FREE_SPACE + x * cellSize.width, FREE_SPACE + y * cellSize.height, cellSize.width, cellSize.height);
                }

                Image backgroundImage = GameGraphics.getInstance().getImage(Configuration.getInstance().getBackground());
                if (nonNull(backgroundImage))
                    g.drawImage(backgroundImage, FREE_SPACE + x * cellSize.width, FREE_SPACE + y * cellSize.height, cellSize.width, cellSize.height, this);

                g.setColor(Color.black);
                g.drawRect(FREE_SPACE + x * cellSize.width, FREE_SPACE + y * cellSize.height, cellSize.width, cellSize.height);
            }
        }

        // sort all by virtual Z-index

        // draw game objects
        ArrayList<GameObject> objects = model.getAllObjects();
        Collections.sort(objects, new Comparator<GameObject>()
        {
            @Override
            public int compare(GameObject o1, GameObject o2)
            {
                if (o1.getY() < o2.getY())
                    return -1;
                if (o1.getY() > o2.getY())
                    return 1;

                if (o1.getX() < o2.getX())
                    return -1;

                if (o1 instanceof Player)
                    return 1;

                if (o2 instanceof Player)
                    return -1;

                return 1;
            }
        });

        objects.forEach(object -> {
            if (object instanceof Player)
            {
                if (showPlayer)
                    drawPlayer(g, object);
            }
            else if (object instanceof Tile)
            {
                drawTile(g, object);
            }
            else
            {
                drawGameObject(g, object);
            }
        });


        g.dispose();

        return image;
    }

    private void drawPlayer(Graphics2D g, GameObject gameObject)
    {
        Image image = getImage(gameObject);
        if (isNull(image))
            return;

        int width = cellSize.width * 3 / 4;
        int height = (image.getHeight(this) * ((width * 100) / image.getWidth(this))) / 100;

        Point pivotPoint = new Point(FREE_SPACE + gameObject.getX() * cellSize.width,
                                     FREE_SPACE + gameObject.getY() * cellSize.height);

        pivotPoint.x = pivotPoint.x + (cellSize.width - width) / 2;
        pivotPoint.y = pivotPoint.y + (cellSize.height - height) - cellSize.height / 7;

        g.drawImage(image, pivotPoint.x, pivotPoint.y, width, height, this);
    }

    private void drawGameObject(Graphics2D g, GameObject gameObject)
    {
        Image image = getImage(gameObject);
        if (isNull(image))
            return;

        int width = cellSize.width;
        int height = (image.getHeight(this) * ((width * 100) / image.getWidth(this))) / 100;

        Point pivotPoint = new Point(FREE_SPACE + gameObject.getX() * cellSize.width,
                                     FREE_SPACE + gameObject.getY() * cellSize.height);

        pivotPoint.x = pivotPoint.x + (cellSize.width - width) / 2;
        pivotPoint.y = pivotPoint.y + (cellSize.height - height);

        g.drawImage(image, pivotPoint.x, pivotPoint.y, width, height, this);
    }

    private void drawTile(Graphics2D g, GameObject gameObject)
    {
        Image image = getImage(gameObject);
        if (isNull(image))
            return;

        Point pivotPoint = new Point(FREE_SPACE + gameObject.getX() * cellSize.width,
                                     FREE_SPACE + gameObject.getY() * cellSize.height);

        g.drawImage(image, pivotPoint.x, pivotPoint.y, cellSize.width, cellSize.height, this);
    }

    private Image getImage(GameObject gameObject)
    {
        Image image = GameGraphics.getInstance().getImage(gameObject.getImageId());
        if (isNull(image))
        {
            logger.warning("Image with id="+gameObject.getImageId()+" not found");
            return null;
        }
        return image;
    }
}
