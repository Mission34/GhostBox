package com.gdx.ghostbox.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.ghostbox.Game;

/**
 * Класс для запуска игры
 */
public class DesktopLauncher {

    public static void main(String[] args) {

        LwjglApplicationConfiguration config =
                new LwjglApplicationConfiguration();

        config.title = Game.TITLE;
        config.width = Game.V_WIDTH * Game.SCALE;
        config.height = Game.V_HEIGHT * Game.SCALE;



        new LwjglApplication(new Game(), config);

    }

}
