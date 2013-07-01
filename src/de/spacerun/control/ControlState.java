/*
 *Copyright (C) 2013 by Matthias Stangl
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 */
package de.spacerun.control;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

import de.spacerun.game.StarBackground;
import de.spacerun.main.Data;
import de.spacerun.main.Spacerun;
import de.spacerun.mainmenu.SimpleFont;

@SuppressWarnings({"rawtypes", "unchecked"}) //prevent eclipse from displaying a warning because Data is a generic type
public class ControlState extends BasicGameState {
  private int ID;
  private ArrayList<Data> status;
  private StarBackground stars;
  private SensorParser spOne, spTwo;
  private SimpleFont font;
  private String[] connectionStatus;
  private String IPaddress;
  private float IPx, IPy;
  
  private String[] menu;
  private int menuPosition[];
  private boolean menuSelection; //true = top; false = bottom

  public ControlState(int ID, ArrayList<Data> status){
    this.ID = ID;
    this.status = status;
  }

  @Override
  public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    connectionStatus = new String[] {"Player 1: connected", "Player 1: disconnected",
        "Player 2:connected", "Player 2: disconnected"};

    int fontSize = gc.getHeight()/36; // 30 @ 1080p
    font =  new SimpleFont("data/space age.ttf", fontSize);

    menu = new String[] {"Handysteuerung aktivieren", "Handysteuerung deaktivieren", "Zurück"};
    menuSelection = true;
    menuPosition = new int[4]; //0 = top x; 1 = top y; 2 = top x; 3 = top y;
    menuPosition[0] = gc.getWidth()/2 - font.get().getWidth(menu[0])/2;
    menuPosition[1] = gc.getHeight()/2 - font.get().getHeight(menu[0])/2;
    menuPosition[2] = gc.getWidth()/2 - font.get().getWidth(menu[2])/2;
    menuPosition[3] = menuPosition[1] + (2 * font.get().getHeight(menu[0]));

    spOne = (SensorParser) status.get(2).getData();
    spTwo = (SensorParser) status.get(3).getData();

    try {
      IPaddress = InetAddress.getLocalHost().getHostAddress();
    }catch(UnknownHostException e){
      IPaddress = "unknown"; //in case there is an exception
    }finally{
      IPx = gc.getWidth() - font.get().getWidth(IPaddress);
      IPy = gc.getHeight() - font.get().getHeight(IPaddress);
    }

    stars = new StarBackground(gc.getWidth(), gc.getHeight(), (int)(gc.getHeight() * gc.getWidth() / 4147));
  }

  @Override
  public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    int index = 0;
    stars.render();
    
    if((boolean)status.get(1).getData()){
      index = 1;
    }

    //draw the IP address
    if(index == 1){
      font.get().drawString(IPx, IPy, IPaddress);
    }else{
      font.get().drawString(IPx, IPy, IPaddress, Color.red);
    }

    //draw the menu
    if(menuSelection){
      font.get().drawString(menuPosition[0], menuPosition[1], menu[index], Color.red);
      font.get().drawString(menuPosition[2], menuPosition[3], menu[2]);
    }else{
      font.get().drawString(menuPosition[0], menuPosition[1], menu[index]);
      font.get().drawString(menuPosition[2], menuPosition[3], menu[2], Color.red);
    }
    
    // player connection status rendering
    if((boolean)status.get(1).getData()){
      float tmpHeight = font.get().getHeight(connectionStatus[0]);
      
      if(spOne.getConnected()){
        font.get().drawString(0, gc.getHeight() - (2 * tmpHeight), connectionStatus[0]);
      }else{
        font.get().drawString(0, gc.getHeight() - (2 * tmpHeight), connectionStatus[1], Color.red);
      }

      if(spTwo.getConnected()){
        font.get().drawString(0, gc.getHeight() - tmpHeight, connectionStatus[2]);
      }else{
        font.get().drawString(0, gc.getHeight() - tmpHeight, connectionStatus[3], Color.red);
      }
    }
  }

  @Override
  public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    Input input = gc.getInput();

    if(input.isKeyPressed(Input.KEY_ESCAPE)){
      sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
			sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
    }

    if(input.isKeyPressed(Input.KEY_UP)){
      menuSelection = true;
    }else if(input.isKeyPressed(Input.KEY_DOWN)){
      menuSelection = false;
    }else if(input.isKeyPressed(Input.KEY_ENTER)){
      if(menuSelection){
        if((boolean)status.get(1).getData()){
          status.get(1).setData(false);
        }else{
          status.get(1).setData(true);
        }
      }else{
        sbg.getState(Spacerun.MAINMENUSTATE).init(gc, sbg);
			  sbg.enterState(Spacerun.MAINMENUSTATE, new FadeOutTransition(), new FadeInTransition());
      }
    }
  }

  @Override
  public int getID() {
    return ID;
  }

}
