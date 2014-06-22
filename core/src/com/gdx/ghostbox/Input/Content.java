package com.gdx.ghostbox.Input;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Класс для загрузки контента с внешних файлов
 */
public class Content {
	
	private HashMap<String, Texture> textures;
    /**
     * Хранение пути внешнего файла и его ключа
     */
	public Content() {
		textures = new HashMap<String, Texture>();
	}

    /**
     * @param path Путь к файлу
     * @param key ключ для обращения к файлу
     */
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key, tex);
	}
    /**
     * @param key Ключ нужного файла
     * @return  Файл по ключу
     */
	public Texture getTexture(String key) {
		return textures.get(key);
	}

    /**
     * Выгружает файл
     * @param key Ключ нужного файла
     */
	public void disposeTexture(String key) {
		Texture tex = textures.get(key);
		if(tex != null) tex.dispose();
	}
	
}













