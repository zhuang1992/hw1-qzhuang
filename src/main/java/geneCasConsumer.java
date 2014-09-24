import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import Type.gene;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.base_cpm.CasObjectProcessor;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

/**
 * The consumer class extracts information from CAS and prints information 
 * to files in required format.
 * 
 * @author Qiankun Zhuang
 * 
 */
public class geneCasConsumer extends CasConsumer_ImplBase implements CasObjectProcessor{
  File outFile;

  FileWriter fileWriter;

  public geneCasConsumer() {
  }
  /**
   * Initializes this CAS Consumer with the parameters specified in the descriptor.
   * 
   * @throws ResourceInitializationException
   *           if there is error in initializing the resources
   */
  public void initialize() throws ResourceInitializationException {

    String oPath = (String) getUimaContext().getConfigParameterValue("outputFile");
    if (oPath == null) {
      throw new ResourceInitializationException(
              ResourceInitializationException.CONFIG_SETTING_ABSENT, new Object[] { "outputFile" });
    }
    outFile = new File(oPath.trim());
    
    try {
      if(outFile.exists()){
        outFile.delete();
      }
      outFile.createNewFile();
      fileWriter = new FileWriter(outFile,true);
        
    } catch (IOException e) {
      throw new ResourceInitializationException(e);
    }
  }
  /**
   * Extract information from gene type and output these information to a file in required format.
   * @param aCAS
   *        Common analysis structure containing gene type.
   * 
   */
  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }
    
    Iterator gIter = jcas.getAnnotationIndex(gene.type).iterator();
    while(gIter.hasNext()){
      gene g = (gene) gIter.next();
      String newline = new String(g.getId()+"|"+g.getBegin()+" "+g.getEnd()+"|"+g.getGeneName()+"\n");
      //System.out.println("Consumer:"+newline);
      try {
        fileWriter.write(newline);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }    
  }
  /**
   * Close the output file after consumer finished all the printing. 
   */ 
  @Override
  public void destroy() {
    if (fileWriter != null) {
      try {
        fileWriter.close();
      } catch (IOException e) {
      }
    }
  }
}
