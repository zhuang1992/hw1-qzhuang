

/* First created by JCasGen Fri Sep 19 07:46:08 EDT 2014 */
package Type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Sep 19 09:49:33 EDT 2014
 * XML source: /home/micz/workspace/hw1-qzhuang/src/main/resources/Descriptors/aeDescriptor.xml
 *  */
public class gene extends Annotation {

  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(gene.class);

  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** 
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   *  */
  protected gene() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * 
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public gene(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** 
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public gene(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** 
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public gene(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   *  
   */
  private void readObject() {/*default - does nothing empty block */}
     


  /** getter for id - gets 
   * 
   * @return value of the feature 
   */
  public String getId() {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Type.gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((gene_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "Type.gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((gene_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: geneName

  /** getter for geneName - gets 
   * 
   * @return value of the feature 
   */
  public String getGeneName() {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_geneName == null)
      jcasType.jcas.throwFeatMissing("geneName", "Type.gene");
    return jcasType.ll_cas.ll_getStringValue(addr, ((gene_Type)jcasType).casFeatCode_geneName);}
    
  /** setter for geneName - sets  
   * 
   * @param v value to set into the feature 
   */
  public void setGeneName(String v) {
    if (gene_Type.featOkTst && ((gene_Type)jcasType).casFeat_geneName == null)
      jcasType.jcas.throwFeatMissing("geneName", "Type.gene");
    jcasType.ll_cas.ll_setStringValue(addr, ((gene_Type)jcasType).casFeatCode_geneName, v);}    
  }

    