/*
* Copyright (c) <2013> <Matthias Stangl>
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	private SimpleFont font[];
    private int height, width, index, len;
    private String[] text;
    private int stateID;
    
    public MainMenuState(int ID) {
    	this.stateID = ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	index = 0;
    	text = new String[] {"Spiel starten", "Multiplayer", "Highscores", "Steuerung", "Exit"};
        len = text.length;
        font = new SimpleFont[len];

        for(int i = 0; i < len; i++){
        	font[i] = new SimpleFont("Arial", Font.PLAIN, 20);
        }
        font[0].setColor(Color.red);

        height = gc.getHeight();
        width = gc.getWidth();
    }
        
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
    	int tmpWidth, tmpHeight;
        int start = 20;

        for(int i = 0; i < len; i++){
        	tmpWidth = font[i].get().getWidth(text[i]);
        	tmpHeight = font[i].get().getHeight(text[i]);
        	font[i].get().drawString(width/2 - tmpWidth/2, start, text[i]);
        	start += tmpHeight + 20;
        }
    }
    
    //FIXME: when pressing up/down the menu items disappear
    @Override
    public void update (GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
        
        if(input.isKeyPressed(Input.KEY_UP)){
        	if(index > 0){
        		font[index].setColor();
                index--;
                font[index].setColor(Color.red);
        	}
        }else if(input.isKeyPressed(Input.KEY_DOWN)){
        	if(index < (len -1)){
        		font[index].setColor();
                index++;
                font[index].setColor(Color.red);
        	}
        }else if(input.isKeyPressed(Input.KEY_ENTER)){
        	//TODO: Implement the menu item handling
        }
    }

	@Override
	public int getID() {
		return stateID;
	}
}