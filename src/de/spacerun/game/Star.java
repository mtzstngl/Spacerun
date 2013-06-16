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
package de.spacerun.game;

import org.newdawn.slick.Color;

public class Star{
  private float x;
  private float y;
  private Color color;

  public Star(float x, float y){
    this(x, y, Color.white);
  }

  public Star(float x, float y, Color color){
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public void setColor(Color color){
    this.color = color;
  }

  public void setColor(float r, float g, float b, float a){
    color = new Color(r, g, b, a);
  }

  public void adjustPosition(float x, float y){
    this.x += x;
    this.y += y;
  }

  public void setPosition(float x, float y){
    this.x = x;
    this.y = y;
  }

  public float getX(){
    return x;
  }

  public float getY(){
    return y;
  }

  public Color getColor(){
    return color;
  }
}
