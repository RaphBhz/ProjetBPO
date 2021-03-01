package jeu;

import java.util.Scanner;

public class Partie {
    private Joueur NORD = new Joueur(0);
    private Joueur SUD = new Joueur(1);
    private int tour = 1;


    public Partie(){
        Start();
    }

    private void Start(){
        Scanner sc = new Scanner(System.in);
        String s;
        String[] tab;
        System.out.print("> ");
        s = sc.nextLine();
        while (true) {
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
        boolean CoupEnnemiAsc;
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
        // tri croissant
        if (tabCarteAsc.length > 1)
            if (!TriSaisieAsc(tabCarteAsc, indexCoupSurEnnemi))
                return false;

        // tri decroissant
        if (tabCarteDesc.length > 1)
            if (!TriSaisieDesc(tabCarteDesc, indexCoupSurEnnemi))
                return false;

        if (indexCoupSurEnnemi != -1){
            if (adversaire == 0)
                if (CoupEnnemiAsc) {
                    if (!SUD.isCartePosable(tabCarteAsc[indexCoupSurEnnemi], true))
                        return false;

                }else{
                    if (!SUD.isCartePosable(tabCarteDesc[indexCoupSurEnnemi], false))
                        return false;
                }
        }


        if (adversaire == 0) {
            if (tabCarteAsc.length != 0)
                return NORD.isCartePosable(tabCarteAsc[0], true);
            if (tabCarteDesc.length != 0)
                return NORD.isCartePosable(tabCarteDesc[0], false);
        }
        else{
            if (tabCarteAsc.length != 0)
                return SUD.isCartePosable(tabCarteAsc[0], true);
            if (tabCarteDesc.length != 0)
                return SUD.isCartePosable(tabCarteDesc[0], false);
        }
    }

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
}
