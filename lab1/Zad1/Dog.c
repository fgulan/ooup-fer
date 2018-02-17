//
//  Dog.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include "Dog.h"

char const* dogGreet(void)
{
    return "vau!";
}

char const* dogMenu(void)
{
    return "kuhanu govedinu";
}

PTRFUN dogTable[2] = {dogGreet, dogMenu};

Animal* createDog(char *name)
{
    Animal *dog = allocAnimal();
    constructDog(dog, name);
    return dog;
}

void constructDog(Animal *dog, char *name)
{
    constructAnimal(dog, name);
    dog->table = dogTable;
}

Animal getStackDogWithName(char *name)
{
    Animal dog;
    dog.name = "Bobi";
    dog.table = dogTable;
    return dog;
}