/*
 * ModApocalyptica.java
 *
 * Created on 4 mai 2005, 21:35
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
public class ModApocalyptica extends DefaultHandler{
    
    MusicScore score;
    int compteur;           // Un petit compteur à des fins de syncro entre pistes
    Pattern[] rytm;         // COntiendra les differents pattern rythmiques joués par lengthpremier violoncelle
    ArrayList amettre;
    
    /** Creates a new instance of ModDebug */
    public ModApocalyptica() {

// Demandes de renseignements propres au mode choisi...
        
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Tempo de base ?");

        try {Partage.basetempo=Integer.parseInt(in.readLine());} 
        catch( Exception e ) {Partage.basetempo=140; System.out.println("bug");}

        System.out.println("Modificateur de tempo ?");
        try {Partage.modtempo=Integer.parseInt(in.readLine());} 
        catch( Exception e ) {Partage.modtempo=2; System.out.println("bug");}

        System.out.print("Un tempo maximum de ");System.out.print(Partage.basetempo+Partage.maxdepth*Partage.modtempo);System.out.println(" pourra etre atteint.");

        score=new MusicScore(Partage.lenom,6,192);
        Partage.ms=score;
        compteur=0;
        Partage.nbvar=0;
        score.SetInstr(0,43);   // Apocalyptica - Plays CSPs with four cellos
        score.SetInstr(1, 43);
        score.SetInstr(2, 43);
        score.SetInstr(3,43);
        amettre=new ArrayList();
        score.ChangeTonality(0,false);
        rytm=new Pattern[12];        
        for (int i=0;i<12;i++) {rytm[i]=new Pattern();}
        MARythmInit.Remplir(rytm);

    }
    
    public void startDocument() throws SAXException {
        score.ChangeTempo(Partage.basetempo);
        score.ChangeTonality(1, true);
    }

    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {

        if (nomnom.equals("variable")) {
        }

        if (nomnom.equals("new-variable")) {
            SyncMelodics();
            score.AddNote(2, 0, 2, 48, 0);
            score.AddNote(2, 1, 2, 48, 0);
            score.AddNote(2, 0, 2, 48, 0);
            score.AddNote(2, 1, 2, 48, 0);
        }
        
        if (nomnom.equals("new-constraint")) {
            SyncMelodics();
            score.AddNote(2, 7, 2, 48, 0);
            score.AddNote(2, 4, 2, 48, 0);
            score.AddNote(2, 2, 2, 48, 0);
            score.AddNote(2, 0, 2, 48, 0);
        }
        
        if (nomnom.equals("vardomain")) {
        }
        
        if (nomnom.equals("solved")) {
            SyncMelodics();
            score.AddNote(2, 0, 2, 48, 0);
            score.AddNote(2, 2, 2, 48, 0);
            score.AddNote(2, 4, 2, 48, 0);
            score.AddNote(2, 0, 3, 48, 0);
        }
        
        if (nomnom.equals("solution")) {
        }
        
        if (nomnom.equals("failure")) {
        }
        
        if (nomnom.equals("back-to")) {
            int longueur=score.GetTrkLength(3)-score.GetTrkLength(0);
            rytm[rythmAJouer()].WritePattern(score, longueur, 0, 1);
        }
        
        if (nomnom.equals("choice-point")) {
            int longueur=score.GetTrkLength(3)-score.GetTrkLength(0);
            rytm[rythmAJouer()].WritePattern(score, longueur, 0, 1);
        }
        
        if (nomnom.equals("state")) {
        }
        
        if (nomnom.equals("reduce")) {
            int celong=score.GetTrkLength(3)-score.GetTrkLength(0);
            System.out.println(celong);
            if (celong>0) {
                rytm[rythmAJouer()].WritePattern(score, celong, 0, 1);
            }
        }
        
        if (nomnom.equals("delta")) {
        }
        
        if (nomnom.equals("range")) {
            int from=Integer.parseInt(attr.getValue("from"));
            int to=Integer.parseInt(attr.getValue("to"));
            if (Partage.vardomain) {
                if (from<200) {amettre.add(new Integer(from));}
                if (to<200) {amettre.add(new Integer(to));}                
            }
            if (Partage.delta) {
                score.AddNote(2, from%7, 2, 96, 0);
                score.AddNote(2,to%7,2,96,0);
            }
            
            
        }
        
        if (nomnom.equals("suspend")) {
            String truc=attr.getValue("cident"); // de la forme c## avec ##= un chiffre
            truc=truc.substring(1); // on vire le c
            int val=Integer.parseInt(truc);
            
        }
        
        if (nomnom.equals("awake")) {
            String truc=attr.getValue("cident"); // de la forme c## avec ##= un chiffre
            truc=truc.substring(1); // on vire le c
            int val=Integer.parseInt(truc);
        }
    }
    
    public void endElement(String uri, String nom, String truc) throws SAXException {

        if (truc.equals("vardomain")) {
            if (amettre.size()>0) {
                int dur=192/amettre.size();
                for (int i=0;i<amettre.size();i++) {
                    int plop=((Integer)(amettre.get(i)));
                    while (plop>13) {plop-=7;}
                    score.AddNote(3, plop, 3, dur, 0);
                }
            
                amettre=new ArrayList();
            }

        }
        
        
        if (truc.equals("reduce")) {
        }
        
        if (truc.equals("state")) {
            SyncMelodics();
        }
        
        if (truc.equals("delta")) {
        }
    }
    
    public void endDocument() throws SAXException{
    }
    
    
    private void Gamme1() {
        score.SetGamme(2, 1, 3, 1, 2, 1, 2);
    }
    
    private void Gamme2() {
        score.SetGamme(2, 1, 3,1,2,1,3);
    }
        
    private int rythmAJouer() {
        if (Partage.nbvar==0) {System.out.println("pas de variables");return 0;}
        else {
            int sum=0;
            for (int i=0;i<Partage.varlist.size();i++) {
                if (Partage.encountered[i]) {
                    sum+=Partage.domainSize[i];
                }
            }

            sum=sum/Partage.nbvar;  // sum= moyenne des cardinalités des domaines de variables
            sum--;
            if (sum>11 || sum<0) {sum=11;}
            sum=11-sum;
            return sum;
        }
    }
    
    private void SyncMelodics() {
        int dif=score.GetTrkLength(3)-score.GetTrkLength(2);
        if (dif<0) {score.AddSilence(3, -dif);}
        if (dif>0) {score.AddSilence(2, dif);}
    }
}
