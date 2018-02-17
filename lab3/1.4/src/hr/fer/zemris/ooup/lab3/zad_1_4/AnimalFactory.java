package hr.fer.zemris.ooup.lab3.zad_1_4;

import hr.fer.zemris.ooup.lab3.zad_1_4.model.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Filip Gulan
 */
public class AnimalFactory {

    public static Animal newInstance(String animalKind, String name) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Animal> clazz;
        clazz = (Class<Animal>)Class.forName("hr.fer.zemris.ooup.lab3.zad_1_4.model.plugins." + animalKind);
        Constructor<?> constructor = clazz.getConstructor(String.class);
        return (Animal)constructor.newInstance(name);
    }
}
