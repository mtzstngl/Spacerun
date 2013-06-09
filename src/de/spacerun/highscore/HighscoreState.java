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
package de.spacerun.highscore;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.spacerun.main.Spacerun;
import de.spacerun.mainmenu.SimpleFont;

public class HighscoreState extends BasicGameState{
	private FileHandler fHandler;
  private int stateID;
	private boolean selection; //true = right; false = left
  private SimpleFont textFont, headerFont, menuFont;
  private int textSpace, width, headerSpace, headerWidth;
  private int menuX, menuY;
  private String[] menu;
  private int[] scoreWidth;
  private ArrayList<String> nameList;
  private ArrayList<Long> scoreList;

	public HighscoreState(int ID){
	  this.stateID = ID;
	} 

  @Override
  public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
    fHandler = new FileHandler();
    selection = false;

    nameList = fHandler.getHighscoreNames();
    scoreList = fHandler.getHighscoreScore();
    
    width = gc.getWidth();
    headerSpace = gc.getHeight()/11;
    textSpace = (gc.getHeight() - (2 * headerSpace))/22; // divided by 11 to get all the spaces then by 2 to get fontsize
    scoreWidth = new int[scoreList.size()];
    menu = new String[] {"Zurück", "Scores löschen"};

    textFont = new SimpleFont("Arial", Font.PLAIN, textSpace, java.awt.Color.lightGray);
    headerFont = new SimpleFont("Arial", Font.PLAIN, headerSpace);
    menuFont = new SimpleFont("Arial", Font.PLAIN, textSpace/2);

    headerWidth = headerFont.get().getWidth("High Scores")/2;
    menuX = width - 10 - menuFont.get().getWidth(menu[1]);
    menuY = gc.getHeight() - 10 - menuFont.get().getHeight(menu[0]);

    int i = 0;
    for(long score : scoreList){
      scoreWidth[i] = textFont.get().getWidth(Long.toString(score));
      i++;
    }
  }
  
  @Override
  public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
    int i = 0;
    int y = 2 * headerSpace;
    
    headerFont.get().drawString(width/2 - headerWidth, 0, "High Scores");

    for(String name : nameList){
      textFont.get().drawString(5, y, Integer.toString(i+1) + ". " + name);
      textFont.get().drawString(width - 5 - scoreWidth[i], y, Long.toString(scoreList.get(i)));
      g.setColor(Color.lightGray);
      g.drawLine(0, y + (textSpace * 2), width, y + (textSpace * 2)); //we want to draw under not above the High Score
      i++;
      y += textSpace * 2;
    }

    if(selection){
      menuFont.get().drawString(10, menuY, menu[0]);  
      menuFont.get().drawString(menuX, menuY, menu[1], Color.red);  
    }else{
      menuFont.get().drawString(10, menuY, menu[0], Color.red);  
      menuFont.get().drawString(menuX, menuY, menu[1]);  
    }
  }

  @Override
  public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
    Input input = gc.getInput();

    if(input.isKeyPressed(Input.KEY_LEFT)){
      if(selection){
        selection = false;
      }
    }else if(input.isKeyPressed(Input.KEY_RIGHT)){
      if(!selection){
        selection = true; 
      }
    }else if(input.isKeyPressed(Input.KEY_ENTER)){
      if(selection){
        fHandler.deleteHighscore();
        sbg.getState(Spacerun.HIGHSCORESTATE).init(gc, sbg); //we need to update the screen; the easiest way
        sbg.enterState(Spacerun.HIGHSCORESTATE, new FadeOutTransition(), new FadeInTransition());
      }else{
        sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
        sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
      }
    }
    if(input.isKeyPressed(Input.KEY_ESCAPE)){
      sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
      sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
    }
  }

  @Override
  public int getID(){
    return stateID;
  }
}
