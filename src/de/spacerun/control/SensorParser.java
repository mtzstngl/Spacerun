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

import de.spacerun.main.Data;

public class SensorParser{
  private SensorServer server;
  private Data<String> sensorInput;
  private static String SEPARATOR = "<|>";
  private float x, y, z;

  public SensorParser(int port){
    x = 0f;
    y = 0f;
    z = 0f;

    sensorInput = new Data<String>();
    server = new SensorServer(port, sensorInput);
    server.start();
  }

  public void closeInput(){
    server.stopServer();
    try{
      server.join();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void updateInput(){
    String input = sensorInput.getData();

    if(input.contains(SEPARATOR)){
      String splitArray[] = input.split(SEPARATOR);
      x = Float.parseFloat(splitArray[0]);
      y = Float.parseFloat(splitArray[2]);
      z = Float.parseFloat(splitArray[4]);
    }else{
      //System.out.println("INPUT: " + input);
      x = 0f;
      y = 0f;
      z = 0f;
    }
  }

  public boolean getConnected(){
    return server.getConnected();
  }

  public float getX(){
    return x;
  }

  public float getY(){
   return y;
  }

  public float getZ(){
    return z;
  }
}
