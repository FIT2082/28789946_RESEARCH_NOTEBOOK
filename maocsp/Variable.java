/*
 * Variable.java
 *
 * Created on 30 mars 2005, 13:03
 */

package maocsp;

/**
 *
 * @author jamian
 */
import java.util.*;

public class Variable {
    
    public String ident;    // 'vident' de la variable dans la trace (de la forme v00)
    public String name;     // 'vname' de la variable : son nom tel qu' il a été entré par l' utilisateur
    public String internal; // 'vinternal' de la variable (de la forme _#00, représentation interne de gnu prolog)
    public int lowbound;    // Valeur minimum prise par la variable sur tout le problème (donné par lengthpre-analyser)
    public int highbound;   // Valeur maximum prise par la variable sur tout le problème (donné par lengthpre-analyser)
    public int noccur;      // Nombre de fois où on voit apparaitre la variable dans la trace
    /** Creates a new instance of Variable */
    public Variable(String s, String in, String nom, int a, int b) {
        ident=new String(s);
        internal=new String(in);
        if (nom!=null) {name=new String(nom);} else {name="(Variable interne)";}
        lowbound=a;
        highbound=b;
        noccur=0;
        
        
    }
    
}
