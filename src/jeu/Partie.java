package jeu;

import java.util.Arrays;
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
        System.out.println(affiche());
        System.out.print("> ");
        s = sc.nextLine();
        while (this.continuer()) {
            int count = s.length() - s.replace("'", "").length();
            if (!s.equals("") && count<=1) {
                tab = decompose(s);
                if (tab.length < 2)
                    // erreur car on doit mettre 2 cartes minimum
                if (checkFormatConditions(tab))
                    joueUnCoup(tab); //

                System.out.println(affiche());
                System.out.print("> ");
            }
            else
                System.out.print("#> ");

            s = sc.nextLine();
        }
    }

    /*
    private boolean joueUnCoup(String[] tab) {
        int[] tabCarteAsc = new int[tab.length], tabCarteDesc = new int[tab.length];
        int tailleCarteAsc = 0, tailleCarteDesc = 0, coupSurEnnemi = -1;
        boolean CoupEnnemiAsc = false;
        for (String mot : tab) {

            if (isMotAsc(mot)) {
                if(jouerSurAdversaire(mot)){
                    coupSurEnnemi = getCarteValue(mot);
                    CoupEnnemiAsc = true;
                    continue;
                }
                tabCarteAsc[tailleCarteAsc++] = getCarteValue(mot);
            }
            else {
                if(jouerSurAdversaire(mot)){
                    coupSurEnnemi = getCarteValue(mot);
                    CoupEnnemiAsc = false;
                    continue;
                }
                tabCarteDesc[tailleCarteDesc++] = getCarteValue(mot);
            }
        }

        if (IsSaisieJouable(tabCarteAsc,tabCarteDesc, coupSurEnnemi, CoupEnnemiAsc)){
            // ajouterCarte
            tour++;
            return true;
        }
        else
            return false;
    }
     */

    private boolean joueUnCoup(String[] tab) {
        int[] tabCarteAsc = new int[tab.length], tabCarteDesc = new int[tab.length];
        int tailleCarteAsc = 0, tailleCarteDesc = 0, coupPlayed = -1;
        boolean coupEnnemi = false;
        for (String mot : tab) {

            if (isMotAsc(mot)) {
                if(jouerSurAdversaire(mot)){
                    coupPlayed = getCarteValue(mot);
                    coupEnnemi = true;
                    continue;
                }
                tabCarteAsc[tailleCarteAsc++] = getCarteValue(mot);
            }
            else {
                if(jouerSurAdversaire(mot)){
                    coupPlayed = getCarteValue(mot);
                    coupEnnemi = false;
                    continue;
                }
                tabCarteDesc[tailleCarteDesc++] = getCarteValue(mot);
            }
        }

        if (IsSaisieJouable(tabCarteAsc,tabCarteDesc, coupPlayed, coupEnnemi)){
            // ajouterCarte
            System.out.println(Arrays.stream(tabCarteAsc).max().getAsInt());
            tabJoueur[this.tour % 2].ajouterCarte(Arrays.stream(tabCarteAsc).max().getAsInt(), true);
            System.out.println(Arrays.stream(tabCarteDesc).min().getAsInt());
            tabJoueur[this.tour % 2].ajouterCarte(Arrays.stream(tabCarteDesc).min().getAsInt(), false);
            this.tour++;
            return true;
        }
        else
            System.out.println("Saisie nou jouable");
            return false;
    }

    private boolean IsSaisieJouable(int[] tabCarteAsc, int[] tabCarteDesc, int coupEnnemi, boolean CoupEnnemiAsc){

        int adversaire = tour%2;

        if (!triSaisie(tabCarteAsc, true) || !triSaisie(tabCarteDesc, false))
            return false;

        if (coupEnnemi != -1){
            if (!tabJoueur[adversaire+1].isCartePosable(coupEnnemi, CoupEnnemiAsc))
                return false;
        }

        if (tabCarteAsc.length != 0)
            return tabJoueur[adversaire].isCartePosable(tabCarteAsc[0], true);
        if (tabCarteDesc.length != 0)
            return tabJoueur[adversaire].isCartePosable(tabCarteDesc[0], false);

        return true;
    }

    private static boolean triSaisie(int[] tab, boolean estAsc){
        boolean expr;
        for (int i = 0; i<tab.length; i++){
            for (int j = i+1; j<tab.length; j++){
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
        return s.split("\\s+");
    }

    public boolean continuer(){
        return tabJoueur[0].peutJouer() && tabJoueur[1].peutJouer();
    }

    public String affiche(){
        StringBuilder s = new StringBuilder();
        s.append("NORD ");
        s.append(tabJoueur[0].toString());
        s.append("\n");
        s.append("SUD ");
        s.append(tabJoueur[1].toString());
        s.append("\ncartes ");
        if(this.tour % 2 == 0){
            s.append("NORD ");
            s.append(tabJoueur[0].afficheMain());
        }
        else{
            s.append("SUD ");
            s.append(tabJoueur[1].afficheMain());
        }
        return s.toString();
    }
}
