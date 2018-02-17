//
//  Animal.h
//  OOuP - Lab 1
//
//  Created by Filip Gulan on 30/03/16.
//  Copyright © 2016 FIlip Gulan. All rights reserved.
//

#ifndef Animal_h
#define Animal_h

typedef char const* (*PTRFUN)();

typedef struct {
    PTRFUN *table;
    char *name;
} Animal;

Animal* allocAnimal();

// Constructor
void constructAnimal(Animal *animal, char *name);

// Public Animal methods
void animalPrintGreeting(Animal *animal);
void animalPrintMenu(Animal *animal);

#endif /* Animal_h */
