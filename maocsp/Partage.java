/*
 * Partage.java
 *
 * Created on 18 mars 2005, 08:33
 * 
 * Heavily modified on 9 mai 2005, 14:20
 *
 */

package maocsp;

/**
 *  Cette classe existe uniquement � des fins de communication entre les autres classes...
 * @author jamian
 */

import java.util.*;

public class Partage {
    
    /** Creates a new instance of Partage */
    public Partage() {
    }
    
    /* Informations relatives au fichier MIDI cr��.  */
    
    static String lenom;    // Nom du fichier midi a cr�er
    static int lespistes;   // Nombre de pistes
    static int ladiv;       // nombre de divisions par noire
    static int basetempo;   // tempo de base
    static int modtempo;    // ce qu' on ajoute au tempo chaque fois qu' on s' enfonce dans l' arbre (� virer...)
    static MusicScore ms;   // le MusicScore trait�
    static Midifile mf;     // Le Midifile trait�
    static String copyright; // Le texte du copyright indiqu� dnas le fichier MIDI g�n�r�
    
    
    
    /* Informations g�n�rales sur la trace, donn�es par le Pre-Analyser ou l' utilisateur */
    
    static ArrayList varlist;       // liste des variables pr�sentes dans la trace
    static ArrayList constrlist;    // liste des contraintes pr�sentes dans la trace
    static ArrayList nodelist;      // liste des diff�rents CP et BT rencontr�s avec leur numero de balise
    static int maxdepth;            // profondeur max de l' arbre de recherche
    static int nbelem;              // Nombre de balises ouvrantes du document XML
    static int balbegin;            // balise sur laquelle on commencera � faire de la musique
    static int balend;              // balise sur laquelle on arretera de faire de la musique
    static int ModChoice;           // Num�ro du mode utilis�
    static boolean notallvar;       // Mis a true si l' utilisateur a choisi de ne pas utiliser toutes les variables pour g�n�rer la musique
    static boolean[] selectedvars;  // selectedvars[v] ssi X s�lenctionn�e par l' utilisateur et varno(X)=v
    static int nbvars;              // Indique le nombre de variables utilis�es dans la musique
    
    
    
    /* Informations modifi�es pendant le second parcours de la trace, donn�es par le handler, 
     * et dont les differents mods peuvent se servir (entre autres) pour g�n�rer la musique.
     * Ces valeurs, contrairement � celles donn�es par lengthpre-analyser, sont donc d�pendantes de la position � laquelle on se trouve dans la trace. */
    
    static int nbvar;               // Nombres de variables que l' on connait actuellement. Diff�re de partage.nbvars tant que toutes les variables n' ont pas �t� rencontr�es (par new-variable)
    static boolean[] encountered;   // Encountered[varno(variable)] = true si on a  pass� le <new-variable vident="variable" ... />
    static int depth;               // Profondeur actuelle dans l' arbre de recherche
    static int[] domainSize;        // n=domainSize[x] :- varno(v)=x , taille_du_domaine(v) = n.
    
    static boolean state;           // Si on est dans la description d' un �tat (toutes les variables connues � la queue leu leu)
    static boolean reduce;          // Si on est dans un �v�nement de r�duction
    static boolean vardomain;       // Si on est en train de d�crire un domaine de variable
    static boolean delta;           // Si on est en train de d�crire une diff�rence (typiquement, dans un reduce, une s�rie de range d�crivant les variables enlev�es)
    static boolean solution;        // Si on est en train de d�crire une solution
    static boolean failure;         // Si on est en train de d�crire un �chec
    static boolean backto;          // Si on est en train de d�crire un backtracking (typiquement : rappel du choice-point o� on viens de remonter)
    static boolean cp;              // Si on est en train de d�crire un point de choix (un state est toujours d�crit lors d' un choice-point).
    
    static int currentvar;          // De quelle variable on est en  train de parler (pour vardomain et range). On parle de X si currentvar=varno(X).
    static int currentcontr;        // De quelle contrainte on est en train de parler.
    static boolean[] isArcCo;       // isArcCo[v] si la variable X tq v=varno(X) est suceptible d' �tre d�crit par arc-consistance (son domaine est compris dans un intervale de moins de 256 valeurs)
    static int[] min;               // min[v] indique la borne inf�rieure de la variable X telle que v=varno(X);
    static int[] max;               // Pareil, mais pour la borne sup�rieure. WARNING : Peut avoir des valeurs excessivement grandes (200 000 000 000 avec des traces g�n�r�es par codeine version Windows 32 bits, en compilant une version 64 bits, j' ose m�me pas imaginer...)
    
    static boolean[][] arcco;       /* description compl�te du domaine d' une variable si celle ci est trait�e par l' arc-consistance sous GNU-prolog.
                                     * on a arcco[v][n] si on a isArcCo[v] et si la valeur max[v]+n est pr�sente dans le domaine de X, pourvu que varno(X)=v :) */
    
    
    
    /** Renvoie pour chaque variable un unique identifiant entier, compris entre 0 et varlist.size() */
    static int varno(String v) {
        int i=0;
        if (varlist==null) {System.out.println("Varlist null");}
        while (i<varlist.size() && !v.equals( ((Variable)(varlist.get(i))).ident))  {i++;}
        if (i==varlist.size()) {i=-1;}
        return i;
    }
    
    /** Renvoir pour chaque contrainte un unique identifiant entier, compris entre 0 et constrlist.size() */
    static int ctrno(String c) {
        int i=0;
        if (constrlist==null) {System.out.println("constrlist null");}
        while (i<constrlist.size() && !c.equals(constrlist.get(i)))  {i++;}
        if (i==constrlist.size()) {i=-1;}
        return i;
    }    
}
