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
package de.spacerun.game;

import java.util.ArrayDeque;
import java.util.Deque;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.spacerun.highscore.FileHandler;
import de.spacerun.main.Data;
import de.spacerun.main.Spacerun;
import de.spacerun.mainmenu.SimpleFont;

//TODO: replace player and obstacles with images
public class GameState extends BasicGameState {
	private int stateID;
	private int width, height;
	private SimpleFont font;
	
	private SimpleFont multiplayerFont;
	private String[] multiplayerMenu;
	private Data data;
	private boolean multiplayer;

	private Rectangle playerOne;
	private Rectangle playerTwo;
	private float playerSpeed;
	
	private boolean notHit;
	private String gameoverMessage;
	private long startTime;
	private long score;
	private long speedStep;
	
	private int sectorWidth;
	private Deque<ObstacleRow> obstacles;
	private float obstacleSpeed, obstacleGap;
	
	private TextField nameField;
	private boolean selection, enteredName;
	private String[] menu;
	private int[] menuX;
	private int[] menuY;

	private StarBackground stars;

	public GameState(int ID, Data d){
		this.stateID = ID;
		data = d;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		font = new SimpleFont("data/space age.ttf", gc.getHeight()/54);
		width = gc.getWidth();
		height = gc.getHeight();
		
    multiplayerFont = new SimpleFont("data/space age.ttf", gc.getHeight()/25);
		multiplayerMenu = new String[] {"Spieler 1 hat gewonnen!", "Spieler 2 hat gewonnen!", "Unentschieden!"};
		multiplayer = data.getData();

		playerSpeed = (float) width/3491; //0.55f @ 1080p
		int playerHeight = gc.getHeight()/22;
		int playerWidth = gc.getWidth()/96;
		playerOne = new Rectangle(width/2 + playerWidth/2, height - playerHeight, playerWidth, playerHeight);
		playerTwo = new Rectangle(width/2 - playerWidth, height - playerHeight, playerWidth, playerHeight);
		
		notHit = true;
		speedStep = 100;
		score = 0;
		startTime = System.currentTimeMillis(); //The Highscore is time based
		
		obstacleSpeed = (float) height/10800; //0.01f @ 1080p
		obstacleGap = height / 4;
		sectorWidth = width / 10;
		obstacles = new ArrayDeque<ObstacleRow>();
		obstacles.add(new ObstacleRow(sectorWidth, 10, -40));
		
		int fieldWidth = gc.getWidth() / 6;
		int fieldHeight = gc.getHeight() / 36;
		nameField = new TextField(gc, font.get(), width/2 - fieldWidth/2, 
		    height/2 - fieldHeight/2, fieldWidth, fieldHeight);
		nameField.setFocus(true);
		selection = false; //true is right; false is left
		enteredName = false;
		
    menu = new String[] {"Nochmal", "Abbrechen"};
    menuX = new int[2];
    menuY = new int[2];
    menuX[0] = nameField.getX() - font.get().getWidth(menu[0]) - 10;
    menuX[1] = nameField.getX() + nameField.getWidth() + 10;
    menuY[0] = nameField.getY() + font.get().getHeight(menu[0]) + 10;
    menuY[1] = nameField.getY() + font.get().getHeight(menu[1]) + 10;

    stars = new StarBackground(gc.getWidth(), gc.getHeight(), (int)(gc.getHeight() * gc.getWidth() / 4147));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//Background rendering
		stars.render();
		
		//Game rendering
		//playerOne
		g.setColor(Color.gray);
		g.fillRect(playerOne.getX(), playerOne.getY(), playerOne.getWidth(), playerOne.getHeight());
		g.draw(playerOne);
    
    if(multiplayer){
      //playerTwo
		  g.setColor(Color.blue);
		  g.fillRect(playerTwo.getX(), playerTwo.getY(), playerTwo.getWidth(), playerTwo.getHeight());
		  g.draw(playerTwo);
    }

		if(!obstacles.isEmpty()){
			for(ObstacleRow i : obstacles){
				i.draw(g, Color.green);
			}
		}
		
		//Highscore rendering
		int tmpWidth = font.get().getWidth(Long.toString(score));
		int tmpHeight = font.get().getYOffset(Long.toString(score));
		font.get().drawString(width - tmpWidth - tmpHeight, 0, Long.toString(score));
		
		//TextField rendering
		if((!notHit) && (!multiplayer)){
			g.setColor(Color.red); //Otherwise it would be green 
			g.drawRect(nameField.getX(), nameField.getY(), nameField.getWidth(), nameField.getHeight());
			    //Otherwise it would only show half of the frame
			nameField.render(gc, g);
			
			if(selection){
			  font.get().drawString(menuX[0], menuY[0], menu[0], Color.white);
			  font.get().drawString(menuX[1], menuY[1], menu[1], Color.red);
			}else{
			  font.get().drawString(menuX[0], menuY[0], menu[0], Color.red);
			  font.get().drawString(menuX[1], menuY[1], menu[1], Color.white);
			}
		}

    //calculations cannot be moved to init because the length of gameoverMessage varies
		if((!notHit) && multiplayer){
      int multiWidth, multiHeight, multiX, multiY, multiMenuWidth;

      multiMenuWidth = font.get().getWidth(menu[0]);
      multiWidth = multiplayerFont.get().getWidth(gameoverMessage);
      multiHeight = multiplayerFont.get().getHeight(gameoverMessage);
      multiX = width/2 - multiWidth/2;
      multiY = height/2 - multiHeight/2;
      multiplayerFont.get().drawString(multiX, multiY, gameoverMessage, Color.white);

			if(selection){
			  font.get().drawString(multiX - multiMenuWidth, multiY + multiHeight, menu[0], Color.white);
			  font.get().drawString(multiX + multiWidth, multiY + multiHeight, menu[1], Color.red);
			}else{
			  font.get().drawString(multiX - multiMenuWidth, multiY + multiHeight, menu[0], Color.red);
			  font.get().drawString(multiX + multiWidth, multiY + multiHeight, menu[1], Color.white);
			}
    }
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		//exiting gamestate
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
		  data.setData(false);
			sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
			sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(notHit){
			//move ALL the players
			playerMovement(input, delta);
			
			//move ALL the stars
			stars.adjustPosition(0, obstacleSpeed * delta * 2);
		
			//generating, moving, hitting obstacles
			if(!obstacles.isEmpty()){
				if((obstacles.getLast().getPosition() - obstacleGap) >= 0){
					obstacles.add(new ObstacleRow(sectorWidth, 10, -40));
				}
				for(ObstacleRow i : obstacles){
					i.setPosition(obstacleSpeed * delta);

					//check if one of the players is hit
					if(i.intersects(playerOne)){
						notHit = false;
						gameoverMessage = multiplayerMenu[1];
					}
          if(i.intersects(playerTwo) && multiplayer){
						notHit = false;
						if(gameoverMessage == multiplayerMenu[1]){
						  gameoverMessage = multiplayerMenu[2];
						}else{
              gameoverMessage = multiplayerMenu[0];
						}
					}

					//remove unused obstacles
					if(i.getPosition() >= height){
						obstacles.remove(i);
					}
				}
			}else{
				obstacles.add(new ObstacleRow(sectorWidth, 10, -40));
			}
		
			//highscore handling
			score = (System.currentTimeMillis() - startTime) / 100;
			if(score >= speedStep){
				speedStep += 100;
				obstacleSpeed += ((float) height/54000);//0.002f @ 1080p
			}
		}else{
		  if(multiplayer){
		    gameoverInput(gc, sbg, input);
      }else{
        nameInput(gc, sbg, input);
      }
    }
	}

  private void gameoverInput(GameContainer gc, StateBasedGame sbg, Input input) throws SlickException {
    if(input.isKeyPressed(Input.KEY_RIGHT)){
	    if(!selection){
        selection = true;
      }
	  }else if(input.isKeyPressed(Input.KEY_LEFT)){
	  	if(selection){
    	  selection = false;
    	}
	  }else if(input.isKeyPressed(Input.KEY_ENTER)){
    	if(selection){
    	  data.setData(false);
    	  sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
    	  sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
	    }else{
    		sbg.getState(Spacerun.GAMESTATE).init(gc, sbg);
    		sbg.enterState(Spacerun.GAMESTATE, new FadeOutTransition(), new FadeInTransition());
    	}
    }
	}

	private void playerMovement(Input input, int delta){
		//moving playerOne
		if(input.isKeyDown(Input.KEY_LEFT)){
			if(playerOne.getX() > 0){ //delta is to make sure we travel the same way each render/frame
			  playerOne.setX(playerOne.getX() - playerSpeed * delta);
				stars.adjustPosition(playerSpeed * delta, 0);
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT)){
		  if((playerOne.getX() + playerOne.getWidth()) < width){
				playerOne.setX(playerOne.getX() + playerSpeed * delta);
			  stars.adjustPosition(-playerSpeed * delta, 0);
			}
		}
      
    if(multiplayer){
		  //moving playerTwo
			if(input.isKeyDown(Input.KEY_A)){
				if(playerTwo.getX() > 0){ //delta is to make sure we travel the same way each render/frame
					playerTwo.setX(playerTwo.getX() - playerSpeed * delta);
					stars.adjustPosition(playerSpeed * delta, 0);
				}
			}
			if(input.isKeyDown(Input.KEY_D)){
				if((playerTwo.getX() + playerTwo.getWidth()) < width){
				  playerTwo.setX(playerTwo.getX() + playerSpeed * delta);
				  stars.adjustPosition(-playerSpeed * delta, 0);
				}
			}
    }
	}

  private void nameInput(GameContainer gc, StateBasedGame sbg, Input input) throws SlickException{
		if(enteredName){
			if(input.isKeyPressed(Input.KEY_RIGHT)){
	      if(!selection){
	        selection = true;
	      }
	    }else if(input.isKeyPressed(Input.KEY_LEFT)){
		  	if(selection){
	    	  selection = false;
	    	}
	    }else if(input.isKeyPressed(Input.KEY_ENTER)){
      	if(selection){
      	  sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
	    		sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
	    	}else{
      		sbg.getState(Spacerun.GAMESTATE).init(gc, sbg);
      		sbg.enterState(Spacerun.GAMESTATE, new FadeOutTransition(), new FadeInTransition());
	    	}
	    }
	  }else{
			if(input.isKeyPressed(Input.KEY_ENTER)){
				new FileHandler().writeHighscore(nameField.getText(), score);
				nameField.setFocus(false);
				nameField.setAcceptingInput(false);
				enteredName = true;
			}
		}
	}

	@Override
	public int getID() {
		return stateID;
	}
}
