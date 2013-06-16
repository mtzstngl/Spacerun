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

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class StarBackground {
  private ArrayList<Star> small, middle, big;
  private int starCount, index, width, height;
  private SGL gl = Renderer.get();
  private float smallSize, middleSize, bigSize;

  public StarBackground(int screenWidth, int screenHeight, int starCount){
    small = new ArrayList<Star>();
    middle = new ArrayList<Star>();
    big = new ArrayList<Star>();

    this.width = screenWidth;
    this.height = screenHeight;
    this.starCount = starCount;
    smallSize = width * height / 2073600;// 1 @ 1920x1080
    middleSize = width * height / 691200;// 3 @ 1920x1080
    bigSize = width * height / 345600;// 6 @ 1920x1080
    index = 0;

    for(int i = 0; i < this.starCount; i++){
      addStar((float)(Math.random() * width), (float)(Math.random() * height));
    }
  }

  public void render(){
    TextureImpl.bindNone();
    gl.glEnable(SGL.GL_POINT_SMOOTH);
    
    //render small stars
    gl.glPointSize(smallSize);
    gl.glBegin(SGL.GL_POINTS);
    for(Star s : small){
      s.getColor().bind();
      gl.glVertex2f(s.getX(), s.getY());
    }
    gl.glEnd();
    
    //render middle stars
    gl.glPointSize(middleSize);
    gl.glBegin(SGL.GL_POINTS);
    for(Star s : middle){
      s.getColor().bind();
      gl.glVertex2f(s.getX(), s.getY());
    }
    gl.glEnd();
    
    //render big stars
    gl.glPointSize(bigSize);
    gl.glBegin(SGL.GL_POINTS);
    for(Star s : big){
      s.getColor().bind();
      gl.glVertex2f(s.getX(), s.getY());
    }
    gl.glEnd();
  }

  public void adjustPosition(float x, float y){
    for(int i = 0; i < small.size(); i++){
      small.get(i).setPosition(small.get(i).getX() + x, small.get(i).getY() + y);
      checkLeft(small, i, x);
      checkRight(small, i, x);
      checkBottom(small, i, y);
    }

    for(int i = 0; i < middle.size(); i++){
      middle.get(i).setPosition(middle.get(i).getX() + x, middle.get(i).getY() + y);
      checkLeft(middle, i, x);
      checkRight(middle, i, x);
      checkBottom(middle, i, y);
    }

    for(int i = 0; i < big.size(); i++){
      big.get(i).setPosition(big.get(i).getX() + x, big.get(i).getY() + y);
      checkLeft(big, i, x);
      checkRight(big, i, x);
      checkBottom(big, i, y);
    }
  }

  //check if X is too small
  private void checkLeft(ArrayList<Star> l, int pos, float x){
    if(l.get(pos).getX() < 0){
      l.remove(pos);
      if(index == 0){
        l.add(new Star(width - x, (float)(Math.random() * height)));
      }else if(index == 1){
        l.add(new Star(width - x, (float)(Math.random() * height), Color.pink));
      }else if(index == 2){
        l.add(new Star(width - x, (float)(Math.random() * height), Color.cyan));
        index  = -1;
      }else{
        index = 0;
      }
      index++;
    }
  }

  //check if X is too big
  private void checkRight(ArrayList<Star> l, int pos, float x){
    if(l.get(pos).getX() > width){
      l.remove(pos);
      if(index == 0){
        l.add(new Star(x, (float)(Math.random() * height)));
      }else if(index == 1){
        l.add(new Star(x, (float)(Math.random() * height), Color.pink));
      }else if(index == 2){
        l.add(new Star(x, (float)(Math.random() * height), Color.cyan));
        index  = -1;
      }else{
        index = 0;
      }
      index++;
    }
  }

  //check if Y is too big;
  private void checkBottom(ArrayList<Star> l, int pos, float y){
    if(l.get(pos).getY() > height){
      l.remove(pos);
      if(index == 0){
        l.add(new Star((float)(Math.random() * width), y));
      }else if(index == 1){
        l.add(new Star((float)(Math.random() * width), y, Color.pink));
      }else if(index == 2){
        l.add(new Star((float)(Math.random() * width), y, Color.cyan));
        index  = -1;
      }else{
        index = 0;
      }
      index++;
    }
  }
  
  private void addStar(float x, float y){
    if(index == 0){
      small.add(new Star(x, y));
    }else if(index == 1){
      middle.add(new Star(x, y, Color.pink));
    }else if(index == 2){
      big.add(new Star(x, y, Color.cyan));
      index = -1;
    }else{
      index = 0;
    }
    index++;
  }
}

