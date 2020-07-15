package rk.win;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class MainMenuScreen implements Screen {
	public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
			+ "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
			+ "1234567890.,:;_¡!¿?'+-*/()[]={}";
	final rk.win.Drop game;
	OrthographicCamera camera;


	Vector3 touchPos;
	Locale ruLocale;
	SpriteBatch batch;
	BitmapFont font;
	FileHandle baseFileHandle;
	Texture fonImage;
	Texture buttonPlay;
	Texture buttonResult;
rk.win.FontFactory fontFactory;
	Integer wightScreen;
	Integer heightScreen;
	Integer sizeDrop;
	I18NBundle myBundle;

	public MainMenuScreen(final Drop gam) {
		game = gam;
		touchPos = new Vector3();
		rk.win.FontFactory.getInstance().initialize();
		baseFileHandle = Gdx.files.internal("i18n/local");
		ruLocale = java.util.Locale.getDefault();
		myBundle = I18NBundle.createBundle(baseFileHandle, ruLocale);

		wightScreen = Gdx.graphics.getWidth();
		heightScreen = Gdx.graphics.getHeight();
		sizeDrop = wightScreen / 8;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, wightScreen, heightScreen);
		font = rk.win.FontFactory.getInstance().getFont(ruLocale);

		fonImage = new Texture("bg.png");
		buttonPlay = new Texture("button.png");
		buttonResult = new Texture("button.png");

		}




	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.begin();
		game.batch.setProjectionMatrix(camera.combined);
		//
		game.font = FontFactory.getInstance().getFont(ruLocale);
//		game.font.setColor(Color.RED);
	//	game.font.getData().setScale(3);

		game.batch.draw(fonImage,0,0,wightScreen,heightScreen);
		game.batch.draw(buttonPlay,sizeDrop,heightScreen/2,6*sizeDrop,sizeDrop);
		game.batch.draw(buttonResult,sizeDrop,heightScreen/2-sizeDrop,6*sizeDrop,sizeDrop);
		game.font.draw(game.batch,
				myBundle.get("playText"), 7 * sizeDrop / 2, heightScreen / 2+ sizeDrop / 2 + sizeDrop / 6);
		game.font.draw(game.batch,
				myBundle.get("resultText")+":"+game.numberObjective, 7 * sizeDrop / 2, heightScreen / 2 - sizeDrop / 3);
		//game.font.draw(game.batch, "Play", 7 * sizeDrop / 2, heightScreen / 2 + sizeDrop / 2 + sizeDrop / 6);
	//	game.font.draw(game.batch,   "Score: " + game.numberObjective, 7 * sizeDrop / 2, heightScreen / 2 - sizeDrop / 3);
		game.batch.end();


		if (Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if(	touchPos.x >sizeDrop && 	touchPos.x<(wightScreen - sizeDrop) &&
					touchPos.y <(heightScreen/2+sizeDrop) && touchPos.y >(heightScreen/2)) {
				game.setScreen(new GameScreen(game));
				//dispose();
			}
		}

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}