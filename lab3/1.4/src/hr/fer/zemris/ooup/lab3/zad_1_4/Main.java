package hr.fer.zemris.ooup.lab3.zad_1_4;

import hr.fer.zemris.ooup.lab3.zad_1_4.model.Animal;

public class Main {

    public static void main(String[] args) {
        try {
            Animal parrot = AnimalFactory.newInstance("Parrot", "Modrobradi");
            Animal tiger = AnimalFactory.newInstance("Tiger", "Straško");

            parrot.animalPrintGreeting();
            tiger.animalPrintGreeting();

            parrot.animalPrintMenu();
            tiger.animalPrintMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
