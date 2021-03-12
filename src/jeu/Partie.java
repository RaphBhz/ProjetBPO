package jeu;

import java.util.ArrayList;
import java.util.Scanner;

public class Partie {
    private Joueur[] tabJoueur = new Joueur[MAX_JOUEURS];
    private static final int MAX_JOUEURS = 2;
    private int tour = 0;


    public Partie(){
        for (int i = 0; i < MAX_JOUEURS; i++)
            tabJoueur[i] = new Joueur();
        Start();
    }

    private void Start(){
        Scanner sc = new Scanner(System.in);
        String s;
        String[] tab;
        int count = 0;
        System.out.println(affiche());
        System.out.print("> ");
        boolean erreur = false;

        // modifier la boucle
        // j'ai ajouté la méthode oneCarteInHandAndZeroInPioche() afin de déterminer si Joueur peut encore poser. Partie s'arrête dès qu'un joueur se trouve dans cette situation.
        while (true) { // On vérifie l'erreur en premier dans le ou car le test logique du ou ne va pas exécuter continuer si erreur est vérifiée donc on ne vérifie la jouabilité du jouer d'après que si la saisie est correcte (c'est pas mal en vrai)

            if (!erreur){
                if (!this.continuer())
                    break;
            }
            s = sc.nextLine();

            count = s.length() - s.replace("'", "").length();
            if (!s.equals("") && count<=1) {
                tab = decompose(s);
                if (checkFormatConditions(tab) && tab.length >= 2) {
                    if (traiterInputBonFormat(tab)) {
                        System.out.println(tabJoueur[(tour+1)%2].toStringNbCartesAddedAndRemoved());
                        System.out.println(affiche());
                        System.out.print("> ");
                        erreur = false;
                    }
                    else {
                        System.out.println("ERREUR 6: JoueUnCoup == false");
                        System.out.print("#> ");
                        erreur = true;
                    }
                }
                else {
                    System.out.println("ERREUR 7: checkFormatConditions(tab) && tab.length >= 2 car " + tab.length);
                    System.out.print("#> ");
                    erreur = true;
                }
            }
            else {
                System.out.println("ERREUR 8: !s.equals(\"\") && count<=1");
                System.out.print("#> ");
                erreur = true;
            }

        }

        System.out.println(gagnantString());
    }


    private String gagnantString(){
        StringBuilder s = new StringBuilder();
        s.append("partie finie, ");
        if ((tour+1)%2 == 0)
            s.append("NORD a gagné");
        else
            s.append("SUD a gagné");
        return s.toString();
    }

    private int getCartes(String[] tab, ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc){
        int carteSurEnnemi = -1;
        for (int i = 0; i<tab.length; i++) {

            if (jouerSurAdversaire(tab[i])){
                //System.out.println("TEST ENNEMI 2");
                carteSurEnnemi = i;
                continue;
            }

            if (isMotAsc(tab[i])) {
                //System.out.println("TEST CONTINUE " + getCarteValue(tab[i]));
                tabCarteAsc.add(getCarteValue(tab[i]));
            }
            else {
                //System.out.println("TEST CONTINUE " + getCarteValue(tab[i]));
                tabCarteDesc.add(getCarteValue(tab[i]));
            }
        }
        return carteSurEnnemi;
    }


    private boolean traiterInputBonFormat(String[] tab) {
        ArrayList<Integer> tabCarteAsc = new ArrayList<>(), tabCarteDesc = new ArrayList<>();
        int carteSurEnnemi = getCartes(tab, tabCarteAsc, tabCarteDesc);
        boolean coupEnnemiAsc = false;

        if(carteSurEnnemi != -1){
            coupEnnemiAsc = isMotAsc(tab[carteSurEnnemi]);
            carteSurEnnemi = getCarteValue(tab[carteSurEnnemi]);
        }

        if (IsSaisieJouable(tabCarteAsc,tabCarteDesc, carteSurEnnemi, coupEnnemiAsc)){
            // ajouterCarte
            this.jouerLesCartes(carteSurEnnemi, coupEnnemiAsc, tabCarteAsc, tabCarteDesc);
            this.tour++;
            return true;
        }
        else
           //  System.out.println("Saisie non jouable");
            return false;
    }

    private void jouerLesCartes(int carteSurEnnemi, boolean coupEnnemiAsc, ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc){
        if(carteSurEnnemi != -1){
            //System.out.println("TEST ENNEMI");
            tabJoueur[(this.tour + 1) % 2 ].ajouterCarteBase(carteSurEnnemi, coupEnnemiAsc);
        }
        if(!tabCarteAsc.isEmpty())
            tabJoueur[this.tour % 2].ajouterCarteBase(tabCarteAsc.get(tabCarteAsc.size() - 1), true);
        if(!tabCarteDesc.isEmpty())
            tabJoueur[this.tour % 2].ajouterCarteBase(tabCarteDesc.get(tabCarteDesc.size() - 1), false);

        tabJoueur[tour%2].removeCartesAndAddCartes(tabCarteDesc, tabCarteAsc, carteSurEnnemi);
    }

    private boolean IsSaisieJouable(ArrayList<Integer> tabCarteAsc, ArrayList<Integer> tabCarteDesc, int coupEnnemi, boolean CoupEnnemiAsc){
        int adversaire = tour%2;

        if (!tabJoueur[adversaire].areCartesInMain(tabCarteAsc, tabCarteDesc, coupEnnemi)) {
            // System.out.println("ERREUR 5: Les cartes ne sont pas dans ta main");
            return false;
        }

        if (!triSaisie(tabCarteAsc, true) || !triSaisie(tabCarteDesc, false)){
            // System.out.println("ERREUR 1: Input non trié");
            return false;
        }

        if (coupEnnemi != -1){
            if (!tabJoueur[(this.tour + 1) % 2 ].isCartePosableEnnemi(coupEnnemi, CoupEnnemiAsc)){
                // System.out.println("ERREUR 2: Coup adversaire non posable");
                return false;
            }
        }

        if (tabCarteAsc.size() != 0){
           // System.out.println("ERREUR3");
            if (!tabJoueur[adversaire].isCartePosable(tabCarteAsc.get(0), true)){
                // System.out.println("ERREUR 3: Coup ascendant non posable");
                return false;
            }
        }
        if (tabCarteDesc.size() != 0){
           // System.out.println("ERREUR4");
            if (!tabJoueur[adversaire].isCartePosable(tabCarteDesc.get(0), false)){
                // System.out.println("ERREUR 4: Coup descendant non posable");
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
        //System.out.println("PTITE STRING AVANT " + tour%2);
        return tabJoueur[tour%2].peutJouer(tabJoueur[(tour + 1)%2].getTopPileAsc(), tabJoueur[(tour + 1)%2].getTopPileDesc());
    }



    public String affiche(){
        StringBuilder s = new StringBuilder();
        s.append("NORD ");
        s.append(tabJoueur[0].toString());
        s.append("\n");
        s.append("SUD  ");
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
