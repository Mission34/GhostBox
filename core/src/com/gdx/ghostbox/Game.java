package com.gdx.ghostbox;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.ghostbox.Input.Content;
import com.gdx.ghostbox.Input.GameStateManager;
import com.gdx.ghostbox.Input.MyInput;
import com.gdx.ghostbox.Input.MyInputProcessor;

public class Game implements ApplicationListener {
	
	public static final String TITLE = "Ghost box";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;
	//private float accum;
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	public void create() {
		
		//Texture.setEnforcePotImages(false);
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		res = new Content();
		res.loadTexture("res/images/frog.png", "frog");
		res.loadTexture("res/images/coin.png", "coin");
        res.loadTexture("res/images/menu.png", "menu");
        res.loadTexture("res/images/level1.png", "level1");
        res.loadTexture("res/images/gameOver.png", "gameOver");
        res.loadTexture("res/images/play.png", "play");
        res.loadTexture("res/images/level1.png", "level1");
        res.loadTexture("res/images/level2.png", "level2");
        res.loadTexture("res/images/level3.png", "level3");
        res.loadTexture("res/images/level4.png", "level4");
        res.loadTexture("res/images/menuLevel.png", "menuLevel");
        res.loadTexture("res/images/youWin.png", "youWin");
        res.loadTexture("res/images/menuScore.png", "menuScore");
        res.loadTexture("res/images/bestScores.png", "bestScores");
        res.loadTexture("res/images/exit.png", "exit");
        res.loadTexture("res/images/back.png", "back");
        res.loadTexture("res/images/woodInd.png", "woodInd");
        res.loadTexture("res/images/steelInd.png", "steelInd");
        res.loadTexture("res/images/glassInd.png", "glassInd");

		
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		gsm = new GameStateManager(this);
		
	}
	
	public void render() {
       // Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());

        gsm.update(Gdx.graphics.getDeltaTime());

//		accum += Gdx.graphics.getDeltaTime();
//		while(accum >= STEP) {
//			accum -= STEP;
//			gsm.update(STEP);
//			MyInput.update();
//		}
        MyInput.update();
		gsm.render();
		
	}
	
	public void dispose() {
		
	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
	
}
