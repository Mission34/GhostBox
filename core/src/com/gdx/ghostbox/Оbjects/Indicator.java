package com.gdx.ghostbox.Оbjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.ghostbox.Input.Variables;
import com.gdx.ghostbox.Game;

/**
 * Класс для отображения индикатора текущей поверхности
 */
public class Indicator {
	
	private Player player;
	
	private TextureRegion[] blocks;
    private TextureRegion block1, block2, block3;
	
	public Indicator(Player player) {
		
		this.player = player;
		
		Texture tex1 = Game.res.getTexture("woodInd");
        Texture tex2 = Game.res.getTexture("steelInd");
        Texture tex3 = Game.res.getTexture("glassInd");

        block1 = new TextureRegion(tex1, 0, 0, 20, 20);
        block2 = new TextureRegion(tex2, 0, 0, 20, 20);
        block3 = new TextureRegion(tex3, 0, 0, 20, 20);
		

	}
	
	public void render(SpriteBatch sb) {
		
		short bits = player.getBody().getFixtureList().first()
						.getFilterData().maskBits;
		
		sb.begin();
		if((bits & Variables.BIT_WOOD) != 0) {
			sb.draw(block1, 40, 200);
		}
		if((bits & Variables.BIT_STEEL) != 0) {
			sb.draw(block2, 40, 200);
		}
		if((bits & Variables.BIT_GLASS) != 0) {
			sb.draw(block3, 40, 200);
		}
		sb.end();
		
	}
	
	
}













