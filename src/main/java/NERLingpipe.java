import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.util.AbstractExternalizable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class NERLingpipe {
  String ChunkerFile = "src/main/resources/ne-en-bio-genetag.HmmChunker";
  File modelFile;
  private static NERLingpipe instance;
  private Map<Integer, Integer> space;
  Chunker chunker;
  private NERLingpipe(){
    try {
      modelFile = new File(ChunkerFile);
      chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
      space = new HashMap<Integer, Integer>();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  public static NERLingpipe getInstance(){
    if(instance == null)
      instance = new NERLingpipe();
    return instance;
  }
  int getSpaceNum(int begin){
    return space.get(begin);
  }
  Map<Integer, Integer> getGeneSpans(String text) throws IOException, ClassNotFoundException{
    Map<Integer, Integer> begin2end = new HashMap<Integer, Integer>();

    Chunking chunking = chunker.chunk(text);
    Iterator<Chunk>iter = chunking.chunkSet().iterator();
    
    while(iter.hasNext()){
      int numOfSpace = 0;
      Chunk c = (Chunk)iter.next();
      begin2end.put(c.start(), c.end());
      for(int i = 0; i < c.start(); i++){
        if(text.charAt(i)==' ')
          numOfSpace++;
      }
      space.put(c.start(), numOfSpace);
    }
    return begin2end;
  }
  /**
   *  The main method is used to test Lingpipe NER
   *  @param args 
   * 
   */
  public static void main(String[] args) throws Exception {
    NERLingpipe test = new NERLingpipe();
    String text = "p53 regulates human insulin-like growth factor II gene expression through active P4 promoter in rhabdomyosarcoma cells.";
    Map<Integer,Integer>r = test.getGeneSpans(text);
    Iterator<Entry<Integer, Integer>>  iter = r.entrySet().iterator();
    while(iter.hasNext()){
      Entry<Integer, Integer> entry = (Entry<Integer, Integer>)(iter.next());
      System.out.println(test.getSpaceNum(entry.getKey())+":"+text.substring(entry.getKey(),entry.getValue()));
    }
  }
}