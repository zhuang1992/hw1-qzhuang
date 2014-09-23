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
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  public static NERLingpipe getInstance(){
    if(instance == null)
      instance = new NERLingpipe();
    return instance;
  }
  Map<Integer, Integer> getGeneSpans(String text) throws IOException, ClassNotFoundException{
    Map<Integer, Integer> begin2end = new HashMap<Integer, Integer>();

    Chunking chunking = chunker.chunk(text);
    Iterator<Chunk>iter = chunking.chunkSet().iterator();
    int numOfSpace = 0;
    int spaceInside = 0;
    while(iter.hasNext()){
      Chunk c = (Chunk)iter.next();
      begin2end.put(c.start(), c.end());
    }
    return begin2end;
  }
  public static void main(String[] args) throws Exception {
    NERLingpipe test = new NERLingpipe();
    String text = "p53 regulates human insulin-like growth factor II gene expression through active P4 promoter in rhabdomyosarcoma cells.";
    Map<Integer,Integer>r = test.getGeneSpans(text);
    Iterator<Entry<Integer, Integer>>  iter = r.entrySet().iterator();
    while(iter.hasNext()){
      Entry<Integer, Integer> entry = (Entry<Integer, Integer>)(iter.next());
      System.out.println(text.substring(entry.getKey(),entry.getValue()));
    }
  }

}