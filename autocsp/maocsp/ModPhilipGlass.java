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
public class ModPhilipGlass extends DefaultHandler {
    
    
    MusicScore score;
    
    int trk;
    int depth;
    int compteur;
    
    int lavar;
    
    
    
    /** Creates a new instance of parser */
    public ModPhilipGlass() {
        super();
// Demandes de renseignements propres au mode choisi...
        
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

        // System.out.println("Tempo de base ?");

        // try {Partage.basetempo=Integer.parseInt(in.readLine());} 
        // catch( Exception e ) {Partage.basetempo=140; System.out.println("bug");}
        Partage.basetempo=140;

        // System.out.println("Modificateur de tempo ?");
        // try {Partage.modtempo=Integer.parseInt(in.readLine());} 
        // catch( Exception e ) {Partage.modtempo=2; System.out.println("bug");}
        Partage.modtempo=2;

        // System.out.print("Un tempo maximum de ");System.out.print(Partage.basetempo+Partage.maxdepth*Partage.modtempo);System.out.println(" pourra etre atteint.");

// C' est parti...        
        score = new MusicScore(Partage.lenom, 2, 192);
        Partage.ms=score;
        score.SetInstr(0, 1);
        score.SetInstr(1, 72);
        
        compteur=0;
        depth=0;
        
    }
    
    
    public void startDocument() {
        System.out.println("Debut du document");
        
        score.ChangeTonality(1, true);
    }
    
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {        
         
        
        if (nomnom.equals("choice-point")) {
            score.ChangeTempo(Partage.basetempo+Partage.modtempo*Partage.depth);
        }
        
        if (nomnom.equals("variable")) {
        }
        
        if (nomnom.equals("vardomain")) {
        }
        
        if (nomnom.equals("reduce")) {
        }
        
        if (nomnom.equals("range")) {
        }
        
        if (nomnom.equals("back-to")) {
            score.ChangeMode(!score.GetMinor());
            score.ChangeTempo(Partage.basetempo+Partage.modtempo*Partage.depth);
        }
        
        if (nomnom.equals("new-constraint")) {
            String truc=attr.getValue("cident"); // de la forme c## avec ##= un chiffre
            truc=truc.substring(1); // on vire le c
            int plop=Integer.parseInt(truc);
             if (compteur>0){score.AddDiatonicTriad(1, 0, 3+plop%3, plop%2, 2, compteur*192);}
            compteur=0;
        }
        
        if (nomnom.equals("solution")) {
            score.Synctracks();
            compteur=0;
            score.AddPerc(25, 48, true);
            
        }
        
        if (nomnom.equals("failure")) {
            score.Synctracks();
            compteur=0;
            score.AddPerc(14, 48, true);
        }
        
        if (nomnom.equals("state")) {
            score.ChangeArmure((score.GetArmure()+1) % 14);
            compteur=0;
        }
    }
    
    
    public void endElement(String uri, String nom, String truc) throws SAXException {
        
        if (truc.equals("vardomain")) {
            
            if (!Partage.isArcCo[Partage.currentvar]) {score.AddDiatonicTriad(0,Partage.min[Partage.currentvar],2,0,3,192);compteur++;}
            
            else {
                
                int cpt=0;
                for (int i=0;i<256;i++) {
                    if (Partage.arcco[Partage.currentvar][i]) {
                        cpt++;
                    }
                }

                if (cpt<8 & cpt>0) {
                    for (int i=0;i<256;i++) {
                        if (Partage.arcco[Partage.currentvar][i]) {
                            score.AddNote(0, (i+Partage.min[Partage.currentvar])%24, 3, 192/cpt, 0);
                        }
                    }
                    compteur++;
                }
                else {score.AddDiatonicTriad(0,Partage.min[Partage.currentvar]+1,2,0,3,192);compteur++;}
            }
            
        }
        
        
        if (truc.equals("reduce")) {
        }
        
        if (truc.equals("back-to")) {
            score.ChangeTempo(Partage.basetempo+Partage.modtempo*Partage.depth);
        }
        
        if (truc.equals("choice-point")) {
            score.ChangeTempo(Partage.basetempo+Partage.modtempo*Partage.depth);
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
