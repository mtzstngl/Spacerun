package de.spacerun.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.spacerun.mainmenu.MainMenuState;

public class Spacerun extends StateBasedGame{
	public static final int MAINMENUSTATE = 0;
	public static final int HIGHSCIRESTATE = 1;
	public static final int GAMESTATE = 2;
	
	public Spacerun(){
		super("Spacerun");
		
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.enterState(MAINMENUSTATE);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MAINMENUSTATE).init(gc, this);
		
	}
	
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Spacerun());
        app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
        app.setShowFPS(true);
        app.start();
    }

}
