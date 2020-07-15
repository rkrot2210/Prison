package rk.win;

import java.util.Locale;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontFactory {

	// Russian cyrillic characters
	public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
	                                              + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
	                                              + "1234567890.,:;_¡!¿?'+-*/()[]={}";
	public static final String English_FONT_NAME = "fonts/goodfish rg.ttf";
	public static final String RUSSIAN_FONT_NAME = "fonts/ImperialWeb.ttf";

	// Singleton: unique instance
	private static FontFactory instance;
	private BitmapFont esFont;
	private BitmapFont ruFont;
	private BitmapFont uaFont;
	/** Private constructor for singleton pattern */
	private FontFactory() { super(); }
	
	public static synchronized FontFactory getInstance() {
		if (instance == null) {
			instance = new FontFactory();
		}
		return instance;
	}
	
	public void initialize() {
		// If fonts are already generated, dispose it!
		if (esFont != null) esFont.dispose();

		if (ruFont != null) ruFont.dispose();
		if (uaFont != null) uaFont.dispose();


		esFont = generateFont(RUSSIAN_FONT_NAME, FreeTypeFontGenerator.DEFAULT_CHARS);
		ruFont = generateFont(RUSSIAN_FONT_NAME, RUSSIAN_CHARACTERS);
		uaFont = generateFont(RUSSIAN_FONT_NAME, RUSSIAN_CHARACTERS);

	}
	

	private BitmapFont generateFont(String fontName, String characters) {
		
		// Configure font parameters
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.characters = characters;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
		BitmapFont font = generator.generateFont(parameter);
		font.setColor(Color.WHITE);
		font.getData().setScale(3);
		generator.dispose();
		return font;
	}
	public BitmapFont getFont(Locale locale) {
		if      ("ru".equals(locale.getLanguage())) return ruFont;
		else if("uk".equals(locale.getLanguage())) return uaFont;
		else {
			return esFont;
		}
		//else throw new IllegalArgumentException("Not supported language");
	}

	public void dispose() {
		esFont.dispose();
		ruFont.dispose();
	}
}
