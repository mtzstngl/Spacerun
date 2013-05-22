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
import java.util.ArrayDeque;
import java.util.Deque;

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
	private int width, height;
	private SimpleFont font;
	private Rectangle playerRec;
	private float playerPos, playerSpeed;
	
	private long startTime;
	private long score;
	
	private int sectorWidth;
	private Deque<ObstacleRow> obstacles;
	private float obstacleSpeed, obstacleGap;
	
	public GameState(int ID){
		this.stateID = ID;
	}
//TODO: provide a way to input your name
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = new SimpleFont("Arial", Font.PLAIN, 20);
		score = 0;
		playerSpeed = 0.55f;
		width = gc.getWidth();
		height = gc.getHeight();
		playerRec = new Rectangle(width/2 - 10, height - 50, 20, 50);//TODO: make this relative
		playerPos = width/2 - 10;
		
		startTime = System.currentTimeMillis(); //TODO: Maybe change score system
		
		obstacleSpeed = 0.01f;
		obstacleGap = height / 4;
		sectorWidth = width / 10;//TODO: make this relative
		obstacles = new ArrayDeque<ObstacleRow>();
		obstacles.add(new ObstacleRow(sectorWidth, 10, -40));//TODO: make this relative
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//Highscore rendering
		int tmpWidth, yOffset;
		
		score = (System.currentTimeMillis() - startTime) / 100;
		
		tmpWidth = font.get().getWidth(Long.toString(score));
		yOffset = font.get().getYOffset(Long.toString(score));
		font.get().drawString(width - tmpWidth - yOffset, 0, Long.toString(score));
		
		//Game rendering
		g.setColor(Color.cyan);
		g.fillRect(playerRec.getX(), playerRec.getY(), playerRec.getWidth(), playerRec.getHeight());
		g.draw(playerRec);
		
		if(!obstacles.isEmpty()){
			for(ObstacleRow i : obstacles){
				i.draw(g, Color.red);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_LEFT)){
			if(playerRec.getX() > 0){
				playerPos -= playerSpeed * delta; //delta is to make sure we travel the same way each render/frame
				playerRec.setX(playerPos);
			}
		}else if(input.isKeyDown(Input.KEY_RIGHT)){
			if((playerRec.getX() + playerRec.getWidth()) < width){
				playerPos += playerSpeed * delta;
				playerRec.setX(playerPos);
			}
		}else if(input.isKeyDown(Input.KEY_ESCAPE)){
			sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
			sbg.enterState(Spacerun.MAINMENUSTATE);
		}
		
		if(!obstacles.isEmpty()){
			if((obstacles.getLast().getPosition() - obstacleGap) >= 0){
				obstacles.add(new ObstacleRow(sectorWidth, 10, -40));
			}
			for(ObstacleRow i : obstacles){
				i.setPosition(obstacleSpeed * delta);
				if(i.intersects(playerRec)){
					System.out.println("you got hit");
				}
				if(i.getPosition() >= height){
					obstacles.remove(i);
				}
			}
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
