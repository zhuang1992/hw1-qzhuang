package Type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;

public class gene extends Annotation {

  public final static int typeIndexID = JCasRegistry.register(gene.class);

  public final static int type = typeIndexID;

  public int getTypeIndexID() {
    return typeIndexID;
  }

  protected gene() {
  }

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

  private void readObject() {
  }

}
