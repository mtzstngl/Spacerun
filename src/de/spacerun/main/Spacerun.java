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
package de.spacerun.main;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.spacerun.control.ControlState;
import de.spacerun.control.SensorParser;
import de.spacerun.game.GameState;
import de.spacerun.highscore.HighscoreState;
import de.spacerun.mainmenu.MainMenuState;

@SuppressWarnings({"rawtypes"}) //prevent eclipse from displaying a warning because Data is a generic type
public class Spacerun extends StateBasedGame{
	public static final int MAINMENUSTATE = 0;
	public static final int GAMESTATE = 1;
	public static final int HIGHSCORESTATE = 2;
	public static final int CONTROLSTATE = 3;
	private ArrayList<Data> data;
	
	public Spacerun(){
		super("Spacerun");
		data = new ArrayList<Data>();
		data.add(new Data<Boolean>(false)); //mulitplayer
		data.add(new Data<Boolean>(false)); //control mode
		data.add(new Data<SensorParser>(new SensorParser(23456))); //sensor server player 1
		data.add(new Data<SensorParser>(new SensorParser(23457))); //sensor server player 2

		this.addState(new MainMenuState(MAINMENUSTATE, data));
		this.addState(new GameState(GAMESTATE, data));
		this.addState(new HighscoreState(HIGHSCORESTATE));
		this.addState(new ControlState(CONTROLSTATE, data));
		this.enterState(MAINMENUSTATE);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MAINMENUSTATE).init(gc, this);
	}
	
  public static void main(String[] args) throws SlickException {
    AppGameContainer app = new AppGameContainer(new Spacerun());
    app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
    app.setMouseGrabbed(true);
    app.setVSync(true);
    app.setShowFPS(false);
    app.start();
  }
}
