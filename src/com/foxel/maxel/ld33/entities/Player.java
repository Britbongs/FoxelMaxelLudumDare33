package com.foxel.maxel.ld33.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxel.maxel.ld33.constants.Constants;
import com.foxel.maxel.ld33.map.Map;

public class Player extends Entity {

	private final float MOVE_SPEED;
	private Image image;

	public Player(Map map) {
		super(map);
		this.MOVE_SPEED = Constants.MOVE_SPEED;

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		image = new Image(Constants.TEMP_PLAYER_LOC);
		x = map.getPlayerStart().x / TILESIZE;
		y = map.getPlayerStart().y / TILESIZE;
		
		System.out.println(x);
		collider = new Rectangle( (x * TILESIZE) + TILESIZE/2, (y * TILESIZE) + TILESIZE/2, image.getWidth(), image.getHeight());

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(image, x * TILESIZE, y * TILESIZE);
		g.draw(collider);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		Input input = gc.getInput();
		Vector2f move = new Vector2f();

		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			move.x = -MOVE_SPEED;
		}

		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			move.x = MOVE_SPEED;
		}

		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			move.y = -MOVE_SPEED;
		}

		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			move.y = MOVE_SPEED;
		}

		moveEntity(move, delta);
	}

	@Override
	protected void moveEntity(Vector2f move, int delta) {

		move = move.normalise();

		move.x *= (delta / 100.f) * MOVE_SPEED;
		move.y *= (delta / 100.f) * MOVE_SPEED;

		collider.setLocation((x + move.x) * TILESIZE, (y + move.y) * TILESIZE);

		if (map.isTileFree(collider)) {
			x += move.x;
			y += move.y;
			//collider.setLocation(x * TILESIZE, y * TILESIZE);
		} else {
			x -= move.x;
			y -= move.y;
		}
	}

	@Override
	public Vector2f getEntityDimensions() {

		return new Vector2f(image.getWidth(), image.getHeight());
	}

}
