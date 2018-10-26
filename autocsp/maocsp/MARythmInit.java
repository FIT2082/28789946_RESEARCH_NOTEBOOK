/*
 * MARythmInit.java
 *
 * Created on 5 mai 2005, 13:17
 */

package maocsp;

/**
 *
 * @author jamian
 */
public class MARythmInit {
    
    /** Creates a new instance of MHRDrumInit */
    public MARythmInit() {
    }
    
    public static void Remplir(Pattern t[]) {
        
        t[0].AddNote(0, 96);
        t[0].AddSilence(96);
        
        t[1].AddChord(true, false, false,false, true, false, false, 96,0,2);
        t[1].AddSilence(96);
        
        t[2].AddNote(0, 48);
        t[2].AddSilence(48);
        t[2].AddNote(0, 48);
        t[2].AddSilence(48);

        t[3].AddChord(true, false, false,false, true, false, false, 48,0,2);
        t[3].AddSilence(48);
        t[3].AddNote(0, 48);
        t[3].AddSilence(48);

        t[4].AddChord(true, false, false,false, true, false, false, 48,0,2);
        t[4].AddSilence(48);
        t[4].AddChord(true, false, false,false, true, false, false, 48,0,2);
        t[4].AddSilence(48);

        t[5].AddNote(0, 32);
        t[5].AddSilence(32);
        t[5].AddNote(0, 32);
        t[5].AddSilence(32);
        t[5].AddNote(0, 32);
        t[5].AddSilence(32);

        t[6].AddChord(true, false, false,false, true, false, false, 32,0,2);
        t[6].AddSilence(32);
        t[6].AddNote(0, 32);
        t[6].AddSilence(32);
        t[6].AddNote(0, 32);
        t[6].AddSilence(32);

        t[7].AddChord(true, false, false,false, true, false, false, 32,0,2);
        t[7].AddSilence(32);
        t[7].AddNote(0, 32);
        t[7].AddSilence(32);
        t[7].AddChord(true, false, false,false, true, false, false, 32,0,2);
        t[7].AddSilence(32);
        
        t[8].AddNote(0, 24);
        t[8].AddSilence(24);
        t[8].AddNote(0, 24);
        t[8].AddSilence(24);
        t[8].AddNote(0, 24);
        t[8].AddSilence(24);
        t[8].AddNote(0, 24);
        t[8].AddSilence(24);
        
        t[9].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[9].AddSilence(24);
        t[9].AddNote(0, 24);
        t[9].AddSilence(24);
        t[9].AddNote(0, 24);
        t[9].AddSilence(24);
        t[9].AddNote(0, 24);
        t[9].AddSilence(24);
        
        t[10].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[10].AddSilence(24);
        t[10].AddNote(0,24);
        t[10].AddSilence(24);
        t[10].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[10].AddSilence(24);
        t[10].AddNote(0,24);
        t[10].AddSilence(24);
        
        t[11].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[11].AddSilence(24);
        t[11].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[11].AddSilence(24);
        t[11].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[11].AddSilence(24);
        t[11].AddChord(true, false, false,false, true, false, false, 24,0,2);
        t[11].AddSilence(24);

    }
}
