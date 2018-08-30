/*
 * Handler.java
 *
 * Created on 20 avril 2005, 09:30
 *
 * Work-overloaded on 9 mai 2005, 14:22
 */

package maocsp;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;
        
/**
 *
 * @author jamian
 */
public class Handler extends DefaultHandler {
    
    boolean vardisabled;    // Si on est en train de décrire un e variable non retenue par l' utilisateur
    int count;              // numéro de la balise que l' on traite
    
    DefaultHandler zemod;   // Créateur de  la musique à qui on va repasser tous les évènements SAX...
    /** Creates a new instance of Handler */
    public Handler() {
        super();
        count=0;
        Partage.nbvar=0;
        vardisabled=false;
        switch (Partage.ModChoice) {
            case 1 : zemod = new ModPhilipGlass(); break;
            case 2 : zemod = new ModHardRock(); break;
            case 3 : zemod = new ModDebug(); break;
            case 4 : zemod = new ModNewAge(); break;
            case 5 : zemod = new ModApocalyptica(); break;
            default : zemod = new modtest(); break;
        }
        
        Partage.encountered=new boolean[Partage.nbvars];
        Partage.domainSize=new int[Partage.nbvars];
        Partage.isArcCo=new boolean[Partage.nbvars];
        Partage.min=new int[Partage.nbvars];
        Partage.max=new int[Partage.nbvars];
        Partage.arcco=new boolean[Partage.nbvars][256];
        
        
    }
    
    public void startDocument() throws SAXException {
        count=0;
        for (int i=0;i<Partage.nbvars;i++) {
            Partage.encountered[i]=false;
            Partage.isArcCo[i]=false;
            for (int j=0;j<256;j++) {
                Partage.arcco[i][j]=false;
            }
        }
        Partage.solution=false;
        Partage.failure=false;
        Partage.backto=false;
        Partage.cp=false;
        Partage.state=false;
        Partage.delta=false;
        Partage.reduce=false;
        Partage.depth=0;
        
        zemod.startDocument();
    }
    
    public void startElement(String uri, String nom, String nomnom, Attributes attr) throws SAXException {
        
        count++;        // Mise a jour du nombre de balises ouvrantes parcourues.
        
        if (nomnom.equals("new-variable")) {                                    // New-variable => Mise a jour du nombre de variables rencontrées
            if (Partage.selectedvars[Partage.varno(attr.getValue("vident"))]) { // Pour peu que ce soit une variable retenue par l' utilisateur
                Partage.nbvar++;
                Partage.encountered[Partage.varno(attr.getValue("vident"))]=true;
            }
        }
        
        if (Partage.notallvar & (nomnom.equals("variable") | nomnom.equals("new-variable") | nomnom.equals("reduce"))) {
            vardisabled=!Partage.selectedvars[Partage.varno(attr.getValue("vident"))];      // On vérifie qu' on n' est pas en train de parler d' une variable non retenue...
        }
        
        
        /* Traitement des différents calculs de domaines, profondeur, etc... */
        
        if (nomnom.equals("new-variable")) {
            Partage.currentvar=Partage.varno(attr.getValue("vident"));
        }

        if (nomnom.equals("new-constraint")) {
            Partage.currentcontr=Partage.ctrno(attr.getValue("cident"));
        }
        
        if (nomnom.equals("state")) {
            Partage.state=true;
        }

        if (nomnom.equals("variable")) {
            Partage.currentvar=Partage.varno(attr.getValue("vident"));
        }
                
        if (nomnom.equals("vardomain")) {
            Partage.vardomain=true;
            Partage.domainSize[Partage.currentvar]=Integer.parseInt(attr.getValue("size"));     // WARNING : Cette information peut se réévéler inexacte. Penser à prévenir Ludovic à ce sujet.
                                                                                                // Et accessoirement, se fier à la longueur des tableaux générés plutot qu' a cette valeur pour éviter les bugs...
            Partage.min[Partage.currentvar]=Integer.parseInt(attr.getValue("min"));
            Partage.max[Partage.currentvar]=Integer.parseInt(attr.getValue("max"));
            
            if (Partage.max[Partage.currentvar]-Partage.min[Partage.currentvar] < 256) {        // Si le domaine est compris dans un intervalle de moins de 256 valeurs...
                Partage.isArcCo[Partage.currentvar]=true;                                       // Alors il est légitime de dire qu' on peut être en présence d' une variable sur laquelle l' Arc consistance a été appliquée
            }
        }
        
        if (nomnom.equals("solved")) {
            Partage.currentcontr=Partage.ctrno(attr.getValue("cident"));
        }

        if (nomnom.equals("range")) {                                                               
            if (Partage.vardomain) {                                                                // Si cet intervalle décrit un domaine de variable...
                int from=Integer.parseInt(attr.getValue("from"));
                int to=Integer.parseInt(attr.getValue("to"));
                if (Partage.isArcCo[Partage.currentvar]) {                                          // ... Et qu' on est en présence d' une variable suceptible d' avoir subi l' arc consistance...
                    for (int i=0;i<256;i++) {Partage.arcco[Partage.currentvar][i]=false;}
                    for (int i=from; i<=to; i++) {
                        try {
                            Partage.arcco[Partage.currentvar][i-Partage.min[Partage.currentvar]]=true;  // ... Alors on remplit le tableau de booleens correspondant.
                        }
                        catch (Exception e) {System.out.print("Bug dans la trace...  ");i=to;}
                    }
                }
            }
        }
        
        if (nomnom.equals("reduce")) {
            Partage.reduce=true;
            Partage.currentvar=Partage.varno(attr.getValue("vident"));
            Partage.currentcontr=Partage.ctrno(attr.getValue("cident"));
        }
        
        if (nomnom.equals("delta")) {
            Partage.delta=true;
        }
        
        if (nomnom.equals("choice-point")) {
            Partage.depth=Integer.parseInt(attr.getValue("depth"));
        }

        if (nomnom.equals("back-to")) {
            Partage.depth=Integer.parseInt(attr.getValue("depth"));            
        }
        
        if (nomnom.equals("solution")) {
            Partage.solution=true;
        }
        
        if (nomnom.equals("failure")) {
            Partage.failure=true;
        }        
        
        
        
        /* Et enfin, si les bonnes conditions sont remplies c' est à dire : 
         * - Si on est parmi les balises choisies par l' utilisateur pour faire la musique, 
         * - Et si on ne parle pas d' une variable dont l' utilisateur ne veut pas,
         * alors, on envoie la balise XML à la couche musicale. */
        
        if (count>=Partage.balbegin & count<=Partage.balend & !vardisabled) {
            zemod.startElement(uri,nom,nomnom,attr);
        }
    }
    
    public void endElement(String uri, String nom, String truc) throws SAXException {

        if (truc.equals("state")) {
            Partage.state=false;
        }
        
        if (truc.equals("reduce")) {
            Partage.reduce=false;
        }
        
        if (truc.equals("vardomain")) {
            Partage.vardomain=false;
        }
        
        if (truc.equals("delta")) {
            Partage.delta=false;
        }
        
        if (truc.equals("choice-point")) {
            Partage.cp=false;
            Partage.depth++;
        }

        if (truc.equals("back-to")) {
            Partage.backto=false;
            Partage.depth++;
        }

        if (truc.equals("solution")) {
            Partage.solution=false;
        }
        
        if (truc.equals("failure")) {
            Partage.failure=false;
        }
        
        
        
        
        if (count>=Partage.balbegin & count <=Partage.balend & !vardisabled) {
            zemod.endElement(uri,nom,truc);
        }
        if (truc.equals("variable") | truc.equals("new-variable") | truc.equals("reduce")) {
            vardisabled=false;
        }
    }
    
    public void endDocument() throws SAXException{
        zemod.endDocument();
    }
    
    public void characters(char[] ch, int debut, int fin) {}
    public void skippedEntity(String bof) {}
    public void processingInstruction(String target, String data) {}
    public void ignorableWhitespace(char[] plop,int debut,int fin) {}
    public void endPrefixMapping(String truc) {}
    public void startPrefixMapping(String truc, String auttruc) {}
    public void setDocumentLocator(Locator l) {}
    
}
