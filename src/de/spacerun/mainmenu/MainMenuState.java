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

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.VerticalSplitTransition;

import de.spacerun.main.Data;
import de.spacerun.main.Spacerun;

public class MainMenuState extends BasicGameState {
  private SimpleFont menuFont, headerFont;
  private int width, index, headerSpace, menuSpace;
  private int headerWidth;
  private int[] menuWidth;
  private String[] menuText;
  private int stateID;
  private Image image;

  private Data<Boolean> data;
  
  public MainMenuState(int ID, Data<Boolean> d) {
  	this.stateID = ID;
  	data = d;
  }

  @Override
  public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
  	index = 0;
    menuText = new String[] {"Spiel starten", "Multiplayer", "Highscores", "Steuerung", "Exit"};
    
    width = gc.getWidth();
    headerSpace = gc.getHeight()/(menuText.length + 1);
    menuSpace = (gc.getHeight() - (2 * headerSpace))/((menuText.length + 1) * 2);
    
    menuFont = new SimpleFont("data/space age.ttf", menuSpace/2);
    headerFont = new SimpleFont("data/space age.ttf", headerSpace);
    
    headerWidth = headerFont.get().getWidth("Spacerun")/2;
    menuWidth = new int[menuText.length];
    for(int i = 0; i < menuText.length; i++){
      menuWidth[i] = menuFont.get().getWidth(menuText[i])/2;
    }
    
    image = new Image("data/Universe.png");
  }
 
  @Override
  public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
    float start = 2 * headerSpace;
    
    image.draw(0,0,gc.getWidth(),gc.getHeight());
    headerFont.get().drawString(width/2 - headerWidth, 0, "Spacerun");

    for(int i = 0; i < menuText.length; i++){
    	if(i == index){
    	  menuFont.get().drawString(width/2 - menuWidth[i], start, menuText[i], Color.red);
    	}else{
    	  menuFont.get().drawString(width/2 - menuWidth[i], start, menuText[i]);
    	}
    	
    	start += menuSpace * 2;
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
      if(index < (menuText.length -1)){
        index++;
      }
    }else if(input.isKeyPressed(Input.KEY_ENTER)){
  	  if(menuText[index] == "Spiel starten"){
  		  sbg.getState(Spacerun.GAMESTATE).init(gc, sbg);
  		  sbg.enterState(Spacerun.GAMESTATE, null, new VerticalSplitTransition());
    	}else if(menuText[index] == "Multiplayer"){
    	  data.setData(true);
  		  sbg.getState(Spacerun.GAMESTATE).init(gc, sbg);
  		  sbg.enterState(Spacerun.GAMESTATE, null, new VerticalSplitTransition());
  	  }else if(menuText[index] == "Highscores"){
  	    sbg.getState(Spacerun.HIGHSCORESTATE).init(gc, sbg);
    	  sbg.enterState(Spacerun.HIGHSCORESTATE, null, new VerticalSplitTransition());
      }else if(menuText[index] == "Steuerung"){
  		  //sbg.getState(Spacerun.CONTROLSTATE).init(gc, sbg);
  		  //sbg.enterState(Spacerun.CONTROLSTATE), null, new VerticalSplitTransition();
  		}else if(menuText[index] == "Exit"){
    	  gc.exit();
  	  }   
    }
    if(input.isKeyPressed(Input.KEY_ESCAPE)){
  	  gc.exit();
    }
  }

  @Override
  public int getID() {
	  return stateID;
	}
}
