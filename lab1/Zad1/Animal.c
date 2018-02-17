//
//  Animal.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include "Animal.h"
#include <stdlib.h>
#include <stdio.h>

Animal* allocAnimal()
{
    Animal *animal = malloc(sizeof(Animal));
    return animal;
}

void constructAnimal(Animal *animal, char *name)
{
    animal->table = NULL;
    animal->name = name;
}

// Public Animal methods

void animalPrintGreeting(Animal *animal)
{
    printf("%s pozdravlja: %s\n", animal->name, animal->table[0]());
}

void animalPrintMenu(Animal *animal)
{
    printf("%s voli: %s\n", animal->name, animal->table[1]());
}
