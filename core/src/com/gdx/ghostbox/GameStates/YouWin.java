package com.gdx.ghostbox.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.ghostbox.Оbjects.Sprites;
import com.gdx.ghostbox.Input.*;
import com.gdx.ghostbox.Game;

import java.util.ArrayList;

import static com.gdx.ghostbox.Input.Variables.PPM;

/**
 * Класс игрового состояния "Вы победили!"
 */
public class YouWin extends GameState {

    private boolean debug = false;

    private Background bg;
    SpriteBatch batch;
    BitmapFont font;
    private static ArrayList<Integer> scorelist = new ArrayList<Integer>();

    private World world;
    private Box2DDebugRenderer b2dRenderer;

    private Array<Sprites> blocks;

    /**
     * Устанавливает фон и шрифт текста
     * @param gsm переменная игрового состояния
     */
    public YouWin(GameStateManager gsm) {

        super(gsm);

        Texture tex = Game.res.getTexture("youWin");
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
        if(dtt> 1) {
            dtt = 0;
            gsm.setState(GameStateManager.LEVEL);
        }





    }

    public void render() {

        sb.setProjectionMatrix(cam.combined);

        bg.render(sb);


       if(debug) {
            cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
            b2dRenderer.render(world, cam.combined);
            cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
        }

        batch.begin();
        font.draw(batch,   "YOUR SCORE : " + Integer.toString(Play.levelScore),
                Game.V_WIDTH / 2, Game.V_HEIGHT / 5);
        batch.end();

    }

    public void dispose() {

    }

    public static void setScorelist(ArrayList<Integer> scorelist) {
        YouWin.scorelist = scorelist;
    }

}
