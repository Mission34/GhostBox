package com.gdx.ghostbox.Оbjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.ghostbox.Game;

public class Coins extends Sprites {
	
	public Coins(Body body) {
		
		super(body);
		
		Texture tex = Game.res.getTexture("coin");
		TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];
		
		setAnimation(sprites, 1 / 12f);
		
	}
	
}
