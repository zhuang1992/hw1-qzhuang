package Tools;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * The class Evaluator reads the gene names from sample.out and hw1-qzhuang.out,
 * and calculates the Precision, Recall and F1 score so that we can evaluate and compare 
 * the performance different annotators.* 
 * 
 * @author Qiankun Zhuang
 * 
 */
public class Evaluator {
  String sampleOutput = "src/main/resources/data/sample.out";
  String myOutput = "hw1-qzhuang.out";
  int hitting;
  int myGeneNum;
  int sampleNum;
  HashMap<String, Boolean> standard;
  HashMap<String, Boolean> my;
  /*
   * Constructor of Evaluator.
   * Do all the initialization and file reading.
   * */
  public Evaluator(){
    hitting = 0;
    myGeneNum = 0;
    sampleNum = 0;
    standard = new HashMap<String, Boolean>();
    my = new HashMap<String, Boolean>();
    File sampleFile = new File(sampleOutput);
    try {
      BufferedReader sampleFileReader = new BufferedReader(new FileReader(sampleFile));
      String temp = null;
      while((temp=sampleFileReader.readLine())!=null){
        String[] items = temp.split("\\|");
        if(!standard.containsKey(items[items.length-1].trim())){
          standard.put(items[items.length-1].trim(), true);
          sampleNum++;
        }          
      }
      sampleFileReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }  
    File myFile = new File(myOutput);
    try {
      BufferedReader myFileReader = new BufferedReader(new FileReader(myFile));
      String temp = null;
      while((temp=myFileReader.readLine())!=null){
        String[] items = temp.split("\\|");
        String key = items[items.length-1].trim();
        if(my.containsKey(key)){
          continue;
        }
        if(standard.containsKey(key))
          hitting++;
        myGeneNum++;
        my.put(key, true);
      }
      myFileReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }   
  }
  double getPrecision(){
    return (double)hitting/(double)myGeneNum;
  }
  double getRecall(){
    return (double)hitting/(double)sampleNum;
  }
  double getF1Score(){
    return 2.0*getPrecision()*getRecall()/(getPrecision()+getRecall());
  }
  public static void main(String[] args){
    Evaluator eva = new Evaluator();
    System.out.println("Hitting number: "+eva.hitting);
    System.out.println("Total Number in my output: "+eva.myGeneNum);
    System.out.println("Total Number in sample: "+eva.sampleNum);
    System.out.println("Precision = " + eva.getPrecision());
    System.out.println("Recall = " + eva.getRecall());
    System.out.println("F1_Score = " + eva.getF1Score());
  }
}
