/*
 * ModDebug.java
 *
 * Created on 24 avril 2005, 14:13
 */

package maocsp;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 *
 * @author jamian
 */
public class ModDebug extends DefaultHandler{
    
    MusicScore score;
    int compteur;
    int depth;
    boolean vardomain;
    /** Creates a new instance of ModDebug */
    public ModDebug() {
        score=new MusicScore(Partage.lenom,6,192);
        Partage.ms=score;
        compteur=0;
        score.SetInstr(0,10);       // Un glockenspiel pour les new-variables
        score.SetInstr(1, 1);       // Un piano pour décrire une variable
        score.SetInstr(2, 71);      // Un basson pour les new-constraints
        score.SetInstr(3,62);       // Et des cuivres pour les solutions/failures
        score.SetInstr(4, 81);      // Une onde sinus dont la hauteur indiquera la profondeur de l' arbre de recherche...
        vardomain=false;
    }
    
    public void startDocument() throws SAXException {
    }

    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {

        
        if (nomnom.equals("new-variable")) {
            score.AddNote(0, 0, 3, 64, 0);
            score.AddNote(0, 2, 3, 64, 0);
            score.AddNote(0, 4, 3, 64, 0);
            score.AddNote(0, 7, 3, 192, 0);
            compteur=compteur+384;
            score.AddSilence(1, 384);
            score.AddSilence(2, 384);
            score.AddSilence(3, 384);
        }
        
        if (nomnom.equals("reduce")) {
            score.AddNote(2, 7, 4, 24, 0);
            score.AddNote(2, 6, 4, 24, 0);
            score.AddNote(2, 5, 4, 24, 0);
            score.AddNote(2, 4, 4, 24, 0);
            score.AddNote(2, 3, 4, 24, 0);
            score.AddNote(2, 2, 4, 24, 0);
            score.AddNote(2, 1, 4, 24, 0);
            score.AddNote(2, 0, 4, 24, 0);
            compteur=compteur+192;
            score.AddSilence(0, 192);
            score.AddSilence(1, 192);
            score.AddSilence(3, 192);
            
        }
        if (nomnom.equals("new-constraint")) {
            score.AddNote(2, 4, 2, 96, 0);
            score.AddNote(2, 2, 2, 96, -1);
            score.AddNote(2, 0, 2, 192, 0);
            compteur=compteur+384;
            score.AddSilence(0, 384);
            score.AddSilence(1, 384);
            score.AddSilence(3, 384);
        }
        
        if (nomnom.equals("vardomain")) {
            vardomain=true;
            int size=Integer.parseInt(attr.getValue("size"));
            score.AddNote(1, size, 3, 48, 0);
            compteur=compteur+48;
            score.AddSilence(0,48);
            score.AddSilence(2,48);
            score.AddSilence(3,48);
        }
        
        if (nomnom.equals("solved")) {
            score.AddNote(3,4,2,48,0);
            score.AddNote(3,7,2,48,0);
            score.AddNote(3,9,2,96,0);
            compteur=compteur+192;
            score.AddSilence(0, 192);
            score.AddSilence(1, 192);
            score.AddSilence(2, 192);
        }
        
        if (nomnom.equals("solution")) {
            score.AddGenericChord(3, 2, true, false, false, false, true, false, false, 1, 3, 48);
            score.AddSilence(3, 48);
            score.AddGenericChord(3, 2, true, false, false, false, true, false, false, 1, 3, 48);
            score.AddGenericChord(3, 2, true, false, false, false, true, false, false, 1, 3, 48);
            score.AddGenericChord(3, 2, true, false, false, false, true, false, false, 1, 3, 192);
            compteur=compteur+384;
            score.AddSilence(0, 384);
            score.AddSilence(1, 384);
            score.AddSilence(2, 384);
        }
        
        if (nomnom.equals("failure")) {
            score.AddNote(3,7,1,192,0);
            score.AddNote(3,4,1,192,0);
            score.AddNote(3,4,1,384,-1);
            compteur=compteur+768;
            score.AddSilence(0, 768);
            score.AddSilence(1, 768);
            score.AddSilence(2, 768);
        }
        
        if (nomnom.equals("back-to")) {
            score.AddNote(4, depth, 1, compteur, 0);
            compteur=192; // car on ajoute un temps de batterie après...
            score.Synctracks();
            String info="BT ";
            info=info.concat(attr.getValue("node")).concat(" ").concat(attr.getValue("node-before"));
            score.addText(info);
            depth=Integer.parseInt(attr.getValue("depth"))+1;
            score.AddPercSilence(score.GetTrkLength(0)-score.GetPercLength());
            score.AddPerc(12, 48, false);
            score.AddPerc(50, 96,false);
            score.AddPerc(13,48,false);
            score.AddSilence(0, 192);
            score.AddSilence(1, 192);
            score.AddSilence(2, 192);
            score.AddSilence(3, 192);
        }
        
        if (nomnom.equals("choice-point")) {
            score.AddNote(4, depth, 1, compteur, 0);
            compteur=192; // car on ajoute un temps de batterie apres...
            score.Synctracks();
            String info="CP ";
            info=info.concat(attr.getValue("nident"));
            score.addText(info);
            depth=Integer.parseInt(attr.getValue("depth"))+1;
            score.AddPercSilence(score.GetTrkLength(0)-score.GetPercLength());
            score.AddPerc(21, 64, false);
            score.AddPerc(23, 64,false);
            score.AddPerc(26,64,false);
            score.AddSilence(0, 192);
            score.AddSilence(1, 192);
            score.AddSilence(2, 192);
            score.AddSilence(3, 192);
        }
        
    }
    
    public void endElement(String uri, String nom, String truc) throws SAXException {
    }
    
    public void endDocument() throws SAXException{
    }
}
