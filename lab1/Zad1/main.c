//
//  main.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>

#include "Animal.h"
#include "Dog.h"
#include "Cat.h"

void testAnimals(void)
{
    Animal *p1 = createDog("Hamlet");
    Animal *p2 = createCat("Ofelija");
    Animal *p3 = createDog("Polonije");
    
    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);
    
    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);
    
    free(p1);
    free(p2);
    free(p3);
}

void testAnimalsOnStack()
{
    Animal dog;
    //= getStackDogWithName("Bobi");
    constructDog(&dog, "Bobi");
    animalPrintGreeting(&dog);
    animalPrintMenu(&dog);
}

Animal* createDogs(int numberOfDogs)
{
    int i;
    Animal *list = malloc(sizeof(Animal) * numberOfDogs);
    for (i = 0; i < numberOfDogs; i++) {
        constructDog(&list[i], "Test");
    }
    return list;
}

int main(void)
{
    int i;
    int num = 4;
    testAnimals();
    testAnimalsOnStack();
    
    Animal *list = createDogs(num);
    for (i = 0; i < num; i++) {
        animalPrintGreeting(&list[i]);
    }
    free(list);
    return 0;
}