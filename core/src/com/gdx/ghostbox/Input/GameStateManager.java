package com.gdx.ghostbox.Input;

import java.util.Stack;

import com.gdx.ghostbox.Game;
import com.gdx.ghostbox.GameStates.*;

/**
 * Класс игровых состояний
 */
public class GameStateManager {

    private Game game;

    private Stack<GameState> gameStates;

    public static final int MENU = 83774392;
    public static final int PLAY = 388031654;
    public static final int GAME_OVER = 65468651;
    public static final int LEVEL = 68746138;
    public static final int YOU_WIN = 863216556;
    public static final int YOUR_SCORE = 86645556;



    public GameStateManager(Game game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(MENU);
    }

    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    /**
     * Отрисовка игровых состояний
     */
    public void render() {
        gameStates.peek().render();
    }

    /**
     * @return Текущую игру
     */
    public Game game() { return game; }

    private GameState getState(int state) {
        if(state == MENU) return new Menu(this);
        if(state == PLAY) return new Play(this);
        if(state == GAME_OVER) return new GameOver(this);
        if(state == LEVEL) return new Level(this);
        if(state == YOU_WIN) return new YouWin(this);
        if(state == YOUR_SCORE) return new YourScore(this);

        return null;
    }
    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }

}













