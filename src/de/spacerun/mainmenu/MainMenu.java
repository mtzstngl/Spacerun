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
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class MainMenu extends BasicGame {
        private SimpleFont font[];
        private int height, width, index, len;
        private String[] text;
        
        public MainMenu() {
                super("Font Test");
        }

        @Override
		public void init(GameContainer container) throws SlickException {
                container.setShowFPS(true);
                
                index = 0;
                text = new String[] {"hi", "hui", "halli", "hallo", "exit"};
                len = text.length;
                font = new SimpleFont[len];
                
                for(int i = 0; i < len; i++){
                	font[i] = new SimpleFont("Comic Sans MS", Font.PLAIN, 20);
                }
                font[0].setColor(Color.red);
                
                height = container.getHeight();
                width = container.getWidth();
        }
        
        @Override
        public void render(GameContainer container, Graphics g) {
        	int tmpW, tmpH;
        	int start = 20;
        	
        	for(int i = 0; i < len; i++){
        		tmpW = font[i].get().getWidth(text[i]);
        		tmpH = font[i].get().getHeight(text[i]);
        		font[i].get().drawString(width/2 - tmpW/2, start, text[i]);
        		start += tmpH + 20;
        	}
        }
        
        @Override
        public void update (GameContainer container, int delta) throws SlickException {
                Input input = container.getInput();
                
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
                }
        }

        public static void main(String[] args) throws SlickException, IOException {
        	Log.setVerbose(false);
            AppGameContainer container = new AppGameContainer(new MainMenu());
            container.setDisplayMode(512, 600, false);
            container.start();
        }
}