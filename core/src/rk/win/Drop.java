package rk.win;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Vitaly on 13.10.2015.
 */
public class Drop extends Game {

	SpriteBatch batch;
	BitmapFont font;
	Integer numberObjective ;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		numberObjective = 0;
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();}}