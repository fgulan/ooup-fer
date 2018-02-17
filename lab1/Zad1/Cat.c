//
//  Cat.c
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#include "Cat.h"

char const* catGreet(void)
{
    return "mijau!";
}

char const* catMenu(void)
{
    return "konzerviranu tunjevinu";
}

PTRFUN catTable[2] = {catGreet, catMenu};

void constructCat(Animal *cat, char *name)
{
    constructAnimal(cat, name);
    cat->table = catTable;
}

Animal* createCat(char *name)
{
    Animal *cat = allocAnimal();
    constructCat(cat, name);
    return cat;
}