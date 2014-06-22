package com.gdx.ghostbox.Оbjects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.gdx.ghostbox.Input.Animation;
        import com.gdx.ghostbox.Input.Variables;

/**
 * Класс нужен для связки тела и текстуры
 */

public class Sprites {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    /**
     *
     * @param body Тело к которому нужно привязать текстуру
     */

    public Sprites(Body body) {
        this.body = body;
        animation = new Animation();
    }

    /**
     * Настройка анимации текстуры
     * @param reg Нужная часть картинки
     * @param delay Задержка смены картинки при анимации
     */

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    /**
     * Обновление анимации
     * @param dt Задержка времени
     */
    public void update(float dt) {
        animation.update(dt);
    }

    /**
     * Отрисовка спрайта
     * @param sb Сам спрайт
     */
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(animation.getFrame(), body.getPosition().x * Variables.PPM - width / 2, body.getPosition().y * Variables.PPM - height / 2);
        sb.end();
    }

    /**
     * @return Объект мира
     */
    public Body getBody() {
        return body;
    }

    /**
     *
     * @return Позицию объекта в мире
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * @return Ширину
     */
    public float getWidth() {
        return width;
    }

    /**
     * @return Высоту
     */
    public float getHeight() {
        return height;
    }

}