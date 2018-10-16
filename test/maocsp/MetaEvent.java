/*
 * MetaEvent.java
 *
 * Created on 19 mars 2005, 20:50
 */

package maocsp;


import java.util.*;

/**
 *
 * @author jamian
 */
public class MetaEvent {

    public String name;
    public int param;
    public int duree;
    public String textparam;
    
    /** Creates a new instance of MetaEvent */
    public MetaEvent(String nom) {
        name=nom;
        param=0;
        duree=0;
        textparam="";
    }
    
}
