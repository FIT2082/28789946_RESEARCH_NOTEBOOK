/*
 * PreAnalyser.java
 *
 * Created on 24 mars 2005, 10:36
 */

package maocsp;

/**
 *
 * @author jamian
 */
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;

public class PreAnalyser extends DefaultHandler{
    
    private int nbelem;
    private String lavar; // ident de la variable de laquelle on parle
    private String lacontr; // ident de la contrainte de laquelle on parle
    
    /** Creates a new instance of PreAnalyser */
    public PreAnalyser() {
        lavar="";
        lacontr="";
        
        nbelem=0;
    }
    
    public void startDocument() {
        Partage.varlist = new ArrayList();
        Partage.constrlist= new ArrayList();
        Partage.nodelist = new ArrayList();
    }
    
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {
        
        nbelem++;
        if (nomnom.equals("new-variable")) {
            Variable hop = new Variable(attr.getValue("vident"),attr.getValue("vinternal"),attr.getValue("vname"),0,0);
            Partage.varlist.add(hop);
            lavar=attr.getValue("vident");
        }
        
        if (nomnom.equals("variable")) {
            lavar=attr.getValue("vident");
            int i=0;
            while (  !lavar.equals(((Variable)(Partage.varlist.get(i))).ident)    ) {
                i++;
            }
            ((Variable)(Partage.varlist.get(i))).noccur++;
        }
        
        if (nomnom.equals("choice-point")) {
            int i = Integer.parseInt(attr.getValue("depth"));
            if (i>Partage.maxdepth) {Partage.maxdepth=i;}
            Partage.nodelist.add("Choice point at : ");
            Partage.nodelist.add(new Integer(nbelem));
        }
        
        if (nomnom.equals("back-to")) {
            int i = Integer.parseInt(attr.getValue("depth"));
            if (i>Partage.maxdepth) {Partage.maxdepth=i;}
            Partage.nodelist.add("BackTracking at : ");
            Partage.nodelist.add(new Integer(nbelem));
        }
        
        if (nomnom.equals("solution")) {
            int i = Integer.parseInt(attr.getValue("depth"));
            if (i>Partage.maxdepth) {Partage.maxdepth=i;}
        }
        
        if (nomnom.equals("failure")) {
            int i = Integer.parseInt(attr.getValue("depth"));
            if (i>Partage.maxdepth) {Partage.maxdepth=i;}
        }
                        
        if (nomnom.equals("new-constraint")) {
            lacontr=attr.getValue("cident");
            Partage.constrlist.add(new String(lacontr));
            int i = Integer.parseInt(attr.getValue("depth"));
            if (i>Partage.maxdepth) {Partage.maxdepth=i;}
        }
        
        if (nomnom.equals("vardomain")) {
            int i=0;
            while (  !lavar.equals(((Variable)(Partage.varlist.get(i))).ident)    ) {
                i++;
            }
            int lo=Integer.parseInt(attr.getValue("min"));
            int hi=Integer.parseInt(attr.getValue("max"));
            
            if ( ((Variable)(Partage.varlist.get(i))).lowbound>lo ) {
                ((Variable)(Partage.varlist.get(i))).lowbound=lo;
            }
            
            if ( ((Variable)(Partage.varlist.get(i))).highbound<hi ) {
                ((Variable)(Partage.varlist.get(i))).highbound=hi;
            }
            
        }
    }
    
    
    public void endElement(String uri, String nom, String truc) throws SAXException {
    }
    
    
    public void endDocument() {
        Partage.nbelem=nbelem;
        Partage.nbvars=Partage.varlist.size();

    }
    
}
