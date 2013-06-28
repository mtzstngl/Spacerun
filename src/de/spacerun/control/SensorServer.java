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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import de.spacerun.main.Data;

public class SensorServer extends Thread{
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  private boolean connected = false;
  private static String RESPONSE = "OK";
  private String clientAddr;
  private int port;
  private Data<String> sensorData;

  public SensorServer(int port, Data<String> sensorData){
    this.port = port;
    this.sensorData = sensorData;
    sensorData.setData(""); //we need to initialize -> no NullPointerException
  }

  @Override
  public void run(){
    try{
      serverSocket = new ServerSocket(port);
      String input;

      while(serverSocket.isBound()){
        clientSocket = serverSocket.accept();
        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientAddr = clientSocket.getRemoteSocketAddress().toString();
        System.out.println(clientAddr + " connected");
        connected = true;

        while(connected){
          input = _read();
          if(input == null){
            System.out.print(clientAddr + " disconnected");
            connected = false;
            break;
          }
          sensorData.setData(input);
          _send(RESPONSE);
        }
      }
    }catch(Exception e){
      System.out.println("WARNING: Address already in use OR _read, _send Exception");
      e.printStackTrace();
    }finally{
      stopServer();
    }
  }

  public boolean getConnected(){
    return connected;
  }

  public void stopServer(){
    connected = false;

    try{
      clientSocket.close();
      serverSocket.close();
    }catch(Exception e){
      //Silently catch everything
    }
  }

  private void _send(String message){
    out.println(message);
    out.flush();
  }

  private String _read() throws IOException{
    return in.readLine();
  }
}
