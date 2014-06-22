package com.gdx.ghostbox.GameStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.ghostbox.Оbjects.Sprites;
import com.gdx.ghostbox.Input.*;
import com.gdx.ghostbox.Game;

import static com.gdx.ghostbox.Input.Variables.PPM;

/**
 * Класс игрового состояния "Меню игры"
 */
public class Menu extends GameState {
	
	private boolean debug = false;
	
	private Background bg;
	private Animation animation;
	private GameButton playButton, playButton2, playButton3;
	
	private World world;
	private Box2DDebugRenderer b2dRenderer;
	
	private Array<Sprites> blocks;
    /**
     * Устанавливливает фон и игровые кнопки
     * @param gsm число игрового состояния
     */
	public Menu(GameStateManager gsm) {
		
		super(gsm);
		
		Texture tex = Game.res.getTexture("menu");
		bg = new Background(new TextureRegion(tex), cam, 0f);
		bg.setVector(-20, 0);
		
		//tex = Game.res.getTexture("bunny");
		TextureRegion[] reg = new TextureRegion[4];
		for(int i = 0; i < reg.length; i++) {
			reg[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
		}
		//animation = new Animation(reg, 1 / 12f);
		
		tex = Game.res.getTexture("play");
		playButton = new GameButton(new TextureRegion(tex,0, 0, 80, 35), Game.V_WIDTH / 2, Game.V_HEIGHT / 2, cam);

        Texture tex2 = Game.res.getTexture("bestScores");
        playButton2 = new GameButton(new TextureRegion(tex2,0, 0, 227, 35), Game.V_WIDTH / 2, Game.V_HEIGHT / 2 - 32, cam);

        Texture tex3 = Game.res.getTexture("exit");
        playButton3 = new GameButton(new TextureRegion(tex3,0, 0, 79, 35), Game.V_WIDTH / 2, Game.V_HEIGHT / 2 - 64, cam);
		
		cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
		world = new World(new Vector2(0, -9.8f * 5), true);
		//world = new World(new Vector2(0, 0), true);
		b2dRenderer = new Box2DDebugRenderer();
		


	}


    /**
     * Действия при нажатии игровых кнопок
     */
	public void handleInput() {
		
		// mouse/touch input
		if(playButton.isClicked()) {
			gsm.setState(GameStateManager.LEVEL);
		}
        if(playButton2.isClicked()) {
            gsm.setState(GameStateManager.YOUR_SCORE);
        }
        if(playButton3.isClicked()) {
            System.exit(0);
        }
		
	}
	
	public void update(float dt) {
		
		handleInput();
		
		world.step(dt / 5, 8, 3);
		
		bg.update(dt);
//		animation.update(dt);
		
		playButton.update(dt);
        playButton2.update(dt);
        playButton3.update(dt);
		
	}

    /**
     * Отрисовка объектов игрового состояния
     */
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		bg.render(sb);
		
		// draw button
		playButton.render(sb);
        playButton2.render(sb);
        playButton3.render(sb);


		if(debug) {
			cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
			b2dRenderer.render(world, cam.combined);
			cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		}
		

		
	}
	
	public void dispose() {
	}
	
}
