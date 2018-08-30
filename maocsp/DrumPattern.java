/*
 * DrumPattern.java
 *
 * Created on 12 avril 2005, 10:57
 */

package maocsp;

import java.util.*;
/**
 *
 * @author jamian
 */
public class DrumPattern {
    
    
        
    private class Beat {
        public int perc;
        public int dur;
        public boolean ensemble;
        public Beat(int percu, int duree, boolean ens) {
            perc=percu;
            dur=duree;
            ensemble=ens;
        }
    }
    
    private ArrayList elems;
    /** Creates a new instance of DrumPattern */
    public DrumPattern() {
        elems=new ArrayList();
    }
    
    public void AddBeat(int percu, int duree, boolean ensemble) {
        elems.add(new Beat(percu, duree, ensemble));
    }
    
    public int GetLength () {
        int compteur=0;
        
        for (int i=0;i<elems.size();i++) {
            Beat b = ((Beat)(elems.get(i)));
            compteur +=b.dur;

        }
        return compteur;
    }
    
    private int GetElemLength(int elem) {
        Beat b;
        
        b=((Beat)(elems.get(elem)));
        
        if (b.ensemble) {return 0;}
        else {return b.dur;}
        
    }
    public void WritePattern(MusicScore ms, int longueur) {
        int compteur=longueur;
        int ptr=0;
        while (compteur-GetElemLength(ptr) >= 0) {
            Beat b=((Beat)(elems.get(ptr)));
            
            ms.AddPerc(b.perc, b.dur, b.ensemble);
            
            compteur-=GetElemLength(ptr);
            ptr++;
            ptr=ptr % elems.size();
        }
        ms.AddPercSilence(compteur);        
    }
}
