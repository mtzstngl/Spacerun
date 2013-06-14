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

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class ObstacleRow {
	private Rectangle[] obstacle;
	private int sectorCount;
	private float position;
	
	public ObstacleRow(int sectorWidth, int sectorCount, float pos){
		this.sectorCount = sectorCount;
		this.position = pos;
		
		obstacle = new Rectangle[sectorCount];
		for(int i = 0; i < sectorCount; i++){
			int rand = (int) (Math.random() * sectorWidth);
			obstacle[i] = new Rectangle(sectorWidth * i, position, rand, 20);
		}
	}
	
	public void setPosition(float pos){
		position += pos;
		for(int i = 0; i < sectorCount; i++){
			obstacle[i].setY(position);
		}
	}
	
	public float getPosition(){
		return position;
	}
	
	public boolean intersects(Shape shape){
		for(int i = 0; i < sectorCount; i++){
			if(obstacle[i].intersects(shape)){
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics g, Color color){
		for(int i = 0; i < sectorCount; i++){
			g.setColor(color);
			g.fillRect(obstacle[i].getX(), obstacle[i].getY(), obstacle[i].getWidth(), obstacle[i].getHeight());
			g.draw(obstacle[i]);
		}
	}
}
