import Type.sentence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

/**
 * A simple collection reader that reads documents from the specified file. It can be
 * configured with the following parameters:
 * <ul>
 * <li><code>InputDirectory</code> - path to directory containing files</li>
 * </ul>
 * It stores the information in sentence type and add it to annotation index.
 * 
 * @author Qiankun Zhuang
 * 
 * 
 */
public class GeneCollectionReader extends CollectionReader_ImplBase{
  /**
   * Name of configuration parameter that must be set to the path of the input file
   * 
   */
  public static final String PARAM_INPUTDIR = "InputDirectory";
  
  private File file;
  private BufferedReader fileReader;

  private String mLanguage;
  private Vector<String>contents;
  private int numLine = 0;
  private int curLine = 0;
  /**
   * This initialize() function is called once at the start of the CPE.
   * It reads the input file by line and stores it for future use
   * 
   */
  public void initialize() throws ResourceInitializationException{
    System.out.println(((String) getConfigParameterValue(PARAM_INPUTDIR)).trim());
    file = new File(((String) getConfigParameterValue(PARAM_INPUTDIR)).trim());  
    try {
      fileReader = new BufferedReader(new FileReader(file));
    } catch (FileNotFoundException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    numLine = 0;
    String temp = null;
    contents = new Vector<String>();
    try {
      while((temp = fileReader.readLine())!=null){
        contents.addElement(new String(temp));
        numLine++;
      }
      fileReader.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
    //System.out.println(numLine);
  }
  /**
   * Get the next sentence to process and store it in CAS.
   * 
   */
  @Override
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    String text = contents.elementAt(curLine++);  
    sentence s = new sentence(jcas);
    String[] items = text.split(" ");
    s.setId(items[0].trim());
    s.setText(text.substring(items[0].length()+1, text.length()));
    s.addToIndexes();
  }
  /**
   * Inform the CPE if there are more sentence we need to process. 
   */
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return curLine < numLine;
  }
  
  
  @Override
  public Progress[] getProgress() {
    return new Progress[] { new ProgressImpl(curLine, numLine, Progress.ENTITIES) };
  }
  
  @Override
  public void close() throws IOException {
    
  }
}
