package com.gdx.ghostbox.GameStates;

import static com.gdx.ghostbox.Input.Variables.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.gdx.ghostbox.Оbjects.Coins;
import com.gdx.ghostbox.Оbjects.Indicator;
import com.gdx.ghostbox.Оbjects.Player;
import com.gdx.ghostbox.Input.*;
import com.gdx.ghostbox.Input.Variables;
import com.gdx.ghostbox.Game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
/**
 * Класс игрового состояния "Игра"
 */
public class Play extends GameState {
	
	private boolean debug = false;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	private MyContactListener cl;
	
	private TiledMap tileMap;

	private OrthogonalTiledMapRenderer tmr;
	
	private Player player;
    private int tileMapWidth;
    private int tileMapHeight;
    private int tileSize;
    private OrthogonalTiledMapRenderer tmRenderer;
	private Array<Coins> crystals;
    public static int levelScore = 0;

    private Background[] backgrounds;
	private Indicator hud;

   public static int level;


    /**
     * Создание игрока, объктов мира
     * @param gsm число игрового состояния
     */
	public Play(GameStateManager gsm) {
		
		super(gsm);

        /**
         * Создание мира
         */
		world = new World(new Vector2(0, -9.81f), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();

		createPlayer();

        createWalls();
		/*
		// create Coins
		createCoins();
        */
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);

		hud = new Indicator(player);
		
	}

    /**
     * Отслеживает нажатие кнопок при управлении игроком
     */
	public void handleInput() {
		
		// player jump
		if(MyInput.isPressed(MyInput.BUTTON1)) {
			if(cl.isPlayerOnGround()) {
                Vector2 vec = player.getBody().getLinearVelocity();
                vec.y=4.5f;
				player.getBody().setLinearVelocity(vec);
			}
		}
		
		// switch block color
		if(MyInput.isPressed(MyInput.BUTTON2)) {
			switchBlocks();
		}
		
	}

    /**
     * Обновление объектов мира
     * @param dt Время задержки обновления
     */
	public void update(float dt) {

        /**
         * Проверка ввода
         */
		handleInput();

		world.step(dt, 6, 2);

        /**
         * Удвление объектов
         */
		Array<Body> bodies = cl.getBodiesToRemove();
		for(int i = 0; i < bodies.size; i++) {
			Body b = bodies.get(i);
			crystals.removeValue((Coins) b.getUserData(), true);
			world.destroyBody(b);

		}
		bodies.clear();
		
		player.update(dt);


        /**
         * Проверка на победу
         */
        if(player.getBody().getPosition().x * PPM > tileMapWidth * tileSize) {

            //счет и сохранение результата результата
            levelScore = (int)(player.getBody().getPosition().x * PPM * 4.5);
            setScore(levelScore);
            Score score = getScore();
            Collections.reverse(score.getScore());
            GameOver.setScorelist(score.getScore());

            gsm.setState(GameStateManager.YOU_WIN);
            }

        /**
         * Проверка на "Game over"
         */
        if(player.getBody().getPosition().y < 0) {

            levelScore = (int)(player.getBody().getPosition().x * PPM * 2.3);
            setScore(levelScore);
            Score score = getScore();
            Collections.reverse(score.getScore());
            GameOver.setScorelist(score.getScore());

            gsm.setState(GameStateManager.GAME_OVER);
        }
        /**
         * Проверка на "Game over"
         */
        if(player.getBody().getLinearVelocity().x < 0.001f) {

            levelScore = (int)(player.getBody().getPosition().x * PPM * 4.5);
            setScore(levelScore);
            Score score = getScore();
            Collections.reverse(score.getScore());
            GameOver.setScorelist(score.getScore());

            gsm.setState(GameStateManager.GAME_OVER);
        }

	}

    /**
     * Отрисовка объктов мира
     */
	public void render() {

        /**
         * Очистка экрана
         */
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /**
         * Настройка камеры для следования за игроков
         */
		cam.position.set(
			player.getPosition().x * PPM + Game.V_WIDTH / 4,
			Game.V_HEIGHT / 2,
			0
		);
		cam.update();


		tmr.setView(cam);
        /**
         *Отрисовка карты
         */
		tmr.render();


		sb.setProjectionMatrix(cam.combined);
        /**
         *Отрисовка игрока
         */
		player.render(sb);
		/*
		for(int i = 0; i < crystals.size; i++) {
			crystals.get(i).render(sb);
		}
		*/
		sb.setProjectionMatrix(hudCam.combined);
		hud.render(sb);

		if(debug) {
			b2dr.render(world, b2dCam.combined);
		}
		
	}
	
	public void dispose() {}

    /**
     * Создание игрока
     */
	private void createPlayer() {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		// создать игрока
		bdef.position.set(100 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		bdef.linearVelocity.set(1f, 0);
		Body body = world.createBody(bdef);
		
		shape.setAsBox(13 / PPM, 13 / PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = Variables.PLAYER_BIT;
		fdef.filter.maskBits = Variables.BIT_WOOD | Variables.COINS_BIT;
		body.createFixture(fdef).setUserData("player");

		// создать сенсоры
		shape.setAsBox(13 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
		fdef.shape = shape;
		fdef.filter.categoryBits = Variables.PLAYER_BIT;
		fdef.filter.maskBits = Variables.BIT_WOOD;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("foot");

		player = new Player(body);
		
		body.setUserData(player);
		
	}


    /**
     * Загрузка и создание игровой карты с внешнего файла
     */

    private void createWalls() {

        // load tile map and map renderer
        try {
            tileMap = new TmxMapLoader().load("res/maps/level" + level + ".tmx");
            tmr = new OrthogonalTiledMapRenderer(tileMap);
        }
        catch(Exception e) {
            System.out.println("Cannot find file: res/maps/level" + level + ".tmx");
            Gdx.app.exit();
        }
        tileMapWidth = (int) tileMap.getProperties().get("width", Integer.class);
        tileMapHeight = (int) tileMap.getProperties().get("height",Integer.class);
        tileSize = (int) tileMap.getProperties().get("tilewidth",Integer.class);
        tmRenderer = new OrthogonalTiledMapRenderer(tileMap);

        // Чтение каждого из слоев карты
        TiledMapTileLayer layer;
        layer = (TiledMapTileLayer) tileMap.getLayers().get("wood");
        createBlocks(layer, Variables.BIT_WOOD);
        layer = (TiledMapTileLayer) tileMap.getLayers().get("steel");
        createBlocks(layer, Variables.BIT_STEEL);
        layer = (TiledMapTileLayer) tileMap.getLayers().get("glass");
        createBlocks(layer, Variables.BIT_GLASS);

    }



    private void createBlocks(TiledMapTileLayer layer, short bits) {

        // размер блока
        float ts = layer.getTileWidth();

        // Прохождение по всем слоям карты
        for(int row = 0; row < layer.getHeight(); row++) {
            for(int col = 0; col < layer.getWidth(); col++) {

                // клетка карты
                Cell cell = layer.getCell(col, row);

                // проверка клетки на пустоту
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                // создание тела из клетки
                BodyDef bdef = new BodyDef();
                bdef.type = BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-ts / 2 / PPM, -ts / 2 / PPM);
                v[1] = new Vector2(-ts / 2 / PPM, ts / 2 / PPM);
                v[2] = new Vector2(ts / 2 / PPM, ts / 2 / PPM);
                cs.createChain(v);
                FixtureDef fd = new FixtureDef();
                fd.friction = 0;
                fd.shape = cs;
                fd.filter.categoryBits = bits;
                fd.filter.maskBits = Variables.PLAYER_BIT;
                world.createBody(bdef).createFixture(fd);
                cs.dispose();

            }
        }

    }

    /**
     * Загрузка указаной карты(нужно для тестов)
     */
	private void createTiles() {
		
		// загрузка карты
		tileMap = new TmxMapLoader().load("res/maps/test.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		tileSize = (int) tileMap.getProperties().get("tilewidth", Integer.class);
		
		TiledMapTileLayer layer;
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("wood");
		createLayer(layer, Variables.BIT_WOOD);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("steel");
		createLayer(layer, Variables.BIT_STEEL);
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("glass");
		createLayer(layer, Variables.BIT_GLASS);
		
	}

    /**
     * Создание объктов карты в игре
     * @param layer Слой карты
     * @param bits Бит поверхности
     */
	private void createLayer(TiledMapTileLayer layer, short bits) {
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		

		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				

				Cell cell = layer.getCell(col, row);
				

				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				// создание тела
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
					(col + 0.5f) * tileSize / PPM,
					(row + 0.5f) * tileSize / PPM
				);
				
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(
					-tileSize / 2 / PPM, -tileSize / 2 / PPM);
				v[1] = new Vector2(
					-tileSize / 2 / PPM, tileSize / 2 / PPM);
				v[2] = new Vector2(
					tileSize / 2 / PPM, tileSize / 2 / PPM);
				cs.createChain(v);
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = bits;
				fdef.filter.maskBits = Variables.PLAYER_BIT;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);
				
				
			}
		}
	}

    /**
     * Создание монет
     */
	private void createCoins() {
		
		crystals = new Array<Coins>();
		
		MapLayer layer = tileMap.getLayers().get("crystals");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		for(MapObject mo : layer.getObjects()) {
			
			bdef.type = BodyType.StaticBody;
			
			float x = (float) mo.getProperties().get("x", Float.class) / PPM;
			float y = (float) mo.getProperties().get("y", Float.class) / PPM;
			
			bdef.position.set(x, y);
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8 / PPM);
			
			fdef.shape = cshape;
			fdef.isSensor = true;
			fdef.filter.categoryBits = Variables.COINS_BIT;
			fdef.filter.maskBits = Variables.PLAYER_BIT;
			
			Body body = world.createBody(bdef);
			body.createFixture(fdef).setUserData("crystal");
			
			Coins c = new Coins(body);
			crystals.add(c);
			
			body.setUserData(c);
			
		}
		
	}

    /**
     * Переключние типа поверхности
     */
	private void switchBlocks() {
		
		Filter filter = player.getBody().getFixtureList().first()
						.getFilterData();
		short bits = filter.maskBits;
		
		// переключение на следующий тип поверхности
		if((bits & Variables.BIT_WOOD) != 0) {
			bits &= ~Variables.BIT_WOOD;
			bits |= Variables.BIT_STEEL;
		}
		else if((bits & Variables.BIT_STEEL) != 0) {
			bits &= ~Variables.BIT_STEEL;
			bits |= Variables.BIT_GLASS;
		}
		else if((bits & Variables.BIT_GLASS) != 0) {
			bits &= ~Variables.BIT_GLASS;
			bits |= Variables.BIT_WOOD;
		}
		

		filter.maskBits = bits;
        /**
         * установка типа поверхности, по которой может ходить игрок
         */
		player.getBody().getFixtureList().first().setFilterData(filter);
		

		filter = player.getBody().getFixtureList().get(1).getFilterData();
		bits &= ~Variables.COINS_BIT;
		filter.maskBits = bits;
        /**
         * установка типа поверхности для сенсоров(ног игрока)
         */
		player.getBody().getFixtureList().get(1).setFilterData(filter);
		
	}

    /**
     * Добавление и сортировка результатов набраных очков
     * @param levelScore Последний результат игры
     */
    private void setScore(int levelScore){
        Json json = new Json();
        FileHandle file = Gdx.files.internal("res/score.txt");
        Score score = json.fromJson(Score.class, file.readString());
        score.addScore(levelScore);
        Collections.sort(score.getScore());
        try {
            FileWriter wft = new FileWriter(file.file());
            wft.write(json.toJson(score));
            wft.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Массив результатов
     */
    public static Score getScore(){
        Json json = new Json();
        FileHandle file = Gdx.files.internal("res/score.txt");
        Score score = json.fromJson(Score.class, file.readString());
        if(score==null)
            score = new Score();

        return score;
    }
	
}









