package com.gdx.ghostbox.Оbjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.ghostbox.Game;

/**
 * Класс игрока, содержит текстуры и делает анимацию движения
 */

public class Player extends Sprites {
	
	private int numCoins;
	private int totalCoins;

    /**
     * @param body Тело игрока
     */
	public Player(Body body) {
		
		super(body);
		
		Texture tex = Game.res.getTexture("frog");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		
		setAnimation(sprites, 1 / 8f);
		
	}
	/*
	public void collectCoins() { numCoins++; }
	public int getNumCoins() { return numCoins; }
	public void setTotalCoins(int i) { totalCoins = i; }
	public int getTotalCoins() { return totalCoins; }
	*/
}










