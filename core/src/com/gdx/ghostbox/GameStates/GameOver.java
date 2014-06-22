package com.gdx.ghostbox.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.ghostbox.Input.*;
import com.gdx.ghostbox.Game;

import java.util.ArrayList;

import static com.gdx.ghostbox.Input.Variables.PPM;

/**
 * Класс игрового состояния "Вы проиграли!"
 */

public class GameOver extends GameState {

    private boolean debug = false;

    private Background bg;
    SpriteBatch batch;
    BitmapFont font;
    private World world;
    private Box2DDebugRenderer b2dRenderer;
    private static ArrayList<Integer> scorelist = new ArrayList<Integer>();

    /**
     * Устанавливает фон и шрифт отображаемого текста
     * @param gsm число игрового состояния
     */
    public GameOver(GameStateManager gsm) {

        super(gsm);

        Texture tex = Game.res.getTexture("gameOver");
        bg = new Background(new TextureRegion(tex), cam, 0f);
        bg.setVector(-20, 0);
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("res/Oswald-Stencbab.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 40;
        font = generator.generateFont(param);

        cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

        world = new World(new Vector2(0, -9.8f * 5), true);
        b2dRenderer = new Box2DDebugRenderer();
    }



    public void handleInput() {}

    private float dtt = 0;
    public void update(float dt) {

        handleInput();

        world.step(dt / 5, 8, 3);

        bg.update(dt);

        dtt+=dt;
        if(dtt> 1.8) {
            dtt = 0;
            gsm.setState(GameStateManager.MENU);
        }
    }

    /**
     * Отрисовка объктов мира
     */
    public void render() {

        sb.setProjectionMatrix(cam.combined);

        /**
         * Отрисовка заднего фона
         */
        bg.render(sb);

        /**
         *    Отрисовка заданого куска мира
          */

        if(debug) {
            cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
            b2dRenderer.render(world, cam.combined);
            cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
        }
        /**
         * Отрисовка таблицы достижений
         */
        batch.begin();
        font.draw(batch, "YOUR SCORE : " + Integer.toString(Play.levelScore),
                Game.V_WIDTH / 2, Game.V_HEIGHT / 5);

        batch.end();

    }

    public void dispose() {}

    /**
     * @param scorelist Список достижений
     */
    public static void setScorelist(ArrayList<Integer> scorelist) {
        GameOver.scorelist = scorelist;
    }
}
