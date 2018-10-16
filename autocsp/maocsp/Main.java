/*
 * Main.java
 *
 * Created on 17 fvrier 2005, 22:20
 */

package maocsp;
import java.io.*;
import java.util.*;

import org.xml.sax.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.*;
import javax.swing.*;
/**
 *
 * @author jamian
 */ 
public class Main {
    
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /** 
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        Partage.maxdepth=0;
        Partage.copyright="Jeremie VAUTARD - Universit d' Orlans"; 
        String nomxml="";
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));

        if(args.length == 0) {
            JFileChooser choix = new JFileChooser();
            choix.setMultiSelectionEnabled(false);
            int retour = choix.showOpenDialog(null);

            if (retour != choix.APPROVE_OPTION) {
                System.exit(1);
            }
            nomxml=choix.getSelectedFile().toString();
        } else {
            nomxml = args[0];
        }

        System.out.println(nomxml);

        try {
            SAXParserFactory fabrique = SAXParserFactory.newInstance();
            SAXParser parseur = fabrique.newSAXParser();
            File fichier = new File(nomxml);
            DefaultHandler gestionnaire = new PreAnalyser();
            parseur.parse(fichier, gestionnaire);


        } catch (SAXException se) {System.out.println(se); System.out.println("Oups... Le XML n' tait pas bon..."); System.exit(-1);}
        catch (IOException ioe) {System.out.println("Oups, je sais pas ce qu' il s' est pass..."); System.exit(-1);}
        catch (ParserConfigurationException ce) {System.out.println("Parser Configuration Exception"); System.exit(-1);}


        // System.out.print("Le document contient ");System.out.print(Partage.nbelem);System.out.println(" balises ouvrantes.");
        // for (int i=0;i<Partage.nodelist.size();i=i+2) {
        //     System.out.print((String)(Partage.nodelist.get(i)));
        //     System.out.println((Integer)(Partage.nodelist.get(i+1)));
        // }
        


        // System.out.println("Commencer sur quelle balise ?");
        // try {Partage.balbegin=Integer.parseInt(in.readLine());}
        // catch( Exception e ) {Partage.balbegin=0; System.out.println("bug");}
        // System.out.println("Finir sur quelle balise ?");
        // try {Partage.balend=Integer.parseInt(in.readLine());}
        // catch( Exception e ) {Partage.balend=2; System.out.println("bug");}
        // System.out.println();

        Partage.balbegin=0;
        Partage.balend=Partage.nodelist.size();

        // System.out.print("Profondeur maximale de l' arbre de recherche = ");System.out.println(Partage.maxdepth);

        // System.out.print("Le probleme possede ");System.out.print(Partage.varlist.size());//System.out.println(" variables. Toutes les utiliser ? (O/N)");
        //String rep="";

        // while (!rep.equalsIgnoreCase("O") & !rep.equalsIgnoreCase("N")) {
        //     try {rep=in.readLine();}
        //     catch ( Exception e) {}
        // }
        Partage.notallvar=false;

        Partage.selectedvars=new boolean[Partage.varlist.size()];
        for (int bip=0;bip<Partage.varlist.size();bip++) {
            Partage.selectedvars[bip]=true;
        }
//         if (rep.equalsIgnoreCase("N")) {
//             Partage.notallvar=true;
//             for (int i=0;i<Partage.varlist.size();i++) {
//                 String rep2="";
//                 Variable plop=((Variable)(Partage.varlist.get(i)));
//                 System.out.print("Variable ");System.out.println(plop.internal);
//                 System.out.print("Intervenant ");System.out.print(plop.noccur);System.out.println(" fois.");
//                 System.out.print("valeur min : ");System.out.print(plop.lowbound);System.out.print("   valeur max : ");System.out.println(plop.highbound);
//                 System.out.print("Correspond  la variable : ");System.out.println(plop.name);
//                 System.out.print("Garder ? (O/N)");
//                 while (!rep2.equalsIgnoreCase("O") & !rep2.equalsIgnoreCase("N")) {
//                     try {rep2=in.readLine();}
//                     catch ( Exception e) {}
//                 }
//                 System.out.println();
//                 if (rep2.equalsIgnoreCase("N")) {
// //                        Partage.nbvars--;
//                     Partage.selectedvars[i]=false;
//                 } else {}
//             }
//         }

        // System.out.println();System.out.println("Que voulez vous comme type de musique ?");
        // System.out.println("1 - Philip Glass mode");
        // System.out.println("2 - Hard Rock mode");
        // System.out.println("3 - Debug mode");
        // System.out.println("4 - NewAge mode");
        // System.out.println("5 - Apocalyptica mode");

        // while (Partage.ModChoice<1 | Partage.ModChoice>5) {
        //     try {Partage.ModChoice=Integer.parseInt(in.readLine());}
        //     catch ( Exception e) {}
        // }

        Partage.ModChoice = 1;
        // Write output

        if(args.length <= 1) {
            JFileChooser choix = new JFileChooser();
            Integer mc = new Integer(Partage.ModChoice);
            int retour2 = choix.CANCEL_OPTION;
            Partage.lenom = nomxml.concat(".txt");
            choix.setSelectedFile(new File(Partage.lenom));
            while (retour2 == choix.CANCEL_OPTION) {
                retour2 = choix.showSaveDialog(null);
            }

            Partage.lenom = choix.getSelectedFile().toString();
        } else {
            Partage.lenom = args[1];
        }

        try {
            SAXParserFactory fabrique = SAXParserFactory.newInstance();
            SAXParser parseur = fabrique.newSAXParser();
            File fichier = new File(nomxml);
            DefaultHandler gestionnaire;

            gestionnaire=new Handler();

            parseur.parse(fichier, gestionnaire);


        } catch (SAXException se) {System.out.println(se); System.out.println("Oups... Le XML n' tait pas bon...");}
        catch (IOException ioe) {System.out.println("Oups, je sais pas ce qu' il s' est pass...");}
        catch (ParserConfigurationException ce) {System.out.println("Parser Configuration Exception");}


        Partage.ms.MakeFile();

    }

}
