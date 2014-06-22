package com.gdx.ghostbox.Input;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Класс анимации текстур
 */
public class Animation {
	
	private TextureRegion[] frames;
	private float time;
	private float delay;
	private int currentFrame;
	private int timesPlayed;
	
	public Animation() {}
    /**
     * @param frames Кадр, либо текстура
     */
	public Animation(TextureRegion[] frames) {
		this(frames, 1 / 12f);
	}
    /**
     *
     * @param frames Кадр, либо текстура
     * @param delay Задеркка смены текстур(кадров)
     */
	public Animation(TextureRegion[] frames, float delay) {
		setFrames(frames, delay);
	}

    /**
     *
     * @param frames Кадр(текстура)
     * @param delay Задеркка смены текстур(кадров)
     */
	public void setFrames(TextureRegion[] frames, float delay) {
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0;
		timesPlayed = 0;
	}
    /**
     * @param dt Задержка времени
     */
	public void update(float dt) {
		if(delay <= 0) return;
		time += dt;
		while(time >= delay) {
			step();
		}
	}
	private void step() {
		time -= delay;
		currentFrame++;
		if(currentFrame == frames.length) {
			currentFrame = 0;
			timesPlayed++;
		}
	}
    /**
     * @return Текущий кадр(текстуру)
     */
	public TextureRegion getFrame() { return frames[currentFrame]; }
    /*
	public int getTimesPlayed() { return timesPlayed; }
	*/
	
}













