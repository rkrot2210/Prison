package rk.win;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {

	final rk.win.Drop game;
	OrthographicCamera camera;
	Texture dropImage;
	Texture bucketImage;
	Sound dropSound;
	Music rainMusic;
	Rectangle bucket;
	Vector3 touchPos;
	Array<Rectangle> raindrops;
	long lastDropTime;
    boolean fail =  false;
    boolean startNumberMessage = false;

	SpriteBatch batch;
	BitmapFont font;


	Sound failSound;
	Music musicPlay;

	String languageDevice;

	Texture fonImage;
	Texture countImage;

	Texture object1Image;
	Texture object2Image;
	Texture object3Image;

	Texture buttonPlay;
	Texture buttonResult;

	ArrayList<rk.win.ObjectDrop> drops;
	Array<Texture> fruits;
	Rectangle raindrop;
	Texture random;
	I18NBundle MyBundle;
	Integer wightScreen;
	Integer heightScreen;
	Integer sizeDrop;
	Integer numberObject = 0;


	Integer speed = 400;
	Array<Texture> textureDrop;
	Iterator<Rectangle> iter;
	Iterator<rk.win.ObjectDrop> iterTexture;
	rk.win.ObjectDrop randDrop;
	Integer countObjective = 0;


	public GameScreen(final Drop gam) {
		this.game = gam;
game.numberObjective = 0;
		camera = new OrthographicCamera();
countObjective=0;
		wightScreen = Gdx.graphics.getWidth();
		heightScreen = Gdx.graphics.getHeight();
		sizeDrop = wightScreen / 8;

		languageDevice = java.util.Locale.getDefault().toString();
		camera.setToOrtho(false, wightScreen, heightScreen);
		font = new BitmapFont();
		font.setColor(Color.RED);
	    font.getData().setScale(2);


		batch = new SpriteBatch();
		touchPos = new Vector3();
        speed = 400;
		fonImage = new Texture("bg.png");
		bucketImage = new Texture("character.png");

		object1Image = new Texture("1.png");
		object2Image = new Texture("2.png");
		object3Image = new Texture("3.png");
		countImage = new Texture("countImage.png");
		drops = new ArrayList<rk.win.ObjectDrop>();
		fruits = new Array<Texture>();
		fruits.add(object1Image);
		fruits.add(object2Image);
		fruits.add(object3Image);

		dropSound = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
		failSound = Gdx.audio.newSound(Gdx.files.internal("failSound.mp3"));
		musicPlay = Gdx.audio.newMusic(Gdx.files.internal("musicPlay.mp3"));
	    musicPlay.setLooping(true);
		musicPlay.play();

		bucket = new Rectangle();
		bucket.x = wightScreen/ 2 - sizeDrop/2;
		bucket.y = 30;
		bucket.width = sizeDrop;
		bucket.height =sizeDrop;



	//	raindrop = new Rectangle();

		buttonPlay = new Texture("button.png");
		buttonResult = new Texture("button.png");
		raindrops = new Array<Rectangle>();
		textureDrop = new Array<Texture>();
		//spawnRaindrop();
	}


	private void spawnRaindrop() {
		raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, wightScreen - sizeDrop);
		raindrop.y = heightScreen;
		raindrop.width = sizeDrop;
		raindrop.height = sizeDrop;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();

	}


	rk.win.ObjectDrop createRandomDrop(Texture random, Rectangle rect) {

		switch (MathUtils.random(0, 3)) {
			case 0:
				return new ObjectDropCase(object1Image,rect);

			case 1:
				return new ObjectDropRey(object3Image, rect);

			default:
				return new ObjectDropKill(object2Image, rect);
		}

	}



		@Override
		public void render (float delta) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			camera.update();
			if(countObjective>3 && countObjective<150)
			{
				speed = 400 + countObjective*5;
			}


			if ( fail == false) {
				game.batch.setProjectionMatrix(camera.combined);
				game.batch.begin();
				game.batch.draw(fonImage, 0, 0, wightScreen, heightScreen);
				game.batch.draw(countImage, wightScreen - 2 * sizeDrop, heightScreen - sizeDrop - sizeDrop / 2, 2 * sizeDrop, 2 * sizeDrop);
				game.font.setColor(Color.WHITE);
				game.font.getData().setScale(3);
				game.batch.draw(bucketImage, bucket.x, bucket.y,sizeDrop,sizeDrop);
				game.font.draw(game.batch, game.numberObjective.toString(), wightScreen - 7 / 4 * sizeDrop, heightScreen - sizeDrop / 2);

				//game.batch.draw(bucketImage, bucket.x, bucket.y);

				for (ObjectDrop drop : drops) {

					drop.rectangle.y -= speed * Gdx.graphics.getDeltaTime();
					game.batch.draw(drop.texture, drop.rectangle.x, drop.rectangle.y, sizeDrop, sizeDrop);
					drawNumberDialog(startNumberMessage);
				}
				game.batch.end();

				//heightScreen - raindrop.y >heightScreen/6
				if (TimeUtils.nanoTime() - lastDropTime > 1000000000 || heightScreen -
						randDrop.rectangle.y >heightScreen/6) {
					random = fruits.random();
				countObjective++;
					spawnRaindrop();
					drops.add(createRandomDrop(random, raindrop));

				}

				iterTexture = drops.iterator();
				iter = raindrops.iterator();

				if (Gdx.input.isTouched()) {
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					camera.unproject(touchPos);
					bucket.x = (int) (touchPos.x - sizeDrop/2);
				}
				if (bucket.x < 0) bucket.x = 0;
				if (bucket.x > heightScreen - sizeDrop) bucket.x = heightScreen - sizeDrop;


				while (iterTexture.hasNext()) {
					raindrop = iter.next();
					randDrop = iterTexture.next();
					if (raindrop.y + sizeDrop < 0 && randDrop.texture.equals(object2Image) ) {

						iter.remove();
						iterTexture.remove();
					}
					else if (raindrop.y + sizeDrop < 0 ){
						musicPlay.pause();
						failSound.play();

						fail = true;

					}

					if (raindrop.overlaps(bucket) && randDrop.texture.equals(object2Image)) {
						musicPlay.pause();
						failSound.play();

						fail = true;


					} else if (raindrop.overlaps(bucket)) {
						dropSound.play();
                        startNumberMessage = true;
						iter.remove();
						iterTexture.remove();
					   game.numberObjective+=2;
					}
				}
			}
			else
			{
				game.setScreen(new MainMenuScreen(game));

			}



		}
	public  void drawNumberDialog(boolean start)
	{
		if(start){
			for(float i = 0;i<=0.35f;i+=Gdx.graphics.getDeltaTime()) {
				game.font.draw(game.batch, "+2",
						bucket.x+bucket.width/2, bucket.height+sizeDrop/2);
				if(i>0.3f)
				{
					startNumberMessage = false;
					break;
				}
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
	public void show() {
//rainMusic.play();
	}
	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}



}

