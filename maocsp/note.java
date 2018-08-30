/*
 * note.java
 *
 * Created on 18 février 2005, 12:47
 */

package maocsp;

/**
 *
 * @author jamian
 */
public class note {
    
    
    public int duree;       // durée en divisions élémentaires
    public int pitch;       // Hauteur de la note (de 0 à 127, 128 pour un silence)
    public boolean inachord;    // true si la note est jouée en accord avec la note suivante
    
    /** Creates a new instance of note */
    public note(int p, int d, boolean i) {
        duree=d;
        pitch=p;
        inachord=i;
    }
    
}
