package Type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

public class sentence_Type extends Annotation_Type {
 
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}

  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (sentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = sentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new sentence(addr, sentence_Type.this);
  			   sentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new sentence(addr, sentence_Type.this);
  	  }
    };
 
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = sentence.typeIndexID;
 
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("Type.sentence");
 
  final Feature casFeat_id;

  final int     casFeatCode_id;
  
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "Type.sentence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "Type.sentence");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
  final Feature casFeat_text;

  final int     casFeatCode_text;
  
  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "Type.sentence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }
 
  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "Type.sentence");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    
  


  public sentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

  }
}



    