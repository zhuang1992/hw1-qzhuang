



import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


public class gene extends Annotation {
 
  public final static int typeIndexID = JCasRegistry.register(gene.class);

  public final static int type = typeIndexID;

  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  protected gene() {}
    
 
  public gene(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  public gene(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  public gene(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  private void readObject() {}
     
 
  public String getId() {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Type.gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((gene_Type)jcasType).casFeatCode_id);}
    
  public void setId(String v) {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Type.gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((gene_Type)jcasType).casFeatCode_id, v);}    
   
    
  public String getGeneName() {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_geneName == null)
      jcasType.jcas.throwFeatMissing("geneName", "Type.gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((gene_Type)jcasType).casFeatCode_geneName);}
    
  public void setGeneName(String v) {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_geneName == null)
      jcasType.jcas.throwFeatMissing("geneName", "Type.gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((gene_Type)jcasType).casFeatCode_geneName, v);}    
  }

    