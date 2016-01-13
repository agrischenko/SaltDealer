package com.gameperks;

import com.gameperks.model.Model;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Main extends JFrame
{
    public static void main(String[] args) throws InvocationTargetException, InterruptedException
    {
        SwingUtilities.invokeAndWait(new Runnable()
        {
            @Override
            public void run()
            {
                new Main().launch();
            }
        });
    }

    private void launch()
    {
        init();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void init()
    {

        Configuration configuration = Configuration.getInstance();
        configuration.setCellsVertical(7);
        configuration.setCellsHorizontal(9);
        configuration.setImageFolder("images");

        Model model = Model.getInstance();
        model.init();

        model.getPlayer().setX(0);
        model.getPlayer().setY(0);

        GameGraphics.getInstance().init();

        MapPanel mapPanel = new MapPanel();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(mapPanel);
        scrollPane.setPreferredSize(new Dimension(mapPanel.getPreferredSize().width + 10, mapPanel.getPreferredSize().height + 10));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setFocusTraversalKeysEnabled(true);

        pack();

    }
}
