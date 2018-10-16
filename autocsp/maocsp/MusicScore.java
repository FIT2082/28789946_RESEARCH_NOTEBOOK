/*
 * MusicScore.java
 *
 * Created on 27 fvrier 2005, 11:49
 *
 * Cette classe permet de faire toutes les gammes  7 tons que l' on dsire, de dfinir des accords sur la gamme existante.
 *
 */

package maocsp;

/**
 *
 * @author jamian
 */

public class MusicScore {
    
    
    private boolean minor;  // true si on est en gamme mineure, false si on est en gamme majeure
    
    private int armure;     // Armure du morceau (ou de la partie du morceau en cours) : 
                            // 0 : rien     8 : 1#
                            // 1 : 1 b      9 : 2#
                            // 2 : 2 b      10: 3#
                            // 3 : 3 b      11: 4#
                            // 4 : 4 b      12: 5#
                            // 5 : 5 b      13: 6#
                            // 6 : 6 b      14: 7#
                            // 7 : 7 b      
    
    
    private int tonique;    // Note tonique de la gamme en cours. Mise  jour ds que l' armure ou le mode (mineur ou majeur) est modifi.
    
    private int[] gamme;  // gamme[x] : xieme note de la gamme en cours (modulo l' octave)
    
    private Midifile zemidi;
    
    /** Permet de dfinir la gamme en cours. Parametres : la tonique et les intervalles entre chaque notes */
    public void SetGamme(int ton, int i1, int i2, int i3, int i4, int i5, int i6) {
        
        int toni=ton;
        while(toni>11) {toni=toni-12;}    // On remet la tonique donne sur la premire octave.
        tonique=toni;
        
        gamme[0]=tonique;
        gamme[1]=tonique+i1;
        gamme[2]=tonique+i1+i2;
        gamme[3]=tonique+i1+i2+i3;
        gamme[4]=tonique+i1+i2+i3+i4;
        gamme[5]=tonique+i1+i2+i3+i4+i5;
        gamme[6]=tonique+i1+i2+i3+i4+i5+i6;
        
        zemidi.SyncTracks();            // Pour viter qu' une piste dans la gamme prcdente ne soit joue avec une autre piste dans la nouvelle gamme, ce serait moche...
    }
    
    /** Renvoie le mode de la gamme actuellement utilise (true-> mineur, false->majeur)*/
    public boolean GetMinor() {return minor;}
    
    /** Renvoie l' armure de la gamme actuellement utilise*/
    public int GetArmure() {return armure;}
    
    /** Calcule une gamme ionienne ou dorienne en fonction de l' armure et du mode prcdamment dfini*/
    private void CalculGamme() {
        
        int ton=0;
        switch (armure) {
            case 0: if (minor) {ton=9;} else {ton=0;}; break;      // rien                          => DoM ou lam
            case 1: if (minor) {ton=2;} else {ton=5;}; break;      // Sib                           => FaM ou Rem
            case 2: if (minor) {ton=7;} else {ton=10;}; break;     // Sib Mib                       => SibM ou Solm
            case 3: if (minor) {ton=0;} else {ton=3;}; break;      // Sib Mib Lab                   => MibM ou Dom
            case 4: if (minor) {ton=5;} else {ton=8;}; break;      // Sib Mib Lab Reb               => LabM ou Fam
            case 5: if (minor) {ton=10;} else {ton=1;}; break;     // Sib Mib Lab Reb Sob           => RebM ou Sibm
            case 6: if (minor) {ton=3;} else {ton=6;}; break;      // Sib Mib Lab Reb Sob Dob       => SobM ou Mibm
            case 7: if (minor) {ton=8;} else {ton=11;}; break;     // Sib Mib Lab Reb Sob Dob Fab   => DobM ou Labm
            
            case 8: if (minor) {ton=4;} else {ton=7;}; break;      // Fa#                           => SolM ou Mim
            case 9: if (minor) {ton=11;} else {ton=2;}; break;     // Fa# Do#                       => ReM ou Sim
            case 10: if (minor) {ton=6;} else {ton=9;}; break;     // Fa# Do# So#                   => LaM ou Fa#m
            case 11: if (minor) {ton=1;} else {ton=4;}; break;     // Fa# Do# So# Re#               => MiM ou Do#m
            case 12: if (minor) {ton=8;} else {ton=11;}; break;    // Fa# Do# So# Re# La#           => SiM ou Sol#m
            case 13: if (minor) {ton=3;} else {ton=6;}; break;     // Fa# Do# So# Re# La# Mi#       => Fa#M ou Re#m
            case 14: if (minor) {ton=10;} else {ton=1;}; break;    // Fa# Do# So# Re# La# Mi# Si#   => Do#M ou La#m
        }
        
        if (minor) {
            SetGamme(ton,2,1,2,2,1,2);
        }
        else {
            SetGamme(ton,2,2,1,2,2,2);
        }
    }
    
    
    
    
    
    /** Creates a new instance of MusicScore */
    public MusicScore(String nom,int pistes,int div) {
        
        minor=false;    // Gamme majeure
        armure=0;       // avec armure vide
        
        gamme=new int[7]; // 7 notes dans une gamme. Je simplifie cette classe au solfege de base, 
                          // Exit les gammes pentatoniques et octotoniques......
                          // A ce propos, exit aussi les commas turques, mais mme le MIDI n' a pas t prvu pour  la base...
        
        zemidi=new Midifile(nom,pistes,div);    // A voir : gestion automatique du nombre de  divisions par noire ? 
        CalculGamme();
        
        
    }

    /** renvoie le nombre de pistes instrument que contient la partition*/
    public int GetNumTrk() {
        return zemidi.GetNumTrk();
    }
    
    /** Renvoie la longueur de la piste zetrak en nombre de divisions lmentaires*/
    public int GetTrkLength(int zetrak) {
        return zemidi.GetTrkLength(zetrak);
    }

    /** Renvoie la longueur de la piste batterie en nombre de divisions lmentaires*/
    public int GetPercLength() {
        return zemidi.GetPercLength();
    }
    
    /** Ajoute un vnement texte au fichier*/
    public void addText(String zetext) {
        zemidi.addText(zetext);
    }

     /** Synchronise les pistes en ajoutant des silences aux pistes les plus courtes*/
    public void Synctracks() {
        zemidi.SyncTracks();
    }
    
    /** change d' armure avec la valeur place en parametre pour les futures notes poses.
     ATTENTION : Recalcule la gamme automatiquement. Exit toute gamme personalise avant...*/
    public void ChangeArmure(int zenew) {
        armure=zenew;
        CalculGamme();
    }
    
    /** Change de mode (mineur/majeur) pour les prochaines notes. L' armure reste la mme
     ATTENTION : Recalcule la gamme automatiquement. Exit toute gamme personalise avant...*/
    public void ChangeMode(boolean zenew) {
        minor=zenew;
        CalculGamme();
    }
    
    /** permet de changer en mme temps l' armure et le mode
     ATTENTION : Recalcule la gamme automatiquement. Exit toute gamme personalise avant...*/
    public void ChangeTonality(int arm, boolean mod) {
        armure=arm;
        minor=mod;
        CalculGamme();
    }
    
    /** Change le tempo de la musique. ATTENTION : Fait un SyncTracks */
    public void ChangeTempo(int newtempo) {
        zemidi.ChangeTempo(newtempo);
    }
    
    
    
    /** Ajoute une note au morceau, sur la piste indique
     * zenote : degr de la note, trak : piste sur laquelle poser la note, octave : octave de la note,
     * duree : dure de la note en nombre de divisions lmentaires,
     * accidental : alteration accidentelle de la note (-1 pour bmol, 1 pour diese, 0 pour ne pas altrer la note)
     * */
    public void AddNote(int trak, int zenote, int octave, int duree, int accidental) {
        int acc;
        if (accidental>1 | accidental<-1) {acc=0;} else {acc=accidental;}
        int zeoctave=octave;
        while (zenote>6) {zenote=zenote-7; zeoctave++;}
        zemidi.AddNote(trak, gamme[zenote]+12*zeoctave+acc, duree, false);
    }
    
    /** Ajoute un accord de nbnotes notes sur la piste trak. POur chaque degr de la gamme, true indique la prsence ventuelle de la note dans l' accord*/
    public void AddGenericChord(int trak, int nbnotes, boolean T, boolean ST, boolean M, boolean sD, boolean D, boolean SD, boolean S, int renv, int octave, int duree) {
        
        boolean[] chrd=new boolean[7];
        chrd[0]=T;          // true si la premiere note de la gamme fait partie de l' accord
        chrd[1]=ST;         // true si la seconde  note de la gamme fait partie de l' accord
        chrd[2]=M;          // Etc........
        chrd[3]=sD;
        chrd[4]=D;
        chrd[5]=SD;
        chrd[6]=S;
        
        
        int notesLeftToPlace=nbnotes;
        int currentNote=0;
        int i=renv+1;
        while (i>0) {
            if (chrd[currentNote]) {i--;}
            currentNote++; if (currentNote>6) {currentNote=0;}
        }
        currentNote--; if (currentNote<0) {currentNote=6;}
        int currentOctave=octave;
        
        while (notesLeftToPlace>1) {
            zemidi.AddNote(trak, gamme[currentNote]+12*currentOctave, duree, true);
            notesLeftToPlace--;
            currentNote++;if(currentNote>6) {currentNote=0;currentOctave++;}
            while(!chrd[currentNote]) {
                currentNote++;if(currentNote>6) {currentNote=0;currentOctave++;}
            }
        } 
        zemidi.AddNote(trak, gamme[currentNote]+12*currentOctave, duree, false);       // Derniere note de l' accord
    }
    
    /** Ajoute un accord sur la piste trak. Cet accord sera uen triade diatonique si nbnotes est gal  3.
     * Number dfinit le degr de base de l' accord, renv son renversement*/
    public void AddDiatonicTriad(int trak, int number, int nbnotes, int renv, int octave, int duree) {
        
        if (number<0) {AddDiatonicTriad(trak,number+7,nbnotes,renv,octave-1,duree);}
        else {
            if (number>6) {AddDiatonicTriad(trak,number-7,nbnotes,renv,octave+1,duree);}
            else {
                
                switch (number) {
                    case 0: AddGenericChord(trak,nbnotes,true,false,true,false,true,false,false,renv,octave,duree); break;
                    case 1: AddGenericChord(trak,nbnotes,false,true,false,true,false,true,false,renv,octave,duree); break;
                    case 2: AddGenericChord(trak,nbnotes,false,false,true,false,true,false,true,renv,octave,duree); break;
                    case 3: AddGenericChord(trak,nbnotes,true,false,false,true,false,true,false,renv+1,octave,duree); break;
                    case 4: AddGenericChord(trak,nbnotes,false,true,false,false,true,false,true,renv+1,octave,duree); break;
                    case 5: AddGenericChord(trak,nbnotes,true,false,true,false,false,true,false,renv+2,octave,duree); break;
                    case 6: AddGenericChord(trak,nbnotes,false,true,false,true,false,false,true,renv+2,octave,duree); break;
                }
            }
        }
    }
    
    /** Ajoute un silence de duree divisions lmentaires  la piste trak */
    public void AddSilence(int trak,int duree) {
        zemidi.AddSilence(trak, duree);
    }
    
    /** Dfinit l' instrument de la piste trak (l' instrument doit tre compris entre 1 et 128 et correspond  la base MIDI 1*/
    public void SetInstr(int trak, int instr) {
        zemidi.SetInstr(trak, instr);
    }
    
    /** Ajoute une percussion, joue en mme temps que la suivante. La prochaine sera joue 'duree' divisions apres*/
    public void AddPerc(int percu, int duree, boolean ensemble) {                       // A FAIRE : 
        zemidi.AddPerc(percu, duree, ensemble);                                         // Trouver un meilleur moyen de grer les percus
    }                                                                                   // L c' est un peu l' anarchie...
    
    /** dcale la prochaine percussion pose de duree divisions*/
    public void AddPercSilence(int duree) {
        zemidi.AddPercSilence(duree);
    }

    /** Cr le fichier descriptif du fichier MIDI ( passer dans Mididsm pour obtenir un fichier midi*/
    public void MakeFile() {
        zemidi.MakeFile();
    }
}
