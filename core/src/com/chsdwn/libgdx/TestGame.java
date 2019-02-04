package com.chsdwn.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class TestGame extends ApplicationAdapter implements InputProcessor {

	/* Variables */
	Random random = new Random();

	ShapeRenderer shapeRenderer;
	Snake snake;

	final int EMPTY_SQUARE = 0;
	final int WALL_SQUARE = 1;
	final int POINT_SQUARE = 2;
	final int SNAKE_SQUARE = 3;

	int rectLimit = 24;
	final int RECT_UNIT = 22;

	final int FIELD_WIDTH = 20;
	final int FIELD_HEIGHT = 20;

	final int POINT_LOGIC_COUNTER_LIMIT = 180;
	int pointLogicCounter = 0;

	boolean canDirectionChange = true;
	boolean runGame = false;

	int frameRate = 0;
	int[][] field = new int[FIELD_WIDTH][FIELD_HEIGHT];
	/* --------- */
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();

		Gdx.input.setInputProcessor(this);

		fieldLayout1();

		// Snake starts point x, y and 2d array
		snake = new Snake(3, 3, field);
		runGame = true;
	}

	@Override
	public void render () {
		if(runGame){
			pointLogicCounter++;
		}

		if(pointLogicCounter >= POINT_LOGIC_COUNTER_LIMIT){
			addPoint();
			pointLogicCounter = 0;
		}

		if(frameRate >= rectLimit){
			update();
			frameRate = 0;
		} else {
			frameRate++;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				if(field[i][j] == EMPTY_SQUARE){
					shapeRenderer.setColor(Color.LIGHT_GRAY);
					shapeRenderer.rect(i * RECT_UNIT, j * RECT_UNIT, RECT_UNIT - 1, RECT_UNIT - 1);
				}
				// Set walls
				else if (field[i][j] == WALL_SQUARE) {
					shapeRenderer.setColor(Color.DARK_GRAY	);
					shapeRenderer.rect(i * RECT_UNIT, j * RECT_UNIT, RECT_UNIT - 1, RECT_UNIT - 1);
				}
				// Snake
				else if(field[i][j] == SNAKE_SQUARE){
					if(runGame){
						shapeRenderer.setColor(Color.BLACK);
					} else{
						shapeRenderer.setColor(Color.RED);
					}
					shapeRenderer.rect(i * RECT_UNIT,
							j * RECT_UNIT,
							RECT_UNIT - 1,
							RECT_UNIT - 1);
				}
				// Points
				else if (field[i][j] == POINT_SQUARE) {
					shapeRenderer.setColor(Color.GREEN);
					shapeRenderer.rect(i * RECT_UNIT, j * RECT_UNIT, RECT_UNIT - 1, RECT_UNIT - 1);
				}
			}
		}

		shapeRenderer.end();
	}

	public void update(){
		if (runGame) {
			runGame = snake.move();
			canDirectionChange = true;
		} else{

		}
	}

	private boolean addPoint(){
		int x = random.nextInt(FIELD_WIDTH);
		int y = random.nextInt(FIELD_HEIGHT);

		if(field[x][y] == EMPTY_SQUARE){
			field[x][y] = POINT_SQUARE;
		}

		return false;
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

	// Create game field
	private void fieldLayout1(){
		// Organize the Field
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				if(i == 0 || i == 19){
					field[i][j] = WALL_SQUARE;
				} else if(j == 0 || j == 19){
					field[i][j] = WALL_SQUARE;
				} else{
					field[i][j] = EMPTY_SQUARE;
				}
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.UP){
			if (runGame && canDirectionChange) {
				snake.setMod(snake.MOVE_UP);
				canDirectionChange = false;
			}
			return true;
		} else if(keycode == Input.Keys.DOWN){
			if (runGame && canDirectionChange) {
				snake.setMod(snake.MOVE_DOWN);
				canDirectionChange = false;
			}
			return true;
		} else if(keycode == Input.Keys.LEFT){
			if (runGame && canDirectionChange) {
				snake.setMod(snake.MOVE_LEFT);
				canDirectionChange = false;
			}
			return true;
		} else if(keycode == Input.Keys.RIGHT){
			if (runGame && canDirectionChange) {
				snake.setMod(snake.MOVE_RIGHT);
				canDirectionChange = false;
			}
			return true;
		}
		// Add node
		else if(keycode == Input.Keys.E){
			if(runGame){
				snake.addNode();
			}
			return true;
		}
		// Reset
		else if(keycode == Input.Keys.R){
			fieldLayout1();
			snake = new Snake(3, 3, field);
			canDirectionChange = true;
			runGame = true;
			return true;
		}
		// Lower speed
		else if(keycode == Input.Keys.NUM_1){
			rectLimit += 10;
			System.out.println("" + rectLimit);
			return true;
		}
		// Increase speed
		else if(keycode == Input.Keys.NUM_2){
			if(rectLimit > 1)
				rectLimit -= 10;
			return true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
