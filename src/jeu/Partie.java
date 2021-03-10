package jeu;

import java.util.ArrayList;
import java.util.Scanner;

public class Partie {
    private Joueur[] tabJoueur;
    private static final int MAX_JOUEURS = 2;
    private int tour = 0;


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
        int count;
        System.out.println(affiche());
        System.out.print("> ");
        s = sc.nextLine();
        // modifier la boucle
        // j'ai ajouté la méthode oneCarteInHandAndZeroInPioche() afin de déterminer si Joueur peut encore poser. Partie s'arrête dès qu'un joueur se trouve dans cette situation
        while (this.continuer()) {
            count = s.length() - s.replace("'", "").length();
            if (!s.equals("") && count<=1) {
                tab = decompose(s);
                if (checkFormatConditions(tab) && tab.length >= 2) {
                    if (joueUnCoup(tab)) {
                        System.out.println(affiche());
                        System.out.print("> ");
                    }
                    else {
                        System.out.println("ERREUR 6: JoueUnCoup == false");
                        System.out.print("#> ");
                    }
                }
                else {
                    System.out.println("~r~ERREUR 7: checkFormatConditions(tab) && tab.length >= 2 car " + tab.length);
                    System.out.print("#> ");
                }
            }
            else {
                System.out.println("~r~ERREUR 8: !s.equals(\"\") && count<=1");
                System.out.print("#> ");
            }

            s = sc.nextLine();
        }
    }

    /*
    private void getCartes(String[] tab, ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int carteSurEnnemi, boolean coupEnnemiAsc){
        for (String mot : tab) {

            if (isMotAsc(mot)) {
                if(jouerSurAdversaire(mot)){
                    System.out.println("TEST ENNEMI 2 ASC");
                    carteSurEnnemi = getCarteValue(mot);
                    coupEnnemiAsc = true;
                }
                System.out.println("TEST CONTINUE " + getCarteValue(mot));
                tabCarteAsc.add(getCarteValue(mot));
            }
            else {
                if(jouerSurAdversaire(mot)){
                    System.out.println("TEST ENNEMI 2 DESC");
                    carteSurEnnemi = getCarteValue(mot);
                    coupEnnemiAsc = false;
                    continue;
                }
                System.out.println("TEST CONTINUE " + getCarteValue(mot));
                tabCarteDesc.add(getCarteValue(mot));
            }
        }
    }
    */

    private boolean joueUnCoup(String[] tab) {
        ArrayList<Integer> tabCarteAsc = new ArrayList<>(), tabCarteDesc = new ArrayList<>();
        int carteSurEnnemi = -1;
        boolean coupEnnemiAsc = false;
        for (String mot : tab) {

            if (isMotAsc(mot)) {
                if(jouerSurAdversaire(mot)){
                    System.out.println("TEST ENNEMI 2 ASC");
                    carteSurEnnemi = getCarteValue(mot);
                    coupEnnemiAsc = true;
                }
                System.out.println("TEST CONTINUE " + getCarteValue(mot));
                tabCarteAsc.add(getCarteValue(mot));
            }
            else {
                if(jouerSurAdversaire(mot)){
                    System.out.println("TEST ENNEMI 2 DESC");
                    carteSurEnnemi = getCarteValue(mot);
                    coupEnnemiAsc = false;
                }
                System.out.println("TEST CONTINUE " + getCarteValue(mot));
                tabCarteDesc.add(getCarteValue(mot));
            }
        }

        if (IsSaisieJouable(tabCarteAsc,tabCarteDesc, carteSurEnnemi, coupEnnemiAsc)){
            // ajouterCarte
            if(carteSurEnnemi != -1){
                System.out.println("TEST ENNEMI");
                tabJoueur[(this.tour + 1) % 2 ].ajouterCarteBase(carteSurEnnemi, coupEnnemiAsc);

                /*
                if(coupEnnemiAsc)
                    tabJoueur[(this.tour + 1) % 2 ].ajouterCarteBase(carteSurEnnemi, true);
                else
                    tabJoueur[(this.tour + 1) % 2 ].ajouterCarteBase(carteSurEnnemi, false);
                */
                //ptdr on devait être fatigué
            }
            if(!tabCarteAsc.isEmpty())
                tabJoueur[this.tour % 2].ajouterCarteBase(tabCarteAsc.get(tabCarteAsc.size() - 1), true);
            if(!tabCarteDesc.isEmpty())
                tabJoueur[this.tour % 2].ajouterCarteBase(tabCarteDesc.get(tabCarteDesc.size() - 1), false);

            tabJoueur[tour%2].removeCartesAndAddCartes(tabCarteDesc, tabCarteAsc, carteSurEnnemi);

            if(carteSurEnnemi != -1){
                int i =0;
                while(tabJoueur[this.tour % 2].nbCartesMain() != 6){
                    boolean exp1 = i<tabCarteAsc.size(), exp2 = i<tabCarteDesc.size();
                    if(exp1) tabJoueur[this.tour % 2].piocher(tabCarteAsc.get(i));
                    if(exp2) tabJoueur[this.tour % 2].piocher(tabCarteDesc.get(i));
                    if(!(exp1 && exp2)) tabJoueur[this.tour % 2].piocher(-1);
                    i++;
                }
            }
            else{
                int i = 0, compteur = 0;
                while(compteur != 2){
                    if(!tabCarteAsc.isEmpty()) {
                        tabJoueur[this.tour % 2].piocher(tabCarteAsc.get(i));
                        compteur++;
                    }
                    if(!tabCarteDesc.isEmpty()) {
                        tabJoueur[this.tour % 2].piocher(tabCarteDesc.get(i));
                        compteur++;
                    }
                    i++;

                }
            }
            this.tour++;
            return true;
        }
        else
           //  System.out.println("Saisie non jouable");
            return false;
    }

    private boolean IsSaisieJouable(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int coupEnnemi, boolean CoupEnnemiAsc){
        int adversaire = tour%2;

        if (!tabJoueur[adversaire].areCartesInMain(tabCarteAsc, tabCarteDesc, coupEnnemi)) {
            System.out.println("ERREUR 5: Les cartes ne sont pas dans ta main");
            return false;
        }

        if (!triSaisie(tabCarteAsc, true) || !triSaisie(tabCarteDesc, false)){
            System.out.println("ERREUR 1: Input non trié");
            return false;
        }

        if (coupEnnemi != -1){
            if (!tabJoueur[(this.tour + 1) % 2 ].isCartePosable(coupEnnemi, CoupEnnemiAsc)){
                System.out.println("ERREUR 2: Coup adversaire non posable");
                return false;
            }
        }

        if (tabCarteAsc.size() != 0){
           // System.out.println("ERREUR3");
            if (!tabJoueur[adversaire].isCartePosable(tabCarteAsc.get(0), true)){
                System.out.println("ERREUR 3: Coup ascendant non posable");
                return false;
            }
        }
        if (tabCarteDesc.size() != 0){
           // System.out.println("ERREUR4");
            if (!tabJoueur[adversaire].isCartePosable(tabCarteDesc.get(0), false)){
                System.out.println("ERREUR 4: Coup descendant non posable");
                return false;
            }
        }

        return true;
    }

/*    private static boolean triSaisie(ArrayList<Integer> tab, boolean estAsc){
        boolean expr;
        for(int num : tab)
            System.out.println(num);
        for (int i = 0; i<tab.size(); i++){
            for (int j = i+1; j<tab.size(); j++){
                System.out.println("COMP : " + tab.get(i)+ " et " + tab.get(j));
                if(estAsc)
                    expr = tab.get(j) > tab.get(i);
                else
                    expr = tab.get(i) > tab.get(j);
                if (!expr)
                    return false;
            }
        }
        return true;
    }*/

    private static boolean triSaisie(ArrayList<Integer> tab, boolean estAsc){
        boolean expr;
        if(tab.size()==1)
            return true;
        /*for(int num : tab)
            System.out.println("TAB = " + num + "\n");*/
        for (int i = 0; i<tab.size() - 1; i++){
            int j = i+1;
//                System.out.println("COMP : " + tab.get(i)+ " et " + tab.get(j) + " estAsc = " + estAsc);
                if(estAsc)
                    expr = tab.get(j) > tab.get(i) || tab.get(j)+10 == tab.get(i);
                else
                    expr = tab.get(i) > tab.get(j) || tab.get(j)-10 == tab.get(i);
                if (!expr)
                    return false;
                j++;
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
        return mot.contains("^");
    }

    private static boolean checkFormatConditions(String[] tab){
        for (String mot : tab) {
           // System.out.println("MOT :" + mot);
            if (!(mot.matches("[0-9]?[0-9][v^]'?")))
                return false;
        }
        return true;
    }

    /*private static boolean checkCommenceParNb(String mot){
        return Character.isDigit(mot.charAt(0));
    }*/

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
