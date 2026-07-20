package fr.campus.poo_java;

import java.util.Scanner;

public class Menu {
    protected Scanner sc = new Scanner(System.in);

    protected int checkInput(int start, int end, int input) {
        if (input >= start && input <=end)
            return input;
        else
            return -1;
    }

    public String requestInput() {
        return sc.nextLine();
    }

    public int chooseClass() {
        System.out.println("1) Guerrier, 2) Mage");
        int tmp = Integer.parseInt(requestInput());
        tmp = checkInput(1,2, tmp);
        if (tmp != -1)
            return tmp;
        chooseClass();
        return -1;
    }

    public String requestName()
    {
        System.out.println("Entrer votre nom");
        return requestInput();
    }

    public int requestNbPlayer() {
        System.out.println("1-2) Combien de joueurs ?");
        int tmp = Integer.parseInt(requestInput());
        if (checkInput(1, 2, tmp) != -1)
            return tmp;
        else {
            System.out.println("Erreur, 1-4");
            requestNbPlayer();
        }
        return -1;
    }
}
