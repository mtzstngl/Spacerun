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
package de.spacerun.game;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.spacerun.main.Spacerun;
import de.spacerun.mainmenu.SimpleFont;

public class GameState extends BasicGameState {
	private int stateID;
	private int width, height, score;
	private SimpleFont font;
	private Rectangle playerRec;
	private float playerPos;
	
	public GameState(int ID){
		this.stateID = ID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = new SimpleFont("Arial", Font.PLAIN, 20);
		score = 0;
		width = gc.getWidth();
		height = gc.getHeight();
		playerRec = new Rectangle(width/2 - 10, height - 50, 20, 50);
		playerPos = width/2 - 10;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//Highscore rendering
		int tmpWidth, yOffset;
		
		tmpWidth = font.get().getWidth(Integer.toString(score));
		yOffset = font.get().getYOffset(Integer.toString(score));
		font.get().drawString(width - tmpWidth - yOffset, 0, Integer.toString(score));
		
		//Game rendering
		g.setColor(Color.cyan);
		g.fillRect(playerRec.getX(), playerRec.getY(), playerRec.getWidth(), playerRec.getHeight());
		g.draw(playerRec);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_LEFT)){
			if(playerRec.getX() > 0){
				playerPos -= 0.4f * delta; //delta is to make sure we travel the same way each render/time
				playerRec.setX(playerPos);
			}
		}else if(input.isKeyDown(Input.KEY_RIGHT)){
			if((playerRec.getX() + playerRec.getWidth()) < width){
				playerPos += 0.4f * delta;
				playerRec.setX(playerPos);
			}
		}else if(input.isKeyDown(Input.KEY_ESCAPE)){
			sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
			sbg.enterState(Spacerun.MAINMENUSTATE);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
