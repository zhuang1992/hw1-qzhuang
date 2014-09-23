import Type.sentence;
import Type.gene;

import java.util.*;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;


public class geneNameAnnotator extends JCasAnnotator_ImplBase{
  private static final int threshold = 12;  // If the length of the noun is smaller than threshold, skip it. 
  int checkSpace(String str){
    int cnt = 0;
    for(int i = 0; i < str.length(); i++){
      if(str.charAt(i)==' ')
        cnt++;
    }
    return cnt;
  }
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // get document text
    //String docText = aJCas.getDocumentText();
    PosTagNamedEntityRecognizer recognizer = null;
    try {
      recognizer = PosTagNamedEntityRecognizer.getInstance();
    } catch (ResourceInitializationException e) {
      e.printStackTrace();
    }
    Iterator sIter = aJCas.getAnnotationIndex(sentence.type).iterator();// Need to specify the annotation type you want to use
    while (sIter.hasNext()) {
      sentence s = (sentence) sIter.next();    
      Map<Integer, Integer> ret = recognizer.getGeneSpans(s.getText());
      Iterator<Map.Entry<Integer,Integer>>iter = ret.entrySet().iterator();
      while(iter.hasNext()){
        Map.Entry<Integer, Integer> entry = iter.next();   
        int st = entry.getKey();
        int end = entry.getValue();
        if(end-st< threshold)
          continue;
        gene g = new gene(aJCas);        
        g.setBegin(st-recognizer.getSpaceNum(st));
        g.setGeneName(s.getText().substring(st, end));
        g.setEnd(end-1-recognizer.getSpaceNum(st)-checkSpace(s.getText().substring(st, end)));
        g.setId(s.getId());
        g.addToIndexes();  
      }  
    }        
  }
}
