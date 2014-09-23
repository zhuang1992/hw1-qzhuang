package Type;


/* First created by JCasGen Fri Sep 19 07:46:08 EDT 2014 */

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;

public class sentence extends Annotation {
 
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(sentence.class);
 
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;

  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  protected sentence() {/* intentionally empty block */}

  public sentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  public sentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

 
  public sentence(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  private void readObject() {/*default - does nothing empty block */}
     
 
    
 
  public String getId() {
    if (sentence_Type.featOkTst && ((sentence_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Type.sentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((sentence_Type)jcasType).casFeatCode_id);}
 
  public void setId(String v) {
    if (sentence_Type.featOkTst && ((sentence_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Type.sentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((sentence_Type)jcasType).casFeatCode_id, v);}    
   

  public String getText() {
    if (sentence_Type.featOkTst && ((sentence_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "Type.sentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((sentence_Type)jcasType).casFeatCode_text);}
    

  public void setText(String v) {
    if (sentence_Type.featOkTst && ((sentence_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "Type.sentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((sentence_Type)jcasType).casFeatCode_text, v);}    
  }

    