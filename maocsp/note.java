/*
 * note.java
 *
 * Created on 18 f�vrier 2005, 12:47
 */

package maocsp;

/**
 *
 * @author jamian
 */
public class note {
    
    
    public int duree;       // dur�e en divisions �l�mentaires
    public int pitch;       // Hauteur de la note (de 0 � 127, 128 pour un silence)
    public boolean inachord;    // true si la note est jou�e en accord avec la note suivante
    
    /** Creates a new instance of note */
    public note(int p, int d, boolean i) {
        duree=d;
        pitch=p;
        inachord=i;
    }
    
}
