import Type.sentence;
import Type.gene;

import java.io.IOException;
import java.util.*;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
/**
 * This class annotates the input sentence by calling the Lingpipe NER recognizer.
 * @author Qiankun Zhuang
 */

public class geneNameAnnotatorLingpipe extends JCasAnnotator_ImplBase{
  /**
   * Check how many spaces are inside the input String
   * @param str
   *      Input string 
   * 
   */
  int checkSpace(String str){
    int cnt = 0;
    for(int i = 0; i < str.length(); i++){
      if(str.charAt(i)==' ')
        cnt++;
    }
    return cnt;
  }
  /**
   * Extract all the sentences in the JCas and process them with Lingpipe NER.
   * Store the processed gene information into gene type and add them to annotation index.
   * @param aJcas
   *    CAS containing annotations of sentence type. Gene type will also be stored in here.
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    NERLingpipe recognizer = null;
   
    recognizer = NERLingpipe.getInstance();
    
    Iterator sIter = aJCas.getAnnotationIndex(sentence.type).iterator();// Need to specify the annotation type you want to use
    while (sIter.hasNext()) {
      sentence s = (sentence) sIter.next();    
      Map<Integer, Integer> ret = null;
      try {
        ret = recognizer.getGeneSpans(s.getText());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Iterator<Map.Entry<Integer,Integer>>iter = ret.entrySet().iterator();
      while(iter.hasNext()){
        Map.Entry<Integer, Integer> entry = iter.next();   
        int st = entry.getKey();
        int end = entry.getValue();
        gene g = new gene(aJCas);        
        g.setBegin(st-recognizer.getSpaceNum(st));
        //g.setBegin(st);
        g.setGeneName(s.getText().substring(st, end));
        g.setEnd(end-1-recognizer.getSpaceNum(st)-checkSpace(s.getText().substring(st, end)));
        //g.setEnd(end);
        g.setId(s.getId());
        g.addToIndexes();  
      }  
    }        
  }
}
