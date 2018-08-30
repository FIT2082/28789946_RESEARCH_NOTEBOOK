/*
 * Pattern.java
 *
 * Created on 23 mars 2005, 10:58
 */

package maocsp;

/**
 *
 * @author jamian
 */

import java.util.*;


public class Pattern {
    
    
    
    
    private class Note {
        public int hau;
        public int dur;
        
        public Note(int hauteur, int duree) {
            hau=hauteur;
            dur=duree;
        }
    }
    
    private class Chord {
        public boolean[] zenotes;
        public int dur;
        public int renv;
        public int nbnotes;
        
        public Chord(boolean T, boolean ST, boolean M, boolean sD, boolean D, boolean SD, boolean S, int duree, int renversement, int nombre) {
            zenotes=new boolean[7];
            zenotes[0]=T;
            zenotes[1]=ST;
            zenotes[2]=M;
            zenotes[3]=sD;
            zenotes[4]=D;
            zenotes[5]=SD;
            zenotes[6]=S;
            dur=duree;
            renv=renversement;
            nbnotes=nombre;
        }
    }
    
    private class Silence {
        public int duree;
        
        public Silence(int dur) {
            duree=dur;
        }
    }
    
    ArrayList elems;
    /** renvoie la durée d' un element du pattern (Note ou Chord*/
    private int GetElemLength(int zeelem) {
        if (elems.get(zeelem).getClass()==Note.class) {
            return ((Note)(elems.get(zeelem))).dur;
        }
        else {
            if (elems.get(zeelem).getClass()==Chord.class) {
                return ((Chord)(elems.get(zeelem))).dur;
            }
            else {return ((Silence)(elems.get(zeelem))).duree;}
        }
    }
    
    
    /** Creates a new instance of Pattern */
    public Pattern() {
        elems = new ArrayList();
    }
    
    /** Ajoute une note à la fin du pattern*/
    public void AddNote(int degre,int duree) {
        elems.add(new Note(degre,duree));
    }
    
    /** ajoute un silence de duree divisions élémentaires à la fin du pattern */
    public void AddSilence(int duree) {
        elems.add(new Silence(duree));
    }
    
    /** Ajoute un accord à la fin du pattern*/
    public void AddChord(boolean T, boolean ST, boolean M, boolean sD, boolean D, boolean SD, boolean S, int duree, int renversement, int nombre) {
        elems.add(new Chord(T,ST,M,sD,D,SD,S,duree,renversement,nombre));
    }
    
    /** retourne la durée du pattern (en divisions elementaires)*/
    public int GetLength() {
        int compteur=0;
        
        for (int i=0;i<elems.size();i++) {
            if (elems.get(i).getClass()==Note.class) {
                compteur+=((Note)elems.get(i)).dur;
            }
            if (elems.get(i).getClass()==Chord.class) {
                compteur+=((Chord)elems.get(i)).dur;
            }
        }
        return compteur;
        
    }
    
    /** ecrit le pattern sur la piste spécifiée du MusicScore spécifié sur longueur divisions. Le rab de temps est comblé par un silence */
    public void WritePattern(MusicScore ms, int longueur, int piste, int octave) {
        int compteur=longueur;
        int ptr=0;
        while (compteur-GetElemLength(ptr) >= 0) {
            
            if (elems.get(ptr).getClass()==Note.class) {
                Note zenote = (Note)(elems.get(ptr));
                ms.AddNote(piste, zenote.hau, octave, zenote.dur, 0);
            }
            if (elems.get(ptr).getClass()==Chord.class) {
                Chord zechord = (Chord)(elems.get(ptr));
                ms.AddGenericChord(piste, zechord.nbnotes, zechord.zenotes[0], zechord.zenotes[1],  zechord.zenotes[2],  zechord.zenotes[3], zechord.zenotes[4], zechord.zenotes[5], zechord.zenotes[6], zechord.renv, octave, zechord.dur);
            }
            if (elems.get(ptr).getClass()==Silence.class) {
                Silence zesilence = (Silence)(elems.get(ptr));
                ms.AddSilence(piste, zesilence.duree);
            }
            
            compteur-=GetElemLength(ptr);
            ptr++;
            ptr=ptr % elems.size();
        }
        ms.AddSilence(piste, compteur);
    }
    
    
}
