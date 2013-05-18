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

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.spacerun.main.Spacerun;

public class MainMenuState extends BasicGameState {
	private SimpleFont font;
    private int width, index, len;
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
        font = new SimpleFont("Arial", Font.PLAIN, 20);

        width = gc.getWidth();
    }
        
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
    	float tmpWidth, tmpHeight;
        float start = 20;

        for(int i = 0; i < len; i++){
        	tmpWidth = font.get().getWidth(text[i]);
        	tmpHeight = font.get().getHeight(text[i]);
        	
        	if(i == index){
        		font.get().drawString(width/2 - tmpWidth/2, start, text[i], Color.red);
        	}else{
        		font.get().drawString(width/2 - tmpWidth/2, start, text[i]);
        	}
        	
        	start += tmpHeight + 20;
        }
    }
    
    @Override
    public void update (GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	
        if(input.isKeyPressed(Input.KEY_UP)){
        	if(index > 0){
                index--;
        	}
        }else if(input.isKeyPressed(Input.KEY_DOWN)){
        	if(index < (len -1)){
                index++;
        	}
        }else if(input.isKeyPressed(Input.KEY_ENTER)){
        	if(text[index] == "Spiel starten"){
        		sbg.getState(Spacerun.GAMESTATE).init(gc, sbg);
        		sbg.enterState(Spacerun.GAMESTATE);
        	}else if(text[index] == "Multiplayer"){
        		//sbg.getState(Spacerun.MULTIPLAYER).init(gc, sbg);
        		//sbg.enterState(Spacerun.MULTIPLAYER);
        	}else if(text[index] == "Highscores"){
        		//sbg.getState(Spacerun.HIGHSCORESTATE).init(gc, sbg);
        		//sbg.enterState(Spacerun.HIGHSCORESTATE);
        	}else if(text[index] == "Steuerung"){
        		//sbg.getState(Spacerun.CONTROL).init(gc, sbg);
        		//sbg.enterState(Spacerun.CONTROL);
        	}else if(text[index] == "Exit"){
        		gc.exit();
        	}
        }
    }

	@Override
	public int getID() {
		return stateID;
	}
}