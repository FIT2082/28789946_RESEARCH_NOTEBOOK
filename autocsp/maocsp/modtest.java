/*
 * modtest.java
 *
 * Created on 10 mai 2005, 00:56
 */

package maocsp;

/**
 *
 * @author jamian
 */
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;

public class modtest extends DefaultHandler {
    
    File f;
    FileOutputStream os;
    PrintWriter stylo;
    /** Creates a new instance of modtest */
    public modtest() {
        super();
        Partage.ms=new MusicScore("nul",1,192);
        try {
            f=new File(Partage.lenom);
            os= new FileOutputStream(f);
            stylo=new PrintWriter(os);
        } catch (Exception e) {System.out.println(e);}
        
        
    }
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {
//        
//        stylo.print("Balise ");
//        stylo.print(nomnom);stylo.print(" ");
//        for (int i=0;i<attr.getLength();i++) {
//            stylo.print(attr.getQName(i));stylo.print("=");stylo.print(attr.getValue(i));stylo.print(" ");
//        }
//        stylo.println();
//        stylo.print("depth=");stylo.println(Partage.depth);
//        if (Partage.delta) {stylo.print("Delta ");}
//        if (Partage.solution) {stylo.print("Solution ");}
//        if (Partage.failure) {stylo.print("Failure ");}
//        if (Partage.cp) {stylo.print("CP ");}
//        if (Partage.backto) {stylo.print("Back_to ");}
//        if (Partage.vardomain) {stylo.print("Vardomain ");}
//        if (Partage.reduce) {stylo.print("Reduce ");}
//        if (Partage.state) {stylo.print("State ");}
//        stylo.println(Partage.nbvar);
//        for (int j=0;j<Partage.nbvars;j++) {
//            stylo.print(j);stylo.print(" ");
//            if (!Partage.encountered[j]) {stylo.println("Pas_encore");}
//            else {
//                stylo.print(Partage.min[j]);
//                if (!Partage.isArcCo[j]) {stylo.print(" notarcco");}
//                else {
//                    stylo.print(" ");
//                    for (int k=0;k<20;k++) {
//                        if (Partage.arcco[j][k]) {stylo.print("1");} else {stylo.print("0");}
//                    }
//                }
//            }
//            stylo.println();
//        }
        if (nomnom.equals("choice-point")) {
            stylo.print("CP Depth=");stylo.println(attr.getValue("depth"));
        }
        
        if (nomnom.equals("back-to")) {
            stylo.print("BT Depth=");stylo.println(attr.getValue("depth"));
        }
        
    }
    
    public void endDocument() {
        stylo.flush();
    }
    
}
