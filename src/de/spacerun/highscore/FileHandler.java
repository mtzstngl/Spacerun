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

package de.spacerun.highscore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
  private File file;
  private FileWriter fWriter;
  private PrintWriter out;
  private String SEPARATOR = "<|>";

  public FileHandler() {
    file = new File("./data/highscores.txt");
    
    if(!file.exists()){
      try{
        file.createNewFile();
      }catch(IOException e){
        e.printStackTrace();
      }
    }

    try{
      fWriter = new FileWriter(file, true);
      out = new PrintWriter(fWriter);
    }catch(IOException e){
      e.printStackTrace();
    }
  }
  

  public void writeHighscore(String name, long score){
    ArrayList<String> nameList = getHighscoreNames();
    ArrayList<Long> scoreList = getHighscoreScore();
    boolean first = true;

    deleteHighscore();
    for(int i = 0; i < 10; i++){
      if( (score > scoreList.get(i)) && first){
        write(name + SEPARATOR + Long.toString(score));
        first = false;
      } 
      write(nameList.get(i) + SEPARATOR + Long.toString(scoreList.get(i))); 
    }
  }

  public ArrayList<String> getHighscoreNames(){
    ArrayList<String> name = new ArrayList<String>();
    
    //we need to reset the file pointer to the beginning
    try{
      Scanner in = new Scanner(file);
      //we only want to show the last 10 Highscores
      for(int i = 0; i < 10; i++){
        if(in.hasNextLine()){
          String tmp = in.nextLine();
          if(tmp.contains(SEPARATOR)){ //test if the SEPARATOR is in the String
            String tmpArray[] = tmp.split(SEPARATOR);
            name.add(tmpArray[0]);// 0= player name; 1= SEPARATOR; 2= player score
          }else{ //if not available use nothing as name
            name.add("");
          }
        }else{
          name.add("");
        }
      }
      //tidy up
      in.close();
    }catch(IOException e){
      e.printStackTrace();
      for(int i = 0; i < 10; i++){
        name.add("");
      }
    }

    return name;
  }

  public ArrayList<Long> getHighscoreScore(){ 
    ArrayList<Long> score = new ArrayList<Long>();

    //we need to reset the file pointer to the beginning
    try{
      Scanner in = new Scanner(file);
      //we only want to show the last 10 Highscores
      for(int i = 0; i < 10; i++){
        if(in.hasNextLine()){
          String tmp = in.nextLine();
          if(tmp.contains(SEPARATOR)){ //test if the SEPARATOR is in the String
            String tmpArray[] = tmp.split(SEPARATOR);
            score.add(Long.parseLong(tmpArray[2])); // 0= player name; 1= SEPARATOR; 2= player score
          }else{ //if not available use nothing as name
            score.add(0L);
          }
        }else{
          score.add(0L);
        }
      }
      //tidy up
      in.close();
    }catch(IOException e){
      e.printStackTrace();
      for(int i = 0; i < 10; i++){
        score.add(0L);
      }
    }

    return score;
  }

  public void deleteHighscore(){
    try{
      FileWriter tmp =  new FileWriter(file);
      tmp.write("");
      tmp.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  private void write(String newline){
    out.println(newline);
    out.flush();
  }
}
