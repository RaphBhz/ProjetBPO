package appli;

import jeu.Partie;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Partie game = new Partie();
        Scanner sc = new Scanner(System.in);
        String s;
        boolean erreur = false;
        System.out.println(game.affiche());
        System.out.print("> ");
        while (true) {

            if (!erreur){
                if (!game.continuer())
                    break;
            }
            s = sc.nextLine();

            erreur = game.traiterInput(s);
            if(!erreur){
                System.out.println(game.StringDeCartesAddedEtRemoved());
                System.out.println(game.affiche());
                System.out.print("> ");
            }
            else{
                System.out.print("#> ");
            }
        }
        System.out.println(game.gagnantString());
    }
}
