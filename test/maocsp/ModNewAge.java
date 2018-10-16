/*
 * parser.java
 *
 * Created on 16 mars 2005, 14:53
 */

package maocsp;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
        
/**
 *
 * @author jamian
 */
public class ModNewAge extends DefaultHandler {
    
    
    MusicScore score;
    
    int trk;
    int compteur;
    
    int lavar;
    int Chord;
    
    /** Creates a new instance of parser */
    public ModNewAge() {
        super();
// Demandes de renseignements propres au mode choisi...
        
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Tempo de base ?");

        try {Partage.basetempo=Integer.parseInt(in.readLine());} 
        catch( Exception e ) {Partage.basetempo=140; System.out.println("bug");}

        System.out.println("Modificateur de tempo ?");
        try {Partage.modtempo=Integer.parseInt(in.readLine());} 
        catch( Exception e ) {Partage.modtempo=2; System.out.println("bug");}

        System.out.print("Un tempo maximum de ");System.out.print(Partage.basetempo+Partage.maxdepth*Partage.modtempo);System.out.println(" pourra etre atteint.");

// C' est parti...        
        score = new MusicScore(Partage.lenom, 4, 192);
        Partage.ms=score;
        score.SetInstr(0, 47);
        score.SetInstr(1, 49);
        score.SetInstr(2, 53);
        score.SetInstr(3, 101);
        
        compteur=0;
    }
    
    
    public void startDocument() {
        score.ChangeTonality(1, true);
    }
    
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {
        
        if (nomnom.equals("choice-point")) {
            
            if (compteur!=0){score.AddDiatonicTriad(1, 0, Chord%4, Chord%2, 2, compteur);};
            compteur=0;
            score.ChangeTempo(Partage.basetempo+Partage.depth*Partage.modtempo);
        }
        
        if (nomnom.equals("variable")) {
        }
        
        if (nomnom.equals("vardomain")) {
        }
        
        if (nomnom.equals("reduce")) {
        }
        
        if (nomnom.equals("range")) {
            int from=Integer.parseInt(attr.getValue("from"));
            int to=Integer.parseInt(attr.getValue("to"));

            if (Partage.delta) {
                int dif = score.GetTrkLength(0)-(score.GetTrkLength(2)+192);
                if (dif>0) {
                    score.AddSilence(2, dif);
                }
                else {
                    score.AddSilence(0,0-dif);
                    score.AddSilence(1,0-dif);
                    score.AddPercSilence(0-dif);
                }
                score.AddNote(2, from%7, 3, 96, 0);
                score.AddNote(2, to%7, 3, 96, 0);
            }
        }
        
        if (nomnom.equals("back-to")) {
            if (compteur!=0) {score.AddDiatonicTriad(1, 0, Chord%4, Chord%2, 2, compteur);}
            score.ChangeMode(!score.GetMinor());
            compteur=0;
            score.ChangeTempo(Partage.basetempo+Partage.depth*Partage.modtempo);
        }
        
        if (nomnom.equals("new-constraint")) {
            String truc=attr.getValue("cident"); // de la forme c## avec ##= un chiffre
            truc=truc.substring(1); // on vire le c
            Chord=Integer.parseInt(truc);
            if (compteur!=0) {score.AddDiatonicTriad(1, 0, Chord%4, Chord%2, 2, compteur);};
            compteur=0;
        }
        
        if (nomnom.equals("solution")) {
            if (compteur!=0) {score.AddDiatonicTriad(1, 0, Chord%4, Chord%2, 2, compteur);};
            compteur=0;
            score.Synctracks();
            score.AddGenericChord(3, 2, true, false,false,false, true, false, false, 0, 2, 192*4);
            
        }
        
        if (nomnom.equals("failure")) {
            if (compteur!=0) {score.AddDiatonicTriad(1, 0, Chord%4, Chord%2, 2, compteur);};
            compteur=0;
            score.Synctracks();
        }
        
        if (nomnom.equals("state")) {
            if (compteur!=0) {score.AddDiatonicTriad(1,0, Chord%4, Chord%2, 2,compteur);};
            compteur=0;
            score.ChangeArmure((score.GetArmure()+1) % 14);
        }
        
        if (nomnom.equals("delta")) {}
    }
    
    
    public void endElement(String uri, String nom, String truc) throws SAXException {
        
        if (truc.equals("vardomain")) {
            
            if (!Partage.isArcCo[Partage.currentvar]) {score.AddGenericChord(0, 2, true, false,false, false, true, false, false, 0, 3, 192); compteur +=192;}
            
            else {
                
                int cpt=0;
                for (int i=0;i<256;i++) {
                    if (Partage.arcco[Partage.currentvar][i]) {
                        cpt++;
                    }
                }

                if (cpt<16) {
                    for (int i=0;i<256;i++) {
                        if (Partage.arcco[Partage.currentvar][i]) {
                            score.AddNote(0, i+Partage.min[Partage.currentvar], 3, 192/cpt, 0);
                            compteur += 192/cpt;
                        }
                    }
                }
                else {
                    score.AddGenericChord(0, 2, true, false,false, false, true, false, false, 0, 3, 192); compteur +=192;
                }
            }
            
        }
        
        
        if (truc.equals("reduce")) {
        }
        
        if (truc.equals("delta")) {
        }
        
    }
    
    
    public void endDocument() {
        System.out.println("Hop ! Fini !");
    }
    
    public void characters(char[] ch, int debut, int fin) {}
    public void skippedEntity(String bof) {}
    public void processingInstruction(String target, String data) {}
    public void ignorableWhitespace(char[] plop,int debut,int fin) {}
    public void endPrefixMapping(String truc) {}
    public void startPrefixMapping(String truc, String auttruc) {}
    public void setDocumentLocator(Locator l) {}
    
}
