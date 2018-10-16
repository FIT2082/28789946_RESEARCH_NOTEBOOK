/*
 * ModReggae.java
 *
 * Created on 15 mai 2005, 21:49
 */

package maocsp;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author jamian
 */
public class ModReggae extends DefaultHandler {

    
    MusicScore score;
    int cpt;
    ArrayList amettre;
 
    
    /** Creates a new instance of ModReggae */
    public ModReggae() {
        super();
        score=new MusicScore(Partage.lenom,4,192);
        score.SetInstr(0, 26); //Guitare
        score.SetInstr(1,34); // Basse
        score.SetInstr(2, 17); // orgue jazz
        score.SetInstr(4,54); // voix
        cpt=0;
    }
    
    
    public void startDocument() {
    }
    
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {
        
        if (nomnom.equals("choice-point")) {
        }
        
        if (nomnom.equals("variable")) {
        }
        
        if (nomnom.equals("vardomain")) {
        }
        
        if (nomnom.equals("reduce")) {
            
        }
        
        if (nomnom.equals("range")) {
            if (Partage.reduce) {
                if (Partage.vardomain) {
                    
                }
            }
        }
        
        if (nomnom.equals("back-to")) {
            fillGuitar(cpt);
            score.ChangeMode(!score.GetMinor());
        }
        
        if (nomnom.equals("new-constraint")) {
        }
        
        if (nomnom.equals("solution")) {
        }
        
        if (nomnom.equals("failure")) {
        }
        
        if (nomnom.equals("state")) {
        }
        
        if (nomnom.equals("delta")) {}
    }
    
    
    public void endElement(String uri, String nom, String truc) throws SAXException {
        
        if (truc.equals("vardomain")) {
            
        }
        
        
        if (truc.equals("reduce")) {
        }
        
        if (truc.equals("delta")) {
        }
        
    }
    
    
    public void endDocument() {
    }    
    
    private void fillGuitar(int temps) {
        for (int i=temps;i>0;i--) {
            score.AddNote(1, 0, 3, 96, 0);
            int gt=Partage.currentcontr;
            gt=gt%7;
            score.AddDiatonicTriad(1, gt, 3, Partage.currentcontr%3, 3, 96);
            
            
        }            
    }
}
