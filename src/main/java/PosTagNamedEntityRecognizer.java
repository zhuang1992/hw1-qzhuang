import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.uima.resource.*;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;

public class PosTagNamedEntityRecognizer {

  private StanfordCoreNLP pipeline;
  private Map<Integer, Integer> space;
  private PosTagNamedEntityRecognizer() throws ResourceInitializationException {
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos");
    pipeline = new StanfordCoreNLP(props);
  }
  private static PosTagNamedEntityRecognizer instance = null;
  public static PosTagNamedEntityRecognizer getInstance() throws ResourceInitializationException{
    if(instance==null)
      instance = new PosTagNamedEntityRecognizer();
    return instance;
  }
  public Map<Integer, Integer> getGeneSpans(String text) {
    Map<Integer, Integer> begin2end = new HashMap<Integer, Integer>();
    space = new HashMap<Integer, Integer>();
    Annotation document = new Annotation(text);
    pipeline.annotate(document);
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      List<CoreLabel> candidate = new ArrayList<CoreLabel>();
      int numOfSpace = 0;
      int spaceInside = 0;
      for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        String pos = token.get(PartOfSpeechAnnotation.class);
        if (pos.startsWith("NN")) {
          candidate.add(token);
          spaceInside++;
        } else if (candidate.size() > 0) {
          int begin = candidate.get(0).beginPosition();
          int end = candidate.get(candidate.size() - 1).endPosition();
          begin2end.put(begin, end);
          space.put(begin,numOfSpace);
          candidate.clear();
          spaceInside++;
          numOfSpace+=spaceInside;
          spaceInside = 0;
        }else{
          numOfSpace++;
        }
      }
      if (candidate.size() > 0) {
        int begin = candidate.get(0).beginPosition();
        int end = candidate.get(candidate.size() - 1).endPosition();
        begin2end.put(begin, end);
        space.put(begin,numOfSpace);
        candidate.clear();
      }
    }
    return begin2end;
  }
  public int getSpaceNum(int begin){
    return space.get(begin);
  }
  public String readToString(String fileName) {
    String encoding = "ISO-8859-1";
    File file = new File(fileName);
    Long filelength = file.length();
    byte[] filecontent = new byte[filelength.intValue()];
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(filecontent);
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      return new String(filecontent, encoding);
    } catch (UnsupportedEncodingException e) {
      System.err.println("The OS does not support " + encoding);
      e.printStackTrace();
      return null;
    }
  }
  public static void main(String[] args) throws ResourceInitializationException{
    PosTagNamedEntityRecognizer test = new PosTagNamedEntityRecognizer();
    String t = "Comparison with alkaline phosphatases and 5-nucleotidase";
    Map<Integer, Integer> r = test.getGeneSpans(t);
    Iterator<Entry<Integer, Integer>> iter = r.entrySet().iterator();
    while(iter.hasNext()){
      Entry<Integer, Integer> entry = iter.next();
      int st = entry.getKey();
      int en = entry.getValue();
      int space = test.getSpaceNum(st);
      int spaceInside = 0;
      for(int i = st; i < en; i++){
        if(t.charAt(i)==' ')
          spaceInside++;
      }
      System.out.println((st-space)+" "+(en-space-spaceInside-1)+":"+t.substring(st, en));
    }
    
  }
}