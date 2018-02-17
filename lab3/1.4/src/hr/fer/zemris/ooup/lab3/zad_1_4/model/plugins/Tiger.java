package hr.fer.zemris.ooup.lab3.zad_1_4.model.plugins;

import hr.fer.zemris.ooup.lab3.zad_1_4.model.Animal;

/**
 * @author Filip Gulan
 */
public class Tiger extends Animal {

    private String name;

    public Tiger(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String greet() {
        return "Mijau!";
    }

    @Override
    public String menu() {
        return "mlako mlijeko";
    }
}
