package com.gdx.ghostbox.GameStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.ghostbox.Input.*;
import com.gdx.ghostbox.Game;

import static com.gdx.ghostbox.Input.Variables.PPM;

/**
 * Класс игрового состояния "Выбор уровня"
 */
public class Level extends GameState {

    private boolean debug = false;

    private Background bg;
    private GameButton playButton, playButton2, playButton3, playButton4, PlayButtonBack;

    private World world;
    private Box2DDebugRenderer b2dRenderer;

    /**
     * Устанавливливает фон и игровые кнопки
     * @param gsm число игрового состояния
     */
    public Level(GameStateManager gsm) {

        super(gsm);

        Texture tex = Game.res.getTexture("menuLevel");
        Texture tex2,tex3,tex4;
        bg = new Background(new TextureRegion(tex), cam, 0f);
        bg.setVector(-20, 0);

        TextureRegion[] reg = new TextureRegion[4];

        tex = Game.res.getTexture("level1");
        playButton = new GameButton(new TextureRegion(tex,0, 0, 103, 32),
                Game.V_WIDTH / 2,Game.V_HEIGHT - Game.V_HEIGHT / 9, cam);
        tex2 = Game.res.getTexture("level2");
        playButton2 = new GameButton(new TextureRegion(tex2,0, 0, 117, 32),
                Game.V_WIDTH / 2,Game.V_HEIGHT - Game.V_HEIGHT / 9 - 32, cam);
       /* tex3 = Game.res.getTexture("level3");
        playButton3 = new GameButton(new TextureRegion(tex3,0, 0, 117, 32),
                Game.V_WIDTH / 2,Game.V_HEIGHT - Game.V_HEIGHT / 9 - 64, cam);
        tex4 = Game.res.getTexture("level4");
        playButton4 = new GameButton(new TextureRegion(tex4,0, 0, 117, 32),
                Game.V_WIDTH / 2,Game.V_HEIGHT - Game.V_HEIGHT / 9 - 96, cam);*/
        tex4 = Game.res.getTexture("back");
        PlayButtonBack = new GameButton(new TextureRegion(tex4,0, 0, 35, 15),
                Game.V_WIDTH / 2,Game.V_HEIGHT / 8, cam);


        cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);

        world = new World(new Vector2(0, -9.8f * 5), true);
        b2dRenderer = new Box2DDebugRenderer();



    }


    /**
     * Действия при нажатии игровых кнопок
     */
    public void handleInput() {

        // mouse/touch input
        if(playButton.isClicked()) {
            Play.level = 1;
            gsm.setState(GameStateManager.PLAY);

        }
        if(playButton2.isClicked()) {
            Play.level = 2;
            gsm.setState(GameStateManager.PLAY);
        }
        if(PlayButtonBack.isClicked()) {
            gsm.setState(GameStateManager.MENU);
        }

    }

    /**
     * Обновление игрового состояния
     * @param dt Задержка времени
     */
    public void update(float dt) {

        handleInput();

        world.step(dt / 5, 8, 3);

        bg.update(dt);

        playButton.update(dt);
        playButton2.update(dt);
       /* playButton3.update(dt);
        playButton4.update(dt);*/
        PlayButtonBack.update(dt);

    }

    /**
     * Отрисовка объектов игрового состояния
     */
    public void render() {

        sb.setProjectionMatrix(cam.combined);

        bg.render(sb);

        playButton.render(sb);
        playButton2.render(sb);
      /*  playButton3.render(sb);
        playButton4.render(sb);*/
        PlayButtonBack.render(sb);


       if(debug) {
            cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
            b2dRenderer.render(world, cam.combined);
            cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
        }



    }

    public void dispose() {
    }

}
