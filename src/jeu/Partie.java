package jeu;

import java.util.Scanner;

public class Partie {
    private Joueur[] tabJoueur;
    private static final int MAX_JOUEURS = 2;
    private int tour = 1;


    public Partie(){
        tabJoueur = new Joueur[MAX_JOUEURS];
        for (int i = 0; i < MAX_JOUEURS; i++)
            tabJoueur[i] = new Joueur(i);
        Start();
    }

    private void Start(){
        Scanner sc = new Scanner(System.in);
        String s;
        String[] tab;
        System.out.print("> ");
        s = sc.nextLine();
        while (this.continuer(tabJoueur[0], tabJoueur[1])) {
            int count = s.length() - s.replace("'", "").length();
            if (!s.equals("") && count<=1) {
                tab = decompose(s);
                if (tab.length < 2)
                    // erreur car on doit mettre 2 cartes minimum
                if (checkFormatConditions(tab))
                    joueUnCoup(tab); //

                System.out.print("> ");
            }
            else
                System.out.print("#> ");

            s = sc.nextLine();
        }
    }

    private boolean joueUnCoup(String[] tab) {
        int[] tabCarteAsc = new int[tab.length], tabCarteDesc = new int[tab.length];
        int i = 0, tailleCarteAsc = 0, tailleCarteDesc = 0, indexCoupSurEnnemi = -1;
        boolean CoupEnnemiAsc = false;
        for (String mot : tab) {

            if (isMotAsc(mot)) {
                tabCarteAsc[tailleCarteAsc++] = getCarteValue(mot);
                if(jouerSurAdversaire(mot)){
                    indexCoupSurEnnemi = i;
                    CoupEnnemiAsc = true;
                }
            }
            else {
                tabCarteDesc[tailleCarteDesc++] = getCarteValue(mot);
                if(jouerSurAdversaire(mot)){
                    indexCoupSurEnnemi = i;
                    CoupEnnemiAsc = false;
                }
            }
            i++;
        }

        if (IsSaisieJouable(tabCarteAsc,tabCarteDesc, indexCoupSurEnnemi, CoupEnnemiAsc)){
            // ajouterCarte
            return true;
        }
        else
            return false;
    }

    private boolean IsSaisieJouable(int[] tabCarteAsc, int[] tabCarteDesc, int indexCoupSurEnnemi, boolean CoupEnnemiAsc){

        int adversaire = tour%2;

        if (!triSaisie(tabCarteAsc, indexCoupSurEnnemi, true) || !triSaisie(tabCarteDesc, indexCoupSurEnnemi, false))
            return false;

        if (indexCoupSurEnnemi != -1){
            if (CoupEnnemiAsc) {
                if (!tabJoueur[adversaire].isCartePosable(tabCarteAsc[indexCoupSurEnnemi], true))
                    return false;

            } else {
                if (!tabJoueur[adversaire].isCartePosable(tabCarteDesc[indexCoupSurEnnemi], false))
                    return false;
            }
        }


        if (adversaire == 0) {
            if (tabCarteAsc.length != 0)
                return tabJoueur[adversaire].isCartePosable(tabCarteAsc[0], true);
            if (tabCarteDesc.length != 0)
                return tabJoueur[adversaire].isCartePosable(tabCarteDesc[0], false);
        }

        return true;
    }

    /*
    private static boolean TriSaisieAsc(int[] tab, int indexCoupSurEnnemi){
        for (int i = 0; i<tab.length; i++){
            if (i == indexCoupSurEnnemi) continue;
            for (int j = i+1; j<tab.length; j++){
                if (j == indexCoupSurEnnemi) continue;
                if (tab[j] < tab[i])
                    return false;
            }
        }
        return true;
    }

    private static boolean TriSaisieDesc(int[] tab, int indexCoupSurEnnemi){
        for (int i = 0; i<tab.length; i++){
            if (i == indexCoupSurEnnemi) continue;
            for (int j = i+1; j<tab.length; j++){
                if (j == indexCoupSurEnnemi) continue;
                if (tab[i] < tab[j])
                    return false;
            }
        }
        return true;
    }
    */

    private static boolean triSaisie(int[] tab, int indexCoupSurEnnemi, boolean estAsc){
        boolean expr;
        for (int i = 0; i<tab.length; i++){
            if (i == indexCoupSurEnnemi) continue;
            for (int j = i+1; j<tab.length; j++){
                if (j == indexCoupSurEnnemi) continue;
                if(estAsc)
                    expr = tab[j] < tab[i];
                else
                    expr = tab[i] < tab[j];
                if (expr)
                    return false;
            }
        }
        return true;
    }

    private static int getCarteValue(String mot) {
        int carte;
        if (Character.isDigit(mot.charAt(1)))
            carte = Integer.parseInt("" + mot.charAt(0) + mot.charAt(1));
        else
            carte = Integer.parseInt("" + mot.charAt(0));
        return carte;
    }

    private static boolean isMotAsc(String mot){
        return mot.indexOf("^") == 1;
    }

    private static boolean checkFormatConditions(String[] tab){
        for (String mot : tab) {
            if (!checkCommenceParNb(mot))
                return false;
        }
        return true;
    }

    private static boolean checkCommenceParNb(String mot){
        return Character.isDigit(mot.charAt(0));
    }

    private static boolean jouerSurAdversaire(String mot){
        return mot.contains("'");
    }

    private static String[] decompose(String s) {
        // une solution
        String[] tab = s.split("\\s+");
        return tab;
    }

    public boolean continuer(Joueur j1, Joueur j2){
        return j1.peutJouer() && j2.peutJouer();
    }
}
