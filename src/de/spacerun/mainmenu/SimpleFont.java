package de.spacerun.mainmenu;

import java.awt.Color;
import java.awt.Font;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class SimpleFont {
	private UnicodeFont ufont;
	
	@SuppressWarnings("unchecked") //nothing we can do about this warning because it is a problem of Slick2D
	public SimpleFont(Font font, Color color) throws SlickException {
		ufont = new UnicodeFont(font);
		ufont.getEffects().add(new ColorEffect(color));
		ufont.addAsciiGlyphs();
		ufont.loadGlyphs();
	}
	
	public SimpleFont(Font font) throws SlickException {
		this(font, Color.white);
	}
	
	public SimpleFont(String name, int style, int size, Color color) throws SlickException{
		this(new Font(name, style, size), color);
	}
	
	public SimpleFont(String name, int style, int size) throws SlickException{
		this(name, style, size, Color.white);
	}

	@SuppressWarnings("unchecked")
	public void setColor(Color color) throws SlickException{
		ufont.getEffects().clear();
		ufont.clearGlyphs();
		ufont.getEffects().add(new ColorEffect(color));
		ufont.addAsciiGlyphs();
		ufont.loadGlyphs();
	}
	
	public void setColor() throws SlickException{
		this.setColor(Color.white);
	}
	
	public UnicodeFont get(){
		return ufont;
	}
}
