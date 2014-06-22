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
import java.util.Collections;

import static com.gdx.ghostbox.Input.Variables.PPM;

public class YourScore extends GameState {

    private boolean debug = false;

    private Background bg;
    private Animation animation;
    private GameButton playButton;
    SpriteBatch batch;
    BitmapFont font;
    private World world;
    private Box2DDebugRenderer b2dRenderer;
    private static ArrayList<Integer> scorelist = new ArrayList<Integer>();
    private Array<Sprites> blocks;
    public YourScore(GameStateManager gsm) {

        super(gsm);

        Texture tex = Game.res.getTexture("menuScore");
        bg = new Background(new TextureRegion(tex), cam, 0f);
        bg.setVector(-20, 0);
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("res/Oswald-Stencbab.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 40;
        font = generator.generateFont(param);

        Texture tex3 = Game.res.getTexture("back");
        playButton = new GameButton(new TextureRegion(tex3,0, 0, 35, 15), Game.V_WIDTH / 2, Game.V_HEIGHT / 8, cam);


        cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

        world = new World(new Vector2(0, -9.8f * 5), true);
        //world = new World(new Vector2(0, 0), true);
        b2dRenderer = new Box2DDebugRenderer();

        scorelist = Play.getScore().getScore();
        Collections.reverse(scorelist);


    }



    public void handleInput() {

        // mouse/touch input
        if(playButton.isClicked()) {
            gsm.setState(GameStateManager.MENU);
        }
    }

    private float dtt = 0;
    public void update(float dt) {

        handleInput();

        world.step(dt / 5, 8, 3);

        bg.update(dt);

        playButton.update(dt);


    }

    public void render() {

        sb.setProjectionMatrix(cam.combined);

        // draw background
        bg.render(sb);

        // draw button
         playButton.render(sb);



        // debug draw box2d
        if(debug) {
            cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
            b2dRenderer.render(world, cam.combined);
            cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
        }
        batch.begin();
        int j =0;
        for (Integer score: scorelist){
            font.draw(batch, (j+1) + ". " + Integer.toString(score), 100, 320 - j++*50);

            if (j > 4)
                break;
        }


        batch.end();

    }

    public void dispose() {
        // everything is in the resource manager com.neet.blockbunny.handlers.Content
    }

    public static void setScorelist(ArrayList<Integer> scorelist) {
        YourScore.scorelist = scorelist;
    }
}
