/*
 * Midifile.java
 *
 * Created on 17 février 2005, 22:24
 */

package maocsp;
import java.util.*;
import java.io.*;
/**
 *
 * @author jamian
 */
public class Midifile {
    
    
    private int numtrk;             // nombre de pistes
    private int div;                // nombre de divisions élémentaires par noire
    private String nomfich;         // nom du fichier à générer
    
    private int[] instrus;          // tableau des instruments utilisés (instrus.get(i) => instrument de la ieme piste )
    private ArrayList[] partoche;    // tableau des différentes pistes (chaque piste etant une liste de notes)

    private ArrayList batterie;     // Piste de batterie
    
    private ArrayList metas;        // Liste des méta-évènements présents (changement de tempo et texte
    
    private int offset;             // Décalage de la note dans le temps en cours
    private int temps;              // Temps dans la mesure en cours
    private int mesure;             // Mesure en cours
    
    private String nomnotes;
    
    private void IncMesure() {
        mesure++;
    }
    
    private void IncTemps() {
        temps++;
        if (temps>4) {
            temps=1;
            IncMesure();
        }
    }
    
    private void AddOffset(int zeoffset) {
        offset+=zeoffset;
        while (offset>=div) {
            offset-=div;
            IncTemps();
        }
    }
    
    
    /** Creates a new instance of Midifile */
    public Midifile(String nom, int trk, int divi) {
        
        numtrk=trk;
        div=divi;
        nomfich=new String(nom);
        
        batterie=new ArrayList();
        instrus=new int[trk];
        partoche=new ArrayList[trk];
        metas=new ArrayList();
        for (int i=0; i<trk; i++) {
            instrus[i]=1;                           // Instrument par defaut : Piano 1 (GM numero 1)
            partoche[i]=new ArrayList();
        }
        
        nomnotes="C 0 C#0 D 0 D#0 E 0 F 0 F#0 G 0 G#0 A 0 A#0 B 0 C 1 C#1 D 1 D#1 E 1 F 1 F#1 G 1 G#1 A 1 A#1 B 1 C 2 C#2 D 2 D#2 E 2 F 2 F#2 G 2 G#2 A 2 A#2 B 2 C 3 C#3 D 3 D#3 E 3 F 3 F#3 G 3 G#3 A 3 A#3 B 3 C 4 C#4 D 4 D#4 E 4 F 4 F#4 G 4 G#4 A 4 A#4 B 4 C 5 C#5 D 5 D#5 E 5 F 5 F#5 G 5 G#5 A 5 A#5 B 5 C 6 C#6 D 6 D#6 E 6 F 6 F#6 G 6 G#6 A 6 A#6 B 6 C 7 C#7 D 7 D#7 E 7 F 7 F#7 G 7 G#7 A 7 A#7 B 7 C 8 C#8 D 8 D#8 E 8 F 8 F#8 G 8 G#8 A 8 A#8 B 8 C 9 C#9 D 9 D#9 E 9 F 9 F#9 G 9 G#9 A 9 A#9 B 9 C 10C#10D 10D#10E 10F 10F#10G 10";
                //|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |  Ouf c' est bon, pas de décalages :)
    }

    /** Ajoute une note à la piste trak, en accord avec la suivante ou non*/
    public void AddNote(int trak,int pitch,int duree,boolean accord) {
        note toadd=new note(pitch,duree,accord);
        partoche[trak].add(toadd);
    }
    
    /** Renvoie le nombre de pistes instrument que contient le fichier à créer*/
    public int GetNumTrk() {
        return numtrk;
    }
    
    /** Ajoute une percussion, jouée en même temps que la suivante. La prochaine sera jouée 'duree' divisions apres*/
    public void AddPerc(int percu,int duree,boolean ensemble) {
        note toadd=new note(percu,duree,ensemble);
        batterie.add(toadd);
    }
    
    /** décale la prochaine percussion posée de duree divisions*/
    public void AddPercSilence(int duree) {
        AddPerc(128,duree,false);
    }

    /** Ajoute un silence à la piste trak de duree divisions*/
    public void AddSilence(int trak, int duree) {
        AddNote(trak,128,duree,false);
    }

    /** Définit l' instrument qui jouera la piste trak*/
    public void SetInstr(int trak,int instru) {
        if (trak<numtrk & instru<=127) {
            instrus[trak]=instru;
        }
    }
    
    /** renvoie la longueur d' une piste (en nombre de division élémentaires)*/
    public int GetTrkLength(int zetrak) {
        if (zetrak>=numtrk) {
            return -1;
        }
        else {
            int compteur=0;
            for (int i=0;i< partoche[zetrak].size();i++) {
                if (!((note)(partoche[zetrak].get(i))).inachord) {
                    compteur += ((note)(partoche[zetrak].get(i))).duree;
                }
            }
            return compteur;
        }
    }
    
    /** Renvoie la longueur de la piste de batterie (en nombre de divisions élémentaires) */
    public int GetPercLength() {
        
        int compteur=0;
        for (int i=0;i< batterie.size();i++) {
            if (!((note)(batterie.get(i))).inachord) {
                compteur += ((note)(batterie.get(i))).duree;
            }
        }
        return compteur;
    }
    
    /** Synchronise toutes les pistes en ajoutant des silences aux pistes les plus courtes*/
    public void SyncTracks() {
        int[] durees = new int[numtrk+2];
        int maxdur=0;
        int dur=0;
        for (int i=0; i<numtrk;i++) {
            dur=GetTrkLength(i);
            durees[i]=dur;
            if (maxdur<=dur) {maxdur=dur;}
        }
        
        dur=0;
        
        for (int j=0;j<batterie.size();j++) {
            if (!((note)(batterie).get(j)).inachord) {            
                dur+=((note)(batterie.get(j))).duree;
            }
        }
        
        durees[numtrk]=dur;
        if (maxdur<=dur) {maxdur=dur;}
        
        dur=0;
        for (int j=0;j<metas.size();j++) {            
            dur+=((MetaEvent)(metas.get(j))).duree;
        }
        
        durees[numtrk+1]=dur;
        
        if (maxdur<=dur) {maxdur=dur;}
        
        for (int i=0;i<numtrk;i++) {
            AddSilence(i,(maxdur-durees[i]));
        }
        
        AddPercSilence(maxdur-durees[numtrk]);
        
        {
            MetaEvent rien=new MetaEvent("Rien");
            rien.duree=maxdur-durees[numtrk+1];
            metas.add(rien);
        }
    }
    
    /** Modifie le tempo de la musique. Cette méthode ne synchronise pas les pistes. Le changement s' effectue à la fin de la dernière note de la piste la plus longue */
    public void ChangeTempo(int newtempo) {
        
//        SyncTracks();
        int delta=GetPercLength();
        for (int i=0;i<numtrk;i++) {
            if (delta<GetTrkLength(i)) {delta=GetTrkLength(i);}
        }
        MetaEvent rien=new MetaEvent("Rien");
        int metadur=0;
        for (int j=0;j<metas.size();j++) {            
            metadur+=((MetaEvent)(metas.get(j))).duree;
        }
        rien.duree=delta-metadur;
        metas.add(rien);
        
        MetaEvent tempo=new MetaEvent("Tempo");
        tempo.param=newtempo;
        metas.add(tempo);
    }
    
    /** Ajoute un évènement texte au fichier MIDI*/
    public void addText(String zetext) {
        MetaEvent text=new MetaEvent("text");
        text.textparam=new String(zetext);
        metas.add(text);
    }
    
    
    
    
    /** Créé le fichier descriptif du fichier MIDI (à passer dans Mididsm pour obtenir un fichier midi*/
    public void MakeFile() {
        try {
            File f=new File(nomfich);
            FileOutputStream os= new FileOutputStream(f);
            PrintWriter stylo=new PrintWriter(os);
            
            /*En tête du fichier : 
             *Format MIDI 1
             *Nombre de pistes 
             *Nombres de divisions par temps */
            
            stylo.print("MThd | Format=1 | # of Tracks=");
            stylo.print(numtrk+2); // nombre de pistes du fichier MIDI : la piste 0 qui contient tous les meta-events, les numtrk pistes instruments et la piste batterie
            stylo.print(" | Division=");
            stylo.println(div);
            
            
            /*La piste 0 contient tous les Meta-evenements */
            
            stylo.println("Track #0");
            stylo.println(" |Time Sig |  4/4      | ");
            stylo.print("	    |Copyright	 | len=");stylo.print(Partage.copyright.length());stylo.println("   |");
            stylo.print("<");stylo.print(Partage.copyright);stylo.println(">");
            mesure=1; temps=1; offset=0;
            for (int i=0;i<metas.size();i++) {
                
                if ( ((MetaEvent)(metas.get(i))).name.equals("Tempo")  ) {
                    stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                    stylo.print(" Tempo | BPM=");stylo.print(((MetaEvent)(metas.get(i))).param);stylo.println(" | ");
                }
                
                if ( ((MetaEvent)(metas.get(i))).name.equals("text") ) {
                    stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                    stylo.print(" Text | len=");stylo.println(((MetaEvent)(metas.get(i))).textparam.length());
                    stylo.print("<");stylo.print(((MetaEvent)(metas.get(i))).textparam);stylo.println(">");
                }
                AddOffset(((MetaEvent)(metas.get(i))).duree);
 
            }

            
            stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);
            stylo.println(" |End of track|");
            stylo.println();
            
            
            /*Pistes instruments...*/
            
            for (int i=0;i<numtrk;i++) {
                mesure=1; temps=1; offset=0;
                stylo.print("Track #");stylo.println(i+1);
                int canal=(i%9)+1;
                int nbnote=0;
                
                int j=0; // pour se ballader dans la piste en cours...
                
                while (j<partoche[i].size()) {
                    if (((note)(partoche[i]).get(j)).pitch < 128 ) {
                        stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                        stylo.print("Program | chan=");stylo.print(canal);stylo.print(" | pgm #=");stylo.println(instrus[i]);
                        stylo.print("           | on Note | chan=");stylo.print(canal);stylo.print(" | pitch=");
                        stylo.print(nomnotes.substring( (((note)(partoche[i].get(j))).pitch)*4, ((((note)(partoche[i].get(j))).pitch)*4)+4 )); // Dieu que c' est moche... Mais bon, ca marche...
                        stylo.println(" | vol=64");
                    }
                    if ( ((note)(partoche[i].get(j))).inachord ) {
                        nbnote++;
                        j++;
                    }
                    else {
                        AddOffset(((note)(partoche[i].get(j))).duree);
                        if (nbnote!=0) {
                            for (int a=1;a<=nbnote;a++) {j--;}
                            for (int a=0;a<=nbnote;a++) { // On eteinds toutes les notes de l' accord...
                                if (((note)(partoche[i]).get(j)).pitch < 128 ) {
                                    stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                                    stylo.print("Program | chan=");stylo.print(canal);stylo.print(" | pgm #=");stylo.println(instrus[i]);
                                    stylo.print("           | off Note | chan=");stylo.print(canal);stylo.print(" | pitch=");
                                    stylo.print(nomnotes.substring( (((note)(partoche[i].get(j))).pitch)*4, ((((note)(partoche[i].get(j))).pitch)*4)+4 )); // Dieu que c' est toujours moche... Mais bon, ca marche toujours...
                                    stylo.println(" | vol=64");
                                }
                                j++;
                            }
                        }
                        else {
                            if (((note)(partoche[i]).get(j)).pitch < 128 ) {
                                stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                                stylo.print("Program | chan=");stylo.print(canal);stylo.print(" | pgm #=");stylo.println(instrus[i]);
                                stylo.print("           | off Note | chan=");stylo.print(canal);stylo.print(" | pitch=");
                                stylo.print(nomnotes.substring( (((note)(partoche[i].get(j))).pitch)*4, ((((note)(partoche[i].get(j))).pitch)*4)+4 )); // Dieu que c' est toujours moche... Mais bon, ca marche toujours...
                                stylo.println(" | vol=64");
                            }
                            j++;
                        }
                        nbnote=0;
                    }
                }
                AddOffset(div);
                stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);
                stylo.println(" |End of track|");stylo.println();
            }
            
            // Et maintenant, la batterie....
            
            int nbnote=0;
            int j=0;
            offset=0;temps=1;mesure=1;
            stylo.println();
            stylo.print("Track #");stylo.println(numtrk+1);
            
            stylo.println("1: 1:  0  |Program  | chan=10   | pgm #=  1 Grand Piano");
            
            while (j<batterie.size()) {
                
                if (((note)(batterie.get(j))).pitch < 128 ) {
                    stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                    stylo.print(" on Note | chan=10");stylo.print(" | pitch=");
                    stylo.print(nomnotes.substring( (((note)(batterie.get(j))).pitch)*4, ((((note)(batterie.get(j))).pitch)*4)+4 )); // Dieu que c' est moche... Mais bon, ca marche...
                    stylo.println(" | vol=64");
                }
                if ( ((note)(batterie.get(j))).inachord ) {
                        nbnote++;
                        j++;
                    }
                    else {
                        AddOffset(((note)(batterie.get(j))).duree);
                        if (nbnote!=0) {
                            for (int a=1;a<=nbnote;a++) {j--;}
                            for (int a=0;a<=nbnote;a++) { // On eteinds toutes les notes de l' accord...
                                if (((note)(batterie.get(j))).pitch < 128 ) {
                                    stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                                    stylo.print(" off Note | chan=10 | pitch=");
                                    stylo.print(nomnotes.substring( (((note)(batterie.get(j))).pitch)*4, ((((note)(batterie.get(j))).pitch)*4)+4 )); // Dieu que c' est toujours moche... Mais bon, ca marche toujours...
                                    stylo.println(" | vol=64");
                                }
                                j++;
                            }
                        }
                        else {
                            if (((note)(batterie).get(j)).pitch < 128 ) {
                                stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
                                stylo.print(" off Note | chan=10 | pitch=");
                                stylo.print(nomnotes.substring( (((note)(batterie.get(j))).pitch)*4, ((((note)(batterie.get(j))).pitch)*4)+4 )); // Dieu que c' est toujours moche... Mais bon, ca marche toujours...
                                stylo.println(" | vol=64");
                            }
                            j++;
                        }
                        nbnote=0;
                    }
                }
            
            AddOffset(div);
            stylo.print(mesure);stylo.print(" : ");stylo.print(temps);stylo.print(" : ");stylo.print(offset);stylo.print(" | ");
            stylo.println("End of Track|");
            
            
            
            stylo.flush();
            os.close();
        }
        catch (Exception e) {System.err.print("Oups...  "); System.err.print(e);}
        
    }
}
