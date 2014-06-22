package com.gdx.ghostbox.GameStates;

import java.util.ArrayList;

/**
 * Класс результатов
 */
public class Score {
    private ArrayList<Integer> list = new ArrayList<Integer>();

    /**
     * Добавляет результат игры
     * @param score результат игры
     */
    public void addScore(int score){
        list.add(score);
    }

    /**
     * @return Весь список результатов игры
     */
    public ArrayList<Integer> getScore() {
        return list;
    }
}
