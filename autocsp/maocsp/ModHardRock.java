/*
 * ModHardRock.java
 *
 * Created on 22 mars 2005, 13:05
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
public class ModHardRock extends DefaultHandler {
    
    
    MusicScore score;
    int deltaRythme;
    int trk;
    int compteur;
    int maxdepth;
    int lavar;
    int depthbefore;
    DrumPattern batts[];
    ArrayList amettre;
    
    
    /** Creates a new instance of parser */
    public ModHardRock() {
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

        System.out.print("Un tempo maximum de ");System.out.print(Partage.basetempo+Partage.maxdepth*Partage.modtempo);System.out.println(" pourra etre atteint.");

// C' est parti...        
        
        score = new MusicScore(Partage.lenom, 3, 192);
        Partage.ms=score;
        score.SetInstr(0, 30);  // Guitare lead
        score.SetInstr(1, 31);  // Guitare rythmique
        score.SetInstr(2,33);   // Basse
        maxdepth=Partage.maxdepth;
        if (maxdepth<8) {maxdepth=8;}
        amettre=new ArrayList();
        depthbefore=0;
        
        batts = new DrumPattern[8];   // A modifier en fonction de l' inspiration...
        
        for (int i=0;i<8;i++) {
            batts[i] = new DrumPattern();
        }
        MHRDrumInit.Remplir(batts);
        compteur=0;
        deltaRythme=0;
        
    }
    
    
    public void startDocument() {
        System.out.println("Debut du document");
        score.ChangeTempo(Partage.basetempo);
        score.ChangeTonality(1, true);
    }
    
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {

        if (nomnom.equals("choice-point")) {
            FillDrums(depthbefore);
            while ((score.GetTrkLength(2)+47) < score.GetTrkLength(0)) {
                score.AddNote(2, 0, 1, 96, 0);
            }
//            score.ChangeArmure((score.GetArmure()+2)%7);
            score.ChangeArmure(selectGamme(Integer.parseInt(attr.getValue("nident"))%15));
            score.ChangeTempo(Partage.basetempo+Partage.modtempo*Partage.depth);
            depthbefore=Partage.depth+1;
        }
        
        if (nomnom.equals("variable")) {
        }
        
        if (nomnom.equals("vardomain")) {
        }
        
        if (nomnom.equals("reduce")) {
        }
        
        if (nomnom.equals("range")) {
            int from=Integer.decode(attr.getValue("from"));
            int to=Integer.decode(attr.getValue("to"));
            if (Partage.vardomain) {
                if (from<200) {amettre.add(new Integer(from));}
                if (to<200) {amettre.add(new Integer(to));}
            }
            else {                                      // Si range ne dcrit pas un domaine de variable, il dcrit un delta...
                if (to-from>8) {to=from+8;}             // Car il est possible d' avoir une rduction de 100 milliards de valeurs...
                while ((score.GetTrkLength(2)+(to-from+1)*48) < score.GetTrkLength(0)) {
                    score.AddNote(2, 0, 1, 96, 0);
                }
                int aposer=to-from+1;
                while ((score.GetTrkLength(2)) < score.GetTrkLength(0)) {
                    score.AddNote(2, aposer, 1, 48, 0);
                    aposer--;
                }
            }
        }
        
        if (nomnom.equals("back-to")) {
            FillDrums(depthbefore);
            while ((score.GetTrkLength(2)+47) < score.GetTrkLength(0)) {
                score.AddNote(2, 0, 1, 96, 0);
            }

//            score.ChangeArmure((score.GetArmure()+2)%7);
            score.ChangeArmure(selectGamme(Integer.parseInt(attr.getValue("node"))%15));
            score.ChangeTempo(Partage.basetempo+Partage.modtempo*Partage.depth);
            depthbefore=Partage.depth+1;

        }
        
        if (nomnom.equals("new-constraint")) {
        }
        
        if (nomnom.equals("solution")) {
        }
        
        if (nomnom.equals("failure")) {
        }
    }
    
    
    public void endElement(String uri, String nom, String truc) throws SAXException {
        
        if (truc.equals("vardomain")) {
            int calculRythm=deltaRythme++;
            deltaRythme=deltaRythme%6;
            if (amettre.size()>0) {
                int dur=192/amettre.size();
                for (int i=0;i<amettre.size();i++) {
                    int plop=((Integer)(amettre.get(i)));
                    calculRythm+=plop;
                    while (plop>17) {plop-=8;}
                    score.AddNote(0, plop, 3, dur, 0);
                }
            
                amettre=new ArrayList();

                switch (calculRythm%2) {                    // Sur un temps, la guitare rythmique ne fais jamais de silence
                    case 0 : AddEtouffe(1,0,2,96,0); break; 
                    case 1 : AddPowerChord(1,2,96); break;
                }

                switch ((calculRythm/2) % 2) {              // Et pas d' accord de puissance sur un contretemps...
                    case 0 : AddEtouffe(1,0,2,96,0); break;
                    case 1 : score.AddSilence(1, 96);
                }
            }
        }
        
        
        if (truc.equals("reduce")) {
        }
    
        if (truc.equals("state")) {
            FillDrums(Partage.depth);
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
    
    
    
    // ... PLus des classes dont on a besoin dans celles du parser...
    
    /** Retourne une gamme de sorte que la toniue de la gamme n soit proche de celle de la gamme (n+1)%15 pour tout n*/
    private int selectGamme(int n) {
        // ceci est dfini trs arbitrairement...
        switch (n) {                    // Maj  Min
            case 0 : return 0;    // Do   La
            case 1 : return 14;  // Do#  La#
            case 2 : return 5;    // Reb  Sib
            case 3 : return 9;   // Re   Si
            case 4 : return 3;    // Mib  
            case 5 : return 11;   // Mi   
            case 6 : return 1;   // Fa   
            case 7 : return 13;  // Fa#  
            case 8 : return 6;    // Solb 
            case 9 : return 8;    // Sol  
            case 10 : return 4;   // Lab  
            case 11 : return 10;  // La   
            case 12 : return 2;   // Sib  
            case 13 : return 12;  // Si   
            case 14 : return 7;   // Dob  
            default : return 0;
        }
        
    }
    
    private  void AddPowerChord(int trak, int octave, int duree) {
        
        score.AddGenericChord(trak, 3, true, false, false, false, true, false, false, 0, octave, duree);
    }
    
    
    private void AddEtouffe(int trak, int zenote, int octave, int duree, int accidental) {
        
        if (duree<=24) {score.AddNote(trak, zenote, octave, duree, accidental);}
        else {
            score.AddNote(trak, zenote, octave, 24, accidental);
            score.AddSilence(trak, duree-24);
        }
        
    }
    
    private void FillDrums(int dep) {
        int i=dep*7/maxdepth;
        int aremplir=score.GetTrkLength(0)-score.GetPercLength();
        if (aremplir>0) {
            batts[i].WritePattern(score, aremplir);
        }
    }
    
}
