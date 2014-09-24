package Tools;
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
/**
 *  Named Entity Recognizer from Stanford NLP, based on POS and some simple rules.
 *  @author Qiankun Zhuang
 */
public class PosTagNamedEntityRecognizer {
  /**
   *  The minimal length of noun phrases
   *  If the length of the noun is smaller than threshold, it won't be extracted
   */
  private static final int threshold = 5;   

  private StanfordCoreNLP pipeline;
  private Map<Integer, Integer> space;
  /**
   *  Constructor of PosTagNamedEntityRecognizer
   */
  private PosTagNamedEntityRecognizer() throws ResourceInitializationException {
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos");
    pipeline = new StanfordCoreNLP(props);
  }
  /**
   *  Singleton pattern 
   */
  private static PosTagNamedEntityRecognizer instance = null;
  public static PosTagNamedEntityRecognizer getInstance() throws ResourceInitializationException{
    if(instance==null)
      instance = new PosTagNamedEntityRecognizer();
    return instance;
  }
  /**
   *  Extract gene names from the input string
   *  @param text
   *      The input string to be analyzed
   *  @return Map
   *      Records the start and end position of each gene names in the sentence
   * 
   */
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
          if(end - begin < threshold){
            candidate.clear();
            continue;
          }
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
        if(end - begin < threshold){
          candidate.clear();
          continue;
        }
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
