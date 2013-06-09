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
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class Star{
  private int x;
  private int y;
  private float size;
  private SGL gl = Renderer.get();
  private Color color;

  public Star(int x, int y, float size){
    this(x, y, size, Color.white);
  }

  public Star(int x, int y, float size, Color color){
    this.x = x;
    this.y = y;
    this.size = size;
    this.color = color;
  }

  public void render(){
    TextureImpl.bindNone();
    gl.glEnable(SGL.GL_POINT_SMOOTH);
    gl.glPointSize(size/2);
    color.bind();
    gl.glBegin(SGL.GL_POINTS);
    gl.glVertex2f(x, y);
    gl.glEnd();
  }

  public void setColor(Color color){
    this.color = color;
  }

  public void adjustSize(float size){
    this.size += size;
  }
  
  public void setSize(float size){
    this.size = size;
  }

  public void adjustPosition(int x, int y){
    this.x += x;
    this.y += y;
  }

  public void setPosition(int x, int y){
    this.x = x;
    this.y = y;
  }
}
