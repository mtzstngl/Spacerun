/*
* Copyright (c) <2013> <Matthias Stangl> <Marco Britzl>
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
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
	
	@SuppressWarnings("unchecked") //nothing we can do about this warning because it is a problem of Slick2D
	public SimpleFont(String name, int size, Color color){
		try {
			ufont = new UnicodeFont(name, size, false, false);
			ufont.getEffects().add(new ColorEffect(color));
			ufont.addAsciiGlyphs();
			ufont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public SimpleFont(String name, int size){
		this(name, size, Color.white);
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
